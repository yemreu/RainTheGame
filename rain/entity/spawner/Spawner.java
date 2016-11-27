
package rain.entity.spawner;

import java.util.ArrayList;
import java.util.List;
import rain.entity.Entity;
import rain.entity.particle.Particle;
import rain.level.Level;

public abstract class Spawner extends Entity{
    
    public enum Type{
        MOB, PARTICLE;
    }
    
    private Type type;
    
    public Spawner(int x, int y, Type type, int amount, Level level){
        init(level);
        this.x=x;
        this.y=y;
        this.type=type;
    }
}
