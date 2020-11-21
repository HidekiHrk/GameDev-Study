package game_graphics;

public class GameLoop {

    private long lastTime;
    private double ns;
    private double delta = 0;

    public GameLoop(double frames){
        this.lastTime = System.nanoTime();
        this.ns = 1000000000 / frames;
    }

    public void changeFps(double frames){
        this.ns = 1000000000 / frames;
    }

    public boolean tick() {
        long now = System.nanoTime();
        delta += (now - lastTime) / ns;
        lastTime = now;
        if(delta >= 1){
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

        public void increment(){
            frames++;
        }

        public int getFrames(){
            return lastFrames;
        }

        public boolean tick() {
            if(System.currentTimeMillis() - timer >= 1000){
                lastFrames = frames;
                frames = 0;
                timer += 1000;
                return true;
            }
            return false;
        }
    }
}
