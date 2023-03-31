import javax.swing.*;
import java.awt.*;

public class DisplayPanel extends JPanel implements Runnable{

    Structure[] strucList;
    Entity[] entityList;

    DeltaTime deltaTime;

    public boolean active = true;
    Thread displayThread;

    int maxFPS = 144; //SET MAX DISPLAY FPS
    int it;

    public DisplayPanel(Structure[] strucList, Entity[] entityList, DeltaTime deltaTime){
        this.deltaTime = deltaTime;
        this.strucList = strucList;
        this.entityList = entityList;

        this.setDoubleBuffered(true);
        this.setFocusable(true);
        this.setBackground(Color.lightGray);
    }

    void startGameThread(){
        Thread displayThread = new Thread(this);
        displayThread.start();
    }

    @Override
    public void run() {
        long drawInterval = 1000000000/maxFPS; //optimized from 1000000000/maxFPS, so it uses * in loop instead of /
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;
        long sleepTime;
        long millsec;

        while(active){

            currentTime = System.nanoTime();    // currTime = current Time
            timer += (currentTime - lastTime);  // timer += execution time
            lastTime = currentTime;             // lastTime = preparation for next execution

            //per tick
            repaint();
            drawCount++;

            if(timer >= 1000000000){
                System.out.println("FPS: " + drawCount);
                drawCount = 0;
                timer = 0;
            }

            sleepTime = Math.max(0,drawInterval - (System.nanoTime() - lastTime));
            millsec = (long)(sleepTime*0.000001);

            //sleep till new tick
            try {
                Thread.sleep(millsec,(int)(sleepTime-millsec*1000000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public void paint(Graphics g) { // paint() method
        super.paint(g);

        for(int s = 0; s < strucList.length; s++){
            g.drawImage(strucList[s].getImg(), (int)strucList[s].x, (int)strucList[s].y, null);
        }

        for(int e = 0; e < entityList.length; e++){
            g.drawImage(entityList[e].getImg(),
            (int)(.5 * entityList[e].force_X * deltaTime.deltaTime*deltaTime.deltaTime + entityList[e].vx * deltaTime.deltaTime + entityList[e].x),
            (int)(.5 * entityList[e].force_Y_down * deltaTime.deltaTime*deltaTime.deltaTime + entityList[e].vy * deltaTime.deltaTime + entityList[e].y),
            null);
        }
        
	}
}
