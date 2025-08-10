package entities.WorldObjects;

import java.util.ArrayList;
import java.util.List;

public enum WorldObjectManager {
    INSTANCE;

    private final List<Projectile> projectiles = new ArrayList<>();
    private final List<WorldObject> worldObjects = new ArrayList<>();

    public void determineListEntry(WorldObject w){
        if(w instanceof Projectile){
            projectiles.add((Projectile) w);
        }
        //Add more here
    }

    public void addProjectile(Projectile p){
        projectiles.add(p);
    }

    public void removeProjectile(Projectile p){
        projectiles.remove(p);
    }

    public void addObject(WorldObject w){
        worldObjects.add(w);
    }

    public void removeObject(WorldObject w){
        worldObjects.remove(w);
    }

    public List<Projectile> getProjectiles(){
        return projectiles;
    }

    public List<WorldObject> getAllObjects(){
        List<WorldObject> all = new ArrayList<>();
        all.addAll(projectiles);
        all.addAll(worldObjects);
        return all;
    }

}
