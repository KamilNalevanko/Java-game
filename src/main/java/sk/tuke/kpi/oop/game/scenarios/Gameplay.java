package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.Scenario;
import sk.tuke.kpi.oop.game.Cooler;
import sk.tuke.kpi.oop.game.Reactor;
import sk.tuke.kpi.oop.game.items.Hammer;

public class Gameplay extends Scenario {




    public Gameplay() {

    }


    @Override
    public void setupPlay(@NotNull Scene scene) {
        Reactor reaktor = new Reactor();
        scene.addActor(reaktor, 146, 93);
        reaktor.turnOn();

        Cooler chladic = new Cooler(reaktor);
        scene.addActor(chladic ,170 ,40);

        new ActionSequence<>(
            new Wait<>(5),
            new Invoke<>(chladic::turnOn)
        ).scheduleFor(chladic);

        Hammer kladivo = new Hammer();
        scene.addActor(kladivo,50 ,50);

        new When<>(
            () -> reaktor.getTemperature() >= 3000,
            new Invoke<>(() -> reaktor.repair(kladivo))
        ).scheduleFor(reaktor);
    }




}
