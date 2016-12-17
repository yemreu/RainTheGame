
package rain.graphics.ui;

import java.awt.Color;
import java.awt.Graphics;
import rain.graphics.Screen;
import rain.util.Vector2i;


public class UIComponent {
    
    public Color color;
    public Vector2i position,size;
    public boolean active = true;
    protected Vector2i offset;
    protected UIPanel panel;
    
    public UIComponent(Vector2i position){
        this.position = position;
        offset = new Vector2i();
    }
    
    public UIComponent(Vector2i position, Vector2i size){
        this.position = position;
        this.size = size;
        offset = new Vector2i();
    }
    
    public UIComponent setColor(int color){
        this.color = new Color(color);
        return this;
    }
    
    void init(UIPanel panel) {
        this.panel = panel;
    }
    
    void setOffset(Vector2i offset){
        this.offset = offset;
    }
    
    public Vector2i getAbsolutePosition(){
        return new Vector2i(position).add(offset);
    }
    
    public void update(){
        
    }
    
    public void render(Graphics g){
        
    }
    
    

    
}
