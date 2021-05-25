package sk.tuke.kpi.oop.game.weapons;

public class Gun extends Firearm {


    public Gun(int maxAmmo, int currentAmmo) {

        super(maxAmmo, currentAmmo);
    }

    @Override
    protected Fireable createBullet() {
        return new Bullet();
    }
}
