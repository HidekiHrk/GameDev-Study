package pong_game;

import java.awt.*;

public class Enemy implements Entity {

    public double x, y;
    public int width, height;
    public Ball ball;

    public Enemy(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 40;
        this.height = 5;
    }

    @Override
    public void tick() {
        x += (ball.x - (x + (width/2f) - (ball.width/2f)));
        if(x+width > Game.WIDTH){
            x = Game.WIDTH - width;
        }else if(x < 0){
            x = 0;
        }
        if(ball.y <= y+height && ball.x >= x && ball.x <= x + width){
            ball.y = y+height+1;
            ball.touched = 15;
            ball.dy *= -1;
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect((int) x, (int) y, width, height);
    }
}
