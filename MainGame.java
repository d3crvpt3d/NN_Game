public class MainGame{

    public static void main(String[] args) throws InterruptedException {
        
        KeyHandler keyH = new KeyHandler();
        
        GamePanel panel1 = new GamePanel(keyH);
        DisplayPanel panelDisplay = new DisplayPanel(panel1.getStructureList(), panel1.getEntityList());
        
        GameFrame frame1 = new GameFrame();

        frame1.getContentPane().add(panelDisplay);
        frame1.addKeyListener(keyH);

        panel1.startGameThread();
        panelDisplay.startGameThread();

        double[][][] weightList = new double[panel1.entityList[0].nn.weights.length] // which Layer; which neuron of L0; which neuron of L+1
                                            [panel1.entityList[0].nn.weights[0].length]
                                            [panel1.entityList[0].nn.weights[0][0].length]; 

        long startTime = System.nanoTime();
        long currTime;
        long nano = 1000000000;

        while(true){

            currTime = System.nanoTime();

            if(currTime - startTime >= 10*nano){

                panel1.stall();       //stalls the thread
                System.out.println();System.out.println("panel1 wait..");System.out.println();

                //
                saveBest10Percent(panel1, weightList);

                //panel1.entityList = new EntListFromFile().get();
                

                panel1.startGameThread();     //awakens the thread
                System.out.println();System.out.println("panel1 resume..");System.out.println();

                startTime = currTime;
            }
        }
    }

    //averages the best .1 Weights from EntityList and saves them in a File
    static void saveBest10Percent(GamePanel panel, double[][][] wL){
        
        System.out.println("ENTLIST: "+panel.entityList);
        sort(panel.entityList);

        for(int it0 = 0; it0 < wL.length; it0++){
            for(int it1 = 0; it1 < wL[0].length; it1++){
                for(int it2 = 0; it2 < wL[0][0].length; it2++){
                    for(int it3 = 0; it3 < .1 * panel.entityList.length; it3++){ //Change '.1' for quantity of "Parents"
                        wL[it0][it1][it2] += panel.entityList[it3].nn.weights[it0][it1][it2];
                    }
                }
            }
        }

        //TODO

        //save in a file
    }

    //TODO
    Entity[] getNewEntityList(){

        return null;
    }

    //TODO
    static void sort(Entity[] arr){ //OPTIMIEREN! ist momentan n^2
        System.out.println("SORT: "+arr);
        for(int j = 0; j < arr.length; j++){
            for(int i = 0; i < arr.length; i++){
                Entity tmp;
                if(arr[i].score > arr[j].score){
                    tmp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = tmp;
                }
            }
        }
        //return
    }
}