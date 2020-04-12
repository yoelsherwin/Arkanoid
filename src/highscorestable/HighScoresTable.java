package highscorestable;


import java.io.File;
import java.io.ObjectInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * The type High scores table.
 */
public class HighScoresTable implements Serializable {
    private List<ScoreInfo> scores;
    private int size;

    /**
     * Instantiates a new High scores table.
     *
     * @param size the size
     */
    public HighScoresTable(int size) {
        this.scores = new ArrayList<>();
        this.size = size;
    }

    /**
     * Add a high-score.
     *
     * @param score the score
     */
    public void add(ScoreInfo score) {
        // add only if the new score is bigger than the lowest one on list or there is
        // still place on the table
        if (this.size > this.scores.size()) {
            // add new one
            this.scores.add(score);
            // sort list
            this.scores.sort(new ScoreInfoComparator());
        } else if (score.getScore() > this.scores.get(this.size - 1).getScore()) {
            // remove lowest one
            this.scores.remove(this.size - 1);
            // add new one
            this.scores.add(score);
            // sort list
            this.scores.sort(new ScoreInfoComparator());
        }
    }

    /**
     * Return table size.
     *
     * @return the int
     */
    public int size() {
        return this.size;
    }

    /**
     * Return the current high scores.
     * The list is sorted such that the highest
     * scores come first.
     *
     * @return the high scores
     */
    public List<ScoreInfo> getHighScores() {
        return this.scores;
    }

    /**
     * Return the rank of the current score: where will it be on the list if added.
     *
     * @param score the score
     * @return the rank
     */
    public int getRank(int score) {
        int counter = 1;
        for (ScoreInfo s : scores) {
            if (score > s.getScore()) {
                return counter;
            }
            counter++;
        }
        return counter;
    }

    /**
     * Clears the table.
     */
    public void clear() {
        this.scores.clear();
        this.size = 0;
    }

    /**
     * Load table data from file.
     * Current table data is cleared.
     *
     * @param filename the filename
     * @throws IOException the io exception
     */
    public void load(File filename) throws IOException {
        HighScoresTable highScoresTable = loadFromFile(filename);
        this.scores = highScoresTable.getHighScores();
        this.size = highScoresTable.size();
    }

    /**
     * Save table data to the specified file.
     *
     * @param filename the filename
     * @throws IOException the io exception
     */
    public void save(File filename) throws IOException {
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(filename));
            objectOutputStream.writeObject(this);
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
            } catch (IOException e) {
                throw e;
            }
        }
    }

    /**
     * Read a table from file and return it.
     *
     * @param filename the filename
     * @return the high scores table
     */
    public static HighScoresTable loadFromFile(File filename) {
        HighScoresTable table = null;
        ObjectInputStream objectInputStream = null;
        try {
            objectInputStream = new ObjectInputStream(new FileInputStream(filename));

            // unsafe down casting, we better be sure that the stream really contains a Person!
            table = (HighScoresTable) objectInputStream.readObject();
        } catch (FileNotFoundException e) { // Can't find file to open
            System.err.println("Unable to find file: " + filename);
            table = new HighScoresTable(5);
            try {
                table.save(filename);
            } catch (IOException e2) {
                System.err.println("Unable to create new file");
            }
        } catch (ClassNotFoundException e) { // The class in the stream is unknown to the JVM
            System.err.println("Unable to find class for object in file: " + filename);
        } catch (IOException e) { // Some other problem
            System.err.println("Failed reading object");
            e.printStackTrace(System.err);
        } finally {
            try {
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
            } catch (IOException e) {
                System.err.println("Failed closing file: " + filename.getName());
            }
        }
        return table;
    }

}
