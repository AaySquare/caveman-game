package States;

import TileGame.Handler;

import java.awt.*;

public abstract class State {

    private static State currentState = null;

    public static void setState(State state){
        currentState = state;
    }

    public static State getState(){
        return currentState;
    }

    //Class
    protected Handler handler;

    public State(Handler handler){
        this.handler = handler;
    }

    public abstract void update();
    public abstract void render(Graphics2D g);

}
