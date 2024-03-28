import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BaccaratGUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(400, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // set title
        frame.setTitle("บาคาร่าฆ่าคุณ");
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("./icon.png"));

        JPanel panel = new JPanel(new GridLayout(4, 1)); // 4 rows, 1 column
        frame.add(panel);

        JLabel userLabel = new JLabel(" User");
        panel.add(userLabel);

        JTextField userText = new JTextField(20);
        panel.add(userText);

        JLabel passwordLabel = new JLabel(" Password");
        panel.add(passwordLabel);

        JPasswordField passwordText = new JPasswordField();
        panel.add(passwordText);

        JButton loginButton = new JButton("LOGIN");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = userText.getText();
                String password = new String(passwordText.getPassword());
                if (checkCredentials(username, password)) {
                    openNextScreen();
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username or password", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(new JLabel()); // Placeholder for empty space
        panel.add(loginButton);

        // Center the frame on the screen
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static boolean checkCredentials(String username, String password) {
        return username.equals("ku") && password.equals("888");
    }
    private static void openNextScreen() {
        // When login is successful, create and display the next screen
        JFrame nextScreen = new JFrame("Baccarat Game");
        nextScreen.setSize(800, 600);
        nextScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        nextScreen.setLocationRelativeTo(null);

        // Set background color
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(new ImageIcon("./Background.jpg").getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        nextScreen.setContentPane(panel);
        // Create button panel for the next screen
        JPanel buttonPanel = new JPanel(new BorderLayout());
        JButton playButton = new JButton("Play");
        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Call the startGame() method from BaccaratGame class
                BaccaratGame baccaratGame = new BaccaratGame();
                baccaratGame.startGame();
            }
        });
        buttonPanel.add(playButton, BorderLayout.NORTH);
        nextScreen.add(buttonPanel, BorderLayout.SOUTH);

        // set icon image for the next screen
        nextScreen.setIconImage(Toolkit.getDefaultToolkit().getImage("./icon.png"));

        nextScreen.setVisible(true);
    }
}