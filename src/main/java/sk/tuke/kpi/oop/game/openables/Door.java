package sk.tuke.kpi.oop.game.openables;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.map.MapTile;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.items.Usable;


public class Door extends AbstractActor implements Openable, Usable<Actor> {
    private boolean isOpened;
    public static final Topic<Door> DOOR_OPENED = Topic.create("door opened", Door.class);
    public static final Topic<Door> DOOR_CLOSED = Topic.create("door closed", Door.class);
    private Orientation orientacia;

    public enum Orientation{
        HORIZONTAL,
        VERTICAL
    }

    public Door(String name, Orientation orientation) {
        super(name);
        orientacia = orientation;

        Animation normal;

        if(orientation == Orientation.HORIZONTAL)
        {
            normal  = new Animation("sprites/hdoor.png", 32, 16, 0.1f, Animation.PlayMode.ONCE);

        }else {
            normal  = new Animation("sprites/vdoor.png", 16, 32, 0.1f, Animation.PlayMode.ONCE);
        }
        setAnimation(normal);
        isOpened=true;
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        close();
    }

    @Override
    public void open() {
        if (isOpened==false) {
            getAnimation().setPlayMode(Animation.PlayMode.ONCE);
            getAnimation().play();

            int x = getPosX() / 16;
            int y = getPosY() / 16;

            if(orientacia == Orientation.HORIZONTAL)
            {
                getScene().getMap().getTile(x, y).setType(MapTile.Type.CLEAR);
                getScene().getMap().getTile(x+1, y).setType(MapTile.Type.CLEAR);
            }else {
                getScene().getMap().getTile(x, y).setType(MapTile.Type.CLEAR);
                getScene().getMap().getTile(x, y + 1).setType(MapTile.Type.CLEAR);
            }
            getScene().getMessageBus().publish(DOOR_OPENED,this);
            isOpened=true;
        }

    }

    @Override
    public void close() {
        if (isOpened==true) {
            getAnimation().setPlayMode(Animation.PlayMode.ONCE_REVERSED);
            getAnimation().play();


            int x = getPosX() / 16;
            int y = getPosY() / 16;

            if(orientacia == Orientation.HORIZONTAL)
            {
                getScene().getMap().getTile(x, y).setType(MapTile.Type.WALL);
                getScene().getMap().getTile(x+1, y).setType(MapTile.Type.WALL);
            }else {
                getScene().getMap().getTile(x, y).setType(MapTile.Type.WALL);
                getScene().getMap().getTile(x, y + 1).setType(MapTile.Type.WALL);
            }
            getScene().getMessageBus().publish(DOOR_CLOSED,this);
            isOpened=false;
        }
    }

    @Override
    public boolean isOpen() {
        return isOpened;
    }

    @Override
    public Class<Actor> getUsingActorClass() {
        return Actor.class;
    }


    @Override
    public void useWith(Actor actor) {
         if (isOpened){
             close();
         }else{
             open();
         }
    }
}
