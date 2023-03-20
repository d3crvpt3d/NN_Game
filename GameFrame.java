import javax.swing.JFrame;

public class GameFrame extends JFrame{
    public GameFrame(){
        setTitle("Game");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLocation(0, 0);
        setResizable(false);
        setSize(1920, 1080);
        setUndecorated(true);
        setFocusable(true);
        setExtendedState(MAXIMIZED_BOTH);
        setVisible(true);
    }
}