package Entities;

import Entities.Weapons.Projectile;
import TileGame.Handler;
import Entities.Creatures.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EntityManager {

    private Handler handler;
    private Player player;
    private ArrayList<Entity> entities;
    public ArrayList<Projectile> projectiles = new ArrayList<Projectile>();

    public EntityManager(Handler handler, Player player){
        this.handler = handler;
        this.player = player;
        entities = new ArrayList<>();
    }

    public void update(){
        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            e.update();
        }
        player.update();
    }

    public void render(Graphics g){
        for (Entity e : entities){
            e.render(g);
        }
        player.render(g);
    }

    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }

    public ArrayList<Entity> getEntities() {
        return entities;
    }

    public void addEntity(Entity e){
        entities.add(e);
    }

    public Player getPlayer() {
        return player;
    }
}
