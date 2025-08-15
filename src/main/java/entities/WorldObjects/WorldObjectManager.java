package entities.WorldObjects;

import world.World;

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
        else {
            System.out.println("Adding skeleton head");
            worldObjects.add(w);
        }
    }

    public void addProjectile(Projectile p){
        projectiles.add(p);
    }

    public void removeProjectile(Projectile p) {
        int sizeBefore = projectiles.size();
        projectiles.remove(p);
        assert projectiles.size() < sizeBefore : "Expected list size to decrease after removal";
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

    public ArrayList<WorldObject> getAllObjects(){
        ArrayList<WorldObject> all = new ArrayList<>();
        all.addAll(projectiles);
        all.addAll(worldObjects);
        return all;
    }

    //abstract void behavior();

    public void WorldObjectLogic(){
        //System.out.println("Running object behavior");

        for(WorldObject worldObject : getAllObjects()){

            worldObject.behavior();
        }
    }
}
