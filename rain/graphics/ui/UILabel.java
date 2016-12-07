
package rain.graphics.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Font;
import rain.graphics.Screen;
import rain.util.Vector2i;

public class UILabel extends UIComponent {
    
    public String text;
    private Font font;
    
    public UILabel(Vector2i position, String text) {
        super(position);
        font = new Font("Helvetica",Font.PLAIN,32);
        this.text = text;
        color = new Color(0xff00ff);
    }
    
    public UILabel setFont(Font font){
        this.font = font;
        return this;
    }
    
    public void render(Graphics g){
        g.setFont(font);
        g.setColor(color);
        g.drawString(text, position.getX()+offset.getX(), position.getY()+offset.getY());
    }
    
}
