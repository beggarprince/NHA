package main.java.Game.Event;

import main.java.io.Audio.Sound;
import main.java.io.Audio.AudioConstants;

public class MVPCaptured {
    //this is going to have some other effects later atm just music change
    public static void run(){
        Sound.overrideMusic(AudioConstants.MUS_ALERT_CAPTURED);
    }

    public static void reset(){
        Sound.resumeMusic();
    }
}
