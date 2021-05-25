package sk.tuke.kpi.oop.game.characters;

import java.util.ArrayList;
import java.util.List;

public class Health {

    private boolean isDead=false;
    private  int max;
    private int actual;
    private List<ExhaustionEffect> list;

    @FunctionalInterface
    public interface ExhaustionEffect {
        void apply();
    }

    public Health(int max) {
        this.max=max;
        this.actual =max;
        list = new ArrayList<>();
    }

    public Health( int actual ,int max) {
        this.max = max;
        this.actual = actual;
        list = new ArrayList<>();
    }

    public void onExhaustion(ExhaustionEffect effect){
        list.add(effect);
    }


    public int getValue() {
        return actual;
    }

     public void  refill(int amount){

        this.actual =this.actual +amount;
         if (this.actual >max){
             this.actual =this.max;
         }
    }

    public void restore(){
        this.actual =this.max;
    }

    public  void drain(int amount){

        if(isDead==false) {
            this.actual = this.actual - amount;
            if (this.actual <= 0) {
                this.actual = 0;
                for (ExhaustionEffect exhaustionEffect : list) {
                    exhaustionEffect.apply();
                }
                isDead=true;

            }
        }
    }

    public void exhaust(){
        this.actual =0;
        if (isDead==false) {
            for (ExhaustionEffect exhaustionEffect : list) {
                exhaustionEffect.apply();
                isDead = true;
            }
        }
    }

    public int getMax() {
        return max;
    }
}
