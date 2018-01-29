package Entities;

import Entities.Weapons.Projectile;
import Entities.Weapons.SpearProjectile;
import TileGame.Handler;
import Entities.Creatures.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class EntityManager {

    private Handler handler;
    private Player player;
    private List<Entity> entities;
    public List<Projectile> projectiles;

    public EntityManager(Handler handler, Player player){
        this.handler = handler;
        this.player = player;
        entities = new ArrayList<>();
        projectiles = new ArrayList<>();
    }

    public void update(){
        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            e.update();
            if (e.isRemoved()){
                entities.remove(e);
            }
        }

        for (int i = 0; i < projectiles.size(); i++) {
            Entity e = projectiles.get(i);
            e.update();
        }

        player.update();
    }

    public void render(Graphics g){
        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            e.render(g);
        }

        for (int i = 0; i < projectiles.size(); i++) {
            Entity e = projectiles.get(i);
            e.render(g);
        }

        player.render(g);
    }

    public List<Projectile> getProjectiles() {
        return projectiles;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void addEntity(Entity e){
        entities.add(e);
    }

    public Player getPlayer() {
        return player;
    }
}
