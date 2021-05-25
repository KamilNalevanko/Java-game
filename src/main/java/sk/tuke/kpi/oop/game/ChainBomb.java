package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.Actor;
import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

public class ChainBomb extends TimeBomb {
    public ChainBomb(float time) {
        super(time);
    }

    private void checkAllOthers()
    {
        for (Actor bombActor : getScene().getActors()) {
            if(bombActor instanceof ChainBomb)
            {
                ChainBomb chainBomb = (ChainBomb) bombActor;
                checkCollisionWithZone(chainBomb);
            }
        }
    }

    private void checkCollisionWithZone(ChainBomb bomb)
    {
        if(!bomb.isActivated()) {
            int zoneX = this.getPosX() + 8 - 50;
            int zoneY = this.getPosY() + 8 - 50;
            int zoneSize = 100;
            Ellipse2D.Float ellipseZone = new Ellipse2D.Float(zoneX, zoneY, zoneSize, zoneSize);

            Rectangle2D.Float bombZone = new Rectangle2D.Float(bomb.getPosX(), bomb.getPosY(), 16, 16);

            if(ellipseZone.intersects(bombZone))
            {
                bomb.activate();
            }
        }
    }

    @Override
    public void activate() {
        super.activate();

        new ActionSequence<>(
            new Wait<>(getExplosionTime()),
            new Invoke<>(this::checkAllOthers)
        ).scheduleFor(this);
    }
}
