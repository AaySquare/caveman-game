package Entities;

import Entities.Weapons.Projectile;
import TileGame.Handler;
import Entities.Creatures.Player;
import Tiles.WaterTile;

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
        player.postRender(g);
    }

    public ArrayList<Projectile> getProjectiles() {
        return projectiles;
    }

    public ArrayList<Entity> getEntities() {
        return entities;

    }

    public List<Entity> getEntities(Entity e, int radius){
        List<Entity> result = new ArrayList<>();
        float ex = e.getX();
        float ey = e.getY();
        for (int i = 0; i < entities.size(); i++){
            Entity entity = entities.get(i);
            float x = entity.getX();
            float y = entity.getY();
            float dx = Math.abs(x - ex);
            float dy = Math.abs(y - ey);
            double distance = Math.sqrt((dx*dx) + (dy * dy));
            if (distance <= radius){
                result.add(entity);
            }
        }
        return result;
    }

    public void addEntity(Entity e){
        entities.add(e);
    }

    public Player getPlayer() {
        return player;
    }

    public List<Player> getPlayers(Entity e, int radius){
        List<Entity> entities = getEntities(e, radius);
        List<Player> result = new ArrayList<>();

        for (int i = 0; i < entities.size(); i++) {
            if (entities.get(i) instanceof Player) {
                result.add((Player) entities.get(i));
            }
        }
        return result;
    }
}
