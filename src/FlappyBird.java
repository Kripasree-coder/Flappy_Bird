import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class FlappyBird extends JPanel implements ActionListener, KeyListener {
    int boardWidth = 360;
    int boardHeight = 640;

    // Images
    Image backgroundImg;
    Image birdImg;
    Image topPipeImg;
    Image bottomPipeImg;

    // Bird properties
    int birdX = boardWidth / 8;
    int birdY = boardHeight / 2;
    int birdWidth = 34;
    int birdHeight = 24;
    int velocityY = 0; // Vertical velocity
    int gravity = 1;   // Gravity effect

    // Bird class
    class Bird {
        int x = birdX;
        int y = birdY;
        int width = birdWidth;
        int height = birdHeight;
        Image img;

        Bird(Image img) {
            this.img = img;
        }
    }

    // Pipe properties
    int pipeWidth = 64;
    int pipeHeight = 512;
    int pipeSpeed = -4; // Movement speed of pipes

    // Pipe class
    class Pipe {
        int x;
        int y;
        int width = pipeWidth;
        int height = pipeHeight;
        Image img;
        boolean passed = false;

        Pipe(int x, int y, Image img) {
            this.x = x;
            this.y = y;
            this.img = img;
        }
    }

    // Game variables
    Bird bird;
    ArrayList<Pipe> pipes;
    Timer gameLoop;
    Timer placePipeTimer;
    boolean gameOver = false;
    boolean gameStarted = false;
    double score = 0;

    Random random = new Random();

    // Constructor
    FlappyBird() {
        setPreferredSize(new Dimension(boardWidth, boardHeight));
        setFocusable(true);
        addKeyListener(this);

        // Load images
        backgroundImg = new ImageIcon(getClass().getResource("/flappybirdbg.png")).getImage();
        birdImg = new ImageIcon(getClass().getResource("/flappybird.png")).getImage();
        topPipeImg = new ImageIcon(getClass().getResource("/toppipe.png")).getImage();
        bottomPipeImg = new ImageIcon(getClass().getResource("/bottompipe.png")).getImage();

        // Initialize bird and pipes
        bird = new Bird(birdImg);
        pipes = new ArrayList<>();

        // Timer for game loop
        gameLoop = new Timer(1000 / 60, this);

        // Timer to place pipes
        placePipeTimer = new Timer(1500, e -> placePipes());
    }

    void placePipes() {
        int openingSpace = boardHeight / 4;
        int randomY = -pipeHeight / 4 - random.nextInt(pipeHeight / 2);

        // Top pipe
        pipes.add(new Pipe(boardWidth, randomY, topPipeImg));

        // Bottom pipe
        pipes.add(new Pipe(boardWidth, randomY + pipeHeight + openingSpace, bottomPipeImg));
    }

    void resetGame() {
        bird.y = birdY;
        velocityY = 0;
        pipes.clear();
        score = 0;
        gameOver = false;
        gameStarted = false;
        gameLoop.stop();
        placePipeTimer.stop();
    }

    boolean collision(Bird a, Pipe b) {
        return a.x < b.x + b.width &&
               a.x + a.width > b.x &&
               a.y < b.y + b.height &&
               a.y + a.height > b.y;
    }

    public void move() {
        if (!gameStarted) return;

        // Bird movement
        velocityY += gravity;
        bird.y += velocityY;
        bird.y = Math.max(bird.y, 0);

        // Pipe movement
        for (Pipe pipe : pipes) {
            pipe.x += pipeSpeed;

            // Check for score increment
            if (!pipe.passed && bird.x > pipe.x + pipe.width) {
                score += 0.5; // 0.5 for each pipe
                pipe.passed = true;
            }

            // Check for collisions
            if (collision(bird, pipe)) {
                gameOver = true;
            }
        }

        // Remove pipes that are off-screen
        pipes.removeIf(pipe -> pipe.x + pipe.width < 0);

        // Check if bird hits the ground
        if (bird.y > boardHeight) {
            gameOver = true;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        repaint();

        if (gameOver) {
            gameLoop.stop();
            placePipeTimer.stop();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw background
        g.drawImage(backgroundImg, 0, 0, boardWidth, boardHeight, null);

        // Draw bird
        g.drawImage(birdImg, bird.x, bird.y, bird.width, bird.height, null);

        // Draw pipes
        for (Pipe pipe : pipes) {
            g.drawImage(pipe.img, pipe.x, pipe.y, pipe.width, pipe.height, null);
        }

        // Draw score
        g.setColor(Color.white);
        g.setFont(new Font("Arial", Font.PLAIN, 23));

        if (gameOver) {
            // Show game over message and score
            String gameOverText = "Game Over! Score: " + (int) score;
            g.drawString(gameOverText, 50, boardHeight / 2);

            // Show instructions to restart the game
            String restartText = "Press SPACE to Restart";
            g.drawString(restartText, 50, boardHeight / 2 + 40);
        } else {
            // Show live score
            g.drawString(String.valueOf((int) score), 10, 35);
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (gameOver) {
                resetGame(); // Reset the game when game is over and space is pressed
            } else if (!gameStarted) {
                gameStarted = true;
                gameLoop.start(); // Start the game loop
                placePipeTimer.start(); // Start pipe generation
            } else {
                velocityY = -10; // Jump when space is pressed during the game
            }
        }

        // Handle arrow keys for movement (optional)
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            velocityY = -10; // Move the bird upwards
        }

        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            velocityY = 10; // Move the bird downwards
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Flappy Bird");
        FlappyBird flappyBird = new FlappyBird();
        frame.add(flappyBird);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
