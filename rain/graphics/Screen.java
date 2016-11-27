
package rain.graphics;

import java.util.Random;
import rain.entity.mob.Chaser;
import rain.entity.mob.Mob;
import rain.entity.mob.Player;
import rain.entity.mob.Star;
import rain.entity.projectile.Projectile;
import rain.level.tile.Tile;

public class Screen {
    
    public final int MAP_SIZE = 8;
    public final int MAP_SIZE_MASK = MAP_SIZE - 1;
    
    public int[] pixels;
    public int[] tiles = new int[MAP_SIZE*MAP_SIZE];
    public int width,height;
    public int xOffset,yOffset;
    
    
    
    private Random random = new Random();
    private final int ALPHA_COL = 0xffff00ff;
    
    public Screen(int width, int height){
        this.width=width;
        this.height=height;
        pixels = new int[width*height];
        
        for (int i = 0; i < MAP_SIZE*MAP_SIZE; i++) {
            tiles[i]=random.nextInt(0xffffff);
            
        }
    }
    
    public void clear(){
        for (int i = 0; i < pixels.length; i++) {
            pixels[i]=0;
        }
    }
    
    public void renderSheet(int xp, int yp, SpriteSheet sheet, boolean fixed, int alpha){
        if(fixed){
        xp-=xOffset;
        yp-=yOffset;
        }
        for (int y = 0; y < sheet.SPRITE_HEIGHT; y++) {
            int ya = y + yp;
            for (int x = 0; x < sheet.SPRITE_WIDTH; x++) {
                int xa = x+xp;
                if(xa<0 || xa>= width || ya <0 || ya>=height) continue;
                pixels[xa+ya*width] = sheet.pixels[x+y*sheet.SPRITE_WIDTH];
            }
        }
        
    }
    
    public void renderTextCharacter(int xp, int yp, Sprite sprite,int color, boolean fixed){
        if(fixed){
        xp-=xOffset;
        yp-=yOffset;
        }
        for (int y = 0; y < sprite.getHeight(); y++) {
            int ya = y + yp;
            for (int x = 0; x < sprite.getWidth(); x++) {
                int xa = x+xp;
                if(xa<0 || xa>= width || ya <0 || ya>=height) continue;
                int col = sprite.pixels[x+y*sprite.getWidth()];
                if(col != ALPHA_COL && col != 0xff7f007f) pixels[xa+ya*width] = color;
            }
        }
        
    }
    
    public void renderSprite(int xp, int yp, Sprite sprite, boolean fixed){
        if(fixed){
        xp-=xOffset;
        yp-=yOffset;
        }
        for (int y = 0; y < sprite.getHeight(); y++) {
            int ya = y + yp;
            for (int x = 0; x < sprite.getWidth(); x++) {
                int xa = x+xp;
                if(xa<0 || xa>= width || ya <0 || ya>=height) continue;
                int col = sprite.pixels[x+y*sprite.getWidth()];
                if(col != ALPHA_COL && col != 0xff7f007f) pixels[xa+ya*width] = col;
            }
        }
        
    }
    
    /**
     *   a = absolute (position)
     */
     public void renderTile(int xp, int yp, Tile tile){
         xp -= xOffset;
         yp -= yOffset;
         for (int y = 0; y < tile.sprite.SIZE; y++) {
             int ya = y + yp;
             for (int x = 0; x < tile.sprite.SIZE; x++) {
             int xa = x + xp;
             if (xa<-tile.sprite.SIZE || xa>=width || ya<0 || ya>=height) break;
             if (xa<0) xa = 0;
             pixels[xa+ya*width] = tile.sprite.pixels[x+y*tile.sprite.SIZE];
                 
         }
         }
     }
     
     public void renderProjectile(int xp, int yp, Projectile p){
         xp -= xOffset;
         yp -= yOffset;
         for (int y = 0; y < p.getSpriteSize(); y++) {
             int ya = y + yp;
             for (int x = 0; x < p.getSpriteSize(); x++) {
             int xa = x + xp;
             if (xa<-p.getSpriteSize() || xa>=width || ya<0 || ya>=height) break;
             if (xa<0) xa = 0;
             int col = p.getSprite().pixels[x+y*p.getSpriteSize()];
             if (col != ALPHA_COL) pixels[xa+ya*width] = col;
                 
         }
         }
     }
     
    
     
     public void renderMob(int xp, int yp, Sprite sprite, int flip){
         xp -= xOffset;
         yp -= yOffset;
         for (int y = 0; y < 32; y++) {
             int ya = y + yp;
             int ys = y;
             if (flip == 2 || flip == 3) {
                 ys = 31-y;
             }
             for (int x = 0; x < 32; x++) {
             int xa = x + xp;
             int xs = x;
             if (flip == 1 || flip == 3) {
                 xs = 31-x;
             }
             if (xa<-32 || xa>=width || ya<0 || ya>=height) break;
             if (xa<0) xa = 0;
             int col = sprite.pixels[xs+ys*32];
            if (col != ALPHA_COL) pixels[xa+ya*width] = col;
                 
         }
         }
     }
     
     public void renderMob(int xp, int yp, Mob mob){
         xp -= xOffset;
         yp -= yOffset;
         for (int y = 0; y < 32; y++) {
             int ya = y + yp;
             int ys = y;
             for (int x = 0; x < 32; x++) {
             int xa = x + xp;
             int xs = x;
             if (xa<-32 || xa>=width || ya<0 || ya>=height) break;
             if (xa<0) xa = 0;
             int col = mob.getSprite().pixels[xs+ys*32];
             if(mob instanceof Chaser && col==0xff472bbf) col = 0xffba0015;
             if(mob instanceof Star && col==0xff472bbf) col = 0xffe8e83a;
             if (col != ALPHA_COL) pixels[xa+ya*width] = col;
                 
         }
         }
     }
     
     public void setOffset(int xOffset, int yOffset){
         this.xOffset = xOffset;
         this.yOffset = yOffset;
     }

    public void drawRect(int xp, int yp, int width, int height, int color, boolean fixed) {
        if(fixed){
            xp-=xOffset;
            yp-=yOffset;
        }
        for (int x = xp; x < xp+width; x++) {
            if(x<0 || x>=this.width || yp>=this.height) continue; 
            if(yp>0) pixels[x + yp * this.width] = color; 
            if(yp + height >= this.height) continue;
            if(yp + height >0) pixels[x + (yp +height) * this.width] = color;
        }
        for (int y = yp; y <= yp+height; y++) {
            if(xp>=this.width || y < 0 ||y>= this.height) continue; 
            if(xp>0) pixels[xp + y * this.width] = color;
            if(xp + width >= this.width) continue;
            if(xp + width >0) pixels[(xp+width) + y * this.width] = color; 
        }
        
    }
     
     
    
    
}