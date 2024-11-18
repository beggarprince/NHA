package PlayerActions;

import entities.Monsters.MonsterFactory;
import entities.Monsters.MonsterList;
import graphics.ScreenSettings;
import graphics.TileType;
import level.Tile;

public class Spawn {

    public static void spawnEnemyAtPlayer( Tile tile, MonsterList eList){
        String reqMonster = "";

        if(tile.type == TileType.NUTRIENT){
            if(tile.getNutrients() > Tile.nutrientL2Min) reqMonster = "Slime_Flower";
            else reqMonster = "Slime";
            eList.addEnemy( MonsterFactory.createMonster(reqMonster, tile.x * ScreenSettings.TILE_SIZE, tile.y * ScreenSettings.TILE_SIZE ));
        }
        else if(tile.type == TileType.MANA){
            eList.addEnemy( MonsterFactory.createMonster("Spirit", tile.x * ScreenSettings.TILE_SIZE, tile.y * ScreenSettings.TILE_SIZE));
        }
    }
}
