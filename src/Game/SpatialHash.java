package Game;

import entities.NPC.NPC;
import graphics.ScreenSettings;

import java.util.ArrayList;

//This may not even be used, but meant for multithreading

public class SpatialHash {
    private static SpatialHash instance;
    private final int scale;

    public ArrayList<NPC> I;
    public ArrayList<NPC> II;
    public ArrayList<NPC> III;

    public static SpatialHash getInstance(){
        if(instance == null){
            instance = new SpatialHash();
        }
        return instance;
    }

    private SpatialHash(){
        scale = ScreenSettings.TS_World_Y / 3;

        I = new ArrayList<>();
        II = new ArrayList<>();
        III = new ArrayList<>();
    }


    //Set to the appropriate array
    public void hash( NPC npc){
        if(npc.tilePositionY < scale){
            npc.zone = 1;
            I.add(npc);
        }
        else if(npc.tilePositionY < 2* scale){

            npc.zone = 2;
            II.add(npc);
        }
        else{
            III.add(npc);
            npc.zone=3;
        }
    }

    public void updateNPCZone(NPC npc){
        //NPC has gone lower than zone 1
        if(npc.zone == 1 && npc.tilePositionY > scale){
            I.remove(npc);
            hash(npc);
        }
        else if (npc.zone ==2 && (npc.tilePositionY > scale * 2 || npc.tilePositionY < scale)){
            II.remove(npc);
            hash(npc);
        }
        else if (npc.zone == 3 && npc.tilePositionY < scale * 2){
            III.remove(npc);
            hash(npc);
        }

    }


}
