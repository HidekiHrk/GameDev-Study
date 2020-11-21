package game_graphics;

import java.awt.*;

import javax.swing.ImageIcon;
import javax.swing.JFrame;


public class Game extends Canvas implements Runnable {

    // Static methods
    public static JFrame frame;
    public static final int WIDTH = 180;
    public static final int HEIGHT = 120;
    public static final int SCALE = 3;
    
    // Object methods
    private Thread thread;
    public boolean isRunning;


    public Game() {
        setPreferredSize(
            new Dimension(WIDTH * SCALE, HEIGHT * SCALE)
        );

        initFrame();
    }

    public void initFrame() {
        ImageIcon icon = new ImageIcon("img/icon.png");
        frame = new JFrame("My Game");
        frame.setIconImage(icon.getImage());
        frame.setResizable(false);
        frame.add(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }


    public synchronized void stop() {

    }

    public synchronized void start() {
        thread = new Thread(this);
        isRunning = true;
        thread.start();
    }

    public void tick() {
        // System.out.println("Meu jogo está rodando!");
    }

    public void render() {
        
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }

    public void run(){
        GameLoop loop = new GameLoop(60.0);
        GameLoop.Fps fps = new GameLoop.Fps();

        while(isRunning){
            if(loop.tick()){
                tick();
                render();
                fps.increment();
            }
            if(fps.tick()){
                System.out.println("FPS: " + fps.getFrames());
            }
        }
    }

}