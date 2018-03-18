package TileGame;

import Inputs.KeyController;
import Inputs.Mouse;
import Maps.GameWorld;
import gfx.Camera;

public class Handler {

    private Game game;
    private GameWorld gameWorld;

    public Handler(Game game){
        this.game = game;
    }

    public Camera getGameCamera(){
        return game.getGameCamera();
    }

    public int getWidth(){
        return game.getWidth();
    }

    public int getHeight(){
        return game.getHeight();
    }

    public Game getGame() {
        return game;
    }

    public KeyController getKeyController(){
        return game.getKeyController();
    }

    public Mouse getMouse(){
        return game.getMouse();
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public GameWorld getGameWorld() {
        return gameWorld;
    }

    public void setGameWorld(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
    }
}
