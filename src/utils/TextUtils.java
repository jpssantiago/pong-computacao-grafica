package utils;

import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.util.awt.TextRenderer;
import java.awt.Font;
import java.awt.geom.Rectangle2D;

public class TextUtils {
    public static void drawText(GLAutoDrawable drawable, String text, int fontSize, int x, int y) {
        drawText(drawable, text, fontSize, x, y, true);
    }
    
    public static void drawText(GLAutoDrawable drawable, String text, int fontSize, int x, int y, boolean centered) {
        TextRenderer textRenderer = new TextRenderer(new Font("SansSerif", Font.BOLD, fontSize));
        
        Rectangle2D bounds = textRenderer.getBounds(text);
        int width = (int) bounds.getWidth();
        int height = (int) bounds.getHeight();
        
        textRenderer.beginRendering(drawable.getSurfaceWidth(), drawable.getSurfaceHeight());
        textRenderer.setColor(1, 1, 1, 1);
        textRenderer.draw(text, centered ? x - (width / 2) : x, centered ? y - (height / 2) : y);
        textRenderer.endRendering();
        
        textRenderer.dispose();
    }
}
