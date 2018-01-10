package Maps;

import Entities.Creatures.Player;
import Entities.Entity;
import Entities.EntityManager;
import Entities.Static.Tree;
import TileGame.Handler;
import Tiles.Tile;
import Utilities.LoadFile;

import java.awt.*;

public class GameWorld {

    Handler handler;
    private int width, height;
    private int spawnX, spawnY;
    private int[][] tiles;

    private EntityManager entityManager;

    public GameWorld(Handler handler, String path) {
        this.handler = handler;
        entityManager = new EntityManager(handler, new Player(handler, 100, 100));

        loadWorld(path);

        entityManager.getPlayer().setX(spawnX);
        entityManager.getPlayer().setY(spawnY);
    }

    public void update() {
        entityManager.update();
    }

    public void render(Graphics g) {
        //To only render tiles that can be seen on screen/camera and not off screen/camera
        int xStart = (int) Math.max(0, handler.getGameCamera().getxOffset() / Tile.TILE_WIDTH);
        int xEnd = (int) Math.min(width, (handler.getGameCamera().getxOffset() + handler.getWidth()) / Tile.TILE_WIDTH + 1);
        int yStart = (int) Math.max(0, handler.getGameCamera().getyOffset() / Tile.TILE_HEIGHT);
        int yEnd = (int) Math.min(height, (handler.getGameCamera().getyOffset() + handler.getHeight()) / Tile.TILE_HEIGHT + 1);

        for (int x = xStart; x < xEnd; x++) {
            for (int y = yStart; y < yEnd; y++) {
                getTile(x, y).render(g, (int) (x * Tile.TILE_WIDTH - handler.getGameCamera().getxOffset()),
                        (int) (y * Tile.TILE_HEIGHT - handler.getGameCamera().getyOffset()));
            }
        }
        entityManager.render(g);
    }

    public void add(Entity entity){
        entityManager.getEntities().add(entity);
    }

    public Tile getTile(float x, float y){
        if (x < 0 || y < 0 || x >= width || y >= height){
            return Tile.grassTile;
        }

        Tile tile = Tile.tiles[tiles[(int) x][(int) y]];
        if (tile == null){
            return Tile.dirtTile;
        }
        return tile;
    }

    private void loadWorld(String path) {
        String file = LoadFile.loadFile(path);
        String[] tokens = file.split("\\s+");
        width = LoadFile.parseInt(tokens[0]);
        height = LoadFile.parseInt(tokens[1]);
        spawnX = LoadFile.parseInt(tokens[2]);
        spawnY = LoadFile.parseInt(tokens[3]);

        tiles = new int[width][height];
        for (int x = 0; x < height; x++){
            for (int y = 0; y < width; y++){
                tiles[x][y] = LoadFile.parseInt(tokens[(x + y * width)+4]);
            }
        }
    }

    public int getWidth(){
        return width;
    }
    public int getHeight(){
        return height;
    }
    public EntityManager getEntityManager() {
        return entityManager;
    }
}