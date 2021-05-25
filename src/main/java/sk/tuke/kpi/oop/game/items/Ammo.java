package sk.tuke.kpi.oop.game.items;

import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.graphics.Animation;
import sk.tuke.kpi.oop.game.characters.Armed;


public class Ammo extends AbstractActor implements Usable<Armed> {

    public Ammo() {
        Animation animacia = new Animation( "sprites/ammo.png",16,16);
        setAnimation(animacia);
    }

   /* @Override
    public Class<Ripley> getUsingActorClass() {
        return Ripley.class;
    }
   */

    @Override
    public Class<Armed> getUsingActorClass() {
        return Armed.class;
    }

    @Override
    public void useWith(Armed actor) {

        if (actor!=null && actor.getFirearm().getCurrentAmmo() < actor.getFirearm().getMaxAmmo()){
             actor.getFirearm().reload(50);
              getScene().removeActor(this);

        }
/*
        if(actor.getFirearm().getCurrentAmmo()<=499){
            actor.getFirearm().getCurrentAmmo()+50);
            if (actor.getFirearm().getCurrentAmmo()>500){
                actor.getFirearm().
            }
            getScene().removeActor(this);
        }*/
    }
/*
    @Override

    public void useWith(Armed player) {

        if(player!=null&&player.getFirearm().getAmmo()<player.getFirearm().getMaxValue()) {

            player.getFirearm().reload(50);

            Scene world = this.getScene();

            assert world != null;

            world.removeActor(this);

        }

    }
*/

}
