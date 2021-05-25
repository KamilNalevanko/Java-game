package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;


public class Ventilator extends AbstractActor implements Repairable {
      private Animation fan;
      private boolean broken =false;
    public static final Topic<Ventilator> VENTILATOR_REPAIRED = Topic.create("Ventilator repaired", Ventilator.class);


    public Ventilator() {
        fan = new Animation("sprites/ventilator.png",32,32,0.1f, Animation.PlayMode.LOOP);
        setAnimation(fan);
        broken =true;
        fan.stop();

    }

    @Override
    public boolean repair() {
        if(broken ==true) {
            fan.play();
            broken = false;
            getScene().getMessageBus().publish(VENTILATOR_REPAIRED,this);
            return true;

        }else
            return false;
    }
}
