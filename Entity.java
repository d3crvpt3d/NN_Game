import java.awt.image.BufferedImage;

public class Entity implements GetImage{
    double x, y, width, height, force_X, force_Y_down, luftwiderstand, speed, airSpeed,
    gravity, maxSpeed, maxSpeed_Y, jump_F, ydis, minydis, xdis, minxdis, sec_minxdis, sec_minydis;
    boolean onGround;
    BufferedImage right, left, jump;
    String direction;
    BufferedImage img;
    double[] input;
    int outputs, it;

    Structure[] strucList;

    NeuralNetwork nn;
    int[] hiddenlayer_arr;

    @Override
    public BufferedImage getImg() {
        return img;
    }

    public void update(){

        updateInputs();

        outputs = nn.getOutput(input);
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
        input[2] = force_X;
        input[3] = force_Y_down;
        input[4] = boolToDouble(onGround);
        input[5] = sec_minxdis;
        input[6] = sec_minydis;
    }

    void updateDistances(){

        minxdis = 10000;
        minydis = 10000;

        for(it = 0; it < strucList.length; it++){

            xdis = Math.abs(this.x - strucList[it].x);
            ydis = Math.abs(this.y - strucList[it].y);

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
}