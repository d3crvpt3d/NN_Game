import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable{
    
    Fps FPS = new Fps(144); //set FPS

    Structure[] structureList = new Structure[7]; // anzahl der structures
    Entity[] entityList = new Entity[1]; //anzahl der player

    //for iteration
    Entity currPlayer = null;
    Structure currStructure;

    Walls wall0;
    Walls wall1;
    Walls wall2;
    Walls wall3;
    Walls wall4;
    Walls wall5;
    Walls wall6;

    //collision variables
    boolean collision = false, x_coll = true, y_coll = false;
    Structure coll_struc0;
    Structure coll_struc1;
    int iterator;
    int iterator2;

    boolean x_rearrange, y_rearrange;

    Thread gameThread;
    Player player;
    KeyHandler keyHgp;

    public GamePanel(KeyHandler keyHgp){
        player = new Player(FPS);

        wall0 = new Walls(0,1060,1920,20, "stone");
        wall1 = new Walls(0,0,20,1080, "stone");
        wall2 = new Walls(1900,0,20,1080, "stone");
        wall3 = new Walls(0,0,1920,20, "stone");
        wall4 = new Walls(20,1800,200,10);
        wall5 = new Walls(860,1700,200,10);
        wall6 = new Walls(20,1600,200,10);

        this.keyHgp = keyHgp;
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.setBackground(Color.white);
        player.setDefaultValues();

        entityList[0] = player;

        structureList[0] = wall0;
        structureList[1] = wall1;
        structureList[2] = wall2;
        structureList[3] = wall3;
        structureList[4] = wall4;
        structureList[5] = wall5;
        structureList[6] = wall6;
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

            if(delta >= 1){
                player.update();
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

        //controls for player
        if(keyHgp.aPressed){
            player.direction = "left";
            if(player.onGround){
                player.force_X -= player.speed;
            }else{
                player.force_X -= player.airSpeed;
            }
        }
        if(keyHgp.dPressed){
            player.direction = "right";
            if(player.onGround){
                player.force_X += player.speed;
            }else{
                player.force_X += player.airSpeed;
            }
        }

        if(keyHgp.spacePressed && player.onGround){
            player.direction = "jump"; //visual
            player.force_Y_down = -player.gravity*80;
            player.onGround = false;
        }

       
        //collision detection
        for(iterator2 = 0; iterator2 < entityList.length; iterator2++){ //player iterator
            currPlayer = entityList[iterator2];

            //set onGround
            for(iterator = 0; iterator < structureList.length; iterator++){
                currStructure = structureList[iterator];
                if(currPlayer.y + currPlayer.height == currStructure.y){
                    currPlayer.onGround = true;
                }
            }

            //gravity
            if(!currPlayer.onGround){
                currPlayer.force_Y_down += currPlayer.gravity;
            }

            //nur wenn player y-force hat
            if(currPlayer.force_Y_down != 0){

                if(currPlayer.force_Y_down > 0){
                    currPlayer.force_Y_down = Math.min(currPlayer.force_Y_down, currPlayer.maxSpeed_Y);
                }else{
                    currPlayer.force_Y_down = Math.max(currPlayer.force_Y_down, -currPlayer.maxSpeed_Y);
                }

                currPlayer.y += currPlayer.force_Y_down;

                //Y resolve
                for(iterator = 0; iterator < structureList.length; iterator++){ //walls iterator
                    currStructure = structureList[iterator];

                    //collision detection
                    if((currPlayer.y + currPlayer.height > currStructure.y)
                    && !(currPlayer.y > currStructure.y + currStructure.height)
                    && (currPlayer.x + currPlayer.width > currStructure.x)
                    && !(currPlayer.x >= currStructure.x + currStructure.width)){
                        
                        //down or up
                        if(currPlayer.force_Y_down > 0){

                            //move player
                            currPlayer.y = currStructure.y - currPlayer.height;
                            currPlayer.force_Y_down = 0;
                            currPlayer.onGround = true; //exlusive on fall
                        }else{

                            //move player
                            currPlayer.y = currStructure.y + currStructure.height;
                            currPlayer.force_Y_down = 0;
                        }
                    }

                    //walls iterator end
                }

                //physics Y
                currPlayer.force_Y_down *= currPlayer.luftwiderstand;
            }

            //nur wenn player x-force hat
            if(currPlayer.force_X != 0){

                if(currPlayer.force_X > 0){
                    currPlayer.force_X = Math.min(currPlayer.force_X, currPlayer.maxSpeed);
                }else{
                    currPlayer.force_X = Math.max(currPlayer.force_X, -currPlayer.maxSpeed);
                }
                currPlayer.x += currPlayer.force_X;

                //X resolve
                for(iterator = 0; iterator < structureList.length; iterator++){//walls iterator
                    currStructure = structureList[iterator];

                    //collision detection
                    if((currPlayer.y + currPlayer.height > currStructure.y)
                    && !(currPlayer.y > currStructure.y + currStructure.height)
                    && (currPlayer.x + currPlayer.width > currStructure.x)
                    && !(currPlayer.x >= currStructure.x + currStructure.width)){
                    
                        //right or left
                        if(currPlayer.force_X > 0){

                            //move player
                            currPlayer.x = currStructure.x - currPlayer.width; //-.1
                            currPlayer.force_X = 0;
                        }else{

                            //move player
                            currPlayer.x = currStructure.x + currStructure.width; //+.1
                            currPlayer.force_X = 0;
                        }
                    }

                    //walls iterator end
                }
            }

            //no sliding
            if(currPlayer.onGround){
                currPlayer.force_X = 0;
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

            g.drawImage(currStructure.getImg(), (int)currStructure.x, (int)currStructure.y,null);
        }
        for(int j = 0; j < entityList.length; j++){
            currPlayer = entityList[j];

            g.drawImage(currPlayer.getImg(), (int)currPlayer.x, (int)currPlayer.y, null);
        }
	}
}