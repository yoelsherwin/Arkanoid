package highscorestable;

import java.util.Comparator;

/**
 * The type Score info comparator.
 */
public class ScoreInfoComparator implements Comparator<ScoreInfo> {
    @Override
    public int compare(ScoreInfo score1, ScoreInfo score2) {
        if (score1.getScore() < score2.getScore()) {
            return 1;
        } else if (score1.getScore() > score2.getScore()) {
            return -1;
        } else {
            return 0;
        }
    }
}
