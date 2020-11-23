package pong_game;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

public class Game extends Canvas implements Runnable {

    public static JFrame frame;
    public static final int WIDTH = 240;
    public static final int HEIGHT = 120;
    public static final int SCALE = 3;
    private Thread thread;
    private boolean isRunning;
    public Player player;
    public static GameKeyBoard keyboard = new GameKeyBoard();;
    private final BufferedImage layer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);;

    public Game() {
        player = new Player(100, HEIGHT-10);
        this.addKeyListener(keyboard);
        this.setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        initFrame();
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
        player.tick();
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
        player.render(g);
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
