import java.util.Random;

public class GamePanel implements Runnable{
    
    Tickrate tickrate = new Tickrate(60); //set Tickrate "60=default"

    Structure[] structureList = new Structure[20]; // anzahl der structures
    Entity[] entityList = new Entity[100]; //anzahl der player

    //for iteration
    Entity currEntity;
    Structure currStructure;

    //collision variables
    boolean collision = false;

    //iteratoren
    int iterator;
    int iterator2;
    int it;

    //generating World
    Random rand = new Random();




    Thread gameThread;
    Player player;
    KeyHandler keyHgp;

    public GamePanel(KeyHandler keyHgp){

        this.keyHgp = keyHgp;
        

        //declare Entity's and Structures
        for(it = 0; it < entityList.length; it++){
            entityList[it] = new Player(50., 900., structureList);
        }
        

        structureList[0] = new Walls(0,1060,1920,20, "default");
        structureList[1] = new Walls(0,0,20,1080, "default");
        structureList[2] = new Walls(1900,0,20,1080, "default");
        structureList[3] = new Walls(0,0,1920,20, "default");
        
        for(it = 4; it < structureList.length; it++){
            structureList[it] = new Walls(rand.nextInt(1721), rand.nextInt(881), rand.nextInt(181) + 20, rand.nextInt(181) + 20, "stone");
        }
    }

    public Structure[] getStructureList() {
        return structureList;
    }

    public Entity[] getEntityList() {
        return entityList;
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000/tickrate.Tickrate;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;


        while(gameThread != null){

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            //per tick
            if(delta >= 1){
                for(iterator2 = 0; iterator2 < entityList.length; iterator2++){
                    entityList[iterator2].update(); //update nn
                }
                this.update();
                //repaint();
                delta--;
                drawCount++;
            }

            if(timer >= 1000000000){
                System.out.println("Tickrate: " + drawCount);
                drawCount = 0;
                timer = 0;
            }
        }
    }

    public void update(){

        for(iterator2 = 0; iterator2 < entityList.length; iterator2++){ //player iterator
            currEntity = entityList[iterator2];

            

            //controls
            if(currEntity.outputs == 1){
                currEntity.direction = "right"; //visual
                if(currEntity.onGround){
                    currEntity.force_X += currEntity.speed;
                }else{
                    currEntity.force_X += currEntity.airSpeed;
                }
            }
            if(currEntity.outputs == 2){
                currEntity.direction = "left"; //visual
                if(currEntity.onGround){
                    currEntity.force_X -= currEntity.speed;
                }else{
                    currEntity.force_X -= currEntity.airSpeed;
                }
            }
            if(currEntity.outputs == 3 && currEntity.onGround){
                currEntity.direction = "jump"; //visual
                currEntity.force_Y_down = -currEntity.jump_F;
                currEntity.onGround = false;
            }

            
            //collision detection
            
            //check onGround
            currEntity.onGround = false;
            for(iterator = 0; iterator < structureList.length; iterator++){
                currStructure = structureList[iterator];
                if(currEntity.y + currEntity.height == currStructure.y
                && currEntity.x + currEntity.width > currStructure.x
                && currEntity.x < currStructure.x + currStructure.width){
                    currEntity.onGround = true;
                }
            }

            //gravity
            if(!currEntity.onGround){
                currEntity.force_Y_down += currEntity.gravity;
            }

            //nur wenn player y-force hat
            if(currEntity.force_Y_down != 0){

                if(currEntity.force_Y_down > 0){
                    currEntity.force_Y_down = Math.min(currEntity.force_Y_down, currEntity.maxSpeed_Y);
                }else{
                    currEntity.force_Y_down = Math.max(currEntity.force_Y_down, -currEntity.maxSpeed_Y);
                }

                currEntity.y += currEntity.force_Y_down;

                //Y resolve
                for(iterator = 0; iterator < structureList.length; iterator++){ //walls iterator
                    currStructure = structureList[iterator];

                    //collision detection
                    if((currEntity.y + currEntity.height > currStructure.y)
                    && !(currEntity.y >= currStructure.y + currStructure.height)
                    && (currEntity.x + currEntity.width > currStructure.x)
                    && !(currEntity.x >= currStructure.x + currStructure.width)){
                        
                        //down or up
                        if(currEntity.force_Y_down > 0){

                            //move player
                            currEntity.y = currStructure.y - currEntity.height;
                            currEntity.force_Y_down = 0;
                            currEntity.onGround = true; //exlusive on fall
                        }else{

                            //move player
                            currEntity.y = currStructure.y + currStructure.height;
                            currEntity.force_Y_down = 0;
                        }
                    }

                    //walls iterator end
                }

                //physics Y
                currEntity.force_Y_down *= currEntity.luftwiderstand;
            }

            //nur wenn player x-force hat
            if(currEntity.force_X != 0){

                if(currEntity.force_X > 0){
                    currEntity.force_X = Math.min(currEntity.force_X, currEntity.maxSpeed);
                }else{
                    currEntity.force_X = Math.max(currEntity.force_X, -currEntity.maxSpeed);
                }
                currEntity.x += currEntity.force_X;

                //X resolve
                for(iterator = 0; iterator < structureList.length; iterator++){//walls iterator
                    currStructure = structureList[iterator];

                    //collision detection
                    if((currEntity.y + currEntity.height > currStructure.y)
                    && !(currEntity.y >= currStructure.y + currStructure.height)
                    && (currEntity.x + currEntity.width > currStructure.x)
                    && !(currEntity.x >= currStructure.x + currStructure.width)){
                    
                        //right or left
                        if(currEntity.force_X > 0){

                            //move player
                            currEntity.x = currStructure.x - currEntity.width; //-.1
                            currEntity.force_X = 0;
                        }else{

                            //move player
                            currEntity.x = currStructure.x + currStructure.width; //+.1
                            currEntity.force_X = 0;
                        }
                    }

                    //walls iterator end
                }
            }

            //no sliding
            if(currEntity.onGround){
                currEntity.force_X = 0;
            }

            entityList[iterator2].updateTexture();
            //player iterator end
        }

        //update end
    }        

    //@Override
    //public void paint(Graphics g) { // paint() method
	//	super.paint(g);
	//	for(it = 0; it < structureList.length; it++){
    //        currStructure = structureList[it];

    //        g.drawImage(currStructure.getImg(), (int)currStructure.x, (int)currStructure.y, null);
    //    }

    //    for(it = 0; it < entityList.length; it++){
    //        currEntity = entityList[it];

    //        g.drawImage(currEntity.getImg(), (int)currEntity.x, (int)currEntity.y, null);
    //    }
	//}
}