package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Alive;

public class Energy extends AbstractActor implements Usable<Alive> {

    public Energy() {
        Animation animacia = new Animation( "sprites/energy.png",16,16);
        setAnimation(animacia);
    }

    @Override
    public Class<Alive> getUsingActorClass() {
        return Alive.class;
    }

    @Override
    public void useWith(Alive actor) {
        if (actor != null && actor.getHealth().getValue() <= actor.getHealth().getMax()){
                actor.getHealth().restore();
                getScene().removeActor(this);
    }
    }


}
