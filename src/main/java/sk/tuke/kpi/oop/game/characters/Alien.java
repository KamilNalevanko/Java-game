package sk.tuke.kpi.oop.game.characters;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.behaviours.Behaviour;

public class Alien extends AbstractActor implements Movable,Enemy,Alive {

    private Health health;

    private Behaviour<? super Alien> behaviour;

    public Alien() {
       this(100);
    }

    public Alien(int health){

        this(health,null);

    }

    public Alien(Behaviour<? super Alien> behaviour){

        this(100,behaviour);

    }

    public Alien(int health, Behaviour<? super Alien> behaviour){

        Animation allien= new Animation("sprites/alien.png",32,32,0.1f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(allien);
        this.health = new Health(health);


        this.behaviour = behaviour;

        this.health.onExhaustion(()->{
            getScene().removeActor(this);
        });

    }

    @Override
    public void startedMoving(Direction direction) {
        getAnimation().setRotation(direction.getAngle());
        getAnimation().play();
        if (direction==Direction.NONE){
            getAnimation().stop();
        }
    }


    @Override
    public int getSpeed() {
        return 1;
    }



    @Override
    public Health getHealth() {
        return health;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::uberacik)).scheduleFor(this);
        if(behaviour != null)
        {
            behaviour.setUp(this);
        }
    }

    private  void uberacik(){
        for (Actor actor : getScene().getActors()) {
            if (this.intersects(actor) && actor instanceof Alive && (actor instanceof Enemy)==false){
                Alive zivyaktor =(Alive)actor;
                zivyaktor.getHealth().drain(1);
            }
        }
    }
}
