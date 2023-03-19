import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    
    Fps FPS = new Fps(144); //set FPS/tickrate

    Structure[] structureList = new Structure[7]; // anzahl der structures
    Entity[] entityList = new Entity[10]; //anzahl der player

    //for iteration
    Entity currEntity;
    Structure currStructure;

    //collision variables
    boolean collision = false;

    //iteratoren
    int iterator;
    int iterator2;

    Thread gameThread;
    Player player;
    KeyHandler keyHgp;

    public GamePanel(KeyHandler keyHgp){

        this.keyHgp = keyHgp;
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.setBackground(Color.white);

        //declare Entity's and Structures
        entityList[0] = new Player(FPS, 400., 400.);
        entityList[1] = new Player(FPS, 350., 600.);
        entityList[2] = new Player(FPS, 350., 600.);
        entityList[3] = new Player(FPS, 350., 600.);
        entityList[4] = new Player(FPS, 350., 600.);
        entityList[5] = new Player(FPS, 350., 600.);
        entityList[6] = new Player(FPS, 350., 600.);
        entityList[7] = new Player(FPS, 350., 600.);
        entityList[8] = new Player(FPS, 350., 600.);
        entityList[9] = new Player(FPS, 350., 600.);

        structureList[0] = new Walls(0,1060,1920,20, "stone");
        structureList[1] = new Walls(0,0,20,1080, "stone");
        structureList[2] = new Walls(1900,0,20,1080, "stone");
        structureList[3] = new Walls(0,0,1920,20, "stone");
        structureList[4] = new Walls(20,960,200,10);
        structureList[5] = new Walls(1700,860,200,10);
        structureList[6] = new Walls(20,760,200,10);
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {

        double drawInterval = 1000000000/FPS.FPS;
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
                    entityList[iterator2].updateTexture(); //update textures
                }
                this.update();
                repaint();
                delta--;
                drawCount++;
            }

            if(timer >= 1000000000){
                System.out.println("FPS: " + drawCount);
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
                currEntity.direction = "right";
                if(currEntity.onGround){
                    currEntity.force_X += currEntity.speed;
                }else{
                    currEntity.force_X += currEntity.airSpeed;
                }
            }
            if(currEntity.outputs == 2){
                currEntity.direction = "left";
                if(currEntity.onGround){
                    currEntity.force_X -= currEntity.speed;
                }else{
                    currEntity.force_X -= currEntity.airSpeed;
                }
            }
            if(currEntity.outputs == 3 && currEntity.onGround){
                currEntity.direction = "jump"; //visual
                currEntity.force_Y_down = -currEntity.gravity*80;
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

            //player iterator end
        }

        //update end
    }        

    @Override
    public void paint(Graphics g) { // paint() method
		super.paint(g);
		for(int i = 0; i < structureList.length; i++){
            currStructure = structureList[i];

            g.drawImage(currStructure.getImg(), (int)currStructure.x, (int)currStructure.y,this);
        }
        for(int j = 0; j < entityList.length; j++){
            currEntity = entityList[j];

            g.drawImage(currEntity.getImg(), (int)currEntity.x, (int)currEntity.y, this);
        }
	}
}