package io.Audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import java.net.URL;

public class Sound {
    static Clip musicClip;
    static Sound instance;
    private static boolean musicMuted = false;
    private static float musicMutePreviousSoundLevel;
    URL[] music ;
    URL[] fx;

    private Sound(){
        music = new URL[30];
        fx = new URL[100];

        music[0] = getClass().getResource("/Music/dungeonIdleTrack2.wav");
        fx[1] = getClass().getResource("/Sound/swordslash.wav");
    }

    public static Sound getSoundInstance(){
        if(instance == null){
            instance = new Sound();
        }
        return instance;
    }

    public void setMusic(int i){
        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(music[i]);
            musicClip = AudioSystem.getClip();
            musicClip.open(ais);

        }catch (Exception e ){
            System.out.println("Could not find sound resource at " + i);
        }
    }

    public static void adjustMusicVolume(float adjustment){
        if(musicClip != null && musicClip.isOpen()){
            try{
                FloatControl gainControl = (FloatControl) musicClip.getControl(FloatControl.Type.MASTER_GAIN);
                if(gainControl.getValue() + adjustment > gainControl.getMaximum() || gainControl.getValue() + adjustment < gainControl.getMinimum()){
                    System.out.println("Volume already at peak/low");
                    return;
                }
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


   public void playFXClip(int i){
        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(fx[i]);
            var fxclip =  AudioSystem.getClip();
            fxclip.open(ais);
            fxclip.start();
        }
        catch (Exception e){
            System.out.println("Could not find sound resource at fx["+i+"]");
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

