package States;

import Audio.AudioPlayer;
import Maps.GameWorld;
import TileGame.Handler;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.awt.*;

public class GameState extends State {

    private GameWorld gameWorld;
    public static Font font;
    private AudioPlayer bgMusic;

    public GameState(Handler handler){
        super(handler);
        gameWorld = new GameWorld(handler, "src/Maps/Map1.txt");
        handler.setGameWorld(gameWorld);
        font = new Font("Comic Sans MS", Font.BOLD, 24);

        bgMusic = new AudioPlayer("/Audio/Lost-Jungle.mp3");
        bgMusic.play();

    }

    @Override
    public void update() {
        gameWorld.update();

    }

    @Override
    public void render(Graphics2D g) {
        gameWorld.render(g);
        g.setColor(Color.BLACK);
        g.setFont(font);
    }
}
