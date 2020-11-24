package pong_game;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Player implements Entity {

    public int x, y, width, height;
    private boolean sprint;
    private int touched = 0;
    public Ball ball;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 40;
        this.height = 5;
    }

    public void tick() {
        if(touched > 0){
            touched--;
        }
        GameKeyBoard keyBoard = Game.keyboard;
        sprint = keyBoard.check(KeyEvent.VK_SHIFT) == 1;
        int direction = keyBoard.check(KeyEvent.VK_RIGHT) - keyBoard.check(KeyEvent.VK_LEFT);
        int velocity = sprint ? 3 : 1;
        x += direction * velocity;
        if(x+width > Game.WIDTH){
            x = Game.WIDTH - width;
        }else if(x < 0){
            x = 0;
        }
        if(ball.y+ball.height >= y && ball.y < y+ball.speed && ball.x >= x && ball.x <= x + width){
            ball.y = y - (ball.height+1);
            ball.dy *= -1;
            touched = 10;
            ball.touched = 15;
            Game.score++;
        }
    }

    public void render(Graphics g){
        Color color;
        if(touched > 0){
            color = new Color(187, 153, 255);
        }else if(sprint) {
            color = new Color(153, 255, 187);
        }else{
            color = Color.white;
        }
        g.setColor(color);
        g.fillRect(x, y, width, height);
    }

}
