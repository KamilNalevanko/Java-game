package sk.tuke.kpi.oop.game;

import org.jetbrains.annotations.NotNull;
import sk.tuke.kpi.gamelib.Scene;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

import java.awt.geom.Rectangle2D;

public class Teleport extends AbstractActor {
    private Teleport teleportTo;
    private boolean teleportUsed;

    public Teleport(Teleport destinationTeleport) {

        if(destinationTeleport != this)
        {
            teleportTo = destinationTeleport;
        }

        teleportUsed = false;

        Animation animation = new Animation("sprites/lift.png");
        setAnimation(animation);
    }

    public Teleport getDestination() {
        return teleportTo;
    }

    public boolean isTeleportUsed() {
        return teleportUsed;
    }

    public void setTeleportUsed(boolean teleportUsed) {
        this.teleportUsed = teleportUsed;
    }

    public void setDestination(Teleport destinationTeleport) {
        if(destinationTeleport != this)
        {
            teleportTo = destinationTeleport;
        }
    }

    public void teleportPlayer(Player player)
    {
        if(player == null)
        {
            return;
        }

        int posX = (getPosX()+(getWidth()/2));
        posX -= player.getWidth()/2;

        int posY =(getPosY()+(getHeight()/2));
        posY -= player.getHeight()/2;

        player.setPosition(posX,posY);
    }

    private Player getFirstPlayer()
    {
        return getScene().getFirstActorByType(Player.class);
    }

    private void checkIfOnPortal()
    {
        Player player = getFirstPlayer();

        if(getDestination() != null)
        {
            if(isIntersecting(player))
            {
                if(!isTeleportUsed())
                {
                    getDestination().setTeleportUsed(true);
                    getDestination().teleportPlayer(player);
                }
            }else{
                setTeleportUsed(false);
            }
        }
    }

    private boolean isIntersecting(Player player)
    {
        int playerSize = 16;
        Rectangle2D.Float rect = new Rectangle2D.Float(player.getPosX()+playerSize, player.getPosY()+playerSize,1,1);
        Rectangle2D.Float rectPortal = new Rectangle2D.Float(this.getPosX(), this.getPosY(),48, 48);

        if(rectPortal.intersects(rect))
        {
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void addedToScene(@NotNull Scene scene) {
        super.addedToScene(scene);
        new Loop<>(new Invoke<>(this::checkIfOnPortal)).scheduleFor(this);
    }
}
