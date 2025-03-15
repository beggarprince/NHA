package main.java.PlayerActions;

import main.java.entities.NPC.Monsters.MonsterLogic.MonsterFactory;
import main.java.entities.NPC.Monsters.MonsterLogic.MonsterList;
import main.java.graphics.ScreenSettings;
import main.java.level.TileType;
import main.java.level.Tile;

public class Spawn {

    public static void spawnEnemyAtPlayer( Tile tile, MonsterList eList){

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

    public static void spawnEnemyAtPlayerDebug(MonsterList eList, Tile tile){

        var reqMonster = "Slime";
        eList.addEnemy( MonsterFactory.createMonster(reqMonster, tile.x * ScreenSettings.TILE_SIZE, tile.y * ScreenSettings.TILE_SIZE ));
    }
}
