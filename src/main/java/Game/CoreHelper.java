package main.java.Game;

import main.java.Game.Scripts.BattleBeginScript;
import main.java.Game.Scripts.HeroEntryScript;
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

public class CoreHelper {

    static Level level;// i hate typing get instance
    public CoreHelper(){
        level = Level.getInstance();
    }

    protected static boolean checkTimer(long currentTime, long previousTime){
        return ((currentTime - previousTime) >= ScreenSettings.INTERVAL);
    }

    protected static boolean attemptDig(Player player){
        if (dig(level.tileData,
                level.tileData.get(player.playerTilePositionY)
                        .get(player.playerTilePositionX),
                player.playerTilePositionX,
                player.playerTilePositionY
        )) {
            Spawn.spawnEnemyAtPlayer(level.tileData
                            .get(player.playerTilePositionY)
                            .get(player.playerTilePositionX),
                    MonsterList.getInstance());
            level.tileData.get(player.playerTilePositionY)
                    .get(player.playerTilePositionX).digDestruct();
            return true;
        }
        return false;
    }

    protected static void debugAddHero(int kbInputDebugJankTimer, Player player){
        if( kbInputDebugJankTimer != 60){
            System.out.println("Debug timer on cooldown");
            return;
        }

        kbInputDebugJankTimer = 0;
        HeroList.getInstance().addHero(HeroFactory.getInstance().createHero("knight",
                player.playerTilePositionX,
                player.playerTilePositionY));

    }

    protected static int decrementHeroTimer(int heroSpawnTimer, int heroSpawnCountdown, Camera camera){
        heroSpawnCountdown++;
//        try {
//            System.out.println("Hero spawn countdown: " + heroSpawnCountdown);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        if(heroSpawnTimer <= heroSpawnCountdown){
            GameState.heroActive = true;
            HeroEntryScript.run("Soldier", HeroList.getInstance(), camera);
            //GameState.setMvpHiding();
        }
        return heroSpawnCountdown;
    }


    protected static void checkPlayerInputActiveStage(KbInputInGame kb,
                                             Player player,
                                             int kbInputDebugJankTimer){
        if (kb.dig) {
            if(player.getDigPower() >0){
                if(attemptDig(player))player.digPowerDecrement();
            }
        }
        else if (kb.debug ) {
            debugAddHero(kbInputDebugJankTimer, player);
        }
        else if(kb.spawnDebug){
            Spawn.spawnEnemyAtPlayerDebug(MonsterList.getInstance(), level.tileData
                    .get(player.playerTilePositionY)
                    .get(player.playerTilePositionX));
        }

        if (kbInputDebugJankTimer != 60) kbInputDebugJankTimer++;
    }

    protected static void playerMovement(KBInputAccelerator kba, KbInputInGame kb, Player player, Camera camera){
        if (kb.kbCheckIfPlayerMoving() ) {
            if(kb.maxSpeed){
                player.movePlayer(player, camera,  kb.returnMovementType());
            }
            else {
                kba.accelerateInput();
                if (kba.readyToMovePlayer()) {
                    player.movePlayer(player, camera, kb.returnMovementType() );
                }
            }
        }else{
            kba.resetAcceleration();
        }
    }

    protected static void attemptToPlaceMVPAndCheckIfSuccessful(KbInputInGame kb, Player player, int heroSpawnTimer, int heroSpawnCountdown, Camera camera){

        if(kb.dig && level.tileData.get(player.playerTilePositionY).get(player.playerTilePositionX).type == TileType.PATH){ //I'm just going to rename this to ACTION button or something
            GameState.heroActive = true;
            heroSpawnCountdown = 0;
            Mvp.getInstance().setXY(player.playerTilePositionX, player.playerTilePositionY);

            //TODO change this to battle state or something
            GameState.mvpSuccesfullyHidden();
            if(!GameState.stateHidingMvp()){
                BattleBeginScript.run();
            }
        }
    }

    public static void updateNPCLists(){
        MonsterList.getInstance().destroyEnemies();
        HeroList.getInstance().destroyHeroes();
    }

    protected static void setMvpHiding(){
        if(GameState.heroActive ){

        }
    }

    //end of helper functions
}
