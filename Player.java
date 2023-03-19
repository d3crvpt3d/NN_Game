import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Player extends Entity implements GetImage{
    //GamePanel gp;

    Fps FPS;
    BufferedImage right;
    BufferedImage left;
    BufferedImage jump;

    BufferedImage image;

    public Player(Fps FPS){
        //this.gp = gp;
        this.FPS = FPS;
        try{
            image = ImageIO.read(new File("bin/character_right.png"));
        }catch(IOException e){
            System.out.println(e);
        }
        setPlayerImages();
        setDefaultValues();
    }

    public void setDefaultValues(){
        speed = 800/FPS.FPS;// 400pix/s
        maxSpeed = 800/FPS.FPS;// 800pix/s (max ground speed)
        maxSpeed_Y = 1000/FPS.FPS; // 1000pix/s (max fall speed)
        airSpeed = speed * .015;
        gravity = 12/(double)FPS.FPS;// 12pix/s
        x=640;
        y=200;
        width = 32;
        height = 64;
        luftwiderstand = .99;
        direction = "right";
        force_X = 0;
        force_Y_down = 0;
        onGround = false;
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

    public BufferedImage getImg(){
        return image;
    }

    public void update(){

        switch(direction){
        case "right":
            image = right;
            break;
        case "left":
            image = left;
            break;
        case "jump":
            image = jump;
        }
    }
}