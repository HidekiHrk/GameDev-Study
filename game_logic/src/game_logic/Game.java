package game_logic;

import java.util.ArrayList;

public class Game implements Runnable {

    private boolean isRunning;
    private Thread thread;
    private ArrayList<Entity> entities = new ArrayList<>();

    public Game() {
        entities.add(new Entity());
        entities.add(new Entity());
        entities.add(new Entity());
        entities.add(new Entity());
        entities.add(new Entity());
        entities.add(new Entity());
        entities.add(new Entity());
        for(Object item : entities.toArray()){
            System.out.println(item);
        }
    }

    public static void main(String[] args){
        Game game = new Game();
        // game.start();
    }

    public synchronized void start() {
        isRunning = true;
        thread = new Thread(this);
        thread.start();
    } 

    public void tick() {
        System.out.println("Tick");
    }

    public void render() {
        System.out.println("Render");
    }

    @Override
    public void run() {
        while(isRunning) {
            tick();
            render();
            try {
                Thread.sleep(1000/60);
            }catch(InterruptedException e) {
                isRunning = false;
            }
        }

    }
}