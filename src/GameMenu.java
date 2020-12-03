
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

/**
 * @date June 2019
 * @author Ronny Sun
 */
public class GameMenu extends JFrame implements ActionListener {

    private JTextArea inputUsername, inputPwd, wordUsername, wordPwd, invalid, title, instruction;
    private JButton login, create, guest, confirm, returnBtn;
    private static String username, password;
    private JLabel image;
    final Color[] colorArray = {Color.decode("#FFF4FF"), Color.decode("#45BC05"), Color.decode("#3E7CB1"), Color.decode("#62E1EA"), Color.decode("#FF9C23")};

    public static void main(String[] args) {
        new GameMenu();
    }

    public GameMenu() {
        super("Game 2048");
        setSize(800, 600);

        title = new JTextArea("Welcome to 2048!");
        title.setBounds(150, 30, 500, 100);
        title.setBackground(colorArray[3]);
        title.setFont(new Font("IMPACT", Font.BOLD, 60));
        title.setForeground(Color.decode("#560456"));
        title.setEditable(false);

        wordUsername = new JTextArea("Username");
        wordUsername.setBounds(360, 175, 150, 50);
        wordUsername.setBackground(colorArray[3]);
        wordUsername.setFont(new Font("Georgia", Font.ITALIC, 25));
        wordUsername.setEditable(false);

        wordPwd = new JTextArea("Password");
        wordPwd.setBounds(360, 235, 150, 30);
        wordPwd.setBackground(colorArray[3]);
        wordPwd.setFont(new Font("Georgia", Font.ITALIC, 25));
        wordPwd.setEditable(false);

        inputUsername = new JTextArea();
        inputUsername.setBounds(510, 178, 150, 23);
        inputUsername.setBackground(colorArray[0]);
        inputUsername.setFont(new Font("Georgia", Font.PLAIN, 17));
        inputUsername.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));

        inputPwd = new JTextArea();
        inputPwd.setBounds(510, 238, 150, 23);
        inputPwd.setBackground(colorArray[0]);
        inputPwd.setFont(new Font("Georgia", Font.PLAIN, 17));
        inputPwd.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));

        invalid = new JTextArea("Invalid username or password, please try again.");
        invalid.setBounds(369, 270, 400, 30);
        invalid.setBackground(colorArray[3]);
        invalid.setFont(new Font("TimesNewRoman", Font.ITALIC, 13));
        invalid.setForeground(Color.RED);
        invalid.setVisible(false);

        login = new JButton("Login");
        login.setBounds(450, 295, 140, 50);
        login.setBackground(colorArray[4]);
        login.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 18));
        login.setBorder(BorderFactory.createLineBorder(Color.BLUE, 1));
        login.addActionListener(this);

        confirm = new JButton("Comfirm");
        confirm.setBounds(450, 295, 140, 50);
        confirm.setBackground(colorArray[1]);
        confirm.setForeground(Color.white);
        confirm.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 18));
        confirm.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));
        confirm.setVisible(false);
        confirm.addActionListener(this);

        create = new JButton("Register");
        create.setBounds(275, 375, 250, 50);
        create.setBackground(colorArray[1]);
        create.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 18));
        create.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));
        create.setForeground(Color.WHITE);
        create.addActionListener(this);

        guest = new JButton("Play as guest");
        guest.setBounds(275, 445, 250, 50);
        guest.setBackground(colorArray[1]);
        guest.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 18));
        guest.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));
        guest.setForeground(Color.WHITE);
        guest.addActionListener(this);

        returnBtn = new JButton("Return");
        returnBtn.setBounds(275, 445, 250, 50);
        returnBtn.setBackground(colorArray[1]);
        returnBtn.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 18));
        returnBtn.setBorder(BorderFactory.createLineBorder(Color.GREEN, 1));
        returnBtn.setForeground(Color.WHITE);
        returnBtn.addActionListener(this);

        instruction = new JTextArea("  *Username and password must be at least 3 characters without spaces*\n                                             *Username cannot be Guest*"
                + "\n*Program will auto remove commas and have max length of 10 for username*");
        instruction.setBounds(177, 375, 444, 50);
        instruction.setBackground(colorArray[3]);
        instruction.setForeground(Color.GRAY);
        instruction.setFont(new Font("SansSerif", Font.BOLD | Font.ITALIC, 12));
        instruction.setEditable(false);
        instruction.setVisible(false);

        image = new JLabel(new ImageIcon("Images/2048.png"));
        image.setBounds(45, 160, 280, 110);

        Container c = getContentPane();
        c.setLayout(null);
        c.setBackground(colorArray[3]);
        c.add(title);
        c.add(wordUsername);
        c.add(wordPwd);
        c.add(inputUsername);
        c.add(inputPwd);
        c.add(login);
        c.add(invalid);
        c.add(create);
        c.add(guest);
        c.add(image);
        c.add(confirm);
        c.add(returnBtn);
        c.add(instruction);

        setLocationRelativeTo(null); // https://stackoverflow.com/questions/2442599/how-to-set-jframe-to-appear-centered-regardless-of-monitor-resolution
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void displayHome() {
        inputUsername.setVisible(true);
        inputUsername.setText("");
        inputPwd.setText("");
        inputPwd.setVisible(true);
        wordUsername.setVisible(true);
        wordPwd.setVisible(true);
        invalid.setText("Invalid username or password, please try again.");
        invalid.setBounds(369, 270, 400, 30);
        invalid.setVisible(false);
        title.setVisible(true);
        login.setVisible(true);
        image.setVisible(true);
        guest.setVisible(true);
        create.setVisible(true);
        instruction.setVisible(false);
        setBackground(colorArray[3]);
    }

    public void createUser(String user, String pwd) throws IOException {
        IO.appendOutputFile("UserList.txt");
        IO.println(user + "," + pwd + ",0");
        IO.closeOutputFile();
        sortFile();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == create) {
            inputUsername.setText("");
            inputPwd.setText("");
            login.setVisible(false);
            invalid.setText("Follow the rules, try again.");
            invalid.setBounds(510, 270, 400, 17);
            invalid.setVisible(false);
            guest.setVisible(false);
            confirm.setVisible(true);
            returnBtn.setVisible(true);
            create.setVisible(false);
            instruction.setVisible(true);
            // somehow change background colors
        }
        if (e.getSource() == returnBtn) {
            displayHome();
        }
        if (e.getSource() == confirm) {
            boolean correct = false;
            username = inputUsername.getText();
            password = inputPwd.getText();
            username = username.replace(" ", "");
            password = password.replace(" ", "");
            username = username.replace(",", "");
            
            if (username.length() >= 3 && username.length() <= 10 && !"Guest".equals(username)) {
                if (password.length() >= 3) {
                    correct = true;
                } else {
                    invalid.setVisible(true);
                }
            } else {
                invalid.setVisible(true);
            }
            if (correct == true) {
                try {
                    if (checkExistingUser(username) == false) {
                        try {
                            createUser(username, password);
                            displayHome();
                        } catch (IOException ex) {
                            Logger.getLogger(GameMenu.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } else {
                        invalid.setText("Username taken");
                        invalid.setVisible(true);
                    }
                } catch (IOException ex) {
                    Logger.getLogger(GameMenu.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        if (e.getSource() == login) {
            username = inputUsername.getText();
            password = inputPwd.getText();
            try {
                if (checkPassword(username, password)) {
                    setVisible(false);
                    openGame(username);
                } else {
                    invalid.setVisible(true);
                }
            } catch (IOException ex) {
                Logger.getLogger(GameMenu.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (e.getSource() == guest) {
            setVisible(false);
            openGame("Guest");
        }
    }

    public boolean checkExistingUser(String user) throws IOException { // see if registration is possible
        ArrayList<String> userInfo = new ArrayList<>();
        IO.openInputFile("UserList.txt");
        String line = IO.readLine();
        while (line != null) {
            userInfo.add(line.substring(0, line.indexOf(',')));
            line = IO.readLine();
        }
        IO.closeInputFile();
        return SS.search(userInfo, user) != -1; // return true if user is taken
    }

    public boolean checkPassword(String user, String password) throws IOException { // works the same as checkExistingUser but with password
        ArrayList<String> playerUser = new ArrayList<>();
        ArrayList<String> playerPwd = new ArrayList<>();
        IO.openInputFile("UserList.txt");
        String line = IO.readLine();
        while (line != null) {
            playerUser.add(line.substring(0, line.indexOf(',')));
            playerPwd.add(line.substring(line.indexOf(',') + 1, line.lastIndexOf(',')));
            line = IO.readLine();
        }
        IO.closeInputFile();
        int index = SS.search(playerUser, user);
        if (index > -1) {
            if (playerPwd.get(index).equals(password)) {
                return true;
            }
        }
        return false;
    }

    public void sortFile() throws IOException {
        ArrayList<String> playerInfo = new ArrayList<>();
        IO.openInputFile("UserList.txt");
        String line = IO.readLine();
        while (line != null) {
            playerInfo.add(line);
            line = IO.readLine();
        }
        IO.closeInputFile();
        SS.insertion(playerInfo);
        IO.createOutputFile("UserList.txt");
        for (int i = 0; i < playerInfo.size(); i++) {
            IO.println(playerInfo.get(i));
        }
        IO.closeOutputFile();
    }

    public void openGame(String user) { // opens game 2048

        JFrame frame = new JFrame("2048");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        try {
            frame.add(new Game2048(user));
        } catch (IOException ex) {
            Logger.getLogger(Game2048.class.getName()).log(Level.SEVERE, null, ex);
        }
        frame.pack(); // https://stackoverflow.com/questions/18872287/jpanel-is-not-displaying-in-my-jframe
        frame.setLocationRelativeTo(null); // https://stackoverflow.com/questions/2442599/how-to-set-jframe-to-appear-centered-regardless-of-monitor-resolution
        frame.setVisible(true);

    }

}
