package main.java.Game.Scripts;

import main.java.io.Audio.Sound;
import main.java.util.AudioConstants;

public class MVPCaptured {
    //this is going to have some other effects later atm just music change
    public static void run(){
        Sound.setAndLoopMusic(AudioConstants.MUS_ALERT_CAPTURED);
    }

}
