package sk.tuke.kpi.oop.game.actions;

import org.jetbrains.annotations.Nullable;
import sk.tuke.kpi.gamelib.actions.Action;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;


public class Move<M extends Movable> implements Action <Movable>{

    private Movable aktor;
    private boolean stav;
    private boolean pohyb;
    private Direction smer;
    private float trvanie;
    private float dlzka;


    public Move(Direction direction, float duration){
       stav = false;
       pohyb =false;
       smer=direction;
       trvanie=duration;
       dlzka=0;
    }

    public Move(Direction direction){
        this(direction,0);
    }



    @Nullable
    @Override
    public Movable getActor() {
        return aktor;
    }

    @Override
    public void setActor(@Nullable Movable actor) {
      aktor =actor;
    }

    @Override
    public boolean isDone() {
        return stav;
    }



    @Override
    public void execute(float deltaTime) {

        if(dlzka>= trvanie) {
            stav =true;
        }else{
            if (getActor() == null) {
                stop();
                return;
            }


            if (pohyb == false) {
                aktor.startedMoving(smer);
                pohyb = true;
            }

            aktor.setPosition(aktor.getPosX() + (smer.getDx() * aktor.getSpeed()), aktor.getPosY() + (smer.getDy() * aktor.getSpeed()));

            if (aktor.getScene().getMap().intersectsWithWall(aktor)) {
                aktor.setPosition(aktor.getPosX() - (smer.getDx() * aktor.getSpeed()), aktor.getPosY() - (smer.getDy() * aktor.getSpeed()));
                aktor.collidedWithWall();
            }

            dlzka = dlzka + deltaTime;
            if (dlzka > trvanie) {
                stop();
            }
        }
    }

    @Override
    public void reset() {
        stav=false;
        pohyb =false;
        dlzka=0;
    }

    public void stop(){
        if (getActor()!=null) {
            stav = true;
            aktor.stoppedMoving();
        }
    }
}
