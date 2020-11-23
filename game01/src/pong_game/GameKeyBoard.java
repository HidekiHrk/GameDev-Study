package pong_game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class GameKeyBoard implements KeyListener {

    private final ArrayList<Integer> keys = new ArrayList<>();

    public int check(int keyCode){
        return keys.contains(keyCode) ? 1 : 0;
    }

    @Override
    public void keyTyped(KeyEvent keyEvent) {}

    @Override
    public void keyPressed(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        if(!keys.contains(keyCode)){
            keys.add(keyCode);
        }
    }

    @Override
    public void keyReleased(KeyEvent keyEvent) {
        int keyCode = keyEvent.getKeyCode();
        if(keys.contains(keyCode)){
            keys.remove(keys.indexOf(keyCode));
        }
    }
}
