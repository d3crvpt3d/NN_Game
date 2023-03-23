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

        long startTime = System.nanoTime();
        long currTime;
        long nano = 1000000000;

        while(true){

            currTime = System.nanoTime();

            if(currTime - startTime >= 10*nano){

                saveBest10Percent();

                new GamePanel(keyH, "");



                startTime = currTime;
            }
        }
    }

    static void saveBest10Percent(){

    }
}