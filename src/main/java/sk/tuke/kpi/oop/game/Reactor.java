package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.actions.PerpetualReactorHeating;
import sk.tuke.kpi.oop.game.items.BreakableTool;

import java.util.HashSet;
import java.util.Set;

public class Reactor extends AbstractActor implements Switchable,Repairable{
    private int temperature;
    private int damage;
    private EnergyConsumer energyConsumer;
    private boolean status;
    private Animation turnoffAnimation;
    private Animation normalAnimation;
    private Animation hotAnimation;
    private Animation brokenAnimation;
    private Animation reactorExtinguished;
    private Set<EnergyConsumer> devices;

    public Reactor() {
        temperature = 0;
        damage = 0;
        status = false;
        turnoffAnimation= new Animation("sprites/reactor.png", 80, 80);
        normalAnimation = new Animation("sprites/reactor_on.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        hotAnimation = new Animation("sprites/reactor_hot.png", 80, 80, 0.05f, Animation.PlayMode.LOOP_PINGPONG);
        brokenAnimation = new Animation("sprites/reactor_broken.png", 80, 80, 0.1f, Animation.PlayMode.LOOP_PINGPONG);
        reactorExtinguished = new Animation("sprites/reactor_extinguished.png",80,80);
        setAnimation(turnoffAnimation);

        devices = new HashSet<>();
    }


    public int getTemperature()
    {
        return temperature;
    }

    public int getDamage() {

        return damage;
    }

    public void increaseTemperature(int increment) {

        //ak by boli negativne vstupy
        if (increment <= -1 || status==false) {
            return;
        }
        int localInc = increment;
        // ked je poskodenie od 33 do 66 tak 1.5 sa zvysuje ked viac jak 66 tak 2x
        if(damage>32 && damage<67){
            double docasny=0;
            docasny=Math.ceil(1.5 * increment);

            localInc=(int)docasny;
        }
        else if(damage >=67){
            localInc=increment*2;
        }

        temperature = temperature+localInc;

        //nad 2000 stupnov sa uz poskodzuje reaktor
        if (temperature > 2000) {

            double actualtemperature = temperature-2000;

            //vypocet poskodenia v percentach
            double actualdamage = (actualtemperature/(4000) )* 100;


            if (actualdamage>damage) {
                //ak je poskodenie viac ako 100 tak sa nastavi na 100
                if (actualdamage > 100) {
                    actualdamage = 100;
                    turnOff();
                }
                //nastavi poskodenie na aktualne poskodenie

                damage = (int)actualdamage;
            }
        }

        // aktualizacia animacie
        changeReactorAnimation();
    }


    public void decreaseTemperature(int decrement) {


        if (decrement <= -1 || status==false) {
            return;
        }
        if (temperature<0){
            return;
        }
        int localDec = decrement;

        //ked je poskodenie viac jak 100 neda sa znizit teplota
        if (damage > 99) {
            return;
        }
        //ked je poskodenie nad 50 tak znizovanie teploty je pomalsie

        else if (damage > 49) {
            localDec=decrement/ 2;
        }

        temperature = temperature-localDec;

        //aktualizacia animacie
        changeReactorAnimation();
    }

    private void changeReactorAnimation() {
        //ked je teplota nad 6000 reaktor sa pokazi
        if (temperature > 5999) {
            setAnimation(brokenAnimation);
        }
        else if(status) {

        if (temperature >= 4001) {  //ked bude teplota nad 4000 prehreje sa
                setAnimation(hotAnimation);
            } else {
                    setAnimation(normalAnimation);
                //animacia blika rychlejsie ked sa poskodzuje reaktor
                float damageanim = damage;


                if (damage > 50) {
                    damageanim = 50;
                }

                normalAnimation.setFrameDuration(0.1f - ((0.05f / 50.0f) * damageanim));

                setAnimation(normalAnimation);
            }
        } else {
            setAnimation(turnoffAnimation);
        }
    }

    // oprava reaktora

    public boolean repair(BreakableTool<Repairable> tool) {
        if (tool == null || damage > 99 || damage<1) {
            return false;
        }

        tool.useWith(this);
        return true;
    }

    private void calcNewTemperatureAfterRepair()
    {
        float temperature_new = 2000 + ( ((float)damage/100) *  4000);

        if(temperature_new < temperature)
        {
            temperature = (int)temperature_new;
        }
    }

    @Override
    public boolean repair(){
        if (damage > 99 || damage<1) {
            return false;
        }

        damage=damage - 50;
        if (damage<0){
            damage=0;
        }

        calcNewTemperatureAfterRepair();
        changeReactorAnimation();
        return true;
    }

    public boolean extinguish(BreakableTool<Reactor> tool) {
        if (tool == null || damage < 99) {
            return false;
        }
        tool.useWith(this);
        return true;
    }

    public boolean extinguish(){
        if (damage < 99) {
            return false;
        }
        temperature=4000;
        setAnimation(reactorExtinguished);
        return true;
    }

    @Override
    public void turnOn(){
        if (damage < 100) {
            status = true;
        }

        for (EnergyConsumer device : devices) {
            device.setPowered(true);
        }

        changeReactorAnimation();
        //setAnimation(normalAnimation);
    }

    @Override
    public void turnOff(){
       if (damage < 100) {
           status = false;
       }

        if (energyConsumer!=null){
            energyConsumer.setPowered(false);
        }

        for (EnergyConsumer device : devices) {
            device.setPowered(false);
        }

       changeReactorAnimation();
       // setAnimation(turnoffAnimation);
    }

    @Override
    public boolean isOn(){
        return status;
    }

    public void addDevice(EnergyConsumer energyConsumer){
         if(energyConsumer!=null)
         {
             devices.add(energyConsumer);
         }

        for (EnergyConsumer device : devices) {
            if(isOn()) {
                device.setPowered(true);
            }
            else{
                device.setPowered(false);
            }
        }
    }

    public void removeDevice(EnergyConsumer energyConsumer){
        if(energyConsumer!=null)
        {
            devices.remove(energyConsumer);
            energyConsumer.setPowered(false);
        }
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        scene.scheduleAction(new PerpetualReactorHeating(1), this);
    }
}
