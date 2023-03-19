import java.awt.image.BufferedImage;

public class Entity implements GetImage{
    double x, y, width, height, force_X, force_Y_down, luftwiderstand, speed, airSpeed, gravity, maxSpeed, maxSpeed_Y;
    boolean onGround;
    BufferedImage right, left, jump;
    String direction;
    BufferedImage img;
    double[] input;
    int outputs;

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

    private void updateInputs(){
        input[0] = x;
        input[1] = y;
        input[2] = force_X;
        input[3] = boolToDouble(onGround);
    }

    private double boolToDouble(boolean b){
        if(b){
            return 1;
        }
        return  0;
    }
}