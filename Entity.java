import java.awt.image.BufferedImage;

public class Entity extends Thread{
    double x, y, width, height, force_X, force_Y_down, vx, vy, luftwiderstand, speed, airSpeed,
    gravity, maxSpeed, maxSpeed_Y, jump_F, ydis, minydis, xdis, minxdis, sec_minxdis, sec_minydis, score = 0;
    boolean onGround;
    BufferedImage right, left, jump;
    String direction;
    BufferedImage img;
    double[] input;
    int outputs, it, tickrate;

    DeltaTime deltaTime;

    Structure[] strucList;

    NeuralNetwork nn;
    int[] hiddenlayer_arr;

    public BufferedImage getImg() {
        return img;
    }

    public int getTicksPerSecond(){
        return this.tickrate;
    }

    @Override
    public void run(){
        long lastTime, currTime, timeToSleep, millsec, counter = System.nanoTime();
        int ticks = 0;

        while(true){

            lastTime = System.nanoTime();

            update();
            ticks++;

            currTime = System.nanoTime();

            timeToSleep = Math.max(0, deltaTime.drawInterval - (currTime - lastTime));
            millsec = (long)(timeToSleep*0.000001);

            if(currTime - counter >= 1000000000){
                this.tickrate = ticks;
                ticks = 0;
                counter = currTime;
            }

            try {
                Thread.sleep(millsec, (int)(timeToSleep-millsec*1000000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    void updateTexture(){
        switch(direction){
            case "right":
                img = right;
                break;
            case "left":
                img = left;
                break;
            case "jump":
                img = jump;
            }
    }

    //SET INPUTS
    private void updateInputs(){

        updateDistances();

        input[0] = minxdis;
        input[1] = minydis;
        input[2] = vx;
        input[3] = vy;
        input[4] = boolToDouble(onGround);
        input[5] = sec_minxdis;
        input[6] = sec_minydis;
    }

    void updateDistances(){

        minxdis = 10000;
        minydis = 10000;

        for(it = 0; it < strucList.length; it++){

            xdis = Math.abs(x - strucList[it].x);
            ydis = Math.abs(y - strucList[it].y);

            if(xdis < minxdis){
                minxdis = xdis;
            }else if(xdis < sec_minxdis){
                sec_minxdis = xdis;
            }

            if(ydis < minydis){
                minydis = ydis;
            }else if(ydis < sec_minydis){
                sec_minydis = ydis;
            }
        }
    }

    private double boolToDouble(boolean b){
        if(b){
            return 1;
        }
        return  0;
    }

    public void update() {

        //score
        score += 100* 1/y;

        updateInputs();

        outputs = nn.getOutput(input);
        
        switch(outputs){
            case 0: ;
            case 1: vx = speed;
            case 2: vx = -speed;
            case 3: force_Y_down = -jump_F;
        }

        //X Physics
        vx += force_X;
        x += vx;

        //coll resolve X
        if(vx != 0){
            if(vx > 0){
                for (Structure st : strucList) {
                    if(x + width > st.x && st.x + width > x
                    && y + height > st.y && st.y + height > y){
                        x = st.x - width;
                        force_X = 0;
                        vx = 0;
                    }
                }

            }else{
                for (Structure st : strucList) {
                    if(x + width > st.x && st.x + width > x
                    && y + height > st.y && st.y + height > y){
                        x = st.x + st.width;
                        force_X = 0;
                        vx = 0;
                    }
                }
                
            }

        }

        //should work TODO
        onGround = false;
        for(Structure struc: strucList){
            if(y+height == struc.y){
                onGround = true;
            }
        }

        if(!onGround){
            force_Y_down += gravity;
        }

        //Y Physics
        vy += force_Y_down;
        y += vy;

        //coll resolve Y
        if(vy != 0){
            if(vy > 0){
                for (it = 0; it < strucList.length; it++) {
                    if(x + width > strucList[it].x && strucList[it].x + width > x
                    && y + height > strucList[it].y && strucList[it].y + height > y){
                        y = strucList[it].y - height;
                        force_Y_down = 0;
                        vy = 0;
                    }
                }

            }else{
                for (it = 0; it < strucList.length; it++) {
                    if(x + width > strucList[it].x && strucList[it].x + width > x
                    && y + height > strucList[it].y && strucList[it].y + height > y){
                        y = strucList[it].y + strucList[it].height;
                        force_Y_down = 0;
                        vy = 0;
                    }
                }
                
            }

        }

        //run end
    }
}