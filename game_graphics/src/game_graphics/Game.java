package game_graphics;

import java.awt.*;
import java.awt.image.*;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Game extends Canvas implements Runnable {

    // Static methods
    public static JFrame frame;
    public static final int WIDTH = 240;
    public static final int HEIGHT = 160;
    public static final int SCALE = 3;
    
    // Object methods
    private Thread thread;
    private BufferedImage image;
    private SpriteSheet sheet;
    private BufferedImage[] player;
    private final int animVel = 3;
    private int animCounter = 0;
    private int animFrame = 0;
    public boolean isRunning;


    public Game() {
        sheet = new SpriteSheet("/spritesheet.png");

        player = new BufferedImage[3];
        for(int spriteId = 0; spriteId < 3; spriteId++){
            player[spriteId] = sheet.getSprite(spriteId * 16, 0, 16,16);
        }
        setPreferredSize(
            new Dimension(WIDTH * SCALE, HEIGHT * SCALE)
        );

        image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);

        initFrame();
    }

    public void initFrame() {
        ImageIcon icon = new ImageIcon(getClass().getResource("/icon.png"));
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
        isRunning = false;
        try{
            thread.join();
        }catch(InterruptedException e){}
    }

    public synchronized void start() {
        thread = new Thread(this);
        isRunning = true;
        thread.start();
    }

    public void tick() {
        if(animCounter < animVel){
            animCounter++;
        }else{
            animFrame++;
            animCounter = 0;
        }
        if(animFrame >= 1000) animFrame = 0;
        System.out.println("Frame: "+ animFrame);
    }

    public void render() {
        BufferStrategy bs = this.getBufferStrategy();
        if(bs == null){
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = image.getGraphics();
        int mouse_x = (MouseInfo.getPointerInfo().getLocation().x - this.getLocationOnScreen().x) / SCALE;
        int mouse_y = (MouseInfo.getPointerInfo().getLocation().y - this.getLocationOnScreen().y) / SCALE;
        g.setColor(new Color(0,0,0));
        g.fillRect(0, 0, WIDTH * SCALE, HEIGHT * SCALE);
        // Draw
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(player[animFrame % 3], 90, 90, null);
        // End of draw
        g.dispose();
        g = bs.getDrawGraphics();
        g.drawImage(image, 0, 0, WIDTH*SCALE, HEIGHT*SCALE, null);
        bs.show();
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.start();
    }

    public void run(){
        GameLoop loop = new GameLoop(60.0);
//        GameLoop.Fps fps = new GameLoop.Fps();

        while(isRunning){
            if(loop.tick()){
                tick();
                render();
//                fps.increment();
            }
//            if(fps.tick()){
//                System.out.println("FPS: " + fps.getFrames());
//            }
        }

        stop();
    }

}