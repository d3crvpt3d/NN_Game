public class MainGame{

    public static void main(String[] args) throws InterruptedException {
        
        KeyHandler keyH = new KeyHandler();
        
        double quantityOfParents = .1, mutationRate = .1;

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
                saveBest10Percent(panel1, weightList, quantityOfParents);

                //panel1.entityList = new EntListFromFile().get();

                spreadEntitys(panel1.entityList, quantityOfParents, mutationRate);

                panel1.startGameThread();     //awakens the thread
                System.out.println();System.out.println("panel1 resume..");System.out.println();

                startTime = currTime;
            }
        }
    }

    //averages the best .1 Weights from EntityList and saves them in a File
    static void saveBest10Percent(GamePanel panel, double[][][] wL, double qOP){
        
        sort(panel.entityList);

        for(int it0 = 0; it0 < wL.length; it0++){
            for(int it1 = 0; it1 < wL[0].length; it1++){
                for(int it2 = 0; it2 < wL[0][0].length; it2++){
                    for(int it3 = 0; it3 < qOP * panel.entityList.length; it3++){ //Change '.1' for quantity of "Parents"
                        wL[it0][it1][it2] += panel.entityList[it3].nn.weights[it0][it1][it2];
                    }
                }
            }
        }

        //TODO

        //save in a file
    }

    //TODO
    static void sort(Entity[] arr){ //OPTIMIEREN! ist momentan n^2

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
    }

    //TODO
    //unefficient i think
    //fills up the entitys with mutation
    static void spreadEntitys(Entity[] arr, double qOP, double mut){
        
        for(int outer = 0; outer < qOP * arr.length; outer++){
            
            //fills up entityList
            for(int inner = 0; inner < 1/qOP; inner++){
                arr[ outer * (int)(arr.length * qOP) + inner] = arr[outer]; //fills from last Parent excluding every qOP*arr.length up
                mutate(arr[outer], mut);
            }
        }
    }

    //TODO
    //not efficient
    //mutates the weights
    static void mutate(Entity e, double mutRate){
        for(int i = 0; i < e.nn.weights.length; i++){
            for(int j = 0; j < e.nn.weights[0].length; j++){
                for(int k = 0; k < e.nn.weights[0][0].length; k++){
                    e.nn.weights[i][j][k] += -1 + 2 * mutRate * Math.random();
                }
            }
        }
    }
}