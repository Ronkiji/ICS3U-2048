
import java.awt.*;
import java.io.IOException;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author S346909948
 */
public class GameLeaderboard extends JFrame{
    
    private JTextField[] text;
    private JTextField title;
    
    public static void main(String[] args) throws IOException {
        new GameLeaderboard();
    }
    
    public GameLeaderboard() throws IOException{
        super("Game Leaderboard");
        text = new JTextField[10];
        setSize(400, 700);
        
        Container c = getContentPane();
        c.setLayout(null);
        c.setBackground(Color.decode("#9D44B5"));
        
        ArrayList<String> playerInfo = new ArrayList<>();
        IO.openInputFile("UserList.txt");
        String line = IO.readLine();
        while (line != null) {
            playerInfo.add(line);
            line = IO.readLine();
        }
        IO.closeInputFile();
     
        ArrayList<Integer> playerScore = new ArrayList<>();
        IO.openInputFile("UserList.txt");
        String line1 = IO.readLine();
        while (line1 != null) {
            playerScore.add(Integer.parseInt(line1.substring(line1.lastIndexOf(',') + 1)));
            line1 = IO.readLine();
        }
        IO.closeInputFile();
        SS.reverseSort(playerScore, playerInfo); // sorts array based on score

        for(int i = 0; i < text.length; i++){
            text[i] = new JTextField();
            if(i < 9)
                text[i].setText("" + (i + 1) + ".   " + playerInfo.get(i).substring(0, playerInfo.get(i).indexOf(',')) + " - " + playerInfo.get(i).substring(playerInfo.get(i).lastIndexOf(',') + 1));
            else if(i == 9)
                text[i].setText("" + (i + 1) + ". " + playerInfo.get(i).substring(0, playerInfo.get(i).indexOf(',')) + " - " + playerInfo.get(i).substring(playerInfo.get(i).lastIndexOf(',') + 1));
            text[i].setBackground(Color.decode("#9D44B5"));
            text[i].setBounds(110, 55 * (i + 1) + 50, 250, 40);
            text[i].setFont(new Font("Georgia", Font.ITALIC, 25));
            text[i].setForeground(Color.decode("#BADEFC"));
            text[i].setBorder(null);
            text[i].setEditable(false);
            c.add(text[i]);
        }
        
        title = new JTextField("Game Leaderboard");
        title.setBounds(0, 0, 400, 100);
        title.setBackground(Color.decode("#9D44B5"));
        title.setForeground(Color.decode("#BADEFC"));
        title.setFont(new Font("Georgia", Font.ITALIC, 40));
        title.setBorder(null);
        title.setEditable(false);
        title.setHorizontalAlignment(JTextField.CENTER);
        c.add(title);
        
        setVisible(true);
        setResizable(false);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
    }
}
