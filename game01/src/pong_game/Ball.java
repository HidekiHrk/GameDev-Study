package pong_game;

import java.awt.*;
import java.util.Random;

public class Ball implements Entity {
    public double x, y;
    public int width, height;

    public double dx, dy;
    public double speed = 1.6;
    public int touched = 0;


    public Ball(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 4;
        this.height = 4;

        Random random = new Random();
        dx = random.nextGaussian();
        dy = random.nextGaussian();
    }

    @Override
    public void tick() {
        if(touched > 0) touched--;
        this.x += dx * speed;
        this.y += dy * speed;

        if(this.x > Game.WIDTH - width){
            this.x = Game.WIDTH - width;
            touched = 15;
            this.dx *= -1;
        }else if(this.x < 0){
            this.x *= -1;
            touched = 15;
            this.dx = Math.abs(dx);
        }else if(this.y > Game.HEIGHT){
            this.x = Game.WIDTH/2d-4;
            this.y = Game.HEIGHT/2d-4;
            Random random = new Random();
            dx = random.nextGaussian();
            dy = random.nextGaussian();
            Game.score = 0;
        }
    }

    @Override
    public void render(Graphics g) {
        Color color = touched > 0 ? new Color(187, 153, 255) : new Color(153, 255, 187);
        g.setColor(color);
        g.fillRect((int) x, (int) y, width, height);
        g.setColor(Color.black);
        g.fillRect((int) x+1, (int) y+1, width-2, height-2);
    }
}
