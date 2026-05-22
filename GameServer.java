/**
	This class contains the code that manages the game server's functionality.
    It also contains the main method that instantiates and starts the server.
	
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

import java.net.*;
import java.io.*;
import java.util.Random;

public class GameServer {
    private int seed = new Random().nextInt(1000000);
    private ServerSocket ss;
    private Socket socketPlayer, socketPen;
    private int numPlayers, maxPlayers;
    
    private ReadFromClient rfcPlayer, rfcPen;
    private WriteToClient wtcPlayer, wtcPen;

    private double playerX, playerY;
    private boolean playerMovingLeft, playerMovingRight;

    private double penX, penY;
    private int penInkAmount;
    private boolean penDown;

    /** Constructs the game server */
    public GameServer() {
        numPlayers = 0;
        maxPlayers = 2;

        playerX = 50;
        playerY = GameFrame.HEIGHT-100;
        playerMovingLeft = false;
        playerMovingRight = false;

        penX = 0;
        penY = GameFrame.HEIGHT;
        penInkAmount = 1500;
        penDown = false;

        try {
            ss = new ServerSocket(8898);
        } catch (IOException e) {
            System.out.println("IOException from Server constructor");
        }
    }

    /** Checks the connections by accepting two players */
    public void acceptConnections() {
        try {
            System.out.println("Waiting for connections...");
            while(numPlayers < maxPlayers) {
                numPlayers++;

                Socket s = ss.accept();
                DataInputStream in = new DataInputStream(s.getInputStream());
                DataOutputStream out = new DataOutputStream(s.getOutputStream());
                
                out.writeInt(numPlayers);
                out.writeInt(seed);
                out.flush();
                System.out.println("Player #" + numPlayers + " has connected.");

                ReadFromClient rfc = new ReadFromClient(numPlayers, in);
                WriteToClient wtc = new WriteToClient(numPlayers, out);

                if (numPlayers == 1) {
                    socketPlayer = s;
                    rfcPlayer = rfc;
                    wtcPlayer = wtc;
                } else {
                    socketPen = s;
                    rfcPen = rfc;
                    wtcPen = wtc;
                    wtcPlayer.sendStartMsg();
                    wtcPen.sendStartMsg();
                    
                    Thread readThread1 = new Thread(rfcPlayer);
                    Thread writeThread1 = new Thread(wtcPlayer);
                    readThread1.start();
                    writeThread1.start();

                    Thread readThread2 = new Thread(rfcPen);
                    Thread writeThread2 = new Thread(wtcPen);
                    readThread2.start();
                    writeThread2.start();
                }
            }

            System.out.println("The maximum number of players have connected.");
        } catch (IOException e) {
            System.out.println("IOException from acceptConnections()");
        }
    }

    /** This inner class implements Runnable and used to read from client side */
    private class ReadFromClient implements Runnable {
        private int playerID;
        private DataInputStream in;

        /** Constructs ReadFromClient
         * @param playerID an integer to determine which player
         * @param in Data input stream
         */
        public ReadFromClient(int playerID, DataInputStream in) {
            this.playerID = playerID;
            this.in = in;
        }

        /** For running ReadFromClient */
        public void run() {
            try {
                while (true) {
                    if (playerID == 1) {
                        playerX = in.readDouble();
                        playerY = in.readDouble();
                        playerMovingLeft = in.readBoolean();
                        playerMovingRight = in.readBoolean();
                    } else {
                        penX = in.readDouble();
                        penY = in.readDouble();
                        penInkAmount = in.readInt();
                        penDown = in.readBoolean();
                    }
                }
            } catch (IOException e) {
                System.out.println("IOException from RFC run()");
            }
        } 
    }

    /** This inner class implements Runnable and used to write to client side */
    private class WriteToClient implements Runnable {
        private int playerID;
        private DataOutputStream out;

        /** Constructs WriteToClient
         * @param playerID an integer to determine which player
         * @param out Data output stream
         */
        public WriteToClient(int playerID, DataOutputStream out) {
            this.playerID = playerID;
            this.out = out;
        }

        /** For running WriteToClient */
        public void run() {
            try {
                while (true) {
                    if (playerID == 1) {
                        out.writeDouble(penX);
                        out.writeDouble(penY);
                        out.writeInt(penInkAmount);
                        out.writeBoolean(penDown);
                        out.flush();
                    } else {
                        out.writeDouble(playerX);
                        out.writeDouble(playerY);
                        out.writeBoolean(playerMovingLeft);
                        out.writeBoolean(playerMovingRight);
                        out.flush();
                    }
                    
                    try {
                        Thread.sleep(5);
                    } catch (InterruptedException e) {
                        System.out.println("InterruptedException from WTC run()");
                    }
                }
            } catch (IOException e) {
                System.out.println("IOException from WTC run()");
            }
        }

        /** Sends message when maximum players achieved. */
        public void sendStartMsg() {
            try {
                out.writeUTF("2 players connected. The game will start...");
            } catch(IOException e) {
                System.out.println("IOException from sendStartMsg()");
            }
        }
    }

    /** Main method for server */
    public static void main(String[] args) {
        GameServer gs = new GameServer();
        gs.acceptConnections();
    }
}