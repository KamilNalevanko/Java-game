package sk.tuke.kpi.oop.game.weapons;

public abstract class Firearm  {


    private int maxAmmo;
    private int currentAmmo;

    public Firearm(int currentAmmo, int maxAmmo) {
        this.currentAmmo=currentAmmo;
        this.maxAmmo=maxAmmo;
    }

    public Firearm(int maxAmmo) {
        this.currentAmmo=maxAmmo;
        this.maxAmmo=maxAmmo;
    }

    public int getMaxAmmo() {
        return maxAmmo;
    }
    public int getCurrentAmmo() {
        return currentAmmo;
    }

    public int getAmmo() {
        return currentAmmo;
    }

    public void reload(int newAmmo){
        currentAmmo=currentAmmo+newAmmo;
        if (currentAmmo>maxAmmo){
            currentAmmo=maxAmmo;
        }
    }

    public Fireable fire (){

        if (currentAmmo>0){
            currentAmmo=currentAmmo-1;
        }else {
            return null;}
        return createBullet();
    }

    protected abstract Fireable createBullet();
    /*{
           Bullet bullet = new Bullet();
        return bullet;
    }
   */
}
