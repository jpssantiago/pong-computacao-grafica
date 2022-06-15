package pong;

import com.jogamp.newt.event.WindowAdapter;
import com.jogamp.newt.event.WindowEvent;
import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.FPSAnimator;
import pong.scenes.IntroductionScene;
import keyboard.IntroductionKeyboard;
import keyboard.GameKeyboard;
import mouse.GameMouse;
import pong.scenes.GameScene;
import resources.Textura;
import utils.TextUtils;

public class Pong {
    private static GLWindow window;
    
    public static int level = 1;
    public static int points = 0;
    public static int pointsIncrement = 25;
    public static int lives = 5;
    public static boolean paused = true;
    public static boolean gameOver = false;
    
    private static IntroductionScene introductionScene;
    private static IntroductionKeyboard introductionKeyboard;
    
    private static GameScene gameScene;
    private static GameKeyboard gameKeyboard;
    private static GameMouse gameMouse;
    
    public static void main(String[] args) {
        initRender();
    }
    
    public static void showIntroduction() {
        introductionScene = new IntroductionScene(window);
        window.addGLEventListener(introductionScene);
        
        introductionKeyboard = new IntroductionKeyboard();
        window.addKeyListener(introductionKeyboard);
    }
    
    public static void showGame() {
        window.removeGLEventListener(introductionScene);
        window.removeKeyListener(introductionKeyboard);
        
        gameScene = new GameScene(window.getWidth());
        window.addGLEventListener(gameScene);
        
        gameKeyboard = new GameKeyboard(gameScene);
        window.addKeyListener(gameKeyboard);
        
        gameMouse = new GameMouse(gameScene);
        window.addMouseListener(gameMouse);
    }
    
    public static void togglePaused() {
        if (gameOver) {
            lives = 5;
            gameOver = false;
        }
        
        paused = !paused;
    }
    
    public static void exitGame() {
        System.exit(0);
    }
    
    public static void initRender() {
        GLProfile.initSingleton();
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities caps = new GLCapabilities(profile);
        
        window = GLWindow.create(caps);
        window.setResizable(false);
        window.setTitle("Pong");
        window.setFullscreen(true);
        
        showIntroduction();
        
        FPSAnimator animator = new FPSAnimator(window, 60); // 144
        animator.start();
        
        window.addWindowListener(new WindowAdapter() {
            @Override
            public void windowDestroyNotify(WindowEvent e) {
                animator.stop();
                System.exit(0);
            }
        });
               
        window.setVisible(true);
        window.setPointerVisible(false);
    }
}
