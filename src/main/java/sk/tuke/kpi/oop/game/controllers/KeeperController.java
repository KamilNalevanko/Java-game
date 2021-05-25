package sk.tuke.kpi.oop.game.controllers;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.actions.Drop;
import sk.tuke.kpi.oop.game.actions.Shift;
import sk.tuke.kpi.oop.game.actions.Take;
import sk.tuke.kpi.oop.game.actions.Use;
import sk.tuke.kpi.oop.game.items.Collectible;
import sk.tuke.kpi.oop.game.items.Usable;

public class KeeperController implements KeyboardListener {
    private Keeper keeper;

    public KeeperController(Keeper keeper) {
        this.keeper = keeper;
    }

    @Override
    public void keyPressed(@NotNull Input.Key key) {

        half1(key);
        half2(key);
    }


    public void half1(@NotNull Input.Key key) {
        switch (key) {
            case ENTER:
                new Take<Keeper>().scheduleFor(keeper);
                break;
            case BACKSPACE:
                new Drop<Keeper>().scheduleFor(keeper);
                break;
            case S:
                new Shift<Keeper>().scheduleFor(keeper);
                break;
            default:
                return;
        }
    }

    public void half2(@NotNull Input.Key key) {
        switch (key) {
            case U:
                for (Actor actor : keeper.getScene()) {
                    if (actor instanceof Usable<?> && actor.intersects(keeper)) {
                        new Use<>((Usable<?>) actor).scheduleForIntersectingWith(keeper);
                    }
                }
                break;

            case B:
                Collectible predmet = keeper.getBackpack().peek();
                if (predmet instanceof Usable<?>){   //predmet != null &&
                    Usable<?> pouzitelnypredmet= (Usable<?>)predmet;
                    new Use<>(pouzitelnypredmet).scheduleForIntersectingWith(keeper);
                }
                break;
            default:
                return;
        }
    }
}
