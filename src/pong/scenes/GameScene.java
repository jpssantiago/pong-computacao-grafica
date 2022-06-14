package pong.scenes;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import java.util.Random;
import pong.Pong;
import utils.TextUtils;

public class GameScene implements GLEventListener {
    private float xMin, xMax, yMin, yMax;
    
    private float barX1;
    private float barX2;
    private float barIncrement = 0.05f;
    
    private float ballXPosition = 0;
    private float ballYPosition;
    private float ballYIncrement;
    private float ballXIncrement;
    
    private int maxWidth;
    
    public GameScene(int maxWidth) {
        this.maxWidth = maxWidth;
        startLevel();
    }
    
    private void startLevel() {
        if (Pong.level == 1) {
            ballXIncrement = 0.008f;
            ballYIncrement = -0.012f;
        } else {
            ballXIncrement = 0.014f;
            ballYIncrement = 0.03f;
        }
        
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
            
            // Colisão com as laterais
            if (ballYPosition >= yMax - 0.08f) {
                ballYIncrement *= -1;
            }
            
            if (ballXPosition <= xMin + 0.04f) {
                ballXIncrement *= -1;
            }
            
            if (ballXPosition >= xMax - 0.04f) {
                ballXIncrement *= -1;
            }
            
            // Colisão com o objeto da 2 fase.
            if (Pong.level == 2) {
                if (ballXPosition >= -0.2f && ballXPosition <= 0.2f) {
                    if (ballYPosition >= -0.21 && ballYPosition <= -0.19f) {
                        ballYIncrement *= -1;
                    }
                }
                
                if (ballYPosition >= 0 && ballYPosition <= 0.2f) {
                    if (ballXPosition >= 0 && ballXPosition <= 0.1f) {
                        ballXIncrement *= -1;
                        ballYIncrement *= -1;
                    } else if (ballXPosition >= -0.1f && ballXPosition <= 0) {
                        ballXIncrement *= -1;
                        ballYIncrement *= -1;
                    }
                } else if (ballYPosition >= -0.2f && ballYPosition <= 0) {
                    if (ballXPosition >= 0 && ballXPosition <= 0.2f) {
                        ballXIncrement *= -1;
                    } else if (ballXPosition >= -0.2f && ballXPosition <= 0) {
                        ballXIncrement *= -1;
                    }
                }
            }
            
            if (ballYPosition <= yMin + 0.08f) {
                if (ballXPosition >= barX1 && ballXPosition <= barX2) {
                    ballYIncrement *= -1;
                    Pong.points += Pong.pointsIncrement;
                    
                    boolean invert = new Random().nextBoolean();
                    if (invert) {
                        ballXIncrement *= -1;
                    }
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
        if (Pong.paused) return;
        
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
    
    private void drawLevel2Object(GL2 gl) {
        gl.glPushMatrix();
        gl.glColor3f(0.2f, 0.2f, 0.2f);
        gl.glBegin(GL2.GL_TRIANGLES);
            gl.glVertex2f(-0.2f, -0.2f);
            gl.glVertex2f(0.2f, -0.2f);
            gl.glVertex2f(0, 0.2f);
        gl.glEnd();
        gl.glPopMatrix();
    }
   
    @Override
    public void init(GLAutoDrawable drawable) {       
        xMin = yMin = -1;
        xMax = yMax = 1;
    }
    
    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();   
        gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();
        
        if (Pong.level == 1 && Pong.points == 200) {
            Pong.level = 2;
            ballXIncrement = 0.012f;
            ballYIncrement = 0.02f;
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
        
        if (Pong.level == 2) {
            drawLevel2Object(gl);
        }
        
        gl.glFlush();
    }

    @Override
    public void dispose(GLAutoDrawable drawable) {}

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {}
}
