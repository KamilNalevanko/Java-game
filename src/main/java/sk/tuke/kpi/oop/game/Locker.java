package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.items.Hammer;
import sk.tuke.kpi.oop.game.items.Usable;

public class Locker extends AbstractActor implements Usable<Ripley> {

    private int uses=0;

    public Locker() {

        Animation normal= new Animation("sprites/locker.png",16,16);
        setAnimation(normal);
        uses=1;
    }


    @Override
    public Class<Ripley> getUsingActorClass() {
        return Ripley.class;
    }

    @Override
    public void useWith(Ripley actor) {
        if(uses==1){
            uses=0;
            Hammer hammer = new Hammer();
            int x = getPosX();
            int y = getPosY();
            getScene().addActor(hammer,x,y);

        }

    }
}
