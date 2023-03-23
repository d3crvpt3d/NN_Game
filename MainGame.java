public class MainGame{

    double[][][] weightList; // which Layer; which neuron of L0; which neuron of L+1

    public static void main(String[] args) throws InterruptedException {
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

                panel1.stall();       //stalls the thread
                System.out.println();System.out.println("panel1 wait..");System.out.println();

                //
                saveBest10Percent();

                panel1.entityList = new EntListFromFile().get();
                //

                panel1.startGameThread();     //awakens the thread
                System.out.println();System.out.println("panel1 resume..");System.out.println();

                startTime = currTime;
            }
        }
    }

    //averages the best .1 Weights from EntityList and saves them in a File
    static void saveBest10Percent(){
        

        
        //TODO
    }

    Entity[] getNewEntityList(){
        return null;
    }
}