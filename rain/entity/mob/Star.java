
package rain.entity.mob;

import java.util.List;
import rain.graphics.AnimatedSprite;
import rain.graphics.Screen;
import rain.graphics.Sprite;
import rain.graphics.SpriteSheet;
import rain.level.Node;
import rain.util.Vector2i;

public class Star extends Mob {
    private AnimatedSprite down = new AnimatedSprite(SpriteSheet.dummy_down,32,32,3);
    private AnimatedSprite up = new AnimatedSprite(SpriteSheet.dummy_up,32,32,3);
    private AnimatedSprite left = new AnimatedSprite(SpriteSheet.dummy_left,32,32,3);
    private AnimatedSprite right = new AnimatedSprite(SpriteSheet.dummy_right,32,32,3);

    private AnimatedSprite animSprite = down;
    
    private double xa=0,ya=0;
    private double speed=0.8;
    private List<Node> path = null;
    private int time = 0;
    
    public Star(int x, int y){
        this.x=x<<4;
        this.y=y<<4;
        sprite = Sprite.dummy;
    }
    private void move(){
        xa = 0;
        ya = 0;
        int px = level.getPlayerAt(0).getX();
        int py = level.getPlayerAt(0).getY();
        Vector2i start = new Vector2i(getX()>>4,getY()>>4);
        Vector2i destination = new Vector2i(px>>4,py>>4);
        if(time%2==0) path = level.findPath(start, destination);
        if(path!=null){
            if(path.size()>0){
                Vector2i vec = path.get(path.size()-1).tile;
                if(x<vec.getX() << 4) xa++;
                if(x>vec.getX() << 4) xa--;
                if(y<vec.getY() << 4) ya++;
                if(y>vec.getY() << 4) ya--;
            }
        }
        if(xa !=0 || ya !=0) {
            move(xa,ya);
            walking = true;
        } else{
            walking = false;
        }
    }

    @Override
    public void update() {
        time++;
        move();
        if(walking)animSprite.update();
        else animSprite.setFrame(0);
        if (ya<0) {
            animSprite = up;
            dir = Mob.Direction.UP;
        } else if (ya>0) {
            animSprite = down;
            dir = Mob.Direction.DOWN;
        }
        if (xa<0) {
            animSprite = left;
            dir = Mob.Direction.LEFT;
        }else if (xa>0) {
            animSprite = right;
            dir = Mob.Direction.RIGHT;
        }
        
    }

    @Override
    public void render(Screen screen) {
        sprite = animSprite.getSprite();
        screen.renderMob((int)(x-16), (int)(y-16), this);
    }
}
