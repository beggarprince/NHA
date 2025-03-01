package io;

public class KBInputAccelerator {
    private int acceleration = 0;
    private int accelerationCycle = 60;
    private int currVelocity = 1;
    private int state = 1;

    public int getState()
    {
        return state;
    }

    static KBInputAccelerator instance;

    private KBInputAccelerator(){

    }

    public static KBInputAccelerator getInstance(){
        if(instance == null){
            instance = new KBInputAccelerator();
        }
        return instance;
    }

    public int returnCurrentKBSpeed(){
        return currVelocity;
    }

    public void resetAcceleration(){
        acceleration = 0;
        accelerationCycle = 60;
        currVelocity = 1;
    }

    public void accelerateInput(){
        state++;
        if(acceleration == 181) return;

        acceleration++;
        if(acceleration == accelerationCycle){
            accelerationCycle += accelerationCycle;
            currVelocity++;
        }
    }
    private void resetState(){
        state = 1;
    }

    public boolean readyToMovePlayer(){
        int cycle = 6;
        if ((state * currVelocity) >= cycle){
            resetState();
            return true;
        }
        return false;
    }



}
