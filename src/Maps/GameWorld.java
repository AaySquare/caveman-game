package Maps;

import Audio.AudioPlayer;
import Entities.Creatures.Fox;
import Entities.Creatures.Player;
import Entities.Creatures.Tiger;
import Entities.EntityManager;
import Entities.Static.Tree;
import Entities.Weapons.Projectile;
import Inventory.Inventory;
import Inventory.Items.ItemManager;
import TileGame.Handler;
import Tiles.*;
import Utilities.LoadFile;

import java.awt.*;
import java.util.Random;

public class GameWorld {

    Handler handler;
    private int width, height;
    private int spawnX, spawnY;
    private int[][] tiles;
    private int numOfFoxes = 5;
    private int numOfTrees = 15;

    private EntityManager entityManager;
    public Inventory inventory;
    private ItemManager itemManager;
    private WaterSplashTile waterSplashTile;
    private AudioPlayer waterSplashSFX;

    private Random random = new Random();

    public GameWorld(Handler handler, String path) {
        this.handler = handler;
        entityManager = new EntityManager(handler, new Player(handler, 0, 0));
        waterSplashTile = new WaterSplashTile(5);
        waterSplashSFX = new AudioPlayer("/SFX/waterfall.mp3");

        //Spawn Foxes
        for (int i = 0; i < numOfFoxes; i++){
            entityManager.addEntity(new Fox(handler, random.nextInt(Tile.TILE_WIDTH*17 - Tile.TILE_WIDTH + 1) + Tile.TILE_WIDTH,
                    random.nextInt(Tile.TILE_HEIGHT*16 - Tile.TILE_HEIGHT*2 + 1) + Tile.TILE_HEIGHT*2));

        }

        entityManager.addEntity(new Tiger(handler, Tile.TILE_WIDTH*30, 500));
        //entityManager.addEntity(new Fox(handler, Tile.TILE_WIDTH, Tile.TILE_HEIGHT));

        itemManager = new ItemManager(handler);

        //Spawn Trees
        for (int i = 0; i < numOfTrees; i++) {
            entityManager.addEntity(new Tree(handler, random.nextInt(Tile.TILE_WIDTH*17 - Tile.TILE_WIDTH) + 1 + Tile.TILE_WIDTH,
                    random.nextInt(Tile.TILE_HEIGHT*36 - Tile.TILE_HEIGHT*2 + 1) + Tile.TILE_HEIGHT*2));
        }

        for (int i = 0; i < numOfTrees; i++) {
            entityManager.addEntity(new Tree(handler, random.nextInt(Tile.TILE_WIDTH*36 - Tile.TILE_WIDTH*19) + 1 + Tile.TILE_WIDTH*21,
                    random.nextInt(Tile.TILE_HEIGHT*36 - Tile.TILE_HEIGHT*2 + 1) + Tile.TILE_HEIGHT*2));
        }

        loadWorld(path);

        entityManager.getPlayer().setX(spawnX);
        entityManager.getPlayer().setY(spawnY);
    }

    public void update() {
        itemManager.update();
        entityManager.update();

        int xStart = (int) Math.max(0, handler.getGameCamera().getxOffset() / Tile.TILE_WIDTH);
        int xEnd = (int) Math.min(width, (handler.getGameCamera().getxOffset() + handler.getWidth()) / Tile.TILE_WIDTH + 1);
        int yStart = (int) Math.max(0, handler.getGameCamera().getyOffset() / Tile.TILE_HEIGHT);
        int yEnd = (int) Math.min(height, (handler.getGameCamera().getyOffset() + handler.getHeight()) / Tile.TILE_HEIGHT + 1);

        for (int x = xStart; x < xEnd; x++) {
            for (int y = yStart; y < yEnd; y++) {
                getTile(x, y).update();
            }
        }
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
    public Player getPlayer(){
        for (int i = 0; i < getEntityManager().getEntities().size(); i++){
            if (getEntityManager().getEntities().get(i) instanceof Player){
                return (Player) getEntityManager().getEntities().get(i);
            }
        }
        return null;
    }
}