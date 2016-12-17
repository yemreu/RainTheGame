
package rain.graphics.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import rain.input.Mouse;
import rain.util.Vector2i;

public class UIButton extends UIComponent {
    
    public UILabel label;
    private UIButtonListener buttonListener;
    private UIActionListener actionListener;
    private boolean inside = false;
    private boolean pressed = false;
    private boolean ignorePressed = false;
    private boolean ignoreAction = false;
    private Image image;
    
    public UIButton(Vector2i position, Vector2i size, UIActionListener actionListener) {
        super(position,size);
        this.actionListener = actionListener;
        Vector2i lp = new Vector2i(position);
        lp.setX(lp.getX()+4);
        lp.setY(lp.getY()+size.getY()-2);
        label = new UILabel(lp,"");
        label.setColor(0x444444);
        label.active = false;
        init();
    }
    
    public UIButton(Vector2i position, BufferedImage image, UIActionListener actionListener){
        super(position,new Vector2i(image.getWidth(),image.getHeight()));
        this.actionListener = actionListener;
        setImage(image);
        init();
    }
    
    private void init(){
        setColor(0xaaaaaa);
        buttonListener = new UIButtonListener();
    }
    
    void init(UIPanel panel){
        super.init(panel);
        if(label!=null) panel.addComponent(label);
    }
    
    public void setImage(Image image){
        this.image = image;
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
    
    public void performAction() {
        actionListener.perform();
    }
    
    public void ignoreNextPress(){
        ignoreAction = true;
    }
    
    @Override
    public void update(){
        Rectangle rect = new Rectangle(getAbsolutePosition().getX(),getAbsolutePosition().getY(),size.getX(),size.getY());
        boolean leftMouseButtonDown = Mouse.getB() == MouseEvent.BUTTON1;
        if(rect.contains(new Point(Mouse.getX(),Mouse.getY()))){
            if(!inside) {
                if(leftMouseButtonDown) {
                    ignorePressed = true;
                }else ignorePressed = false;
                
                    buttonListener.entered(this);
            }
            inside = true;
            if(!pressed && !ignorePressed && leftMouseButtonDown){
                buttonListener.pressed(this);
                pressed = true;
            }else if(Mouse.getB() == MouseEvent.NOBUTTON){
                if(pressed){
                    buttonListener.released(this);
                    if(!ignoreAction) {
                        actionListener.perform();
                    }else ignoreAction = false;
                    pressed = false;
                }
                ignorePressed = false;
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
        int x = position.getX()+offset.getX();
        int y = position.getY()+offset.getY();
        if(image!=null){
            g.drawImage(image, x, y, null);
        }else{
            g.setColor(color);
            g.fillRect(x,y, size.getX(),size.getY());
            if(label!=null) label.render(g);
        }
    }

    
}
