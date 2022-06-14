package pong.scenes;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import pong.Pong;
import utils.TextUtils;

// TODO: Trocar Level1Scene para GameScene e controlar tudo somente pelo Pong.level
public class GameScene implements GLEventListener {
    private float xMin, xMax, yMin, yMax;
    private GLU glu;
    
    private float barX1;
    private float barX2;
    private float barIncrement = 0.05f;
    
    private float ballXPosition = 0;
    private float ballYPosition;
    private float ballYIncrement = -0.01f;
    private float ballXIncrement;
    
    private int maxWidth;
    
    public GameScene(int maxWidth) {
        this.maxWidth = maxWidth;
        startLevel();
    }
    
    private void startLevel() {        
        ballXIncrement = 0.004f;
        ballYPosition = 0.9f;
        
        barX1 = -0.15f;
        barX2 = 0.15f;
        
        if (Math.random() < 0.5) {
            ballXIncrement *= -1;
        }
    }
    
    // TODO: Trocar direção de X da bola dependendo de onde a bola bater no bastão.
    public void moveBall() {
        if (!Pong.paused) {
            ballYPosition += ballYIncrement;
            ballXPosition += ballXIncrement;
            
            if (ballYPosition >= yMax - 0.08f) {
                ballYIncrement *= -1;
            }
            
            if (ballXPosition <= xMin + 0.04f) {
                ballXIncrement *= -1;
            }
            
            if (ballXPosition >= xMax - 0.04f) {
                ballXIncrement *= -1;
            }
            
            if (ballYPosition <= yMin + 0.08f) {
                if (ballXPosition >= barX1 && ballXPosition <= barX2) {
                    ballYIncrement *= -1;
                    Pong.points += Pong.pointsIncrement;
                } else {
                    startLevel();
                    Pong.lives--;
                    
                    if (Pong.lives == 0) {
                        ballYPosition = 0.9f;
                        ballXPosition = 0f;
                        Pong.paused = true;
                        Pong.gameOver = true;
                    }
                }
            }
        }
    }
    
    // TODO: Mover bastão com MOUSE também.
    public void moveLeft() {
        if (barX1 - barIncrement > xMin - 0.05f && !Pong.paused) {
            System.out.println(barX1 - barIncrement);
            System.out.println(xMin);
            barX1 -= barIncrement;
            barX2 -= barIncrement;
        }
    }
    
    public void moveRight() {
        if (barX2 + barIncrement < xMax + 0.05f && !Pong.paused) {
            barX1 += barIncrement;
            barX2 += barIncrement;
        }
    }
    
    public void moveBarWithMouse(int x) {
        float oldRange = (maxWidth - 0);  // Get width of window.
        float newRange = (xMax - xMin);
        float newValue = (((x - xMin) * newRange) / oldRange) + xMin;
        
        barX1 = newValue - 0.15f;
        barX2 = newValue + 0.15f;
        
        if (barX1 < xMin) {
            barX1 = xMin;
            barX2 = xMin + 0.3f;
        }
        
        if (barX2 > xMax) {
            barX1 = xMax - 0.3f;
            barX2 = xMax + 0.3f;
        }
    }
   
    @Override
    public void init(GLAutoDrawable drawable) {
        glu = new GLU();
        
        xMin = yMin = -1;
        xMax = yMax = 1;
    }
    
    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();   
        gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();
        
        if (Pong.points == 200) {
            Pong.paused = true;
            Pong.level = 2;
        }
        
        Pong.drawBall(gl, ballXPosition, ballYPosition);
        Pong.drawBar(gl, barX1, barX2);
        Pong.drawPointsIndicator(drawable);
        Pong.drawLivesIndicator(gl);
        Pong.drawPausedIndicator(drawable);
                
        if (!Pong.paused) {
            moveBall();
        }
        
        if (Pong.gameOver) {
            int width = drawable.getSurfaceWidth();
            int height = drawable.getSurfaceHeight();
            
            TextUtils.drawText(drawable, "Você perdeu! Para recomeçar, pressione ESPAÇO.", 30, width / 2, height / 2);
        }
        
        gl.glFlush();
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {}

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {}
}
