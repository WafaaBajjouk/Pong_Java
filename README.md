
# Pong Game README - BAJJOUK WAFAA

Welcome to the Pong game! This README provides an overview of the game's key components, features, and design patterns.

## Table of Contents
- [Game Classes](#game-classes)
- [Difficulty Levels](#difficulty-levels)
- [Multiplayer Mode](#multiplayer-mode)
- [Play with Computer Mode - Play with AI](#play-with-computer-mode---play-with-ai)
- [Collision Handling and Ball Behavior](#collision-handling-and-ball-behavior)
- [Design Patterns](#design-patterns)
- [Database](#database)
- [Graphics](#graphics)
- [Future Features](#future-features)

---

## Game Classes


- **Ball**: Responsible for tracking the game ball's position, speed, and collision handling.
- **Paddle**: Represents the player-controlled paddles, allowing for movement and interaction with the ball.
- **Player**: Stores player details such as their name, birthday, and password.
- **Match**: Records details about each game match, including scores, dates, and player IDs.

## Difficulty Levels

The game offers three distinct difficulty levels. These levels are configured using the `DifficultyLevel` enum from the `config` package:

- **Slow**: A slow-moving ball and easy-to-control paddles.
- **Medium**: Moderate ball and paddle speeds.
- **Fast**: A quickly moving ball and highly responsive paddles.

## Multiplayer Mode

Multiplayer Mode allows two users to play the game on the same laptop.
Player one controls their paddle using the "Up" and "Down" arrow keys,
while player two uses the "W" and "S" keys. Both players can choose the game's difficulty level.

### Gameplay

- Each player has their own score displayed on the screen.
- To pause the game, press the "P" key. To resume, press "P" again.
- To exit the game, press the "Q" key. To return to the menu, press "R."

### Challenge

The game adds an extra challenge by increasing the ball's speed by 10% after 2 minutes of gameplay.

## Play with Computer Mode - Play with AI

In the "Play with Computer" mode, you can challenge yourself against an AI-controlled opponent.
The computer-controlled paddle tracks the ball's vertical movement and attempts to intercept it, adding an extra layer of challenge for players in Single Player Mode.

## Collision Handling and Ball Behavior

Collision handling determines the ball's behavior during gameplay:

1. **Paddle Collisions**: When the ball intersects with either player's paddle, it results in the reversal of the ball's X-direction, changing its direction based on which paddle it collided with.

2. **Top and Bottom Wall Collisions**: If the ball reaches the top or bottom boundaries of the game area, it reverses its Y-direction upon collision, preventing the ball from moving out of the game area.

3. **Edge Collisions**: When the ball goes beyond the left or right edge of the game area, it triggers a scoring event, updating the corresponding player's score.

## Design Patterns

### Observer Design Pattern

The Pong game's `Game` class employs the Observer design pattern to enhance its functionality.
This pattern enables efficient communication between various game components.

### Singleton Pattern

The Pong game implements the Singleton pattern in crucial classes to ensure that only one instance of these classes exists throughout the game's execution.
This design pattern guarantees global access to these instances.
For example, the `DbConnection` class, responsible for managing the database connection, and the `ScoreManager` class.

## Database

The game features a database system (MYSQL) that manages player login, account creation, password changes, and profile viewing.

- **Login**: Players can log in to their accounts to access the game and their profiles.

- **Creating an Account**: New players can create accounts to start playing the game and save their progress.

- **Changing Password**: Players have the option to change their account passwords.

- **Profile Viewing**: Upon login, players can view their game history and personal data in their profiles.

## Graphics

The game's graphical user interface is developed using Java Swing,

<img width="886" alt="Screenshot 2024-01-16 at 12 01 14" src="https://github.com/WaffIee/Pong_Java/assets/76793719/541bfc9e-1959-4ab4-83e7-7eddfd27248f">


<img width="893" alt="Screenshot 2024-01-16 at 12 01 57" src="https://github.com/WaffIee/Pong_Java/assets/76793719/dbd21368-15e7-4f11-b1a4-8153e3c4e6fe">

---

## Future Features JAVA SOCKETS

IAM WORKING on implementing a socket-based multiplayer feature that will allow players to enjoy the game from different locations.

JAR FILE = in build/libs

---
