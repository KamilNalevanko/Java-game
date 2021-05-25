package sk.tuke.kpi.oop.game.controllers;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Input;
import sk.tuke.kpi.gamelib.KeyboardListener;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.actions.Move;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MovableController implements KeyboardListener {

    private Movable aktor;

    private Map<Input.Key, Direction> keyDirectionMap = Map.ofEntries(
        Map.entry(Input.Key.UP, Direction.NORTH),
        Map.entry(Input.Key.DOWN, Direction.SOUTH),
        Map.entry(Input.Key.RIGHT, Direction.EAST),
        Map.entry(Input.Key.LEFT, Direction.WEST)
    );

    private Move<Movable> pohyb;
    private Set<Input.Key> kluce;

    public MovableController(Movable actor) {
     this.aktor =actor;
     kluce = new HashSet<>();
    }

    @Override
    public void keyPressed(@NotNull Input.Key kluc) {
        if(keyDirectionMap.containsKey(kluc)) {
            Direction smer = keyDirectionMap.get(kluc);



            if (pohyb!=null){
                pohyb.stop();
            }

            for (Input.Key kluc2 : kluce) {
                Direction smerkluca = keyDirectionMap.get(kluc2);
                 smer=smer.combine(smerkluca);
            }

            kluce.add(kluc);

            System.out.println("smer: "+smer);
            pohyb = new Move<>(smer, 560000);
            pohyb.scheduleFor(aktor);
        }
    }


    @Override
    public void keyReleased(@NotNull Input.Key key) {

        boolean set= true;
        Direction smer = Direction.NONE;

        kluce.remove(key);

        for (Input.Key klucik : kluce){

            if(set){
                smer = keyDirectionMap.get(klucik);
                set=false;
            }else{
                Direction smerkluca=keyDirectionMap.get(klucik);
                smer=smer.combine(smerkluca);
            }

        }

        if(pohyb!=null){
            pohyb.stop();
        }

        pohyb = new Move<>(smer, 455555);
        pohyb.scheduleFor(aktor);
    }
}
