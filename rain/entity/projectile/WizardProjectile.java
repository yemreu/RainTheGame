
package rain.entity.projectile;

import rain.entity.spawner.Spawner;
import rain.entity.particle.Particle;
import rain.entity.spawner.ParticleSpawner;
import rain.graphics.Screen;
import rain.graphics.Sprite;

public class WizardProjectile extends Projectile{
    
    public static final int FIRE_RATE = 15;

    public WizardProjectile(double x, double y, double dir) {
        super(x, y, dir);
        range = 200;
        damage = 20;
        speed = 4;
        sprite = Sprite.rotate(Sprite.projectile_arrow,angle);
        nx = speed * Math.cos(angle);
        ny = speed * Math.sin(angle);
    }
    
    private int time = 0;
    
    @Override
    public void update(){
        if(level.tileCollision((int)(x+nx), (int)(y+ny), 7,5,4)) {
            level.add(new ParticleSpawner((int)x,(int)y,44,60,level));
            remove();
        }
        time++;
        if(time%2 == 0){
            sprite = Sprite.rotate(sprite,Math.PI/20.0);
        }
        move();
    }
    
    @Override
    protected void move(){
        x += nx;
        y += ny;
        if(distance() > range) remove();
    }
    
    @Override
    public void render(Screen screen){
        screen.renderProjectile((int)x-12, (int)y-2, this);
    }

    private double distance() {
        double dist = 0;
        dist = Math.sqrt(Math.abs((xOrigin - x)*(xOrigin-x)+(yOrigin - y)*(yOrigin-y)));
        return dist;
    }
}
