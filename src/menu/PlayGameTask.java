package menu;

import gamelogic.GameFlow;
import levels.LevelInformation;
import java.util.List;

/**
 * The type Play game task.
 */
public class PlayGameTask implements Task<Void> {
    private GameFlow gameFlow;
    private List<LevelInformation> list;

    /**
     * Instantiates a new Play game task.
     *
     * @param gameFlow the game flow
     * @param list     the list
     */
    public PlayGameTask(GameFlow gameFlow, List<LevelInformation> list) {
        this.gameFlow = gameFlow;
        this.list = list;
    }

    /**
     * Run t.
     *
     * @return the t
     */
    public Void run() {
        this.gameFlow.runLevels(list);
        return null;
    }
}
