
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.*;

/**
 * @date June 2019
 * @author Ronny Sun
 */
public class Game2048 extends JPanel implements ActionListener, KeyListener {

    private JButton returnBtn, leaderboard;
    final Color[] colorArray = {Color.decode("#D8D8D8"), Color.decode("#EFEFEF"), Color.decode("#55CE55"),
        Color.decode("#00B500"), Color.decode("#8B9FD9"), Color.decode("#b06fc8"), Color.decode("#3B5DBE"),
        Color.decode("#9F44C0"), Color.decode("#FFE04E"), Color.decode("#FFD300"), Color.decode("#FFC540"),
        Color.decode("#FFB200"), Color.decode("#FF8E00"), Color.decode("#FF611F"), Color.decode("#56185f")};
    private Tile[][] tiles;
    private int side = 4;
    private static JTextField displayHighScoreText, displayHighScore, displayScoreText, displayScore, playerUsername;
    private int highScore, score;
    private boolean checkMoves, gameState = true;
    private static String username;
    private JTextArea instruction;
    private Random rand = new Random(); // https://www.thoughtco.com/how-to-generate-random-numbers-2034206
    private static JFrame frame = new JFrame();

    public static void main(String args[]) throws IOException {
        frame = new JFrame("2048");
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent event) { // https://stackoverflow.com/questions/16372241/run-function-on-jframe-close
                try {
                    exitProcedure();
                } catch (IOException ex) {
                    Logger.getLogger(Game2048.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            private void exitProcedure() throws IOException {
                update();
            }
        });
        frame.setResizable(false);
        try {
            frame.add(new Game2048(username));
        } catch (IOException ex) {
            Logger.getLogger(Game2048.class.getName()).log(Level.SEVERE, null, ex);
        }
        frame.pack(); // // https://stackoverflow.com/questions/18872287/jpanel-is-not-displaying-in-my-jframe
        frame.setLocationRelativeTo(null); // https://stackoverflow.com/questions/2442599/how-to-set-jframe-to-appear-centered-regardless-of-monitor-resolution
        frame.setVisible(true);
    }

    public Game2048(String user) throws IOException {
        username = user;

        setPreferredSize(new Dimension(800, 600));

        this.addKeyListener(this);
        this.setFocusable(true);
        this.requestFocusInWindow(); // https://stackoverflow.com/questions/16530775/keylistener-not-working-for-jpanel

        setPlayerUsername(user);

        ArrayList<String> users = new ArrayList<>(); // creating array list to store the entire file to use later to find the high score
        IO.openInputFile("UserList.txt");
        String line = IO.readLine();
        while (line != null) {
            users.add(line.substring(0, line.indexOf(',')));
            line = IO.readLine();
        }
        IO.closeInputFile();
        ArrayList<String> userInfo = new ArrayList<>();
        IO.openInputFile("UserList.txt");
        String line2 = IO.readLine();
        while (line2 != null) {
            userInfo.add(line2);
            line2 = IO.readLine();
        }
        IO.closeInputFile();
        if (!"Guest".equals(username)) {
            line2 = userInfo.get(SS.search(users, username));
            highScore = Integer.parseInt(line2.substring(line2.lastIndexOf(',') + 1));
        }

        returnBtn = new JButton(new ImageIcon("Images/return.jpg"));
        returnBtn.setBounds(15, 110, 90, 90); // 30, 300, 290, 170
        returnBtn.setBorder(null);
        returnBtn.addActionListener(this);

        leaderboard = new JButton(new ImageIcon("Images/leaderboard.jpg"));
        leaderboard.setBounds(120, 110, 90, 90); // 30, 300, 290, 170
        leaderboard.setBorder(null);
        leaderboard.addActionListener(this);

        playerUsername = new JTextField(username);
        playerUsername.setBounds(0, 10, 225, 100);
        playerUsername.setFont(new Font("IMPACT", Font.BOLD, 60));
        playerUsername.setForeground(Color.decode("#55CE55"));
        playerUsername.setHorizontalAlignment(JTextField.CENTER);
        playerUsername.setEditable(false);
        playerUsername.setBorder(null);
        playerUsername.setBackground(Color.WHITE);

        instruction = new JTextArea();
        instruction.setEditable(false);
        instruction.setBounds(23, 375, 195, 300);
        instruction.setBackground(Color.gray);
        instruction.setText("INSTRUCTION:\n"
                + "The objective of the game is to reach 2048 by merging adjacent similar number tiles on a 4x4 board. "
                + "The game ends when your board is completely filled with numbers and you don’t have any move left.");
        instruction.setLineWrap(true);
        instruction.setWrapStyleWord(true);
        instruction.setFont(new Font("Georgia", Font.ITALIC, 16));
        instruction.setForeground(Color.decode("#8B9FD9"));
        instruction.setBackground(Color.white);

        displayScoreText = new JTextField("Score: ");
        displayScoreText.setBounds(20, 210, 185, 30);
        displayScoreText.setFont(new Font("Georgia", Font.BOLD, 20));
        displayScoreText.setForeground(Color.decode("#3B5DBE"));
        displayScoreText.setHorizontalAlignment(JTextField.CENTER);
        displayScoreText.setEditable(false);
        displayScoreText.setBorder(null);
        displayScoreText.setBackground(Color.white);

        displayHighScoreText = new JTextField("Highscore: ");
        displayHighScoreText.setBounds(20, 292, 185, 30);
        displayHighScoreText.setFont(new Font("Georgia", Font.BOLD, 20));
        displayHighScoreText.setForeground(Color.decode("#3B5DBE"));
        displayHighScoreText.setHorizontalAlignment(JTextField.CENTER);
        displayHighScoreText.setEditable(false);
        displayHighScoreText.setBorder(null);
        displayHighScoreText.setBackground(Color.white);

        displayScore = new JTextField("" + score);
        displayScore.setBounds(20, 240, 185, 50);
        displayScore.setFont(new Font("Georgia", Font.BOLD, 40));
        displayScore.setForeground(Color.decode("#00B500"));
        displayScore.setHorizontalAlignment(JTextField.CENTER);
        displayScore.setEditable(false);
        displayScore.setBorder(null);
        displayScore.setBackground(Color.white);

        displayHighScore = new JTextField("" + highScore);
        displayHighScore.setBounds(20, 322, 185, 50);
        displayHighScore.setFont(new Font("Georgia", Font.BOLD, 40));
        displayHighScore.setForeground(Color.decode("#00B500"));
        displayHighScore.setHorizontalAlignment(JTextField.CENTER);
        displayHighScore.setEditable(false);
        displayHighScore.setBorder(null);
        displayHighScore.setBackground(Color.white);

        setLayout(null);
        setBackground(Color.WHITE);
        add(returnBtn);
        add(playerUsername);
        add(instruction);
        add(displayScoreText);
        add(displayHighScoreText);
        add(leaderboard);
        add(displayScore);
        add(displayHighScore);

        tiles = new Tile[side][side];
        tileGenerator();
        tileGenerator(); // the two tiles the player starts with
    }

    @Override
    public void keyPressed(KeyEvent e) {
        try {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_UP:
                    moveUp();
                    break;
                case KeyEvent.VK_DOWN:
                    moveDown();
                    break;
                case KeyEvent.VK_LEFT:
                    moveLeft();
                    break;
                case KeyEvent.VK_RIGHT:
                    moveRight();
                    break;
            }
            repaint(); 
        } catch (IOException ex) {
            Logger.getLogger(Game2048.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == returnBtn) {
            setVisible(false);
            frame.dispose();
            frame.setVisible(false);
            new GameMenu();
            try {
                update();
            } catch (IOException ex) {
                Logger.getLogger(Game2048.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == leaderboard) {
            try {
                try {
                    update();
                } catch (IOException ex) {
                    Logger.getLogger(Game2048.class.getName()).log(Level.SEVERE, null, ex);
                }
                new GameLeaderboard();
                setFocusable(true);
                requestFocusInWindow();
            } catch (IOException ex) {
                Logger.getLogger(Game2048.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        Graphics2D graphics2d = (Graphics2D) graphics;
        graphics2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, // https://blog.idrsolutions.com/2011/08/java-and-anti-aliasing/
                RenderingHints.VALUE_ANTIALIAS_ON);
        drawBoard(graphics2d);
    }

    public void drawBoard(Graphics2D g) {
        g.setColor(colorArray[0]);
        g.fillRoundRect(225, 25, 550, 550, 45, 45); // 200, 100, 499, 499

        for (int i = 0; i < side; i++) {
            for (int k = 0; k < side; k++) {
                if (tiles[i][k] == null) {
                    g.setColor(colorArray[1]);
                    g.fillOval(243 + k * 135, 44 + i * 135, 112, 112); // 112x112 to provide extra padding, 243 + k * 135 because square side / 4 = 137.5, 
                    // then make k + smaller number to provide padding, so 135, then add the 20 pixels padding onto square side lenght to make ~245, then adjust 
                    // to make it look even...math is hard.
                } else { // if tile was craeted with addTile
                    drawTile(g, i, k);
                }
            }
        }
    }

    public void drawTile(Graphics2D g, int i, int k) {
        int num = tiles[i][k].getNumber();
        int numTemp = num;
        int index = 0;
        while (numTemp != 1) {
            numTemp = numTemp / 2;
            index++;
        }
        g.setColor(colorArray[index + 1]); // drawing the single box
        g.fillOval(243 + k * 135, 44 + i * 135, 112, 112);
        String s = String.valueOf(num); // adding the number on it
        g.setColor(Color.white);
        g.setFont(new Font("IMPACT", Font.BOLD | Font.ITALIC, 20));
        g.drawString(s, 293 + k * 135, 105 + i * 135);
        
    }

    private void tileGenerator() {
        int position = rand.nextInt(side * side);
        int row, col;
        do {
            position = (position + 1) % (side * side);
            row = position / side;
            col = position % side;
        } while (tiles[row][col] != null); // picking an open space that will be fit for new tile

        int num;
        if (rand.nextInt(10) == 0) {
            num = 4;
        } else {
            num = 2;
        }

        tiles[row][col] = new Tile(num); // declaring it with num as the new number
    }

    public static void setPlayerUsername(String user) {
        if (user != null) {
            username = user;
        } else {
            username = "Guest";
        }
    }

    public boolean move(int index, int xIncrement, int yIncrement) throws IOException {
        boolean moved = false; // to check if it moved, since it can be unavailable
        for (int i = 0; i < side * side; i++) {
            int j = Math.abs(index - i);
            int x = j / side;
            int y = j % side;
            if (tiles[x][y] == null) {
                continue;
            }
            int newX = x + xIncrement;
            int newY = y + yIncrement;
            while (newX >= 0 && newX < side && newY >= 0 && newY < side) { 
                Tile next = tiles[newX][newY];
                Tile curr = tiles[x][y];
                if (next == null) { // finding the potential swap
                    if (checkMoves) {
                        return true;
                    }
                    tiles[newX][newY] = curr;
                    tiles[x][y] = null;
                    x = newX;
                    y = newY;
                    newX += xIncrement;
                    newY += yIncrement;
                    moved = true;

                } else if (next.combinable(curr)) { // swapping and changing numbers

                    if (checkMoves) {
                        return true;
                    }
                    int number = next.combine(curr);
                    if (number > highScore) {
                        highScore = number;
                    }
                    score += number;
                    tiles[x][y] = null;
                    moved = true;
                    break;
                } else {
                    break;
                }
            }
        }
        if (moved) {
            resetCombine();
            tileGenerator();
            if (score >= highScore) {
                highScore = score;
                displayHighScore.setText("" + highScore);
            }
            displayScore.setText("" + score);
            if (!movesAvailable()) {
                tiles = new Tile[side][side];
                tileGenerator();
                tileGenerator();
                score = 0;
            }
        }

        return moved;
    }

    public boolean moveUp() throws IOException { // all boolean as it is a void that returns a true or false, which is used in movesAvailable method
        return move(0, -1, 0);
    }

    public boolean moveDown() throws IOException {
        return move(side * side - 1, 1, 0);
    }

    public boolean moveLeft() throws IOException {
        return move(0, 0, -1);
    }

    public boolean moveRight() throws IOException {
        return move(side * side - 1, 0, 1);
    }

    public void resetCombine() {
        for (int i = 0; i < side; i++) {
            for (int j = 0; j < side; j++) {
                if (tiles[i][j] != null) {
                    tiles[i][j].setCombine(false);
                }
            }
        }
    }

    private static void update() throws IOException { // updates the highscore onto the file
        ArrayList<String> playerInfo = new ArrayList<>();
        IO.openInputFile("UserList.txt");
        String line = IO.readLine();
        while (line != null) {
            playerInfo.add(line);
            line = IO.readLine();
        }
        IO.closeInputFile();

        if (SS.searchUsername(playerInfo, username) > -1) {
            String temp = playerInfo.get(SS.searchUsername(playerInfo, username));
            temp = temp.substring(0, temp.lastIndexOf(',') + 1);
            temp += "" + displayHighScore.getText(); // highScore variable does not update fast enough
            playerInfo.set(SS.searchUsername(playerInfo, username), temp);
        }
        IO.createOutputFile("UserList.txt");
        for (int i = 0; i < playerInfo.size(); i++) {
            IO.println(playerInfo.get(i));
        }
        IO.closeOutputFile();
    }
    
    public boolean movesAvailable() throws IOException { // see if anything is movable
        checkMoves = true;
        boolean hasMoves = moveUp() || moveDown() || moveLeft() || moveRight();
        checkMoves = false;
        return hasMoves;
    }
}
