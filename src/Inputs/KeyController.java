package Inputs;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import Inventory.Inventory;

public class KeyController implements KeyListener{

    private boolean[] keys, justPressed, cantPress;
    public boolean up, left, down, right;
    public boolean meleeButton;

    public KeyController(){
        keys = new boolean[256];
        justPressed = new boolean[keys.length];
        cantPress = new boolean[keys.length];
    }

    public void update(){
        for(int i = 0; i < keys.length; i++){
            if(cantPress[i] && !keys[i]){
                cantPress[i] = false;
            }else if(justPressed[i]){
                cantPress[i] = true;
                justPressed[i] = false;
            }
            if(!cantPress[i] && keys[i]){
                justPressed[i] = true;
            }
        }

        up = keys[KeyEvent.VK_UP];
        down = keys[KeyEvent.VK_DOWN];
        left = keys[KeyEvent.VK_LEFT];
        right = keys[KeyEvent.VK_RIGHT];

        meleeButton = keys[KeyEvent.VK_SPACE];
    }

    public boolean keyJustPressed(int keyCode){
        if(keyCode < 0 || keyCode >= keys.length)
            return false;
        return justPressed[keyCode];
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() < 0 || e.getKeyCode() >= keys.length)
            return;
        keys[e.getKeyCode()] = true;

        if(e.getKeyCode() == KeyEvent.VK_I) {
            if(!Inventory.isOpen) {
                Inventory.isOpen = true;
            } else {
                Inventory.isOpen = false;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(e.getKeyCode() < 0 || e.getKeyCode() >= keys.length)
            return;
        keys[e.getKeyCode()] = false;
    }
}
