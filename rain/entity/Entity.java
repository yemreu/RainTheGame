
package rain.entity;

import java.util.Random;
import rain.graphics.Screen;
import rain.graphics.Sprite;
import rain.level.Level;

public class Entity {
    
    protected int x,y;
    protected Sprite sprite;
    private boolean removed = false;
    protected Level level;
    protected Random random = new Random();
    
    public Entity(){
        
    }
    
    public Entity(int x, int y, Sprite sprite){
        this.x=x;
        this.y=y;
        this.sprite=sprite;
    }
    
    public void update(){
        
    }
    
    public void render(Screen screen){
        if(sprite!=null) screen.renderSprite((int)x, (int)y, sprite, true);
    }
    
    public void remove(){
        //remove from level
        removed = true;
    }
    
    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }
    
    public boolean isRemoved(){
        return removed;
    }
    
    public void init(Level level){
        this.level= level;
    }
    
    public Sprite getSprite(){
        return sprite;
    }
}
