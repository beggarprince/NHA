package PlayerActions;

import entities.Enemy.EnemyFactory;
import graphics.ScreenSettings;
import graphics.TileType;
import level.Tile;
import util.Coordinate;

public class Spawn {

    public static void spawnEnemyAtPlayer(EnemyFactory e, Tile tile){

        if(tile.type == TileType.GRASS){
            e.createEnemy("Slime", tile.x * ScreenSettings.TILE_SIZE, tile.y * ScreenSettings.TILE_SIZE );
        }
        else if(tile.type == TileType.MANA){
            e.createEnemy("Spirit", tile.x * ScreenSettings.TILE_SIZE, tile.y * ScreenSettings.TILE_SIZE);
        }
    }
}
