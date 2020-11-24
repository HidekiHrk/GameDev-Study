package pong_game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Game extends Canvas implements Runnable {

    public static JFrame frame;
    public static final int WIDTH = 240;
    public static final int HEIGHT = 180;
    public static final int SCALE = 3;
    private Thread thread;
    private boolean isRunning;
    public Player player;
    public Enemy enemy;
    public Ball ball;
    public static int score = 0;
    public ArrayList<Entity> entities = new ArrayList<>();
    public static GameKeyBoard keyboard = new GameKeyBoard();;
    private final BufferedImage layer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);;

    public Game() {
        startEntities();
        this.addKeyListener(keyboard);
        this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        initFrame();
    }

    public void startEntities(){
        entities.clear();
        player = new Player(100, HEIGHT-30);
        enemy = new Enemy(100, 25);
        ball = new Ball(WIDTH/2-4, HEIGHT/2-4);
        enemy.ball = ball;
        player.ball = ball;
        entities.add(player);
        entities.add(enemy);
        entities.add(ball);
    }

    public void initFrame(){
        ImageIcon icon = new ImageIcon(getClass().getResource("/icon.png"));
        frame = new JFrame("Pong Game");
        frame.setIconImage(icon.getImage());
        frame.add(this);
        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    public static void main(String[] args){
        Game game = new Game();
        game.start();
    }

    public void tick(){
        if(keyboard.check(KeyEvent.VK_SPACE) == 1){
            startEntities();
            score = 0;
        }
        for(Entity entity : entities){
            entity.tick();
        }
    }

    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }
        Graphics g = layer.getGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        for(Entity entity : entities){
            entity.render(g);
        }
        g.setFont(new Font("Arial", Font.PLAIN, 9));
        g.setColor(Color.white);
        g.drawString("Score: "+score, 10, 10);
//        g.setColor(Color.white);
        g.dispose();
        g = bs.getDrawGraphics();
        g.drawImage(layer, 0, 0, WIDTH * SCALE, HEIGHT * SCALE,null);
        bs.show();
    }

    public synchronized void stop() {
        isRunning = false;
        try{
            thread.join();
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }

    public synchronized void start() {
        thread = new Thread(this);
        isRunning = true;
        thread.start();
    }

    public void run() {
        requestFocus();
        GameLoop loop = new GameLoop(60.0);
        while (isRunning){
            if(loop.tick()){
                tick();
                render();
            }
        }
        stop();
    }
}
