
package rain.graphics.ui;

import rain.graphics.Font;
import rain.graphics.Screen;
import rain.util.Vector2i;

public class UILabel extends UIComponent {
    
    public String text;
    private Font font;
    
    public UILabel(Vector2i position, String text) {
        super(position);
        font = new Font();
        this.text = text;
    }
    
    public void render(Screen screen){
        font.render(position.getX()+offset.getX(), position.getY()+offset.getY(),-4,0, text, screen);
    }
    
}
