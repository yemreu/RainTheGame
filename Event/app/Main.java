
package app;

import app.core.Window;
import java.awt.Color;

public class Main {

    public static void main(String[] args) {
        Window window = new Window("Events",640,360);
        window.addLayer(new ExampleLayer("Bottom",new Color(0x2233cc)));
        window.addLayer(new ExampleLayer("Top",new Color(0xcc2233)));
    }
    
}
