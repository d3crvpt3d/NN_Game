public class MainGame{
    public static void main(String[] args) {
        KeyHandler keyH = new KeyHandler();
        GamePanel panel1 = new GamePanel(keyH);
        GameFrame frame1 = new GameFrame();

        frame1.getContentPane().add(panel1);
        frame1.addKeyListener(keyH);
        panel1.startGameThread();
    }
}