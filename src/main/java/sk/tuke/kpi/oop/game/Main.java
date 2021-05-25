package sk.tuke.kpi.oop.game;

import sk.tuke.kpi.gamelib.*;
import sk.tuke.kpi.oop.game.scenarios.EscapeRoom;

public class Main {


    public static void main(String[] args) {




// nastavenie okna hry: nazov okna a jeho rozmery
        WindowSetup windowSetup = new WindowSetup("Project Ellen", 800, 600);
        Game game = new GameApplication(windowSetup);
        Scene scene = new World("world","maps/escape-room.tmx",new EscapeRoom.Factory());
        game.addScene(scene);


        EscapeRoom scene3 = new EscapeRoom();
        scene.addListener(scene3);

/*
      MissionImpossible scene2= new MissionImpossible();
      scene.addListener(scene2);
*/
        game.start();
        game.getInput().onKeyPressed(Input.Key.ESCAPE, game::stop);





    }

    }


