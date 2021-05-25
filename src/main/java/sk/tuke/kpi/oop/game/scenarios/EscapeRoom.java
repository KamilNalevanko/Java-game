package sk.tuke.kpi.oop.game.scenarios;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.oop.game.behaviours.Observing;
import sk.tuke.kpi.oop.game.behaviours.RandomlyMoving;
import sk.tuke.kpi.oop.game.characters.Alien;
import sk.tuke.kpi.oop.game.characters.AlienMother;
import sk.tuke.kpi.oop.game.characters.Ripley;
import sk.tuke.kpi.oop.game.controllers.KeeperController;
import sk.tuke.kpi.oop.game.controllers.MovableController;
import sk.tuke.kpi.oop.game.controllers.ShooterController;
import sk.tuke.kpi.oop.game.items.Ammo;
import sk.tuke.kpi.oop.game.items.Energy;
import sk.tuke.kpi.oop.game.openables.Door;


public class EscapeRoom implements SceneListener {
    private Ripley ripley;


    @Override
    public void sceneUpdating(@NotNull Scene scene) {
        ripley.showRipleyState();

    }

    public static class Factory implements ActorFactory {


        @Override
        public @Nullable Actor create(@Nullable String type, @Nullable String name) {

            if(name.equals("ellen")){
                return new Ripley();
            }else if(name.equals("alien")){


                switch (type){
                    case "running":
                        return new Alien(new RandomlyMoving());
                    case "waiting1":
                        return new Alien(new Observing<>(
                            Door.DOOR_OPENED,
                            actor -> actor.getName().equals("front door"),
                            new RandomlyMoving()
                        ));
                    case "waiting2":
                        return new Alien(new Observing<>(
                            Door.DOOR_OPENED,
                            actor -> actor.getName().equals("back door"),
                            new RandomlyMoving()
                        ));
                    default:
                        return new Alien();
                }
            }else if(name.equals("ammo")){
                return new Ammo();
            }else if(name.equals("energy")){
                return new Energy();
            }
            else if(name.equals("alien mother")){
                return new AlienMother(new Observing<>(
                    Door.DOOR_OPENED,
                    actor -> actor.getName().equals("back door"),
                    new RandomlyMoving()
                ));
            }
            return createDoor(name,type);
        }

        private Actor createDoor(String name, String type)
        {
            if(type != null) {
                if (type.equals("vertical")) {
                    return new Door(name, Door.Orientation.VERTICAL);
                } else if (type.equals("horizontal")) {
                    return new Door(name, Door.Orientation.HORIZONTAL);
                }
            }

            return null;
        }

    }
    @Override
    public void sceneInitialized(@NotNull Scene scene) {

        ripley=scene.getFirstActorByType(Ripley.class);
        scene.follow(ripley);
        MovableController keyController = new MovableController(ripley);
        scene.getInput().registerListener(keyController);
        KeeperController keeperController= new KeeperController(ripley);
        scene.getInput().registerListener(keeperController);
        ShooterController shooterController= new ShooterController(ripley);
         scene.getInput().registerListener(shooterController);

    }


    @Override
    public void sceneCreated(@NotNull Scene scene) {

        /*
        scene.getMessageBus().subscribe(World.ACTOR_ADDED_TOPIC, new Consumer<Actor>() {
            @Override
            public void accept(Actor actor) {
                if (actor instanceof Alien){
                   ((Alien)actor).pohyb();
                }
            }
        });

         */
    }
}
