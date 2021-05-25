package sk.tuke.kpi.oop.game;


import sk.tuke.kpi.gamelib.actions.Invoke;
import sk.tuke.kpi.gamelib.framework.AbstractActor;
import sk.tuke.kpi.gamelib.framework.Player;
import sk.tuke.kpi.gamelib.framework.actions.Loop;
import sk.tuke.kpi.gamelib.graphics.Animation;

public class Helicopter extends AbstractActor{


    public Helicopter() {
        Animation heli = new Animation ("sprites/heli.png", 64 , 64 , 0.2f, Animation.PlayMode.LOOP);
        setAnimation(heli);
    }

    private void helina(){
        Player hrac = getScene().getFirstActorByType(Player.class);
        int posX = hrac.getPosX();
        int posY = hrac.getPosY();
        int newX;
        int newY;

         if (this.intersects(hrac)){
             hrac.setEnergy(hrac.getEnergy()-1);
         }

        if (getPosX()>posX){
            newX=-1;
        }else{
            newX=1;
        }


        if (getPosY()>posY){
            newY=-1;
        }else{
            newY=1;
        }
        setPosition(getPosX()+newX,getPosY()+newY);
    }

    public void searchAndDestroy(){
        new Loop<>(new Invoke<>(this::helina)).scheduleFor(this);
    }



}
