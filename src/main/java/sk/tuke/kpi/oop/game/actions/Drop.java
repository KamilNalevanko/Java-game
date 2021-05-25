package sk.tuke.kpi.oop.game.actions;

import sk.tuke.kpi.gamelib.framework.actions.AbstractAction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.items.Collectible;

public class Drop<K extends Keeper> extends AbstractAction<K> {
    @Override
    public void execute(float deltaTime) {
        if (getActor() != null) {

            Collectible collectible = getActor().getBackpack().peek();

            if(collectible != null) {
                getActor().getBackpack().remove(collectible);
                int x = getActor().getPosX() + getActor().getWidth() / 2 + collectible.getWidth() / 2;
                int y = getActor().getPosY() + getActor().getHeight() / 2 + collectible.getHeight() / 2;
                getActor().getScene().addActor(collectible, x, y);
            }


        }
        setDone(true);
    }
}
