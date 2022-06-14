package mouse;

import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;
import pong.scenes.GameScene;

public class GameMouse implements MouseListener {
    
    private GameScene gameScene;
    
    public GameMouse(GameScene gameScene) {
        this.gameScene = gameScene;
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        gameScene.moveBarWithMouse(e.getX());
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseDragged(MouseEvent e) {}

    @Override
    public void mouseWheelMoved(MouseEvent e) {}   
}
