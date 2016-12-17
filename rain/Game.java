
package rain;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
//import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.Random;
import javax.swing.JFrame;
import rain.entity.mob.Mob;
import rain.entity.mob.Player;
import rain.graphics.Font;
import rain.graphics.Screen;
import rain.graphics.Sprite;
import rain.graphics.SpriteSheet;
import rain.graphics.ui.UIManager;
import rain.input.Keyboard;
import rain.input.Mouse;
import rain.level.Level;
import rain.level.RandomLevel;
import rain.level.SpawnLevel;
import rain.level.TileCoordinate;
 
public class Game extends Canvas implements Runnable{
    
    private static int width = 300-80;
    private static int height = 168;
    private static int scale = 3;
    public static String title = "Rain";
    
    private Thread thread;
    private JFrame frame;
    private boolean running = false;
    private BufferedImage image = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
    private int[] pixels = ((DataBufferInt)image.getRaster().getDataBuffer()).getData();
    private Screen screen;
    private Keyboard key;
    private Level level;
    private Player player;
    private static UIManager uiManager;
            
    public Game(){
        Dimension size = new Dimension(width*scale+80*3,height*scale);
        //Dimension size = new Dimension(1920,1080);
        setPreferredSize(size);
        screen = new Screen(width,height);
        uiManager = new UIManager();
        frame = new JFrame();
        key = new Keyboard();
        level = Level.spawn;
        TileCoordinate playerSpawn = new TileCoordinate(19,42);
        player = new Player("TheNoob",playerSpawn.getX(),playerSpawn.getY(),key);
        level.add(player);
        addKeyListener(key);
        Mouse mouse = new Mouse();
        addMouseListener(mouse);
        addMouseMotionListener(mouse);
        
    }
    
    public static int getWindowWidth(){
        return width * scale;
    }
    
    public static int getWindowHeight(){
        return height * scale;
    }
    
    public static UIManager getUIManager(){
        return uiManager;
    }
    
    public synchronized void start(){
        running = true;
        thread = new Thread(this,"Display");
        thread.start();
    }
    
    public synchronized void stop(){
        running = false;
        try {    
        thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    
    /**
     * thread.start();
     */
    int text_frames =0;
    int text_updates =0;
    @Override
    public void run(){
        long lastTime = System.nanoTime();
        long timer = System.currentTimeMillis();
        final double ns = 1000000000.0 / 60.0;
        double delta = 0;
        int frames = 0;
        int updates = 0;
        requestFocus();
        while (running){
            long now = System.nanoTime();
            delta += (now-lastTime)/ns;
            lastTime = now;
            while (delta >= 1){
                //60fps
                update();
                updates++;
                delta--;
            }
            //unlimited
            render();
            frames++;
            if (System.currentTimeMillis() - timer > 1000){
                timer += 1000;
                frame.setTitle(title+"  |  "+updates+"ups, "+frames+"fps");
                text_frames=frames;
                text_updates=updates;
                updates = 0;
                frames = 0;
            }
        }
    }
    
    
    public void update(){
        key.update();
        level.update();
        uiManager.update();
    }

    public void render(){
        BufferStrategy bs = getBufferStrategy();
        if (bs == null){
            createBufferStrategy(3);
            return;
        }
        screen.clear();
        double xScroll = player.getX() - screen.width / 2;
        double yScroll = player.getY() - screen.height / 2;
        level.render((int)xScroll, (int)yScroll, screen);
        //font.render(50,50,-3,"Hey\nBro!\nWassup!",screen);
        for (int i = 0; i < pixels.length; i++) {
            pixels[i]=screen.pixels[i];
        }
        Graphics g = bs.getDrawGraphics();
        g.setColor(new Color(0xff00ff));
        g.fillRect(0,0,getWidth(),getHeight());
        g.drawImage(image, 0, 0, width*scale,height*scale,null);
        //g.drawImage(image, 0, 0, 1920,1080,null);
        uiManager.render(g);
        g.setColor(Color.WHITE);
        g.setFont(new java.awt.Font("Verdana",0,9));
        g.drawString("X: " + (int)player.getX() + ", Y:" + (int)player.getY(),5,20);
        g.drawString("FPS: " + text_frames,5,30);
        g.drawString("UPS: " +text_updates,5,40);
        g.drawString("Mouse.X: "+Mouse.getX(), 5, 50);
        g.drawString("Mouse.Y: "+Mouse.getY(),5,60);
        g.drawString("Mouse.B: "+Mouse.getButton(),5,70);
        g.dispose();
        bs.show();
    }
    
    public static void main(String[] args){
        Game game = new Game();
        game.frame.setResizable(false);
        game.frame.setTitle(Game.title);
        game.frame.add(game);
        game.frame.pack();
        game.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        game.frame.setLocationRelativeTo(null);
        game.frame.setVisible(true);
        
        game.start();
        
        
        
    }
}
