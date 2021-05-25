package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Collectible;


public class Take<K extends Keeper> extends AbstractAction<K> {


    @Override
    public void execute(float deltaTime) {
        if (getActor()!=null) {
        for (Actor actorFromScene : getActor().getScene().getActors()) {
            if (actorFromScene instanceof Collectible && getActor().intersects(actorFromScene)) {
                try {

                    getActor().getBackpack().add((Collectible) actorFromScene);
                    getActor().getScene().removeActor(actorFromScene);

                }catch (Exception ex){
                    getActor().getScene().getGame().getOverlay().drawText(ex.getMessage(), 80, 20).showFor(2);
                }
                break;
            }
        }

        }
        setDone(true);
    }


}
