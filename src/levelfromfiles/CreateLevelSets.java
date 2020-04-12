package levelfromfiles;

import levels.LevelInformation;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * The type Create level sets.
 */
public class CreateLevelSets {
    private Map<String, String> keyAndName;
    private Map<String, List<LevelInformation>> keyAndLevel;

    /**
     * Instantiates a new Create level sets.
     */
    public CreateLevelSets() {
        this.keyAndName = new TreeMap<>();
        this.keyAndLevel = new TreeMap<>();
    }


    /**
     * From reader.
     *
     * @param reader the reader
     */
    public void fromReader(Reader reader) {
        BufferedReader lineNumberReader = new BufferedReader(reader);
        String odd;
        String even;
        String[] temp;
        BufferedReader pathLineNumberReader = null;
        LevelSpecificationReader levelSpecificationReader = new LevelSpecificationReader();
        List<LevelInformation> levels;

        try {
            odd = lineNumberReader.readLine();
            even = lineNumberReader.readLine();

            while (odd != null && even != null) {
                temp = odd.split(":");
                this.keyAndName.put(temp[0], temp[1]);

                InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(even);
                //if (is == null)
                pathLineNumberReader = new BufferedReader(new InputStreamReader(is));
                levels = levelSpecificationReader.fromReader(pathLineNumberReader);

                this.keyAndLevel.put(temp[0], levels);

                odd = lineNumberReader.readLine();
                even = lineNumberReader.readLine();
            }
            pathLineNumberReader.close();
            lineNumberReader.close();
        } catch (IOException e) {
            System.out.println("error");
        }
    }

    /**
     * Gets key and name.
     *
     * @return the key and name
     */
    public Map<String, String> getKeyAndName() {
        return keyAndName;
    }

    /**
     * Gets key and level.
     *
     * @return the key and level
     */
    public Map<String, List<LevelInformation>> getKeyAndLevel() {
        return keyAndLevel;
    }

    /**
     * Add level set.
     *
     * @param key                  the key
     * @param message              the message
     * @param levelInformationList the level information list
     */
    public void addLevelSet(String key, String message, List<LevelInformation> levelInformationList) {
        this.keyAndLevel.put(key, levelInformationList);
        this.keyAndName.put(key, message);
    }


}
