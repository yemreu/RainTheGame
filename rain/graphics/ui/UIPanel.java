
package rain.graphics.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import rain.graphics.Screen;
import rain.graphics.Sprite;
import rain.util.Vector2i;

public class UIPanel {

    private List<UIComponent> components = new ArrayList<UIComponent>();
    private Vector2i position,size;
    
    
    public UIPanel(Vector2i position, Vector2i size){
        this.position = position;
        this.size = size;
        this.color = new Color(0xcacaca);
    }
    
    public void addComponent(UIComponent component){
        components.add(component);
    }
    
    public void update(){
        for(UIComponent component : components){
            component.setOffset(position);
            component.update();
        }
    }
    
    public void render(Graphics g){
        g.setColor(color);
        g.fillRect(position.getX(),position.getY(), size.getX(),size.getY());
        for(UIComponent component : components){
            component.render(g);
        }
    }
}
