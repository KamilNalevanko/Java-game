package sk.tuke.kpi.oop.game;

public enum Direction {


    NORTH(1,0),
    SOUTH(-1,0),
    WEST(0,-1),
    EAST(0,1),
    NORTHEAST(1,1),
    NORTHWEST(1,-1),
    SOUTHEAST(-1,1),
    SOUTHWEST(-1,-1),
    NONE(0,0);

    final private int dy;
    final private int dx;

    Direction(int dy, int dx) {
        this.dy = dy;
        this.dx = dx;
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }


    public Direction combine(Direction other) {
        Direction[] directions = Direction.values();

        for (int i = 0; i < directions.length; i++) {
            int x = 0;
            int y = 0;

            x = dx + other.dx;
            y = dy + other.dy;
            if (x>1){
                x=1;
            }else if(x<-1){
                x=-1;
            }
            if (y>1){
                y=1;
            }else if(y<-1){
                y=-1;
            }
            if (x==directions[i].dx && y== directions[i].dy) {
                return directions[i];
            }
        }
        return NONE;
    }

    public float getAngle(){

        float angl = 0;
        angl = half1();

      return angl;
    }

    public  float half1(){
        if (dx==0 && dy==1){
            return 0;
        }else if(dx==0 && dy==-1){
            return 180;
        }else if(dy==0 && dx==-1){
            return 90;
        }else if(dy==0 && dx==1){
            return 270; }
        return half2();
    }

    public  float half2(){
         if(dy==1 && dx==1){
            return 315;
        }
        else if(dy==1 && dx==-1){
            return 45;
        }
        else if(dy==-1 && dx==1){
            return 225;
        }
        else if(dy==-1 && dx==-1){
            return 135;
        }
       return 0;
    }

   public static  Direction fromAngle(float angle){
        Direction direction = NONE;
         if (angle==0) {
             direction= NORTH;
         }else if (angle==180){
             direction=SOUTH;
         }else if(angle==90){
             direction= WEST;
         }else if (angle==270) {
             direction= EAST;
         }else {
             direction=half3(angle);
         }
         return direction;
    }

    public static Direction half3(float angle){
        Direction direction = NONE;
        if (angle==315) {
            direction= NORTHEAST;
        }else if (angle==225){
            direction=SOUTHEAST;
        }else if(angle==45){
            direction= NORTHWEST;
        }else if (angle==135) {
            direction= SOUTHWEST;
        }
        return  direction;
    }

}
