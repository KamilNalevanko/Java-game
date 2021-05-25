package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.characters.Armed;
import sk.tuke.kpi.oop.game.weapons.Fireable;

public class Fire<M extends Armed> extends AbstractAction<M> {

    public Fire() {
    }

    @Override
    public void execute(float deltaTime) {
        if (getActor()!=null && getActor().getFirearm() != null && getActor().getFirearm().getCurrentAmmo() > 0){

            Fireable fireable = getActor().getFirearm().fire();
            getActor().getScene().addActor(fireable, getActor().getPosX(), getActor().getPosY());
            new Move<>(Direction.fromAngle(getActor().getAnimation().getRotation()),54812).scheduleFor(fireable);

    }
        setDone(true);
    }
}
