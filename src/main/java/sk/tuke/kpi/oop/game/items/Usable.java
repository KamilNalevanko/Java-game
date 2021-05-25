package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.Actor;

public interface Usable<A extends Actor>{

    Class<A> getUsingActorClass();
    void useWith(A actor);
}
