package States;

import Maps.GameWorld;
import TileGame.Handler;

import java.awt.*;

public class GameState extends State {

    private GameWorld gameWorld;

    public GameState(Handler handler){
        super(handler);
        gameWorld = new GameWorld(handler, "src/Maps/Map1.txt");
        handler.setGameWorld(gameWorld);
    }

    @Override
    public void update() {
        gameWorld.update();

    }

    @Override
    public void render(Graphics2D g) {
        gameWorld.render(g);

    }
}
