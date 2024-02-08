# Pong Game README - BAJJOUK WAFAA

Welcome to the  Pong game project! This document provides a comprehensive overview of the game's architecture, features, and recent improvements aimed at enhancing gameplay, design patterns application, and development practices.

Main file of the game to run it : src/main/java/com/pongame/graphics/PongGame.java

## Table of Contents
- [Game Classes](#game-classes)
- [Difficulty Levels](#difficulty-levels)
- [Gameplay Modes](#gameplay-modes)
- [Collision Handling and Ball Behavior](#collision-handling-and-ball-behavior)
- [Design Patterns and Architectural Improvements](#design-patterns-and-architectural-improvements)
- [Database Integration](#database-integration)
- [Graphics](#graphics)
- [Development Notes](#development-notes)

---

## Game Classes
- **Ball**: Tracks the game ball's dynamics and collisions.
- **Paddle**: Controls for player and AI paddles.
- **Player**: Manages player data.
- **Match**: Records match outcomes and statistics.

## Difficulty Levels
- **Slow**, **Medium**, **Fast**: Varied gameplay speeds.

## Gameplay Modes
- **Multiplayer Mode**: Two-player gameplay on a single device.
- **Play with Computer Mode**: Single-player gameplay against AI.

## Collision Handling and Ball Behavior
- **Paddle Collisions**: Ball direction reversal on paddle hit.
- **Wall Collisions**: Vertical direction change on boundary hit.
- **Edge Collisions**: Score update on crossing game boundaries.

## Design Patterns and Architectural Improvements
- **Singleton Pattern**: For unique instances of critical classes.
- **Dependency Inversion**: Through `IPlayerDAO` and `IGameDAO` for decoupling.

## Database Integration
- **H2 In-Memory Database**: Switched to H2 for ease of setup in different locales and isolated testing.

## Graphics
- Utilizes Java Swing for UI elements.

## Development Notes
- **Database Transition**: Switched to H2 for ease of setup in different locales and isolated testing.
- **Liskov Substitution Principle (LSP)**: Ensured `PlayWithAI` can replace `Game` without altering expected behavior.
- **Simplified Collision Handling**: Integrated directly into `Ball` and `Paddle` classes.
- **Test Isolation**: Achieved in `PlayerDAOTest` using Mockito for mock dependencies.
- **DAO Decoupling**: Refined `LoginForm` and game logic to rely on interfaces, enhancing modularity.

**Executable**: The JAR file located in `build/libs` facilitates easy game distribution and launching.

---

## Running the Game Locally

**no manual database setup is required**. The H2 database is automatically initialized when the game starts

---

