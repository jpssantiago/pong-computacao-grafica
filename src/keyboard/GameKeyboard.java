package keyboard;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import pong.Pong;
import pong.scenes.GameScene;

public class GameKeyboard implements KeyListener {
    private GameScene level1Scene;
    
    public GameKeyboard(GameScene level1Scene) {
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
