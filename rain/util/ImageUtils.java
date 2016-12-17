
package rain.util;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;
import static rain.util.MathUtils.*;

public class ImageUtils {
    
    private ImageUtils(){   
    }
    
    //Returns a new image
    public static BufferedImage changeBrightness(BufferedImage original, int amount){
        BufferedImage result = new BufferedImage(original.getWidth(), original.getHeight(), BufferedImage.TYPE_INT_ARGB);
        byte[] pixels = ((DataBufferByte) original.getRaster().getDataBuffer()).getData();
        int[] resultPixels = ((DataBufferInt) result.getRaster().getDataBuffer()).getData();
        int offSet = 0;
        for (int yy = 0; yy < original.getHeight(); yy++) {
            for (int xx = 0; xx < original.getWidth(); xx++) {
                int a = Byte.toUnsignedInt(pixels[offSet++]);
                int b = Byte.toUnsignedInt(pixels[offSet++]);
                int g = Byte.toUnsignedInt(pixels[offSet++]);
                int r = Byte.toUnsignedInt(pixels[offSet++]);
                r = clamp(r + amount,0,255);
                g = clamp(g + amount,0,255);
                b = clamp(b + amount,0,255);
                resultPixels[xx+yy*result.getWidth()] = a << 24 | r << 16 | g << 8 | b;
            }
        }
        return result;
    }
}
