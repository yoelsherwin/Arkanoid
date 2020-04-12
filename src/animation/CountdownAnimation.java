package animation;

import biuoop.DrawSurface;
import biuoop.Sleeper;
import gamelogic.SpriteCollection;

import java.awt.Color;

/**
 * The type Countdown animation.
 */
public class CountdownAnimation implements Animation {
    private double numOfSeconds;
    private int countFrom;
    private boolean stop;
    private SpriteCollection gameScreen;

    /**
     * Instantiates a new Countdown animation.
     *
     * @param numOfSeconds the num of seconds
     * @param countFrom    the count from
     * @param gameScreen   the game screen
     */
    public CountdownAnimation(double numOfSeconds, int countFrom, SpriteCollection gameScreen) {
        this.numOfSeconds = numOfSeconds;
        this.countFrom = countFrom;
        this.gameScreen = gameScreen;
        this.stop = true;
    }

    /**
     * Do one frame.
     *
     * @param d the d
     */
    public void doOneFrame(DrawSurface d) {
        Sleeper sleeper = new Sleeper();
        this.gameScreen.drawAllOn(d);
        d.setColor(Color.red);
        d.drawText(d.getWidth() / 2, d.getHeight() / 2, Integer.toString(this.countFrom), 30);
        this.countFrom--;
        if (this.numOfSeconds <= 0) {
            this.stop = false;
        }
        this.numOfSeconds--;
        sleeper.sleepFor(1000);
    }

    /**
     * Should stop boolean.
     *
     * @return the boolean
     */
    public boolean shouldStop() {
        return !this.stop;
    }
}