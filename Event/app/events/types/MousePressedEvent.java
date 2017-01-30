
package app.events.types;

import app.events.Event;

public class MousePressedEvent extends MouseButtonEvent {
    
    public MousePressedEvent(int button, int x, int y, Type type) {
        super(button, x, y, Event.Type.MOUSE_PRESSED);
    }
    
}
