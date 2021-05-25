package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Disposable;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.framework.actions.Loop;

public class DefectiveLight extends Light implements Repairable{

    private boolean repaired;
    private Disposable loopStatus;

    public DefectiveLight() {
     super();
    }

     public void randomnumber(){
            int number = (int) (Math.random() * 20);
            if (number == 1) {
                toggle();
            }
    }

    public boolean isRepaired() {
        return repaired;
    }

    public void setRepaired(boolean repaired) {
        this.repaired = repaired;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        loopStatus = new Loop<>(new Invoke<>(this::randomnumber)).scheduleFor(this);
    }

    private void destroyAgain()
    {
        setRepaired(false);
        loopStatus = new Loop<>(new Invoke<>(this::randomnumber)).scheduleFor(this);
    }

    @Override
    public boolean repair() {
        if(isRepaired())
        return false;

        setRepaired(true);
        loopStatus.dispose();

        turnOn();

        new ActionSequence<>(
            new Wait<>(10),
            new Invoke<>(this::destroyAgain)
        ).scheduleFor(this);

        return true;
    }
}
