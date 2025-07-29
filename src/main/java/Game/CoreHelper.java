package Game;

import   Game.Event.BattleBeginScript;
import   Game.Event.HeroEntryScript;
import   PlayerActions.Spawn;
import   entities.NPC.Heroes.HeroFactory;
import   entities.NPC.Heroes.HeroList;
import   entities.NPC.Monsters.MonsterLogic.MonsterList;
import   entities.NPC.Mvp;
import   entities.Player;
import   graphics.Camera;
import   graphics.ScreenSettings;
import   io.keyboard.KBInputAccelerator;
import   io.keyboard.KbInputInGame;
import   level.Level;
import   level.TileType;

import static   PlayerActions.Dig.dig;

//TODO fuck this delete this file
public class CoreHelper {

    static Level level;

    public CoreHelper(){

        level = Level.getInstance();
    }

    //end of helper functions
}
