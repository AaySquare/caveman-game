package TileGame;

import Display.Display;
import Entities.Creatures.Player;
import Inputs.KeyController;
import Inputs.Mouse;
import States.GameState;
import States.MenuState;
import States.State;
import UI.UIManager;
import gfx.Camera;

import java.awt.*;
import java.awt.image.BufferStrategy;

import static Entities.Weapons.SpearProjectile.numberOfSpears;
import static Entities.Weapons.ArrowProjectile.numberOfArrows;
import static Entities.Creatures.Player.playerDead;
import static Entities.Creatures.Tiger.tigerDead;
import static Inventory.Items.ItemManager.canCraft;
import static States.MenuState.game_state;
import static Inventory.ItemStack.amount;

public class Game implements Runnable {

    private Display display;
    private int width, height;
    public String title;

    private Thread thread;
    private boolean running = false;

    private BufferStrategy bs;
    private Graphics g;

    //States
    public State gameState;
    public State menuState;

    private KeyController keyController;
    private Mouse mouse;
    private Camera gameCamera;
    private Handler handler;
    private static UIManager uiManager;
    private Player player;

    public Game(String title, int width, int height){
        this.width = width;
        this.height = height;
        this.title = title;
        keyController = new KeyController();
        mouse = new Mouse();
    }

    public void init(){
        display = new Display(title, width, height);
        display.getFrame().addKeyListener(keyController);
        display.getFrame().addMouseListener(mouse);
        display.getFrame().addMouseMotionListener(mouse);

        display.getCanvas().addMouseListener(mouse);
        display.getCanvas().addMouseMotionListener(mouse);
        gfx.Assets.init();

        handler = new Handler(this);
        gameCamera = new Camera(handler, 0, 0);


        gameState = new GameState(handler);
        menuState = new MenuState(handler);
        State.setState(menuState);
    }

    public void update(){
        keyController.update();
        if (State.getState() != null){
            State.getState().update();
        }
    }

    public void render(){
        bs = display.getCanvas().getBufferStrategy();
        if (bs == null){
            display.getCanvas().createBufferStrategy(3);
            return;
        }

        g = bs.getDrawGraphics();
        g.clearRect(0, 0, width, height);

        if (State.getState() != null){
            State.getState().render((Graphics2D) g);
        }

        if (game_state){
            g.setColor(Color.white);
            g.setFont(new Font("Comic Sans MS", 0, 20));
            g.drawString("Spears: " + numberOfSpears, 0, 50);
            g.drawString("Arrows: " + numberOfArrows, 0, 80);
            if (amount > 0 && canCraft){
                g.drawString("Craft Arrow (A): ", 120, 80);
            }
            if (amount > 1 && canCraft){
                g.drawString("Craft Spear (S): ", 120, 50);
            }

            g.drawString("Objective: Find the Tiger and kill it.", 350, 30);

            if (playerDead){
                g.setColor(Color.red);
                g.setFont(new Font("Comic Sans MS", 0, 70));
                g.drawString("You Lose!", 200, 330);
            }
            else if (tigerDead){
                g.setColor(Color.green);
                g.setFont(new Font("Comic Sans MS", 0, 70));
                g.drawString("You Win!", 200, 330);
            }
        }

        else {
            g.setColor(Color.black);
            g.setFont(new Font("Comic Sans MS", Font.BOLD + Font.ITALIC, 30));
            g.drawString("CAVEMAN VS ANIMALS", 170, 50);
            g.setFont(new Font("Comic Sans MS", Font.ITALIC, 25));
            g.drawString("A Tile-Based RPG", 250, 90);

            g.setFont(new Font("Comic Sans MS", Font.BOLD, 17));
            g.drawString("CONTROLS", 300, 300);
            g.setFont(new Font("Comic Sans MS", 0, 17));
            g.drawString("Move -> Arrow Keys", 240, 330);
            g.drawString("Aim -> Mouse Pointer", 240, 360);
            g.drawString("Throw Spears -> Left Mouse Button", 240, 390);
            g.drawString("Shoot Arrows -> Right Mouse Button", 240, 420);
            g.drawString("Melee Attack -> Middle Mouse Button", 240, 450);
            g.drawString("Inventory -> 'I' Button", 240, 480);

            g.setFont(new Font("Comic Sans MS", Font.ITALIC, 18));
            g.drawString("Created by Aayush Mathur", 250, 570);
        }

        bs.show();
        g.dispose();
    }

    @Override
    public void run() {
        init();
        //To make the game frame independent. Used as Time.deltaTime in Unity
        int fps = 60;
        double timePerTick = 1000000000/fps;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();
        long timer = 0;
        int ticks = 0;

        while (running){
            now = System.nanoTime();
            delta += (now - lastTime) / timePerTick;
            timer += now - lastTime;
            lastTime = now;
            if (delta >= 1) {
                update();
                render();
                ticks++;
                delta--;
            }
            if (timer >= 1000000000){
                ticks = 0;
                timer = 0;
            }
        }
        stop();
    }

    public synchronized void start(){
        if (running){
            return;
        }
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public synchronized void stop(){
        if (!running){
            return;
        }
        running = false;
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static UIManager getUimanager() {
        return uiManager;
    }

    public KeyController getKeyController() {
        return keyController;
    }

    public Mouse getMouse() {
        return mouse;
    }

    public Camera getGameCamera() {
        return gameCamera;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
