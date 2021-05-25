package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Computer extends AbstractActor implements EnergyConsumer {

    private boolean energy;

    public Computer() {
        Animation normalAnimation = new Animation("sprites/computer.png", 80, 48, 0.2f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(normalAnimation);
        getAnimation().stop();
    }

    public int add(int num1, int num2)
    {
        if(!energy)
        {
            return 0;
        }
          int sum=0;
          sum= num1 + num2;
          return sum;
    }

    public float add(float num1, float num2)
    {
        if(!energy)
        {
            return 0;
        }
        float sum=0;
        sum=num1+num2;
        return sum;
    }

    public int sub(int num1, int num2)
    {
        if(!energy)
        {
            return 0;
        }
        int sum=0;
        sum=num1-num2;
        return sum;
    }

    public float sub(float num1, float num2)
    {
        if(!energy)
        {
            return 0;
        }
        float sum=0;
        sum=num1-num2;
        return sum;
    }

    @Override
    public void setPowered(boolean energy) {
        this.energy = energy;

        if(!energy)
        {
            getAnimation().stop();
        }else{
            getAnimation().play();
        }
    }
}
