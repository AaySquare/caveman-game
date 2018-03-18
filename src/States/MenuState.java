package States;

import TileGame.Handler;
import UI.ClickListener;
import UI.UIImageButton;
import UI.UIManager;
import gfx.Assets;
import Audio.AudioPlayer;

import java.awt.*;

public class MenuState extends State {

    private UIManager uiManager;
    public static Boolean game_state= false;
    private AudioPlayer startSound;

    public MenuState(Handler handler){
        super(handler);
        uiManager = new UIManager(handler);
        handler.getMouse().setUIManager(uiManager);
        uiManager.addObject(new UIImageButton(200, 200, 128, 64, Assets.start_button, new ClickListener() {
            @Override
            public void onClick() {
                game_state = true;
                startSound = new AudioPlayer("/Audio/UI_Quirky30.mp3");
                startSound.play();

                handler.getMouse().setUIManager(null);
                State.setState(handler.getGame().gameState);


            }
        }));
    }

    @Override
    public void update() {
        uiManager.tick();
    }

    @Override
    public void render(Graphics2D g) {
        uiManager.render(g);
    }
}
