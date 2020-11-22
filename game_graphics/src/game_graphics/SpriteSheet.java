package game_graphics;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;

public class SpriteSheet {

    private BufferedImage spritesheet;

    public SpriteSheet(String path) {
        try{
            spritesheet = ImageIO.read(getClass().getResource(path));
        }catch(java.io.IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getSprite(int x, int y, int width, int height) {
        return spritesheet.getSubimage(x, y, width, height);
    }
}
