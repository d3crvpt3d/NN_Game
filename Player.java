import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Player extends Entity{

    private Tickrate tickrate;
    private int inputSize;

    public Player(Tickrate tickrate, Double x, Double y){

        this.tickrate = tickrate;
        this.x = x;
        this.y = y;

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
        speed = 800/tickrate.Tickrate;                    // (beschleunigung)
        maxSpeed = 800/tickrate.Tickrate;                 // (max ground speed)
        maxSpeed_Y = 1000/tickrate.Tickrate;              // (max fall speed)
        airSpeed = speed * .015;
        gravity = 12/(double)tickrate.Tickrate;
        width = 32;
        height = 64;
        luftwiderstand = .99;
        direction = "right";
        force_X = 0;
        force_Y_down = 0;
        onGround = false;
        inputSize = 5;                          //INPUT SIZE
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