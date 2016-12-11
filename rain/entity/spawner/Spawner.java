
package rain.entity.spawner;


import rain.entity.Entity;
import rain.level.Level;

public abstract class Spawner extends Entity{
    
    public enum Type{
        MOB, PARTICLE;
    }
    
    protected Type type;
    
    public Spawner(int x, int y, Type type, int amount, Level level){
        init(level);
        this.x=x;
        this.y=y;
        this.type=type;
    }
}
