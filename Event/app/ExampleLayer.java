
package app;

import app.events.Event;
import app.events.EventDispatcher;
import app.events.EventHandler;
import app.events.types.MouseMovedEvent;
import app.events.types.MousePressedEvent;
import app.events.types.MouseReleasedEvent;
import app.layers.Layer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.Random;

public class ExampleLayer extends Layer{
    
    private String name;
    private Color color;
    private Rectangle box;
    private static Random random = new Random();
    private int px,py;
    private boolean dragging = false;

    public ExampleLayer(String name, Color color) {
        this.name = name;
        this.color = color;
        box = new Rectangle(random.nextInt(300)+100,random.nextInt(200)+80,80,50);
    }
    int c=0;
    public void onEvent(Event event){
        EventDispatcher dispatcher = new EventDispatcher(event);
        dispatcher.dispatch(Event.Type.MOUSE_PRESSED, (Event e) -> onMousePressed((MousePressedEvent) e));
        dispatcher.dispatch(Event.Type.MOUSE_RELEASED, (Event e) -> onMouseReleased((MouseReleasedEvent) e));
        dispatcher.dispatch(Event.Type.MOUSE_MOVED, (Event e) -> onMouseMoved((MouseMovedEvent) e));
    }
    
    public boolean onMousePressed(MousePressedEvent e){
        System.out.println("pressed "+this);
        if(box.contains(new Point(e.getX(),e.getY()))) dragging = true;
        return true;
    }
    
    public boolean onMouseReleased(MouseReleasedEvent e){
        System.out.println("released "+this);
        dragging = false;
        return false;
    }
    
    public boolean onMouseMoved(MouseMovedEvent e){
        System.out.println("moved "+this);
        int x = e.getX();
        int y = e.getY();
        if(dragging){
            box.x += x - px;
            box.y += y - py;
        }
        px = x;
        py = y;
        return dragging;
    }
    
    @Override
    public void onRender(Graphics g){
        g.setColor(color);
        g.fillRect(box.x, box.y, box.width, box.height);
        g.setColor(Color.white);
        g.drawString(name, box.x+5, box.y+15);
    }
}
