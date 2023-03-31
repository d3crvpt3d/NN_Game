import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Player extends Entity{

    private int inputSize;

    public Player(Double x, Double y, Structure[] strucList, DeltaTime deltaTime){
        this.strucList = strucList;
        this.x = x;
        this.y = y;
        this.deltaTime = deltaTime;

        try{
            img = ImageIO.read(new File("bin/character_right.png"));
        }catch(IOException e){
            System.out.println(e);
        }
        setPlayerImages();
        setDefaultValues();

        input = new double[inputSize];
    }

    //CHANGE DEFAULT VALUES
    public void setDefaultValues(){
        speed = 13;                     // (beschleunigung) 15pix/tick^2
        maxSpeed = 13;                  // (max ground speed) 15pix/tick
        maxSpeed_Y = 15;                // (max fall speed) 16pix/tick
        airSpeed = .3;                  // (adjustierung im fall) 0,012pix/tick^2
        gravity = .5;
        jump_F = 500;
        width = 32;
        height = 64;
        direction = "right";
        force_X = 0;
        force_Y_down = 0;
        vx = 0;
        vy = 0;
        inputSize = 7;                          //INPUT SIZE (need to add inputs in "Entity" if more then 5 are used)
        hiddenlayer_arr = new int[2];
        hiddenlayer_arr[0] = 4;
        hiddenlayer_arr[1] = 4;

        nn = new NeuralNetwork(inputSize, hiddenlayer_arr, 4);
    }

    public void setPlayerImages(){
        try{
            jump = ImageIO.read(new File("bin/character_jump.png"));
            right = ImageIO.read(new File("bin/character_right.png"));
            left = ImageIO.read(new File("bin/character_left.png"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

}