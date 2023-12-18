# Pong Game Project Structure

## Classes

### `Ball` Class
- Represents the game ball with position, speed, and methods for movement and collision detection.
- Uses a constant color (red) for drawing.

### `Paddle` Class
- Represents the paddles for player 1 and player 2.
- Has methods for drawing, movement, and collision detection.

### `DifficultyLevel` Enum
- Enumerates difficulty levels with associated speed values.

### `Game` Class
- Implements the game logic, including the ball, paddles, collision handling, and scoring.
- Follows the **Singleton** pattern to ensure a single instance.
- Utilizes the **Observer** pattern to notify observers of state changes.

### `ScoreManager` Class
- Manages player scores with methods for scoring, retrieving scores, and resetting scores.
- Follows the **Singleton** pattern.

### `PongGameGraphic` Class
- Extends `JFrame` and represents the main GUI window for the Pong game.
- Initializes the UI and sets up the game panel.


## Project Structure

- **com.pongame.classes:** Contains classes related to game entities (Ball, Paddle).
- **com.pongame.config:** Contains the `DifficultyLevel` enum.
- **com.pongame.game:** Contains the main game logic and GUI classes (Game, PongGameGraphic, GamePanel).
- **com.pongame.patterns:** Contains classes related to design patterns.
    - **Observer:** The `Observer` interface for implementing the Observer pattern.
- **com.pongame.utils:** Contains constants and utility classes.
