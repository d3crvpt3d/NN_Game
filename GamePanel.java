import java.util.Random;

public class GamePanel implements Runnable{
    
    Tickrate tickrate = new Tickrate(60); //set Tickrate "60=default"

    //for iteration
    Structure currStructure;

    Structure[] structureList;

    //collision variables
    boolean collision = false;

    //public for DisplayPanel (Object instad of Primitive type)
    public DeltaTime deltaTime = new DeltaTime();

    //iteratoren
    int iterator;
    int iterator2;
    int it;

    //generating World
    Random rand = new Random();

    boolean active_bool = true;

    Entity[] entityList;

    Thread gameThread;
    Player player;
    KeyHandler keyHgp;

    public GamePanel(KeyHandler keyHgp, boolean new_){

        this.keyHgp = keyHgp;
        //make sure entityList not gets Overwritten
        if(new_){
            new_ = false;
            structureList = new Structure[20]; // anzahl der structures
            entityList = new Player[10]; //anzahl der charactere

            //bound walls on first struc slots
            structureList[0] = new Walls(0,1060,1920,20, "default");
            structureList[1] = new Walls(0,0,20,1080, "default");
            structureList[2] = new Walls(1900,0,20,1080, "default");
            structureList[3] = new Walls(0,0,1920,20, "default");

            //declare Entity's
            for(int it = 0; it < entityList.length; it++){
                entityList[it] = new Player( 200., 800., structureList, deltaTime);
            }

        }

        //rand structures each generation
        for(int it = 4; it < structureList.length; it++){
            structureList[it] = new Walls(  rand.nextInt(1721),
                                            rand.nextInt(881),
                                            rand.nextInt(181) + 20,
                                            rand.nextInt(181) + 20,
                                            "stone");
        }

    }

    public Structure[] getStructureList() {
        return structureList;
    }

    public Thread[] getEntityList() {
        return entityList;
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        deltaTime.drawInterval = 1000000000/tickrate.Tickrate;

        for (int i = 0; i < entityList.length; i++) {
            entityList[i].start();
        }

        while(true){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Ticks: "+ entityList[0].tickrate);
        }

    }

    public void stall(){
        this.active_bool = false;
    }
}