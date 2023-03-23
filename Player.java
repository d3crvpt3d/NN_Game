import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Player extends Entity{

    private int inputSize;

    public Player(Double x, Double y, Structure[] strucList){
        this.strucList = strucList;
        this.x = x;
        this.y = y;

        try{
            img = ImageIO.read(new File("bin/character_right.png"));
        }catch(IOException e){
            System.out.println(e);
        }
        setPlayerImages();
        setDefaultValues(0);

        input = new double[inputSize];
    }

    public Player(Double x, Double y, Structure[] strucList, String new_Batch){

        this.strucList = strucList;
        this.x = x;
        this.y = y;

        try{
            img = ImageIO.read(new File("bin/character_right.png"));
        }catch(IOException e){
            System.out.println(e);
        }

        setPlayerImages();
        setDefaultValues(1);

        input = new double[inputSize];
    }

    //CHANGE DEFAULT VALUES
    public void setDefaultValues(int batch){
        speed = 13;                     // (beschleunigung) 15pix/tick^2
        maxSpeed = 13;                  // (max ground speed) 15pix/tick
        maxSpeed_Y = 15;                // (max fall speed) 16pix/tick
        airSpeed = .3;                  // (adjustierung im fall) 0,012pix/tick^2
        gravity = .5;
        jump_F = 500;
        width = 32;
        height = 64;
        luftwiderstand = .99;
        direction = "right";
        force_X = 0;
        force_Y_down = 0;
        onGround = false;
        inputSize = 7;                          //INPUT SIZE (need to add inputs in "Entity" if more then 5 are used)
        hiddenlayer_arr = new int[2];
        hiddenlayer_arr[0] = 4;
        hiddenlayer_arr[1] = 4;

        if(batch == 0){
            nn = new NeuralNetwork(inputSize, hiddenlayer_arr, 4, 0);
        }else{
            nn = new NeuralNetwork(inputSize, hiddenlayer_arr, 4, 1);
        }
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