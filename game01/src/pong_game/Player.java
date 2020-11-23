package pong_game;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player {

    public int x, y, width, height;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 40;
        this.height = 10;
    }

    public void tick() {
        GameKeyBoard keyBoard = Game.keyboard;
        int direction = keyBoard.check(KeyEvent.VK_RIGHT) - keyBoard.check(KeyEvent.VK_LEFT);
        int velocity = keyBoard.check(KeyEvent.VK_SHIFT) == 1 ? 3 : 1;;
        x += direction * velocity;
        if(x+width > Game.WIDTH){
            x = Game.WIDTH - width;
        }else if(x < 0){
            x = 0;
        }
    }

    public void render(Graphics g){
        g.setColor(Color.white);
        g.fillRect(x, y, width, height);
    }

}
