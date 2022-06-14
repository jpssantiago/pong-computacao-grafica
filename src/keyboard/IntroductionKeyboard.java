package keyboard;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import pong.Pong;

public class IntroductionKeyboard implements KeyListener {   
    @Override
    public void keyPressed(KeyEvent e) {
        System.out.println("[introduction-keyboard] Key pressed: " + e.getKeyCode());
        
        switch (e.getKeyCode()) {
            case KeyEvent.VK_ESCAPE:
                Pong.exitGame();
                break;
            case KeyEvent.VK_ENTER:
                Pong.showLevel1();
                break;
            default:
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}
