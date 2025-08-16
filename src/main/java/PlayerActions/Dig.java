package PlayerActions;

import   level.TileType;
import   level.Tile;
import world.World;

import java.util.ArrayList;

public class Dig {

    public static boolean dig(ArrayList<ArrayList<Tile>> tileData,
                           Tile tile,
                           int x,
                           int y) {



        if (tile.type == TileType.PATH) {
            //Spawn skelly
            //System.out.println("Dug on path checking for skelly");
            World.INSTANCE.spawnSkeletonHeadsOnTile(x, y);

            return false;
        }


        if (validateDigSite(tileData,x, y) == false) {
            return false;
        }

        //TODO this needs to have the try removed
        try {
            //tile.digDestruct();

            return true;
        } catch (IndexOutOfBoundsException e) {
            System.err.println("Error: Position is out of bounds.");
        } catch (NullPointerException e) {
            System.err.println("Error: levelData or a nested ArrayList is null.");
        }
        if (tile.type != TileType.PATH) {
            System.out.println("Dig did not change value despite not being PATH");
            return false;
        }
        return true;
    }


    //For it to be a valid dig site there must be one walkable path left, right ,up, or down from said site . Not diagonal.
    private static boolean validateDigSite(ArrayList<ArrayList<Tile>> tileData, int x, int y) {
        //Check bounds, then proceed
        if(x > 0){
            if(tileData.get(y).get(x-1).type == TileType.PATH) return true;
        }
        if(y > 0){
            if(tileData.get(y-1).get(x).type == TileType.PATH) return true;
        }
        if (x  < tileData.get(1).size() - 1){
            if(tileData.get(y).get(x+1).type == TileType.PATH) return true;
        }
        if(y < tileData.size() -1){
            if(tileData.get(y+1).get(x).type == TileType.PATH) return true;
        }
        return false;
    }

}
