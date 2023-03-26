import javax.swing.*;
import java.awt.*;

public class DisplayPanel extends JPanel implements Runnable{

    Structure[] strucList;
    Entity[] entiList;

    public boolean active = true;
    Thread displayThread;

    int maxFPS = 144; //SET MAX DISPLAY FPS
    int it;

    public DisplayPanel(Structure[] strucList, Entity[] entiList){
        this.strucList = strucList;
        this.entiList = entiList;

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
        double drawInterval = (double)maxFPS/1000000000; //optimized from 1000000000/maxFPS, so it uses * in loop instead of /
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while(active){

            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) * drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            //per tick
            if(delta >= 1){
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
    

    //TODO: Show other Iterations
    @Override
    public void paint(Graphics g) { // paint() method
        super.paint(g);

        for(it = 0; it < strucList.length; it++){

            g.drawImage(strucList[it].getImg(), (int)strucList[it].x, (int)strucList[it].y, null);
        }

        
        for(it = 0; it < entiList.length; it++){

            g.drawImage(entiList[it].getImg(), (int)entiList[it].x, (int)entiList[it].y, null);
        }
        
	}
}
