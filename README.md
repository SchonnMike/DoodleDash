# Doodle Dash

Platformer cooperative game, with one controlling the character and the other one controlling a pen to collect treasures through obstacles, created using Java. 

## Requirements

- Java

## Setup

### 1. Clone repository
```bash
git clone https://github.com/SchonnMike/DoodleDash.git
cd DoodleDash
```

### 2. Compile files
```bash
javac *.java
```

### 3. Run server program

```bash
java GameServer
```

### 4. Run client program on two separate terminals for Player 1 and 2

```bash
java GameStarter
```
- Input the same IP Address for both clients (e.g., 127.0.0.1)

## Manual
Game Title: Doodle Dash
Authors: Kelvin Cai, Schonn Michael Serrano

This asymmetrical platformer cooperative game utilizes a teamwork between two
players, with one controlling the character and the other one controlling a pen. The
character must move against obstacles to collect all treasures within the time limit with
the aid of the pen guiding and drawing paths to create platforms for the character to
move.

Mechanics:
One player controls a character and the other controls a pen. Within 30 seconds, the
character must grab all seven treasures
scattered across the screen while evading enemies that are out to get it. The pen must
aid the character by drawing platforms,
but it must do so carefully because it has a limited amount of ink. Note that the scribbles
do not protect the player from
the enemies. Additionally, if the character gets stuck, it has the ability to remove all of
the pen's drawings, but it may only
do so 2 times. These game mechanics are customizable for an easier or harder
experience.

These are the enemies in place:
1. UFO
- The UFO randomly moves left and right, but it also stops from time to time.
2. Pistol
- The pistol is a gray turret that occasionally fires bullets of moderate speed.
3. Sniper
- The sniper is a brown turret. fires less frequently than the pistol, but its bullets are
larger and faster.
4. Sawblade
- The sawblade is fixed to its position and only spins around.
- Its outer blades will not hurt the player, but its inner center will.

Instructions:
1. Player
- Move the player around with the A and D keys
- Jump with the W key
2. Pen
- Use the mouse to guide the pen
- Click and drag the mouse to draw; the pen will not draw unless you drag the mouse
