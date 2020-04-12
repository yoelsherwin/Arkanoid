package levelfromfiles;


import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * The type Strings from file.
 */
public class StringsFromFile {
    private java.io.Reader reader;

    /**
     * Instantiates a new Strings from file.
     *
     * @param reader the reader
     */
    public StringsFromFile(java.io.Reader reader) {
        this.reader = reader;
    }

    /**
     * Gets list of lines.
     *
     * @return the list of lines
     */
    public ArrayList<ArrayList<String>> getListOfLevels() {

        ArrayList<ArrayList<String>> strings = new ArrayList<>();
        String read;
        int i = 0;
        boolean beganParsingLevel = false;

        try {
            BufferedReader lineNumberReader = new BufferedReader(this.reader);


            while (true) {
                read = lineNumberReader.readLine();
                if (read == null) {
                    break;
                } else if (read.equals("START_LEVEL")) {
                    beganParsingLevel = true;
                    strings.add(new ArrayList<>());
                } else if (read.equals("END_LEVEL")) {
                    i++;
                    beganParsingLevel = false;
                } else if (beganParsingLevel) {
                    strings.get(i).add(read);
                }
            }
            lineNumberReader.close();
        } catch (IOException e) {
            System.out.println("error");
        }

        return strings;
    }

    /**
     * Gets list of lines for blocks.
     *
     * @return the list of lines for blocks
     */
    public ArrayList<String> getListOfLinesForBlocks() {
        String line;
        ArrayList strings = new ArrayList();
        try {
            BufferedReader lineNumberReader = new BufferedReader(this.reader);
            line = lineNumberReader.readLine();

            while (line != null) {
                strings.add(line);
                line = lineNumberReader.readLine();
            }
            lineNumberReader.close();
        } catch (IOException e) {
            System.out.println("error");
        }

        return strings;
    }
}
