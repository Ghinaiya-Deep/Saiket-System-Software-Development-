import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class NumberGuessingGame extends JFrame {

    private final int randomNumber;
    private int guessCount = 0;

    private final JTextField guessField;
    private final JLabel feedbackLabel;
    private final JLabel guessCountLabel;

    public NumberGuessingGame() {
        setTitle("Number Guessing Game");
        setSize(400, 250);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(245, 250, 255));

        // Generate random number
        Random rand = new Random();
        randomNumber = rand.nextInt(100) + 1;

        // Title
        JLabel title = new JLabel("Guess a number between 1 and 100");
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
        add(title, BorderLayout.NORTH);

        // Center panel
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(new Color(245, 250, 255));
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));

        guessField = new JTextField();
        guessField.setMaximumSize(new Dimension(200, 30));
        guessField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        guessField.setAlignmentX(Component.CENTER_ALIGNMENT);

        JButton guessButton = new JButton("Submit Guess");
        guessButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        guessButton.setBackground(new Color(100, 149, 237));
        guessButton.setForeground(Color.WHITE);
        guessButton.setFocusPainted(false);
        guessButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        feedbackLabel = new JLabel(" ");
        feedbackLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        feedbackLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        feedbackLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        guessCountLabel = new JLabel("Guesses: 0");
        guessCountLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        guessCountLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        guessCountLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

        centerPanel.add(guessField);
        centerPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        centerPanel.add(guessButton);
        centerPanel.add(feedbackLabel);
        centerPanel.add(guessCountLabel);

        add(centerPanel, BorderLayout.CENTER);

        // Button click listener
        guessButton.addActionListener(e -> processGuess());

        setVisible(true);
    }

    private void processGuess() {
        String input = guessField.getText().trim();
        try {
            int guess = Integer.parseInt(input);

            if (guess < 1 || guess > 100) {
                feedbackLabel.setText("Please enter a number between 1 and 100.");
                return;
            }

            guessCount++;
            guessCountLabel.setText("Guesses: " + guessCount);

            if (guess == randomNumber) {
                feedbackLabel.setText("🎉 Correct! The number was " + randomNumber);
                JOptionPane.showMessageDialog(this,
                        "Congratulations! You guessed it in " + guessCount + " tries.",
                        "You Win!",
                        JOptionPane.INFORMATION_MESSAGE);
                System.exit(0);
            } else if (guess < randomNumber) {
                feedbackLabel.setText("Too low. Try again!");
            } else {
                feedbackLabel.setText("Too high. Try again!");
            }

        } catch (NumberFormatException ex) {
            feedbackLabel.setText("Invalid input. Please enter a number.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(NumberGuessingGame::new);
    }
}
