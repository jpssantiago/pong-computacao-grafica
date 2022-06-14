package keyboard;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import pong.Pong;
import pong.scenes.Level1Scene;

public class Level1Keyboard implements KeyListener {
    private Level1Scene level1Scene;
    
    public Level1Keyboard(Level1Scene level1Scene) {
        this.level1Scene = level1Scene;
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                Pong.exitGame();
                break;
            case KeyEvent.VK_SPACE:
                Pong.togglePaused();
                break;
            case KeyEvent.VK_LEFT:
                level1Scene.moveLeft();
                break;
            case KeyEvent.VK_RIGHT:
                level1Scene.moveRight();
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
