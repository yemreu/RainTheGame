
package app.events.types;

import app.events.Event;

public class MouseReleasedEvent extends MouseButtonEvent {
    
    public MouseReleasedEvent(int button, int x, int y, Type type) {
        super(button, x, y, Event.Type.MOUSE_RELEASED);
    }
    
}
