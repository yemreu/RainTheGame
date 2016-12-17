
package rain.entity.mob;

import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import rain.Game;
import rain.entity.projectile.Projectile;
import rain.entity.projectile.WizardProjectile;
import rain.graphics.AnimatedSprite;
import rain.graphics.Screen;
import rain.graphics.Sprite;
import rain.graphics.SpriteSheet;
import rain.graphics.ui.UIActionListener;
import rain.graphics.ui.UILabel;
import rain.graphics.ui.UIManager;
import rain.graphics.ui.UIPanel;
import rain.graphics.ui.UIProgressBar;
import rain.graphics.ui.UIButton;
import rain.graphics.ui.UIButtonListener;
import rain.input.Keyboard;
import rain.input.Mouse;
import rain.util.ImageUtils;
import rain.util.Vector2i;

public class Player extends Mob {
    
    private String name;
    private Keyboard input;
    private Sprite sprite ;
    private boolean walking = false;
    private AnimatedSprite down = new AnimatedSprite(SpriteSheet.player_down,32,32,3);
    private AnimatedSprite up = new AnimatedSprite(SpriteSheet.player_up,32,32,3);
    private AnimatedSprite left = new AnimatedSprite(SpriteSheet.player_left,32,32,3);
    private AnimatedSprite right = new AnimatedSprite(SpriteSheet.player_right,32,32,3);
    
    private AnimatedSprite animSprite = down;
    
    private UIManager ui;
    private UIProgressBar uiHealthBar;
    private UIButton button;
    
    private int fireRate = 0;
    
    private BufferedImage image;
    
    
    
    @Deprecated
    public Player(String name, Keyboard input){
        this.name = name;
        this.input = input;
        sprite = Sprite.player_forward;

    }
    
    public Player(String name, int x, int y, Keyboard input){
        this.name = name;
        this.x = x;
        this.y = y;
        this.input = input;
        sprite = Sprite.player_forward;
        fireRate = WizardProjectile.FIRE_RATE;
        ui = Game.getUIManager();
        UIPanel panel = (UIPanel) new UIPanel(new Vector2i((300-80)*3,0),new Vector2i(80*3,198*3)).setColor(0x4f4f4f);
        ui.addPanel(panel);
        UILabel nameLabel = new UILabel(new Vector2i(40,200),name);
        nameLabel.setColor(0xbbbbbb);
        nameLabel.setFont(new Font("Verdana",Font.PLAIN,24));
        nameLabel.dropShadow = true;
        panel.addComponent(nameLabel);
        uiHealthBar = new UIProgressBar(new Vector2i(10,215), new Vector2i(80*3-20,20));
        uiHealthBar.setColor(0x6a6a6a);
        uiHealthBar.setForegroundColor(0xee3030);
        panel.addComponent(uiHealthBar);
        UILabel hpLabel = new UILabel(new Vector2i(uiHealthBar.position).add(new Vector2i(2,16)), "HP");
        hpLabel.setColor(0xffffff);
        hpLabel.setFont(new Font("Verdana",Font.PLAIN,18));
        panel.addComponent(hpLabel);
        //player default attributes
        health = 100;
        button = new UIButton(new Vector2i(10,260),new Vector2i(100,30),new UIActionListener() {
            @Override
            public void perform() {
                System.out.println("Button pressed.");
            }
        }          
);
        button.setText("Hello");
        panel.addComponent(button);
        
        try {
            image = ImageIO.read(getClass().getResource("/textures/home.png"));
        } catch (IOException ex) {
                ex.printStackTrace();
        }
        UIButton imageButton = new UIButton(new Vector2i(10,360),image,new UIActionListener() {
                @Override
                public void perform() {
                    System.out.println("imageButton pressed.");
                }
            }
            );      
        imageButton.setButtonListener(new UIButtonListener(){
            public void entered(UIButton button){
                button.setImage(ImageUtils.changeBrightness(image, -50));
            }

            public void exited(UIButton button){
                button.setImage(image);
            }
            
            public void pressed(UIButton button){
                 button.setImage(ImageUtils.changeBrightness(image, 50));
            }
            
            public void released(UIButton button){
                button.setImage(image);
            }
        });
        panel.addComponent(imageButton);
    }
    
    public String getName(){
        return name;
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
        uiHealthBar.setProgress(health/100.0);
    }
    
    @Override
    public void render(Screen screen){
        int flip = 0;
        sprite = animSprite.getSprite();
        screen.renderMob((int)(x-16), (int)(y-16), sprite,flip);
    }
    
    private void updateShooting() {
        if(Mouse.getX() > 660) return;
        if(Mouse.getButton() == 1 && fireRate <= 0){
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
