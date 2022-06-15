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
    
    public static void drawBall(GL2 gl, float xPosition, float yPosition) {
        int vertices = 50;
        double radius = 0.5;
        double angle = 0;
        double angleIncrement = 2 * Math.PI / vertices;
        
        gl.glPushMatrix();
        gl.glTranslatef(xPosition, yPosition, 0);
        gl.glScalef(0.08f, 0.13f, 0.08f);
        gl.glColor3f(1, 1, 1);
        gl.glBegin(GL2.GL_POLYGON);
            for (int z = 0; z < vertices; z++) {
                angle = z * angleIncrement;
                double x = radius * Math.cos(angle);
                double y = radius * Math.sin(angle);
                gl.glVertex2d(x, y);
            }
        gl.glEnd();
        gl.glPopMatrix();
    }
    
    public static void drawBar(GL2 gl, float x1, float x2) {
        gl.glPushMatrix();
        gl.glColor3f(1, 1, 1);
        gl.glBegin(GL2.GL_QUADS);
            gl.glVertex2f(x1, -1f);
            gl.glVertex2f(x1, -0.97f);
            gl.glVertex2f(x2, -0.97f);
            gl.glVertex2f(x2, -1f);
        gl.glEnd();
        gl.glPopMatrix();
    }
    
    public static void drawPointsIndicator(GLAutoDrawable drawable) {
        int height = drawable.getSurfaceHeight();
            
        TextUtils.drawText(drawable, level + "ª Fase - " + points + " pontos", 20, 10, height - 30, false);
    }
    
    public static void drawLivesIndicator(GL2 gl, Textura texture) {
        float xPosition = -0.965f;
        float yPosition = 0.85f;
        
        for (int i = 0; i < lives; i++) {
            Pong.drawLifeObject(gl, xPosition, yPosition, texture);
            
            xPosition += 0.08f;
        }
    }
    
    public static void drawLifeObject(GL2 gl, float xPosition, float yPosition, Textura texture) {
        texture.gerarTextura(gl, "C:\\Users\\jps_s\\Documents\\NetBeansProjects\\Pong\\src\\res\\heart.png", 0);
        
        gl.glPushMatrix();
        gl.glTranslatef(xPosition, yPosition, 0);
        gl.glScalef(0.05f, 0.09f, 0.05f);
        gl.glColor3f(1, 1, 1);
        gl.glBegin(GL2.GL_QUADS);
            gl.glTexCoord2f(1, 0);
            gl.glVertex2f(-0.5f, -0.5f);
            
            gl.glTexCoord2f(1, 1);
            gl.glVertex2f(-0.5f, 0.5f);
            
            gl.glTexCoord2f(0, 1);
            gl.glVertex2f(0.5f, 0.5f);
            
            gl.glTexCoord2f(0, 0);
            gl.glVertex2f(0.5f, -0.5f);
        gl.glEnd();
        gl.glPopMatrix();
        
        texture.desabilitarTextura(gl, 0);
    }
    
    public static void drawPausedIndicator(GLAutoDrawable drawable) {
        if (Pong.paused) {
            int width = drawable.getSurfaceWidth();
            int height = drawable.getSurfaceHeight();
            
            TextUtils.drawText(drawable, "Pausado", 20, width - 95, height - 30, false);
        }
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
