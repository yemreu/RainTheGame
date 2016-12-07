
package rain.entity.mob;

import java.util.List;
import rain.Game;
import rain.entity.Entity;
import rain.entity.projectile.Projectile;
import rain.entity.projectile.WizardProjectile;
import rain.graphics.AnimatedSprite;
import rain.graphics.Screen;
import rain.graphics.Sprite;
import rain.graphics.SpriteSheet;
import rain.graphics.ui.UILabel;
import rain.graphics.ui.UIManager;
import rain.graphics.ui.UIPanel;
import rain.input.Keyboard;
import rain.input.Mouse;
import rain.util.Vector2i;

public class Player extends Mob {
    
    private Keyboard input;
    private Sprite sprite ;
    private int anim=0;
    private boolean walking = false;
    private AnimatedSprite down = new AnimatedSprite(SpriteSheet.player_down,32,32,3);
    private AnimatedSprite up = new AnimatedSprite(SpriteSheet.player_up,32,32,3);
    private AnimatedSprite left = new AnimatedSprite(SpriteSheet.player_left,32,32,3);
    private AnimatedSprite right = new AnimatedSprite(SpriteSheet.player_right,32,32,3);
    
    private AnimatedSprite animSprite = down;
    
    private UIManager ui;
    
    private int fireRate = 0;
    
    
    
    
    public Player(Keyboard input){
        this.input = input;
        sprite = Sprite.player_forward;

    }
    
    public Player(int x, int y, Keyboard input){
        this.x = x;
        this.y = y;
        this.input = input;
        sprite = Sprite.player_forward;
        fireRate = WizardProjectile.FIRE_RATE;
        ui = Game.getUIManager();
        UIPanel panel = new UIPanel(new Vector2i((300-80)*3,0),new Vector2i(80*3,198*3));
        ui.addPanel(panel);
        panel.addComponent(new UILabel(new Vector2i(10,30),"Hello").setColor(0));
    }
    
    @Override
    public void update(){
        if(walking)animSprite.update();
        else animSprite.setFrame(0);
        if(fireRate >0) fireRate--;
        double xa = 0,ya = 0;
        double speed =1.0;
        if (input.up) {
            ya-=speed;
            animSprite = up;
        } else if (input.down) {
            ya+=speed;
            animSprite = down;
        }
        if (input.left) {
            xa-=speed;
            animSprite = left;
        }else if (input.right) {
            xa+=speed;
            animSprite = right;
        }
        if(xa !=0 || ya !=0) {
            move(xa,ya);
            walking = true;
        } else{
            walking = false;
        }
        clear();
        updateShooting();
    }
    
    @Override
    public void render(Screen screen){
        int flip = 0;
        sprite = animSprite.getSprite();
        screen.renderMob((int)(x-16), (int)(y-16), sprite,flip);
    }

    private void updateShooting() {
        if(Mouse.getB() == 1 && fireRate <= 0){
            double dx = Mouse.getX() - Game.getWindowWidth() / 2;
            double dy = Mouse.getY()- Game.getWindowHeight() / 2;
            double dir = Math.atan2(dy, dx);
            shoot(x,y,dir);
            fireRate = WizardProjectile.FIRE_RATE;
        }
    }

    private void clear() {
        for (int i = 0; i < level.getProjectiles().size(); i++) {
            Projectile p = level.getProjectiles().get(i);
            if (p.isRemoved()) level.getProjectiles().remove(i);
        }
    }
    
}
