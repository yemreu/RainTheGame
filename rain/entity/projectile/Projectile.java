
package rain.entity.projectile;

import java.util.Random;
import rain.entity.Entity;
import rain.graphics.Sprite;

public abstract class Projectile extends Entity{
    
    protected final double xOrigin, yOrigin;
    protected double angle;
    protected Sprite sprite;
    protected double nx,ny;
    protected double x,y;
    protected double distance;
    protected double speed,range,damage;
    protected final Random random = new Random();
    
    public Projectile(double x, double y, double dir){
        this.xOrigin = x;
        this.yOrigin = y;
        this.angle = dir;
        this.x=x;
        this.y=y;
    }
    
    public Sprite getSprite(){
        return sprite;
    }
    
    public int getSpriteSize(){
        return sprite.SIZE;
    }
    
    
    protected void move(){
    }
}
