import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

public class BaccaratGame {
    private class Card {
        String value;
        String type;

        Card(String value, String type) {
            this.value = value;
            this.type = type;
        }

        public String toString() {
            return value + "-" + type;
        }

        public int getValue() {
            if ("JQK".contains(value)) { // J, Q, K
                return 0;
            } else if ("A".equals(value)) { // A
                return 1;
            }
            return Integer.parseInt(value); // 2-10
        }

        public String getImagePath() {
            return "./cards/" + toString() + ".png";
        }
    }

    ArrayList<Card> deck;
    Random random = new Random(); // shuffle deck

    // player and banker
    ArrayList<Card> playerHand;
    ArrayList<Card> bankerHand;
    int playerSum;
    int bankerSum;

    // window
    int boardWidth = 800  ;
    int boardHeight = 600 ;

    int cardWidth = 110; // ratio should be 1/1.4
    int cardHeight = 154;

    JFrame frame = new JFrame("Baccarat");
    JPanel gamePanel = new JPanel() {
        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            try {
                // draw player's hand
                for (int i = 0; i < playerHand.size(); i++) {
                    Card card = playerHand.get(i);
                    Image cardImg = new ImageIcon(getClass().getResource(card.getImagePath())).getImage();
                    g.drawImage(cardImg, 280 + (cardWidth + 5) * i, 320, cardWidth, cardHeight, null);
                }

                // draw banker's hand
                for (int i = 0; i < bankerHand.size(); i++) {
                    Card card = bankerHand.get(i);
                    Image cardImg = new ImageIcon(getClass().getResource(card.getImagePath())).getImage();
                    g.drawImage(cardImg, 280 + (cardWidth + 5) * i, 20, cardWidth, cardHeight, null);
                }

                g.setFont(new Font("TimesRoman", Font.BOLD , 25));
                g.setColor(Color.white);
                g.drawString("Player: " + playerSum, 340, 210);
                g.drawString("Banker: " + bankerSum, 340, 500);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    BaccaratGame() {
        startGame();

        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        gamePanel.setLayout(new BorderLayout());
        gamePanel.setBackground(new Color(50, 138, 84));
        frame.add(gamePanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton newGameButton = new JButton("Next Game");
        newGameButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startNewGame();
            }
        });
        buttonPanel.add(newGameButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);

    }

    public void startGame() {
        // deck
        buildDeck();
        shuffleDeck();

        // player and banker
        playerHand = new ArrayList<Card>();
        bankerHand = new ArrayList<Card>();
        playerSum = 0;
        bankerSum = 0;

        // initial deal
        for (int i = 0; i < 2; i++) {
            dealCardToPlayer();
            dealCardToBanker();
        }
    }

    public void buildDeck() {
        deck = new ArrayList<Card>();
        String[] values = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"};
        String[] types = {"C", "D", "H", "S"};

        for (int i = 0; i < types.length; i++) {
            for (int j = 0; j < values.length; j++) {
                Card card = new Card(values[j], types[i]);
                deck.add(card);
            }
        }
    }

    public void shuffleDeck() {
        for (int i = 0; i < deck.size(); i++) {
            int j = random.nextInt(deck.size());
            Card currCard = deck.get(i);
            Card randomCard = deck.get(j);
            deck.set(i, randomCard);
            deck.set(j, currCard);
        }
    }

    public void dealCardToPlayer() {
        Card card = deck.remove(deck.size() - 1);
        playerSum += card.getValue();
        playerHand.add(card);
        playerSum %= 10; // Baccarat rules: only keep last digit
    }

    public void dealCardToBanker() {
        Card card = deck.remove(deck.size() - 1);
        bankerSum += card.getValue();
        bankerHand.add(card);
        bankerSum %= 10; // Baccarat rules: only keep last digit
    }


    public void startNewGame() {
        startGame(); // Start a new game
        gamePanel.repaint(); // Repaint the game panel to show new hands
    }

    public static void main(String[] args) {
        new BaccaratGame();
    }
}
