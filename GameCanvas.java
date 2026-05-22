/**
	This class is a canvas that contains the gameplay.
    It is equipped with methods and variables to facilitate its gameplay logic.
	
	@author Kelvin M. Cai (231181)
	@author Schonn Michael L. Serrano (235771)
	@version 16 May 2024
	
	I have not discussed the Java language code in my program 
	with anyone other than my instructor or the teaching assistants 
	assigned to this course.

	I have not used Java language code obtained from another student, 
	or any other unauthorized source, either modified or unmodified.

	If any Java language code or documentation used in my program 
	was obtained from another source, such as a textbook or website, 
	that has been clearly noted with a proper citation in the comments 
	of my program.
**/

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GameCanvas extends JComponent {
    // NETWORKING
    private int seed;
    private Socket socket;
    private int playerID;
    private ReadFromServer rfs;
    private WriteToServer wts;

    /** Connects the player/client to the server */
    private void connectToServer() {
        try {
            System.out.println("IP Address: ");
            Scanner scanner = new Scanner(System.in);
            String ipAddress = scanner.nextLine();
            socket = new Socket(ipAddress, 8898);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            playerID = in.readInt();
            seed = in.readInt();
            System.out.println("You are Player #" + playerID);
            if (playerID == 1) System.out.println("Waiting for Player #2...");
            rfs = new ReadFromServer(in);
            wts = new WriteToServer(out);
            rfs.waitForStartMsg();
        } catch (IOException e) {
            System.out.println("IOException in connectToServer()");
        }
    }

    /** This inner class implements Runnable and used to read from server side */
    private class ReadFromServer implements Runnable {
        private DataInputStream in;

        /** Constructs ReadFromServer
         * @param in Data input stream
         */
        public ReadFromServer(DataInputStream in) {
            this.in = in;
        }

        /** For running ReadFromServer */
        public void run() {
            try {
                while (true) {
                    if (playerID == 2) {
                        try {
                            Thread.sleep(5);
                        }
                        catch (InterruptedException e) {}
                    }
                    if (playerID == 2 && player != null || playerID == 1 && pen != null) {
                        if (playerID == 1) {
                            pen.setX(in.readDouble());
                            pen.setY(in.readDouble());
                            pen.setInkAmount(in.readInt());
                            pen.setPenDown(in.readBoolean());
                        } else {
                            player.setX(in.readDouble());
                            player.setY(in.readDouble());
                            player.setLeftMotion(in.readBoolean());
                            player.setRightMotion(in.readBoolean());
                        }
                    }
                }
            } catch (IOException e) {
                System.out.println("IOException from RFS run()");
            }
        }

        /** Waits and prints message of server side */
        public void waitForStartMsg() {
            try {
                String start = in.readUTF();
                System.out.println("Server: " + start);
                Thread readThread = new Thread(rfs);
                Thread writeThread = new Thread(wts);
                readThread.start();
                writeThread.start();
            } catch (IOException e) {
                System.out.println("IOException from RFS waitForStartMsg()");
            }
        }
    }

    /** This inner class implements Runnable and used to write to server side */
    private class WriteToServer implements Runnable {
        private DataOutputStream out;

        /** Constructs WriteToServer
         * @param out Data output stream
         */
        public WriteToServer(DataOutputStream out) {
            this.out = out;
        }

        /** For running WriteToServer */
        public void run() {
            try {
                while (true) {
                    if (playerID == 1 && player != null || playerID == 2 && pen != null) {
                        if (playerID == 1) {
                            out.writeDouble(player.getX());
                            out.writeDouble(player.getY());
                            out.writeBoolean(player.isMovingLeft());
                            out.writeBoolean(player.isMovingRight());
                        } else {
                            out.writeDouble(pen.getX());
                            out.writeDouble(pen.getY());
                            out.writeInt(pen.getInkAmount());
                            out.writeBoolean(pen.getPenDown());
                        }
                        out.flush();
                    }

                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        System.out.println("InterruptedException from WTS run()");
                    }
                }

            } catch (IOException e) {
                System.out.println("IO Exception at WTS run()");
            }
        }
    }

    // SPRITES
    private Background bg;
    private Borders borders;
    private Pen pen;
    private Player player;
    private ScribbleBoard sb;
    private ArrayList<Sprite> enemies;
    private final int TREASURES_NUM = 7;
    private ArrayList<Treasure> treasures;

    // TIMER FIELDS
    private final int TIME_LIMIT = 30000; // In milliseconds
    private final int DELAY = 10;
    private Timer timer;
    private int timeLeft;
    private JLabel timerText;

    // LISTENERS AND ACTIONS
    private ActionListener al;
    private MouseMotionListener mml;
    private Action leftAction, leftRelease, rightAction, rightRelease, jumpAction;

    private Random random;

    // SOUNDS
    Sound collectFX, hitFX;
    /** 
     * Constructs all of the GameCanvas's sprites and game variables,
     * in addition to connecting the canvas to the server.
     */
    public GameCanvas() {
        connectToServer();

        random = new Random(seed);

        // SPRITES
        formSprites();
        formTimerText();
        formActionListener();

        if (playerID == 1) formKeyBindings();
        if (playerID == 2) formMouseMotionListener();

        collectFX = new Sound("collect.wav", -10);
        hitFX = new Sound("hit.wav", -5);

        timer = new Timer(DELAY, al);   
        timer.start();
    }

    /** Initializes all sprites */
    private void formSprites() {
        bg = new Background(0, 0);
        borders = new Borders(15);
        pen = new Pen(0, GameFrame.HEIGHT, 25, 150, new Color(75, 0, 125));
        player = new Player(50, GameFrame.HEIGHT-100, 0.2);
        sb = new ScribbleBoard(pen.getInkColor(), 2);
        enemies = new ArrayList<Sprite>();
        addEnemies();
        treasures = new ArrayList<Treasure>();
        for (int i=0; i < TREASURES_NUM; i++)
            treasures.add(new Treasure(random.nextInt(100000)));
    }

    /** Adds a random number of enemies onto the canvas's list of enemies, based on a seed */
    private void addEnemies() {
        // Sawblades
        for (int i = 0; i < random.nextInt(3, 6); i++)
            enemies.add(new Sawblade(random.nextInt(100000)));

        // UFO
        for (int i = 0; i < random.nextInt(2, 5); i++)
            enemies.add(new Ufo(random.nextInt(100000)));
        
        // Turrets
        for (int i = 0; i < random.nextInt(2, 5); i++) {
            if (random.nextBoolean()) enemies.add(new Pistol(random.nextInt(100000)));
            else enemies.add(new Sniper(random.nextInt(100000)));
        }
    }

    /** Forms the text for the GameCanvas's timer */
    private void formTimerText() {
        // Timer Text
        timeLeft = TIME_LIMIT;
        timerText = new JLabel();
        timerText.setForeground(Color.WHITE);
        timerText.setFont(new Font("Arial", Font.BOLD, 10));
        timerText.setHorizontalAlignment(SwingConstants.CENTER);
        updateTimerText();
        
        setLayout(new BorderLayout());
        add(timerText, BorderLayout.SOUTH);
    }

    /** Updates the text of the timer text for it to count down*/
    private void updateTimerText() {
        timerText.setText(String.format("%.2f", timeLeft/1000.0));
    }

    /** Updates the text of the timer text for it to count down
     * @param wonGame determines if player wins or loses the game
    */
    private void updateTimerText(boolean wonGame) {
        if (wonGame) timerText.setText("You Win!!!");
        else timerText.setText("You Lose...");
    }

    /** Forms the action listener for the game */
    private void formActionListener() {
        al = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                // Game Loop
                if (timeLeft > 0 && !playerHitEnemy() && treasures.size() > 0) {
                    // Scribbles
                    if (pen.hasInk() && pen.getPenDown()) {
                        sb.scribble(pen);
                        sb.fillStroke(pen);
                        pen.useInk();
                    }

                    // Sprites
                    doEnemyBehavior();
                    for (Treasure treasure : treasures) treasure.move();
                    if (playerID == 1) player.move(borders.getCollisionBoxes(), sb.getCollisionBoxes());
                    else player.formCollisionBoxSet();

                    playerTreasureCollision();

                    // Time
                    timerText.setText(String.format("%.2f", timeLeft/1000.0));
                    timeLeft -= DELAY;
                    repaint();                    
                } else {
                    timer.stop();
                    updateTimerText(treasures.size() == 0);
                }
            }
        };
    }

    /** Forms the mouse motion listener for the pen */
    private void formMouseMotionListener() {
        mml = new MouseMotionListener() {
            @Override
            public void mouseMoved(MouseEvent me) {
                pen.setX(me.getX());
                pen.setY(me.getY());
                pen.setPenDown(false);
            }
            
            @Override
            public void mouseDragged(MouseEvent me) {
                pen.setX(me.getX());
                pen.setY(me.getY());
                pen.setPenDown(true);
            }
        };
        addMouseMotionListener(mml);
    }

    /** Forms all of the key bindings for the player */
    private void formKeyBindings() {
        // PLAYER'S LEFT MOVEMENT
        leftAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.setLeftMotion(true);
            }
        };
        leftRelease = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.setLeftMotion(false);
            }
        };
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, false), "left");
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_A, 0, true), "leftRelease");
        getActionMap().put("left", leftAction);
        getActionMap().put("leftRelease", leftRelease);

        // PLAYER'S RIGHT MOVEMENT
        rightAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.setRightMotion(true);
            }
        };
        rightRelease = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.setRightMotion(false);
            }
        };
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, false), "right");
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_D, 0, true), "rightRelease");
        getActionMap().put("right", rightAction);
        getActionMap().put("rightRelease", rightRelease);

        // PLAYER'S JUMP MOVEMENT
        jumpAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                player.jump();
            }
        };
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_W, 0, false), "jump");
        getActionMap().put("jump", jumpAction);
    }

    /** Draws all of the sprites onto the canvas
     * @param g The canvas's Graphic object
     */
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHints(new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON));

        bg.draw(g2d);
        player.draw(g2d);
        for (Treasure treasure : treasures) treasure.draw(g2d);
        for (Sprite enemy : enemies) enemy.draw(g2d);
        sb.draw(g2d);
        borders.draw(g2d);
        pen.draw(g2d);
    }

    // GAMEPLAY METHODS
    /** Detects if the player and a treasure are colliding, then performs the appropriate responses to both */
    private void playerTreasureCollision() {
        for (Treasure treasure : treasures) {
            if (treasure.setCollided(player.getCollisionBoxes())) {
                treasures.remove(treasure);
                collectFX.play();
                break;
            } 
        }
    }

    /** Performs all the behaviors for each of the canvas's list of enemies */
    private void doEnemyBehavior() {
        ArrayList<Sprite> newEnemies = new ArrayList<Sprite>();
        ArrayList<Sprite> delEnemies = new ArrayList<Sprite>();

        for (Sprite enemy : enemies) {
            // Sawblade
            if (enemy instanceof Sawblade) {
                ((Sawblade) enemy).move();
            } 

            // Bullet
            if (enemy instanceof Bullet) {
                ((Bullet) enemy).move();
                if (enemy.getCollisionBoxes()[0].colliding(borders.getCollisionBoxes())) delEnemies.add(enemy);
            }

            // Turret
            if (enemy instanceof Turret) {
                Bullet bullet = null;
                ((Turret) enemy).aim(player.getCollisionBoxes());
                if (timeLeft <= TIME_LIMIT - 2000) bullet = ((Turret) enemy).fire();
                if (bullet != null) newEnemies.add(bullet);
            }

            // UFO
            if (enemy instanceof Ufo) {
                ((Ufo) enemy).move(borders.getCollisionBoxes());
            }
        }

        enemies.addAll(0, newEnemies);
        enemies.removeAll(delEnemies);
    }

    /** Checks if the player has hit an enemy */
    private boolean playerHitEnemy() {
        for (Sprite enemy : enemies) {
            if (enemy.setCollided(player.getCollisionBoxes())) {
                hitFX.play();
                return true;
            }
        }
        return false;
    }
}