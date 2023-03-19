import java.awt.image.BufferedImage;

public class Structure implements GetImage{
    public double x, y, width, height;
    public String texture;
    BufferedImage img;
    
    @Override
    public BufferedImage getImg() {
        return img;
    }
}