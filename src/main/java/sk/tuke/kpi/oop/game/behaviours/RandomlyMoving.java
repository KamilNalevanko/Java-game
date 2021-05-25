package sk.tuke.kpi.oop.game.behaviours;

import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;

import java.util.Random;

public class RandomlyMoving implements Behaviour<Movable> {

    private Random random;
    private Move<Movable> move;
    private Movable movable;

    @Override
    public void setUp(Movable actor) {

        if(actor == null)
        {
            return ;
        }

        move = null;
        random = new Random();
        movable = actor;
        pohyb();
    }

    public void pohyb(){
        new Loop<>(new Invoke<>(this::randomMove)).scheduleFor(movable);

    }

    private void randomMove(){

        int smer=random.nextInt(8);

        if (move==null||move.isDone()) {
            if (smer >=0 || smer <=3){
                half1(smer);}
            else if (smer>=4 || smer <=8){
                half2(smer);}
        }
    }


    public  void half1(int smer ){

        Direction direction;
        direction=Direction.NONE;

        if (smer == 4) {
            direction = Direction.NORTHEAST;
        }
        else if (smer == 5) {
            direction = Direction.NORTHWEST;
        } else if (smer == 6) {
            direction = Direction.SOUTHEAST;
        } else if (smer==7) {
            direction = Direction.SOUTHWEST;
        }
        move = new Move<>(direction, 3);
        move.scheduleFor(movable);

    }

    public  void half2(int smer ){

        Direction direction;
        direction=Direction.NONE;

        if (smer == 0) {
            direction = Direction.NORTH;
        } else if (smer == 1) {
            direction = Direction.SOUTH;
        } else if (smer == 2) {
            direction = Direction.EAST;
        } else if (smer == 3) {
            direction = Direction.WEST;
        }
        move = new Move<>(direction, 3);
        move.scheduleFor(movable);

    }

}
