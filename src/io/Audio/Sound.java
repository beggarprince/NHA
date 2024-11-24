package io.Audio;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class Sound {
    Clip clip;

    URL[] music ;
    URL[] fx;

    public Sound(){
        music = new URL[30];
        fx = new URL[100];

        music[0] = getClass().getResource("/Music/dungeonIdleTrack2.wav");
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

