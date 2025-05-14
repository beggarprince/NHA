package main.java.Game;

import main.java.Game.Event.BattleBeginScript;
import main.java.Game.Event.HeroEntryScript;
import main.java.PlayerActions.Spawn;
import main.java.entities.NPC.Heroes.HeroFactory;
import main.java.entities.NPC.Heroes.HeroList;
import main.java.entities.NPC.Monsters.MonsterLogic.MonsterList;
import main.java.entities.NPC.Mvp;
import main.java.entities.Player;
import main.java.graphics.Camera;
import main.java.graphics.ScreenSettings;
import main.java.io.keyboard.KBInputAccelerator;
import main.java.io.keyboard.KbInputInGame;
import main.java.level.Level;
import main.java.level.TileType;

import static main.java.PlayerActions.Dig.dig;

//TODO fuck this delete this file
public class CoreHelper {

    static Level level;

    public CoreHelper(){

        level = Level.getInstance();
    }

    //end of helper functions
}
