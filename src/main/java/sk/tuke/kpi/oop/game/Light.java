package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Light extends AbstractActor implements Switchable ,EnergyConsumer {

    private boolean  on;
    private boolean electricity;
    private Animation onAnimation;
    private Animation offAnimation;

    public Light (){

        on=false;
        Animation normalAnimation= new Animation("sprites/light_off.png");
        onAnimation=new Animation("sprites/light_on.png");
        offAnimation= new Animation("sprites/light_off.png");
        setAnimation(normalAnimation);

    }
    private void updateAnimation(){

if (electricity ==true && on==true){

    setAnimation(onAnimation);
}
else {setAnimation(offAnimation);}
    }


    public void setElectricityFlow(boolean elektrika ){
        if (elektrika==true){
            electricity =true;
            updateAnimation();
        }else{
            electricity =false;
            updateAnimation();
        }
    }

    public void toggle(){
    if (on==false){
        on=true;
    }else{on=false;}
    updateAnimation();
    }

    @Override
    public boolean isOn() {
        return on;
    }

    @Override
    public void turnOff() {
        on=false;
        updateAnimation();
    }

    @Override
    public void turnOn() {
        on=true;
        updateAnimation();
    }
// TOTO JE ZLE
    @Override
    public void setPowered(boolean energy) {
            electricity=energy;
            updateAnimation();
    }
}
