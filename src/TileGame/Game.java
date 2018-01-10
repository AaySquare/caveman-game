package TileGame;

import Display.Display;
import Inputs.KeyController;
import Inputs.Mouse;
import Maps.GameWorld;
import States.GameState;
import States.MenuState;
import States.State;
import gfx.Camera;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game implements Runnable {

    private Display display;
    private int width, height;
    public String title;

    private Thread thread;
    private boolean running = false;

    private BufferStrategy bs;
    private Graphics g;

    //States
    private State gameState;
    private State menuState;

    private KeyController keyController;
    private Mouse mouse;

    private Camera gameCamera;

    private Handler handler;

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
        State.setState(gameState);

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
            State.getState().render(g);
        }

        g.setColor(Color.white);
        //g.fillRect(Mouse.getMouseX() - 32, Mouse.getMouseY() - 32, 64, 64);
        /*if (Mouse.getMouseB() != -1){
            g.drawString("Button: " + Mouse.getMouseB(), 80, 80);
        }*/

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
