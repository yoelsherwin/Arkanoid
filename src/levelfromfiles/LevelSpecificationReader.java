package levelfromfiles;

import levels.LevelInformation;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Level specification reader.
 */
public class LevelSpecificationReader {


    /**
     * Instantiates a new Level specification reader.
     */
    public LevelSpecificationReader() {
    }

    /**
     * From reader list.
     *
     * @param reader the reader
     * @return the list
     */
    public List<LevelInformation> fromReader(java.io.Reader reader) {
        StringsFromFile stringsFromFile = new StringsFromFile(reader);
        ArrayList<ArrayList<String>> multipleLevelsStrings = stringsFromFile.getListOfLevels();

        List<LevelInformation> levels = new ArrayList<>();
        LevelInformation level;

        for (ArrayList<String> strings : multipleLevelsStrings) {
            level = this.levelFromStrings(strings);
            // if a parameter was missing exit program
            if (level == null) {
                System.exit(-1);
            }
            levels.add(level);
        }

        return levels;
    }

    /**
     * Level from strings level information.
     *
     * @param strings the strings
     * @return the level information
     */
    public LevelInformation levelFromStrings(ArrayList<String> strings) {
        //fields of level
        String background = null;
        String ballVelocities = null;
        String levelName = null;
        int paddleSpeed = 0;
        int paddleWidth = 0;
        String blocksDefinition = null;
        int blockStartX = 0;
        int blockStartY = 0;
        int rowHeight = 0;
        int numBlocks = 0;
        boolean beganPrasingBlocks = false;
        List<String> blocks = new ArrayList<>();

        // boolean array to check if we got all the parameters
        boolean[] parametersRecieved = new boolean[11];

        for (String s : strings) {
            if (s.equals("START_LEVEL") || s.equals("END_LEVEL") || s.equals("")) {
                continue;
            }
            if (beganPrasingBlocks) {
                blocks.add(s);
            }
            String[] line = s.split(":");
            switch (line[0]) {
                case "background":
                    background = line[1];
                    parametersRecieved[0] = true;
                    break;
                case "ball_velocities":
                    ballVelocities = line[1];
                    parametersRecieved[1] = true;
                    break;
                case "level_name":
                    levelName = line[1];
                    parametersRecieved[2] = true;
                    break;
                case "paddle_speed":
                    paddleSpeed = Integer.parseInt(line[1]);
                    parametersRecieved[3] = true;
                    break;
                case "paddle_width":
                    paddleWidth = Integer.parseInt(line[1]);
                    parametersRecieved[4] = true;
                    break;
                case "block_definitions":
                    blocksDefinition = line[1];
                    parametersRecieved[5] = true;
                    break;
                case "blocks_start_x":
                    blockStartX = Integer.parseInt(line[1]);
                    parametersRecieved[6] = true;
                    break;
                case "blocks_start_y":
                    blockStartY = Integer.parseInt(line[1]);
                    parametersRecieved[7] = true;
                    break;
                case "row_height":
                    rowHeight = Integer.parseInt(line[1]);
                    parametersRecieved[8] = true;
                    break;
                case "num_blocks":
                    numBlocks = Integer.parseInt(line[1]);
                    parametersRecieved[9] = true;
                    break;
                case "START_BLOCKS":
                    beganPrasingBlocks = true;
                    break;
                case "END_BLOCKS":
                    beganPrasingBlocks = false;
                    break;
                default:
                    break;
            }
        }
        // check that any blocks were read
        if (!blocks.isEmpty()) {
            parametersRecieved[10] = true;
        }

        for (int i = 0; i < parametersRecieved.length; i++) {
            if (!parametersRecieved[i]) {
                return null;
            }
        }

        // return new level
        LevelFromStrings l = new LevelFromStrings(background, ballVelocities, blocksDefinition,
                blockStartX, blockStartY, rowHeight, blocks);
        l.setLevelName(levelName);
        l.setNumBlocks(numBlocks);
        l.setPaddleSpeed(paddleSpeed);
        l.setPaddleWidth(paddleWidth);
        return l;
    }
}
