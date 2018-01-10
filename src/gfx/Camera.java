package gfx;

import Entities.Entity;
import TileGame.Handler;
import Tiles.Tile;

public class Camera {

    private Handler handler;
    private float xOffset;
    private float yOffset;

    public Camera (Handler handler, float xOffset, float yOffset){
        this.handler = handler;
        this.xOffset = xOffset;
        this.yOffset = yOffset;
    }

    public void checkOutofMap(){
        if (xOffset < 0){
            xOffset = 0;
        }
        else if (xOffset > handler.getGameWorld().getWidth() * Tile.TILE_WIDTH - handler.getWidth()){
            xOffset = handler.getGameWorld().getWidth() *  Tile.TILE_WIDTH - handler.getWidth();
        }

        if (yOffset < 0){
            yOffset = 0;
        }
        else if (yOffset > handler.getGameWorld().getHeight() * Tile.TILE_HEIGHT - handler.getHeight()){
            yOffset = handler.getGameWorld().getHeight() * Tile.TILE_HEIGHT - handler.getHeight();
        }
    }

    public void focusOnEntity(Entity entity){
        xOffset = entity.getX() - handler.getWidth() / 2 + entity.getWidth() / 2;
        yOffset = entity.getY() - handler.getHeight() / 2 + entity.getHeight() / 2;
        checkOutofMap();
    }

    public void move(float xa, float ya){
        xOffset += xa;
        yOffset += ya;
        checkOutofMap();
    }

    public float getxOffset() {
        return xOffset;
    }

    public void setxOffset(float xOffset) {
        this.xOffset = xOffset;
    }

    public float getyOffset() {
        return yOffset;
    }

    public void setyOffset(float yOffset) {
        this.yOffset = yOffset;
    }
}
