package menu;

import biuoop.GUI;

/**
 * The type Quit task.
 */
public class QuitTask implements Task<Void> {
    private GUI gui;

    /**
     * Instantiates a new Quit task.
     *
     * @param gui the gui
     */
    public QuitTask(GUI gui) {
        this.gui = gui;
    }

    /**
     * Run t.
     *
     * @return the t
     */
    public Void run() {
        this.gui.close();
        System.exit(0);
        return null;
    }
}
