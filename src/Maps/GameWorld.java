package Maps;

import Entities.Creatures.AnimalTest;
import Entities.Creatures.Player;
import Entities.Entity;
import Entities.EntityManager;
import Entities.Static.Tree;
import Entities.Weapons.Projectile;
import Inventory.Items.ItemManager;
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
    private ItemManager itemManager;

    public GameWorld(Handler handler, String path) {
        this.handler = handler;
        entityManager = new EntityManager(handler, new Player(handler, 0, 0));
        entityManager.addEntity(new AnimalTest(handler, 40, 20));
        entityManager.addEntity(new AnimalTest(handler, 60, 40));
        entityManager.addEntity(new AnimalTest(handler, 10, 60));
        entityManager.addEntity(new AnimalTest(handler, 20, 50));

        itemManager = new ItemManager(handler);

        entityManager.addEntity(new Tree(handler, 450, 200));
        entityManager.addEntity(new Tree(handler, 500, 500));
        entityManager.addEntity(new Tree(handler, 900, 900));
        entityManager.addEntity(new Tree(handler, 850, 200));
        entityManager.addEntity(new Tree(handler, 1000, 350));

        loadWorld(path);

        entityManager.getPlayer().setX(spawnX);
        entityManager.getPlayer().setY(spawnY);
    }

    public void update() {
        itemManager.update();
        entityManager.update();
    }

    public void render(Graphics2D g) {
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
        itemManager.render(g);
        entityManager.render(g);
    }

    public void addProjectiles(Projectile projectile){
        entityManager.getProjectiles().add(projectile);
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

    public boolean tileCollision(double x, double y, double xMove, double yMove, int size){
        boolean solid = false;
        for (int c = 0; c < 4; c++){
            double xtile = (int) (((x+xMove) + c % 2 * size) / 64);
            double yTile = (int) (((y+yMove) + c / 2 * size / 10 + 5) / 64);
            if (handler.getGameWorld().getTile((int)xtile, (int)yTile).isSolid()){
                solid = true;
            }
        }
        return solid;
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
    public ItemManager getItemManager() {
        return itemManager;
    }
    public Handler getHandler() {
        return handler;
    }
}