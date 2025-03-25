package main.java;

import main.java.entities.NPC.Heroes.Hero;
import main.java.entities.NPC.Heroes.HeroFactory;
import main.java.entities.NPC.Heroes.HeroList;
import main.java.graphics.Camera;
import main.java.graphics.ScreenSettings;
import main.java.io.Audio.Sound;
import main.java.level.Level;
import main.java.util.AudioConstants;
import main.java.util.Coordinate;

import java.util.List;
//Todo need a list of heroes and their stats to then simply add here with a script
public class HeroEntryScript {

    //Coordinate for camera to make follow animation
    private static Coordinate heroEntryCameraPosition = new Coordinate(
            (Level.entryPoint.x * ScreenSettings.TILE_SIZE) - (ScreenSettings.PX_SCREEN_WIDTH / 2),
            (Level.entryPoint.y * ScreenSettings.TILE_SIZE) - (ScreenSettings.PX_SCREEN_HEIGHT / 2));

    //take in heroes and spawn them

    //This is not a constructor, this is the only call you can make to run said script
    public static void run(List<String> heroList, HeroList mainList, Camera camera){
        GameState.gameState = State.CINEMATIC;
        setEntryMusic();

        for (String requestedHero : heroList) {
            spawnHero(requestedHero, mainList);
        }
    }

    public static void run(String hero, HeroList mainList, Camera camera){
        GameState.gameState = State.CINEMATIC;
        spawnHero(hero, mainList);
        setEntryMusic();
    }

    private static void spawnHero(String hero,  HeroList heroList){
        try {
            heroList.addHero(HeroFactory.getInstance().createHero(hero,
                    Level.entryPoint.x,
                    Level.entryPoint.y));
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


}
