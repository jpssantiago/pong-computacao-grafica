package pong.scenes;

import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;
import utils.TextUtils;

public class IntroductionScene implements GLEventListener {
    private float xMin, xMax, yMin, yMax, zMin, zMax;
    private GLU glu;
    
    private GLWindow window;
    
    public IntroductionScene(GLWindow window) {
        this.window = window;
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        glu = new GLU();
        
        xMin = yMin = zMin = -1;
        xMax = yMax = zMax = 1;
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();   
        gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT);
        gl.glLoadIdentity();
        
        int width = drawable.getSurfaceWidth();
        int height = drawable.getSurfaceHeight();
        
        TextUtils.drawText(drawable, "Você deve controlar a barra inferior (bastão) utilizando os comandos descritos abaixo e impedir que a bola ultrapasse a borda inferior.", 20, width / 2, (height / 2) + 90);
        TextUtils.drawText(drawable, "Você começa com 5 vidas e o seu saldo de vidas vai diminuindo conforme você não consegue rebater a bola usando o bastão.", 20, width / 2, (height / 2) + 60);
        TextUtils.drawText(drawable, "Ao atingir 200 pontos você irá avançar para a fase 2. A velocidade da bola aumentará e, consequentemente, o grau de dificuldade também.", 20, width / 2, (height / 2) + 30);
        
        TextUtils.drawText(drawable, "ENTER - Iniciar jogo", 20, width / 2, (height / 2) - 30);
        TextUtils.drawText(drawable, "ESPAÇO - Pausar ou retomar jogo", 20, width / 2, (height / 2) - 60);
        TextUtils.drawText(drawable, "ESC - Fechar o jogo", 20, width / 2, (height / 2) - 90);
        TextUtils.drawText(drawable, "Seta para a esquerda - Mover barra para a esquerda", 20, width / 2, (height / 2) - 120);
        TextUtils.drawText(drawable, "Seta para a direita - Mover barra para a direita", 20, width / 2, (height / 2) - 150);
        
        gl.glFlush();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {}
    
    @Override
    public void dispose(GLAutoDrawable drawable) {}
}
