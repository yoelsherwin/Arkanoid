import animation.AnimationRunner;
import animation.KeyPressStoppableAnimation;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import gamelogic.GameFlow;
import highscorestable.HighScoreAnimation;
import highscorestable.HighScoresTable;
import levelfromfiles.CreateLevelSets;

import menu.Menu;
import menu.MenuAnimation;
import menu.QuitTask;
import menu.Task;
import menu.ShowHiScoresTask;
import menu.PlayGameTask;

import java.io.IOException;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;


/**
 * The type Ass 5 game.
 */
public class Ass7Game {
    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        String str = "level_sets.txt";

        java.io.Reader reader = null;
        InputStream is;

        if (args.length == 0) {
            is = ClassLoader.getSystemClassLoader().getResourceAsStream(str);
        } else {
            is = ClassLoader.getSystemClassLoader().getResourceAsStream(args[0]);
        }

        reader = new InputStreamReader(is);
        File highScores = new File("highscores");
        HighScoresTable table = HighScoresTable.loadFromFile(highScores);

        // creating game flow
        GUI gui = new GUI("Arkanoid", 800, 600);
        KeyboardSensor keyboardSensor = gui.getKeyboardSensor();
        AnimationRunner ar = new AnimationRunner(gui, 60);
        HighScoreAnimation highScoreAnimation = new HighScoreAnimation(table);
        KeyPressStoppableAnimation stoppingTableAnimation =
                new KeyPressStoppableAnimation(keyboardSensor, KeyboardSensor.SPACE_KEY, highScoreAnimation);

        // creating tasks for main menu
        Task<Void> highScoresTask = new ShowHiScoresTask(ar, stoppingTableAnimation);
        Task<Void> quit = new QuitTask(gui);

        GameFlow gameFlow = new GameFlow(ar, keyboardSensor, gui, table, highScoreAnimation);

        // generating sub menu
        Menu<Task<Void>> subMenu = new MenuAnimation<>("Arkanoid", ar);
        CreateLevelSets createLevelSets = new CreateLevelSets();
        createLevelSets.fromReader(reader);
        try {
            reader.close();
        } catch (IOException e) {
            System.out.println("error");
        }



        // running menu
        while (true) {
            // tasks for sub menu
            for (String key : createLevelSets.getKeyAndName().keySet()) {
                subMenu.addSelection(key, createLevelSets.getKeyAndName().get(key),
                        new PlayGameTask(gameFlow, createLevelSets.getKeyAndLevel().get(key)));
            }

            // generating main menu
            Menu<Task<Void>> menu = new MenuAnimation<>("Arkanoid", ar);
            // generating tasks for main menu
            menu.addSubMenu("s", "Play Game", subMenu);
            menu.addSelection("h", "High Scores", highScoresTask);
            menu.addSelection("q", "Quit", quit);
            ar.run(menu);
            // wait fo user selection
            Task<Void> task = menu.getCurrStatus();
            task.run();
            //createLevelSets.fromReader(reader);
        }
    }
}
