
package rain.graphics.ui;

import java.awt.Color;
import java.awt.Graphics;
import rain.util.Vector2i;

public class UIButton extends UIComponent {
    
    public UILabel label;
    private UIButtonListener buttonListener;
    
    public UIButton(Vector2i position, Vector2i size) {
        super(position,size);
        Vector2i lp = new Vector2i(position);
        lp.setX(lp.getX()+4);
        lp.setY(lp.getY()+size.getY()-2);
        label = new UILabel(lp,"");
        label.setColor(0x444444);
        label.active = false;
        setColor(0xaaaaaa);
    }
    
    void init(UIPanel panel){
        super.init(panel);
        panel.addComponent(label);
    }
    
    public void setText(String text){
        if(text==""){
            label.active = false;
        }else
            label.text = text;
    }
    
    public void update(){
        
    }
    
    @Override
    public void render(Graphics g) {
        g.setColor(color);
        g.fillRect(position.getX()+offset.getX(),position.getY()+offset.getY(), size.getX(),size.getY());
        if(label!=null) label.render(g);
    }
}
