package gamelogic;

import animation.AnimationRunner;
import animation.GameOverScreen;
import animation.KeyPressStoppableAnimation;
import animation.WinScreen;
import biuoop.DialogManager;
import biuoop.GUI;
import biuoop.KeyboardSensor;
import highscorestable.HighScoreAnimation;
import highscorestable.HighScoresTable;
import highscorestable.ScoreInfo;
import indicators.Counter;
import levels.LevelInformation;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * The type Game flow.
 */
public class GameFlow {
    private AnimationRunner animationRunner;
    private KeyboardSensor keyboardSensor;
    private GUI gui;
    private Counter scoreCounter;
    private Counter livesCounter;
    private HighScoresTable table;
    private HighScoreAnimation highScoreAnimation;


    /**
     * Instantiates a new Game flow.
     *
     * @param ar                 the ar
     * @param ks                 the ks
     * @param gui                the gui
     * @param table              the table
     * @param highScoreAnimation the high score animation
     */
    public GameFlow(AnimationRunner ar, KeyboardSensor ks, GUI gui, HighScoresTable table,
                    HighScoreAnimation highScoreAnimation) {
        this.animationRunner = ar;
        this.keyboardSensor = ks;
        this.gui = gui;
        this.scoreCounter = new Counter(0);
        this.livesCounter = new Counter(7);
        this.table = table;
        this.highScoreAnimation = highScoreAnimation;
    }

    /**
     * Run levels.
     *
     * @param levels the levels
     */
    public void runLevels(List<LevelInformation> levels) {
        // for any level in list
        for (LevelInformation levelInfo : levels) {

            // create level
            GameLevel level = new GameLevel(levelInfo, this.keyboardSensor, this.animationRunner,
                    this.gui, this.livesCounter, this.scoreCounter);

            // initialize it
            level.initialize();

            // play one turn while livesand blocks are not over
            while (level.getRemainingBlocks().getValue() != 0 && this.livesCounter.getValue() != 0) {
                level.getRemainingBalls().increase(level.getLevelInformation().numberOfBalls());
                level.playOneTurn();
                level.removePaddle(level);
                if (level.getRemainingBalls().getValue() == 0) {
                    level.getRemainingLives().decrease(1);
                }
            }

            // if lives are over - game over, quit levels loop
            if (this.livesCounter.getValue() == 0) {
                break;
            }
        }
        // if game is over - go to game over screen. if player won - go to win screen
        if (this.livesCounter.getValue() == 0) {
            this.animationRunner.run(new KeyPressStoppableAnimation(this.keyboardSensor, KeyboardSensor.SPACE_KEY,
                    new GameOverScreen(this.scoreCounter.getValue())));
        } else {
            this.animationRunner.run(new KeyPressStoppableAnimation(this.keyboardSensor, KeyboardSensor.SPACE_KEY,
                    new WinScreen(this.scoreCounter.getValue())));
        }

        // check if score should enter high score table and enter it if it should
        if (this.table.getRank(this.scoreCounter.getValue()) <= this.table.size()) {
            DialogManager dialogManager = this.gui.getDialogManager();
            String name = dialogManager.showQuestionDialog("Name", "What is your name?", "");
            ScoreInfo scoreInfo = new ScoreInfo(name, this.scoreCounter.getValue());
            this.table.add(scoreInfo);
            // save table
            try {
                File highScores = new File("highscores");
                this.table.save(highScores);
            } catch (IOException e) {
                System.err.println("Failed writing object");
                e.printStackTrace(System.err);
            }
        }

        this.animationRunner.run(new KeyPressStoppableAnimation(this.keyboardSensor, KeyboardSensor.SPACE_KEY,
                this.highScoreAnimation));

        // resetting game
        this.livesCounter.increase(7);
        int score = this.scoreCounter.getValue();
        this.scoreCounter.decrease(score);
    }

    /**
     * Gets lives counter.
     *
     * @return the lives counter
     */
    public Counter getLivesCounter() {
        return livesCounter;
    }

    /**
     * Gets score counter.
     *
     * @return the score counter
     */
    public Counter getScoreCounter() {
        return scoreCounter;
    }
}
