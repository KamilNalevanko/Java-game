package sk.tuke.kpi.oop.game.weapons;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.characters.Alive;
import sk.tuke.kpi.oop.game.characters.Ripley;

public class Bullet extends AbstractActor implements Fireable {

    private int damage=10;
    private  int speed=0;

    public Bullet() {
        speed=4;
        Animation animacia= new Animation("sprites/bullet.png",16,16);
        setAnimation(animacia);
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public void startedMoving(Direction direction) {
          if(direction!=null && direction!=Direction.NONE){
              getAnimation().setRotation(direction.getAngle());
          }
    }

    @Override
    public int getSpeed() {
        return speed;
    }

    @Override
    public void collidedWithWall() {
        getScene().removeActor(this);
    }


    public void zmazanie(){
        if (hit()){
            collidedWithWall();
        }
    }

   public boolean hit(){
        for (Actor actor:getScene().getActors()){
            if (this.intersects(actor) && actor instanceof Alive && !(actor instanceof Ripley))
            {
                Alive.class.cast(actor).getHealth().drain(damage);
                return true;
            }
        }
        return false;
   }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::zmazanie)).scheduleFor(this);

    }
}
