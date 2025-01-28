package agh.ics.oop;

import agh.ics.oop.model.app.MainApp;
import agh.ics.oop.model.utils.Vector2d;
import agh.ics.oop.records.WorldConfiguration;
import com.sun.tools.javac.Main;
import javafx.application.Application;

public class World {
    public static void main(String[] args) {

        Application.launch(MainApp.class, args);

        WorldConfiguration worldConfiguration = new WorldConfiguration(new Vector2d(5,5),2,2,3,3,100,20,4, 1,3,8,false,true);
//        Simulation simulation = new Simulation(worldConfiguration);
//        simulation.run();
//        MainApp mainApp = new MainApp();
//        Application.launch(MainApp.class, args);

        System.out.println("system zakończył zadanie");


    }
}
