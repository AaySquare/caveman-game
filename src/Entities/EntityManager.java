package Entities;

import Entities.Creatures.AnimalTest;
import Entities.Weapons.Projectile;
import Entities.Weapons.SpearProjectile;
import TileGame.Handler;
import Entities.Creatures.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class EntityManager {

    private Handler handler;
    private Player player;
    private ArrayList<Entity> entities;
    public ArrayList<Projectile> projectiles;
    private Comparator<Entity> renderOrder = new Comparator<Entity>() {
        @Override
        public int compare(Entity e1, Entity e2) {
            if (e1.getY() + e1.getHeight() < e2.getY() + e2.getHeight()) {
                return -1;
            }
            return 1;
        }
    };

    public EntityManager(Handler handler, Player player){
        this.handler = handler;
        this.player = player;
        entities = new ArrayList<>();
        projectiles = new ArrayList<>();
        addEntity(player);
    }

    public void update(){
        for (int i = 0; i < projectiles.size(); i++) {
            Entity e = projectiles.get(i);
            e.update();
        }

        Iterator<Entity> it = entities.iterator();
        while (it.hasNext()){
            Entity e = it.next();
            e.update();
            if (e.isRemoved()){
                it.remove();
            }
        }

        entities.sort(renderOrder);
    }

    public void render(Graphics2D g){
        for (int i = 0; i < projectiles.size(); i++) {
            Entity e = projectiles.get(i);
            e.render(g);
        }

        for (int i = 0; i < entities.size(); i++) {
            Entity e = entities.get(i);
            e.render(g);
        }
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
