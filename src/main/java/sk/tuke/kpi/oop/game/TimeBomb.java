package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.actions.ActionSequence;
import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.actions.Wait;
import sk.tuke.kpi.gamelib.actions.When;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class TimeBomb extends AbstractActor {
    private float explosionTime;
    private boolean activated;

    private Animation activatedTimeBomb;
    private Animation explosionTimeBomb;

    public TimeBomb(float time) {
        this.explosionTime = time;

        Animation timebomb = new Animation("sprites/bomb.png");
        activatedTimeBomb = new Animation("sprites/bomb_activated.png",16,16,0.35f, Animation.PlayMode.ONCE);
        explosionTimeBomb = new Animation("sprites/small_explosion.png",16,16,0.25f, Animation.PlayMode.ONCE);
        setAnimation(timebomb);
    }

    public float getExplosionTime() {
        return explosionTime;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }


    private void makeExplosion()
    {
        setAnimation(explosionTimeBomb);
        new When<>(
            () -> getAnimation().getCurrentFrameIndex() >= getAnimation().getFrameCount()-1,
            new Invoke<>(() -> getScene().removeActor(this))
        ).scheduleFor(this);
    }

    private void activateTimeBomb()
    {
        setActivated(true);
        setAnimation(activatedTimeBomb);

        new ActionSequence<>(
            new Wait<>(explosionTime),
            new Invoke<>(this::makeExplosion)
        ).scheduleFor(this);
    }

    public void activate()
    {
        activateTimeBomb();
    }
}
