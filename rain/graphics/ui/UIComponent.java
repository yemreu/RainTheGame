
package rain.graphics.ui;

import java.awt.Color;
import java.awt.Graphics;
import rain.graphics.Screen;
import rain.util.Vector2i;


public class UIComponent {
    
    public Color color;
    public Vector2i position;
    protected Vector2i offset;
    
    public UIComponent(Vector2i position){
        this.position = position;
        offset = new Vector2i();
    }
    
    public UIComponent setColor(int color){
        this.color = new Color(color);
        return this;
    }
    
    public void update(){
        
    }
    
    public void render(Graphics g){
        
    }
    
    void setOffset(Vector2i offset){
        this.offset = offset;
    }
}
