package sk.tuke.kpi.oop.game.characters;

import sk.tuke.kpi.gamelib.GameApplication;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.gamelib.messages.Topic;
import sk.tuke.kpi.oop.game.Direction;
import sk.tuke.kpi.oop.game.Keeper;
import sk.tuke.kpi.oop.game.Movable;
import sk.tuke.kpi.oop.game.items.Backpack;
import sk.tuke.kpi.oop.game.weapons.Firearm;
import sk.tuke.kpi.oop.game.weapons.Gun;


public class Ripley extends AbstractActor implements Movable , Keeper,Alive ,Armed{
    private Firearm firearm;
    private Health health;
    private Backpack backpack;
    public static final Topic<Ripley> RIPLEY_DIED = Topic.create("Ripley dead", Ripley.class);

    public Ripley() {
        super("Ellen");
        Animation animacia = new Animation ("sprites/player.png",32,32,0.1f, Animation.PlayMode.LOOP_PINGPONG);
        setAnimation(animacia);
        getAnimation().stop();
        backpack= new Backpack( "Ripley's backpack",3);
        health= new Health(100);

        firearm = new Gun(100,500);

        health.onExhaustion(() -> {
            getScene().getMessageBus().publish(RIPLEY_DIED,this);
            getScene().cancelActions(this); // keby nemam lambdu je tam ripley
        });
        /*  to je toto !
        health.onExhaustion(new Health.ExhaustionEffect() {
      @Override
      public void apply() {
      getScene().getMessageBus().publish(RIPLEY_DIED,this);
            getScene().cancelActions(ripley);
        // implementacia metody
    }
});
         */
    }

    @Override
    public int getSpeed() {
        return 2;
    }

    @Override
    public void startedMoving(Direction direction) {

        getAnimation().play();

        if (direction==Direction.NONE){
            getAnimation().stop();
        }else{
            getAnimation().setRotation(direction.getAngle());
        }
    }

    @Override
    public void stoppedMoving() {
        getAnimation().stop();

    }

  /*  public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
        if (energy==0){
            Animation mrtvy = new Animation("sprites/player_die.png",32,32,0.1f, Animation.PlayMode.ONCE);
            setAnimation(mrtvy);
            getScene().getMessageBus().publish(RIPLEY_DIED,this);
        }
        if (energy<0){
            energy=0;
        }
    }
*/


    @Override
    public Backpack getBackpack() {
        return backpack;
    }


    public void showRipleyState(){
        int windowHeight = this.getScene().getGame().getWindowSetup().getHeight();
        int yTextPos = windowHeight - GameApplication.STATUS_LINE_OFFSET;
        this.getScene().getGame().getOverlay().drawText("| energy : "+ this.getHealth().getValue(),130, yTextPos);
        this.getScene().getGame().getOverlay().drawText("| ammo : "+ this.getFirearm().getCurrentAmmo(),300, yTextPos);
        getScene().getGame().pushActorContainer(this.getBackpack());

    }


    @Override
    public Health getHealth() {
        return health;
    }

    @Override
    public Firearm getFirearm() {
        return firearm;
    }

    @Override
    public void setFirearm(Firearm weapon) {
       this.firearm=weapon;
    }
}
