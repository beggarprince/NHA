package PlayerActions;

import   entities.NPC.Monsters.MonsterLogic.MonsterFactory;
import   entities.NPC.Monsters.MonsterLogic.MonsterList;
import   graphics.ScreenSettings;
import   level.TileType;
import   level.Tile;

public class Spawn {

    public static void spawnEnemyAtPlayer( Tile tile, MonsterList eList){
    //TODO use ENUM i made for monsters not the old string method
        String reqMonster = "";

        if(tile.type == TileType.NUTRIENT){
            if(tile.getNutrients() > Tile.nutrientL3Min) reqMonster = "Lizard";
            else if(tile.getNutrients() > Tile.nutrientL2Min) reqMonster = "Bug";
            else reqMonster = "Slime";
            eList.addEnemy( MonsterFactory.createMonster(reqMonster, tile.x * ScreenSettings.TILE_SIZE, tile.y * ScreenSettings.TILE_SIZE ));
        }
        else if(tile.type == TileType.MANA){

            if(tile.getMana() > Tile.manaL3Min ) {
                reqMonster = "Dragon";
            }

            else if (tile.getMana() > Tile.manaL2Min) reqMonster = "Lilith";

            else reqMonster = "Spirit";
            eList.addEnemy( MonsterFactory.createMonster(reqMonster, tile.x * ScreenSettings.TILE_SIZE, tile.y * ScreenSettings.TILE_SIZE));
        }

        //Should probably switch to this but one problamat a time
        //eList.addEnemy(MonsterFactory.createMonster(reqMonster, tile.x * ScreenSettings.TILE_SIZE, tile.y * ScreenSettings.TILE_SIZE));
    }

    public static void spawnEnemyAtPlayerDebug(MonsterList eList, Tile tile, String requestedMonster){

        eList.addEnemy( MonsterFactory.createMonster(requestedMonster, tile.x * ScreenSettings.TILE_SIZE, tile.y * ScreenSettings.TILE_SIZE ));
    }

    public static void spawnEnemyAtPosition(String requestedMonster,  int x, int y){

        MonsterList.getInstance().addEnemy(MonsterFactory.createMonster(requestedMonster, x *ScreenSettings.TILE_SIZE, y * ScreenSettings.TILE_SIZE));
    }
}
