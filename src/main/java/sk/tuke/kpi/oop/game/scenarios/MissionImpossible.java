package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.oop.game.Locker;
import sk.tuke.kpi.oop.game.Ventilator;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.items.AccessCard;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.openables.Door;

import java.util.function.Consumer;

public class MissionImpossible implements SceneListener {
    private int life=0;
    private Ripley ripley;
    private int time=5;

    public static class  Factory implements ActorFactory{
        @Override
        public @Nullable Actor create(@Nullable String type, @Nullable String name) {
            if (name.equals("access card"))
            {
                return new AccessCard();
            }
            else if(name.equals("door"))
            {
                return new Door("Classic Door", Door.Orientation.VERTICAL);
            }
            else if(name.equals("energy")){
                return new Energy();
            }
            else if(name.equals("locker")){
                return new Locker();
            }
            else if(name.equals("ventilator")){
                return new Ventilator();
            }
            else if(name.equals("ellen")){
                return new Ripley();
            }
            return null;
        }
    }

    @Override
    public void sceneInitialized(@NotNull Scene scene) {
      ripley=scene.getFirstActorByType(Ripley.class);
      scene.follow(ripley);
        MovableController keyController = new MovableController(ripley);
        Disposable disposable = scene.getInput().registerListener(keyController);

        KeeperController keeperController= new KeeperController(ripley);
        Disposable disposable02 = scene.getInput().registerListener(keeperController);

        Door door = scene.getFirstActorByType(Door.class);
        door.close();

       scene.getMessageBus().subscribeOnce(Door.DOOR_OPENED, new Consumer<Door>() {
           @Override
           public void accept(Door door) {
             life=1;

           }

       });
       scene.getMessageBus().subscribe(Ripley.RIPLEY_DIED, new Consumer<Ripley>() {
           @Override
           public void accept(Ripley ripley) {
               disposable.dispose();
               disposable02.dispose();
               life=0;
           }

       });
       scene.getMessageBus().subscribe(Ventilator.VENTILATOR_REPAIRED, new Consumer<Ventilator>() {
           @Override
           public void accept(Ventilator ventilator) {
              life=0;

           }
       });
    }

    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        ripley.showRipleyState();
        if(life==1){

            time--;
            if (time<0) {
                time=5;
                ripley.getHealth().drain(1);
            }

        }
    }

}
