public class MainGame{
    public static void main(String[] args) {
        KeyHandler keyH = new KeyHandler();
        
        GamePanel panel1 = new GamePanel(keyH);
        DisplayPanel panelDisplay = new DisplayPanel(panel1.getStructureList(), panel1.getEntityList());
        
        GameFrame frame1 = new GameFrame();

        frame1.getContentPane().add(panelDisplay);
        frame1.addKeyListener(keyH);

        panel1.startGameThread();
        panelDisplay.startGameThread();
    }
}