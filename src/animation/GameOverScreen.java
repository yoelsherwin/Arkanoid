package animation;

import biuoop.DrawSurface;

/**
 * The type Game over screen.
 */
public class GameOverScreen implements Animation {
    private boolean stop;
    private int score;

    /**
     * Instantiates a new Pause screen.
     *
     * @param score the score
     */
    public GameOverScreen(int score) {
        this.stop = false;
        this.score = score;
    }

    /**
     * Do one frame.
     *
     * @param d the d
     */
    public void doOneFrame(DrawSurface d) {
        d.drawText(10, d.getHeight() / 2, "Game Over. Your score is " + this.score, 32);
    }

    /**
     * Should stop boolean.
     *
     * @return the boolean
     */
    public boolean shouldStop() { return this.stop; }
}
