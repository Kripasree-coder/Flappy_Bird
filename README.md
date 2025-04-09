```markdown
# 🐦 Flappy Bird Game in Java

A clone of the classic **Flappy Bird** game developed in **Java Swing**. This project simulates the addictive gameplay of navigating a bird through pairs of pipes using real-time keyboard input and graphics rendering.

## 🎮 Features

- Bird physics with gravity and jump mechanics
- Procedurally generated pipes with random gaps
- Real-time scoring system
- Game Over and Restart functionality
- Custom background and character assets

## 🧑‍💻 How to Run

### Prerequisites

- Java JDK 8 or higher
- A Java IDE (e.g., IntelliJ IDEA, Eclipse, or NetBeans)

### Steps

1. Clone or download the project.
2. Ensure that all image files are placed in the `/resources/` folder and are correctly referenced in the code via `getResource("/filename.png")`.
3. Compile and run `App.java`.

```bash
javac App.java FlappyBird.java
java App
```

> **Note:** Ensure resources are accessible at runtime (i.e., correctly added to classpath or inside a `resources/` folder if you're using an IDE).

## 🕹️ Controls

- `Spacebar` – Jump / Start game / Restart after Game Over
- `Up Arrow` – Move the bird upwards
- `Down Arrow` – Move the bird downwards

## 📌 Game Logic Summary

- The bird is affected by gravity and gains upward velocity when the spacebar or up arrow is pressed.
- Pipes are spawned every 1.5 seconds with a vertical gap, and they scroll to the left.
- If the bird collides with a pipe or falls off-screen, the game ends.
- Each successfully passed pipe increases the score by 0.5 (two pipes = 1 point).

## 💡 Ideas for Enhancement

- Add background music and sound effects
- Implement high score tracking
- Use smoother animations or JavaFX
- Mobile/Touch controls for Android (using JavaFXPorts or LibGDX)

## 🧑‍🎓 Author

- KRIPASREE M

## 📄 License

This project is licensed under the MIT License – see the [LICENSE](LICENSE) file for details.
```

---
