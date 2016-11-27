
package rain.graphics.ui;

import rain.graphics.Screen;
import rain.util.Vector2i;


public class UIComponent {
    
    public int backgroundColor;
    public Vector2i position, offset;
    
    public UIComponent(Vector2i position){
        this.position = position;
        offset = new Vector2i();
    }
    
    public void update(){
        
    }
    
    public void render(Screen screen){
        
    }
    
    void setOffset(Vector2i offset){
        this.offset = offset;
    }
}
