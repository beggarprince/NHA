package main.java.Game.Event;

import main.java.Game.GameState;
import main.java.Game.LevelState;
import main.java.Game.State;
import main.java.entities.NPC.Heroes.Hero;
import main.java.entities.NPC.Heroes.HeroFactory;
import main.java.entities.NPC.Heroes.HeroList;
import main.java.graphics.Camera;
import main.java.graphics.ScreenSettings;
import main.java.io.Audio.Sound;
import main.java.level.Level;
import main.java.io.Audio.AudioConstants;

import java.util.ArrayList;
import java.util.List;
//Todo need a list of heroes and their stats to then simply add here with a script
public class HeroEntryScript {

    //take in heroes and spawn them

    //This is not a constructor, this is the only call you can make to run said script
    public static void run(ArrayList<LevelState.HeroData> heroList, HeroList mainList, Camera camera){
        script(camera);

        for (LevelState.HeroData requestedHero : heroList) {
            System.out.println("Spawning the [adjective] " + requestedHero.heroName);
            spawnHero(requestedHero.type, mainList, requestedHero.heroName);
        }

    }

    public static void run(String hero, HeroList mainList, Camera camera, String name){
        script(camera);
        spawnHero(hero, mainList, name);
    }

    private static void script(Camera camera){
        setEntryMusic();
        setCinematicCamera(camera);
        //1 is atm a placeholder since we don't have the raw data rn
        GameState.INPUT_STATE += (1+getAmountOfTextBoxes());
        GameState.gameState = State.CINEMATIC;
    }

    //TODO fix this to work with enums
    private static void spawnHero(String hero,  HeroList heroList, String name){
        try {
            heroList.addHero(HeroFactory.getInstance().createHero(hero,
                    Level.entryPoint.x,
                    Level.entryPoint.y,
                    name
            ));
        }catch (Exception e){
            System.out.println("Could not spawn hero");
        }
    }

    private void addHeroesToList(List<Hero> heroList) {
        for (Hero l1 : heroList) {
            HeroList.getInstance().addHero(l1);
        }
    }

    private static void setEntryMusic(){
        Sound.setMusic(AudioConstants.MUS_HERO_ENTRY);
    }

    //28 and -2 xy
    private static void setCinematicCamera(Camera camera){
        //This seems to be a single tile off
        //-Styleoffset is causing issues
           camera.setCinematicCamera(ScreenSettings.PX_WORLD_WIDTH / 2 - ScreenSettings.PX_CAMERA_OFFSET_X + ScreenSettings.TILE_SIZE ,
                   -ScreenSettings.STYLE_OFFSET);
    }

    private static int getAmountOfTextBoxes(){
        //ATM it will access data from level or something which will have the raw numbers for this
        return 0;
    }

}
