import java.awt.image.BufferedImage;

public class Structure implements GetImage{
    double x, y, width, height;
    String texture;
    BufferedImage img;
    
    @Override
    public BufferedImage getImg() {
        return img;
    }
}