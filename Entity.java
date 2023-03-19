import java.awt.image.BufferedImage;

public class Entity implements GetImage{
    public double x, y, width, height, force_X, force_Y_down, luftwiderstand, speed, airSpeed, gravity, maxSpeed, maxSpeed_Y;
    public boolean onGround;
    public BufferedImage right, left, jump;
    public String direction;
    public BufferedImage img;

    @Override
    public BufferedImage getImg() {
        return img;
    }

    public void update(){

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
}