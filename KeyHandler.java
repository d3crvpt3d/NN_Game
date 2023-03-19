import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener{
    boolean aPressed, dPressed, spacePressed;
    int code;

    @Override
    public void keyTyped(KeyEvent e) {
        //
    }

    @Override
    public void keyPressed(KeyEvent e) {
        code = e.getKeyCode();

        if(code == KeyEvent.VK_A){
            aPressed = true;
        }
        if(code == KeyEvent.VK_D){
            dPressed = true;
        }
        if(code == KeyEvent.VK_SPACE){
            spacePressed = true;
        }
        if(code == KeyEvent.VK_ESCAPE){
            System.exit(0);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if(code == KeyEvent.VK_A){
            aPressed = false;
        }
        if(code == KeyEvent.VK_D){
            dPressed = false;
        }
        if(code == KeyEvent.VK_SPACE){
            spacePressed = false;
        }
    }
}
