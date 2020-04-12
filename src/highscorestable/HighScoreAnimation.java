package highscorestable;

import animation.Animation;
import biuoop.DrawSurface;

/**
 * The type High score animation.
 */
public class HighScoreAnimation implements Animation {
    private HighScoresTable scores;
    private boolean stop;

    /**
     * Instantiates a new High score animation.
     *
     * @param scores the scores
     */
    public HighScoreAnimation(HighScoresTable scores) {
        this.scores = scores;
        this.stop = false;
    }

    /**
     * Do one frame.
     *
     * @param d the d
     */
    public void doOneFrame(DrawSurface d) {
        this.stop = false;
        int i = 100;
        for (ScoreInfo s: this.scores.getHighScores()) {
            d.drawText(d.getWidth() / 2 - 50, i, s.getName() + " ... " + s.getScore(), 24);
            i += 30;
        }
    }

    /**
     * Should stop boolean.
     *
     * @return the boolean
     */
    public boolean shouldStop() {
        return this.stop;
    }
}
