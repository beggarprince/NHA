package main.java.io.Audio;

import javax.sound.sampled.*;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;

public class Sound {
    static Clip musicClip;
    static Clip overrideClip;

    static Sound instance;
    private static boolean musicMuted = false;
    private static float musicMutePreviousSoundLevel;
    private static float lastTrackMusicLevel =0.0f;

    private static HashMap<String, URL> AudioResources;

    private Sound(){
        AudioResources = new HashMap<>();
        AudioResources.put(AudioConstants.MUS_DUNGEON_IDLE_1, getClass().getResource(AudioConstants.MUS_DUNGEON_IDLE_1));
        AudioResources.put(AudioConstants.MUS_DUNGEON_IDLE_2, getClass().getResource(AudioConstants.MUS_DUNGEON_IDLE_2));
        AudioResources.put(AudioConstants.MUS_DUNGEON_IDLE_3, getClass().getResource(AudioConstants.MUS_DUNGEON_IDLE_3));
        AudioResources.put(AudioConstants.MUS_DUNGEON_IDLE_4, getClass().getResource(AudioConstants.MUS_DUNGEON_IDLE_4));
        AudioResources.put(AudioConstants.MUS_HIDE_MVP, getClass().getResource(AudioConstants.MUS_HIDE_MVP));
        AudioResources.put(AudioConstants.MUS_ALERT_CAPTURED, getClass().getResource(AudioConstants.MUS_ALERT_CAPTURED));
        AudioResources.put(AudioConstants.MUS_BATTLE_PHASE, getClass().getResource(AudioConstants.MUS_BATTLE_PHASE));
        AudioResources.put(AudioConstants.MUS_GAME_OVER, getClass().getResource(AudioConstants.MUS_GAME_OVER));
        AudioResources.put(AudioConstants.MUS_HERO_ENTRY, getClass().getResource(AudioConstants.MUS_HERO_ENTRY));
        AudioResources.put(AudioConstants.MUS_HERO_PREVIEW, getClass().getResource(AudioConstants.MUS_HERO_PREVIEW));
        AudioResources.put(AudioConstants.MUS_LEVEL_VICTORY, getClass().getResource(AudioConstants.MUS_LEVEL_VICTORY));
        AudioResources.put(AudioConstants.MUS_ROUND_VICTORY, getClass().getResource(AudioConstants.MUS_ROUND_VICTORY));
        AudioResources.put(AudioConstants.MUS_TITLE, getClass().getResource(AudioConstants.MUS_TITLE));

        //FX
        AudioResources.put(AudioConstants.FX_SWORD_SLASH, getClass().getResource(AudioConstants.FX_SWORD_SLASH));
    }

    public static Sound getSoundInstance(){
        if(instance == null){
            instance = new Sound();
        }
        return instance;
    }

    public static void setAndLoopMusic(String requestedMusic){
        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(AudioResources.get(requestedMusic));
            if(musicClip != null && musicClip.isOpen()){
                musicClip.stop();
              //  musicClip.close();
            }
            musicClip = AudioSystem.getClip();
            musicClip.open(ais);
            if(lastTrackMusicLevel != 0.0f){
                adjustMusicVolume(lastTrackMusicLevel);
            }
            musicClip.loop(Clip.LOOP_CONTINUOUSLY);

        }catch (Exception e ){
            System.out.println("Could not find sound resource " + requestedMusic);
        }
    }

    public static void setMusic(String requestedMusic){
        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(AudioResources.get(requestedMusic));
            if(musicClip != null && musicClip.isOpen()){
                musicClip.stop();
            //    musicClip.close();
            }
            musicClip = AudioSystem.getClip();
            musicClip.open(ais);
            if(lastTrackMusicLevel != 0.0f){
                adjustMusicVolume(lastTrackMusicLevel);
            }
            musicClip.start();
        }catch (Exception e ){
            System.out.println("Could not find sound resource " + requestedMusic);
        }
    }

    public static void overrideMusic(String requestedMusic){

        //Stop but don't close so we can resume
        if(musicClip != null && musicClip.isOpen()){
            musicClip.stop();
        }

        try {
            AudioInputStream l = AudioSystem.getAudioInputStream(AudioResources.get(requestedMusic));
            overrideClip = AudioSystem.getClip();
            overrideClip.open(l);
            if(lastTrackMusicLevel != 0.0f){
                adjustMusicVolume(lastTrackMusicLevel);
            }
            overrideClip.start();

        } catch (UnsupportedAudioFileException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (LineUnavailableException e) {
            throw new RuntimeException(e);
        }

    }

    public static void resumeMusic(){
        if(overrideClip != null && overrideClip.isOpen()){
            overrideClip.stop();
            overrideClip.close();
        }
        musicClip.start();
    }

    public static void adjustMusicVolume(float adjustment){

        if(musicClip != null && musicClip.isOpen()){
            try{
                FloatControl gainControl = (FloatControl) musicClip.getControl(FloatControl.Type.MASTER_GAIN);
                if(gainControl.getValue() + adjustment > gainControl.getMaximum()
                        || gainControl.getValue() + adjustment < gainControl.getMinimum()){
                    System.out.println("Volume already at peak/low");
                    return;
                }
                lastTrackMusicLevel += adjustment;
                gainControl.setValue(gainControl.getValue() + adjustment);
            }
            catch (IllegalArgumentException e){
                System.out.println("Failed to adjust music volume");
            }
        }
        else {
            System.out.println("Unable to adjust, clip is null or not opened");
        }
    }

    public static void muteMusicToggle(){
        if(musicMuted){
            unmuteMusic();
        }
        else muteMusic();
    }


    private static void muteMusic(){
        if(musicClip != null && musicClip.isOpen()){
            try{
                FloatControl gainControl = (FloatControl) musicClip.getControl(FloatControl.Type.MASTER_GAIN);
                musicMutePreviousSoundLevel = gainControl.getValue();
                gainControl.setValue(gainControl.getMinimum());
                musicMuted = true;
            }
            catch (IllegalArgumentException e){
                System.out.println("Failed to mute music volume");
            }
        }
    }

    private static void unmuteMusic(){
        if(musicClip != null && musicClip.isOpen()){
            try{
                FloatControl gainControl = (FloatControl) musicClip.getControl(FloatControl.Type.MASTER_GAIN);
                gainControl.setValue(musicMutePreviousSoundLevel);
                musicMuted = false;
            }
            catch (IllegalArgumentException e){
                System.out.println("Failed to unmute music volume");
            }
        }
    }


   public void playFXClip(String requestedFX){
        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(AudioResources.get(requestedFX));
            var fxclip =  AudioSystem.getClip();
            fxclip.open(ais);
            fxclip.start();
        }
        catch (Exception e){
            System.out.println("Could not find sound resource "+ requestedFX);
        }
   }

    public void play(){
        musicClip.start();
    }

    public void loop(){
        musicClip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop(){
        musicClip.stop();
    }


}

