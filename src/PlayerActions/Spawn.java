package PlayerActions;

import entities.Enemy.EnemyFactory;
import entities.Enemy.EnemyList;
import graphics.ScreenSettings;
import graphics.TileType;
import level.Tile;

public class Spawn {

    public static void spawnEnemyAtPlayer(EnemyFactory e, Tile tile, EnemyList eList){
        String reqMonster = "";

        if(tile.type == TileType.NUTRIENT){
            if(tile.getNutrients() > Tile.nutrientL2Min) reqMonster = "Slime_Flower";
            else reqMonster = "Slime";
            eList.addEnemy( e.createEnemy(reqMonster, tile.x * ScreenSettings.TILE_SIZE, tile.y * ScreenSettings.TILE_SIZE ));
        }
        else if(tile.type == TileType.MANA){
            eList.addEnemy( e.createEnemy("Spirit", tile.x * ScreenSettings.TILE_SIZE, tile.y * ScreenSettings.TILE_SIZE));
        }
    }
}
