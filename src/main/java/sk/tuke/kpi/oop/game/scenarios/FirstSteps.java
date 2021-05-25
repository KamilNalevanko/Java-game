package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.SceneListener;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.items.Ammo;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.items.FireExtinguisher;
import sk.tuke.kpi.oop.game.items.Hammer;

public class FirstSteps implements SceneListener {
    private Ripley hrac;
    @Override
    public void sceneInitialized(@NotNull Scene scene) {
        hrac=new Ripley();
        scene.addActor(hrac,0,0);

        MovableController keyController = new MovableController(hrac);
        scene.getInput().registerListener(keyController);

        KeeperController keeperController= new KeeperController(hrac);
        scene.getInput().registerListener(keeperController);

        Energy energia = new Energy();
        scene.addActor(energia,66,66);

        Ammo naboje = new Ammo();
        scene.addActor(naboje,40 ,40 );

        FireExtinguisher fireExtinguisher2= new FireExtinguisher();
        scene.addActor(fireExtinguisher2,90 ,90);

        Hammer hammer = new Hammer();
        hrac.getBackpack().add(hammer);

        FireExtinguisher fireExtinguisher= new FireExtinguisher();
        hrac.getBackpack().add(fireExtinguisher);

        FireExtinguisher fireExtinguisher3= new FireExtinguisher();
        hrac.getBackpack().add(fireExtinguisher3);

        hrac.getBackpack().shift();


    }

    @Override
    public void sceneUpdating(@NotNull Scene scene) {

        for (Actor actorik : scene.getActors()) {
            if(actorik instanceof Energy && actorik.intersects(hrac))
            {
                Energy energy = (Energy)actorik;
                energy.useWith(hrac);
            }
        }



        for (Actor actorik : scene.getActors()) {
            if(actorik instanceof Ammo && actorik.intersects(hrac))
            {
                Ammo naboje = (Ammo)actorik;
                naboje.useWith(hrac);
            }
        }
       hrac.showRipleyState();
    }

}
