
package rain.level;

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import rain.entity.mob.Dummy;
import rain.entity.mob.Shooter;


public class SpawnLevel extends Level{
    


    public SpawnLevel(String path) {
        super(path);
        
    }
    
    @Override
    protected void loadLevel(String path){
        try{
            BufferedImage image = ImageIO.read(SpawnLevel.class.getResource(path));
            int w = width = image.getWidth();
            int h = height = image.getHeight();
            tiles = new int[w*h];
            image.getRGB(0,0,w,h,tiles,0,w);
        }catch(IOException e){
            e.printStackTrace();
            System.out.println("Couldnt load level!");
        }
        
        for (int i = 0; i < 1; i++) {
//            add(new Chaser(20,55));
//            add(new Star(17,45));
            add(new Shooter(20,55));
            add(new Shooter(25,55));
            add(new Dummy(15,53));
        }
        
        
    }
    
    /**
     * grass = 0x00FF00
     * flower = 0xFFFF00
     * rock = 0x7f7f00
     */
    @Override
    protected void generateLevel(){
        
    }
    
    
    
}
