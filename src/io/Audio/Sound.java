package io.Audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {
    Clip clip;
    static Sound instance;
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
            clip = AudioSystem.getClip();
            clip.open(ais);

        }catch (Exception e ){
            System.out.println("Could not find sound resource at " + i);
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
        clip.start();
    }

    public void loop(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop(){
        clip.stop();
    }


}

