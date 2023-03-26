public class MainGame{

    public static void main(String[] args){
        
        KeyHandler keyH = new KeyHandler();
        
        double quantityOfParents = .2, mutationRate = .01;

        GamePanel panel1 = new GamePanel(keyH, true);
        DisplayPanel panelDisplay = new DisplayPanel(panel1.structureList, panel1.entityList);
        
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

                saveBestPercent(panel1, weightList, quantityOfParents);

                //panel1.entityList = new EntListFromFile().get();

                spreadEntitys(panel1.entityList, quantityOfParents, mutationRate, weightList);

                resetScore(panel1.entityList);

                panel1.active_bool = true;
                panel1.startGameThread();     //awakens the thread

                System.out.println();System.out.println("panel1 resume..");System.out.println();

                startTime = currTime;
            }
        }
    }

    //averages the best .1 Weights from EntityList and saves them in a File
    static void saveBestPercent(GamePanel panel, double[][][] wL, double qOP){
        
        sort(panel.entityList);
        
        //DEBUG
        System.err.println("Best: "+panel.entityList[0].score+" y: "+panel.entityList[0].y);
        System.err.println("Worst: "+panel.entityList[panel.entityList.length-1].score+" y: "+panel.entityList[panel.entityList.length-1].y);
        //DEBUG

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
    //sort score absteigend arr[0] > arr[1]
    static void sort(Entity[] arr){ //OPTIMIEREN! ist momentan n^2

        for(int j = 0; j < arr.length; j++){
            for(int i = 0; i < arr.length; i++){
                Entity tmp;
                if(arr[i].score < arr[j].score){
                    tmp = arr[i];
                    arr[i] = arr[j];
                    arr[j] = tmp;
                }
            }
        }
    }
    //TODO
    //fills up the entitys with mutation
    static void spreadEntitys(Entity[] arr, double qOP, double mut, double[][][] weLi){
        

        //average weights
        
        
    }

    //mutates the weights
    static void mutate(Entity e, double mutRate, double[][][] weiList){
        for(int i = 0; i < e.nn.weights.length; i++){
            for(int j = 0; j < e.nn.weights[0].length; j++){
                for(int k = 0; k < e.nn.weights[0][0].length; k++){
                    e.nn.weights[i][j][k] =  weiList[i][j][k] -1 + 2 * mutRate * Math.random();
                }
            }
        }
    }

    //reset score
    static void resetScore(Entity[] arr){
        for(int i = 0; i < arr.length; i++){
            arr[i].score = 0;
        }
    }
}