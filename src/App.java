import javax.swing.*;
public class App {
    public static void main(String[] args) throws Exception {
        int boardWidth = 360;
        int boardHeight = 640;

        // Create a JFrame instance for the game window
        JFrame frame = new JFrame("Flappy Bird");

        // Load the logo image using FlappyBird.class.getResource
        ImageIcon logoIcon = new ImageIcon(App.class.getResource("/game_logo.png"));
        
        // Set the window icon to the logo
        frame.setIconImage(logoIcon.getImage());

        // Set window properties
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null); // Center the window
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create an instance of FlappyBird and add it to the frame
        FlappyBird flappyBird = new FlappyBird();
        frame.add(flappyBird);
        
        // Pack the frame and make it visible
        frame.pack();
        flappyBird.requestFocus();
        frame.setVisible(true);
    }
}
