
package rain.graphics.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import rain.input.Mouse;
import rain.util.Vector2i;

public class UIButton extends UIComponent {
    
    public UILabel label;
    private UIButtonListener buttonListener;
    private UIActionListener actionListener;
    private boolean inside = false;
    private boolean pressed = false;
    
    public UIButton(Vector2i position, Vector2i size, UIActionListener actionListener) {
        super(position,size);
        this.actionListener = actionListener;
        Vector2i lp = new Vector2i(position);
        lp.setX(lp.getX()+4);
        lp.setY(lp.getY()+size.getY()-2);
        label = new UILabel(lp,"");
        label.setColor(0x444444);
        label.active = false;
        setColor(0xaaaaaa);
        buttonListener = new UIButtonListener();
    }
    
    void init(UIPanel panel){
        super.init(panel);
        panel.addComponent(label);
    }
    
    public void setButtonListener(UIButtonListener buttonListener){
        this.buttonListener = buttonListener;
    }
    
    public void setText(String text){
        if(text==""){
            label.active = false;
        }else
            label.text = text;
    }
    
    @Override
    public void update(){
        Rectangle rect = new Rectangle(getAbsolutePosition().getX(),getAbsolutePosition().getY(),size.getX(),size.getY());
        if(rect.contains(new Point(Mouse.getX(),Mouse.getY()))){
            if(!inside) buttonListener.entered(this);
            inside = true;
            if(!pressed && Mouse.getB() == MouseEvent.BUTTON1){
                buttonListener.pressed(this);
                pressed = true;
            }else if(pressed && Mouse.getB() == MouseEvent.NOBUTTON){
                buttonListener.released(this);
                actionListener.perform();
                pressed = false;
            }
        }else {
            if(inside) {
                buttonListener.exited(this);
                pressed = false;
            }
            inside = false;
        }
    }
    
    @Override
    public void render(Graphics g) {
        g.setColor(color);
        g.fillRect(position.getX()+offset.getX(),position.getY()+offset.getY(), size.getX(),size.getY());
        if(label!=null) label.render(g);
    }
}
