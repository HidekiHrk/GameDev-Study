package pong_game;

public class GameLoop {

    private final double ns;
    private long lastTime;
    private double delta = 0;

    public GameLoop(double frames){
        lastTime = System.nanoTime();
        ns = 1000000000 / frames;
    }

    public boolean tick(){
        long now = System.nanoTime();
        delta += (now - lastTime) / ns;
        lastTime = now;
        if(delta >= 1) {
            delta--;
            return true;
        }
        return false;
    }

    public static class Fps {
        private int lastFrames = 0;
        private int frames = 0;
        private long timer;

        public Fps(){
            timer = System.currentTimeMillis();
        }

        public int getFrames(){
            return lastFrames;
        }

        public void increment(){
            frames++;
        }

        public boolean tick(){
            if(System.currentTimeMillis() - timer >= 1000){
                lastFrames = frames;
                timer += 1000;
                frames = 0;
                return true;
            }
            return false;
        }
    }
}
