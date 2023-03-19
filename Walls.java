import java.io.File;
import javax.imageio.ImageIO;
import java.io.IOException;

public class Walls extends Structure{

    //init wall with default texture
    public Walls(double x, double y, double width, double height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.texture = "default";
        updateTexture();
    }

    //init wall with other texture
    public Walls(double x, double y, double width, double height, String type){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.texture = type;
        updateTexture();
    }

    //set texture to String: texture
    void updateTexture(){
        try{
            switch(texture){
                case "default": this.img = ImageIO.read(new File("bin/wall_default.png"));
                case "stone": this.img = ImageIO.read(new File("bin/wall_stone.png"));
            }
        }catch(IOException e){
            e.printStackTrace();
        }
        img = img.getSubimage(0, 0, (int)this.width, (int)this.height);
    }
}