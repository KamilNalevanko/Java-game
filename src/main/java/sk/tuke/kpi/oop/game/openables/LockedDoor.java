package sk.tuke.kpi.oop.game.openables;

public class LockedDoor extends  Door {

    private boolean isLock;

    public LockedDoor(String name, Orientation orientation) {
        super(name, orientation);
      isLock=true;

    }

    public void lock(){
        isLock=true;
        close();
    }

    @Override
    public void open() {

        if (isLock==false){super.open();}

    }

    public void unlock(){
        isLock=false;
        open();
    }

    public boolean isLocked(){
        return isLock;
    }

}
