package levelfromfiles;

import javax.imageio.ImageIO;
import java.awt.Image;
import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * The type Blocks definition reader.
 */
public class BlocksDefinitionReader {


    /**
     * From reader blocks from symbols factory.
     *
     * @param reader the reader
     * @return the blocks from symbols factory
     */
    public static BlocksFromSymbolsFactory fromReader(java.io.Reader reader) {
        Map<String, Integer> spacerWidths = new TreeMap<>();
        Map<String, BlockCreator> blockCreators = new TreeMap<>();

        StringsFromFile stringsFromFile = new StringsFromFile(reader);
        List<String> lines = stringsFromFile.getListOfLinesForBlocks();

        String[] defaultArgsStrings;
        String[] bDefin;
        String[] sDefin;
        String[] keyValDef;
        String[] bKeyVal;
        String[] sKeyVal = new String[2];
        String[] temp;
        Map<String, String> defaultArgs = new TreeMap<>();
        List<Map<String, String>> blockDefinitions = new ArrayList<>();
        int j = 0;
        Map<String, String> spaceDefinitions = new TreeMap<>();
        List<String> blockSymbols = new ArrayList<>();

        // generating maps from lines
        for (String s : lines) {
            if (s.startsWith("#") || s.equals("")) {    // ignore empty lines
                continue;
            } else if (s.startsWith("default")) {       // get default values
                defaultArgsStrings = s.split(" ");
                for (int i = 1; i < defaultArgsStrings.length; i++) {
                    keyValDef = defaultArgsStrings[i].split(":");
                    defaultArgs.put(keyValDef[0], keyValDef[1]);
                }
            } else if (s.startsWith("bdef")) {          // get block definitions
                blockDefinitions.add(new TreeMap<>());
                bDefin = s.split("bdef ");
                bDefin = bDefin[1].split(" ");
                for (int i = 0; i < bDefin.length; i++) {
                    bKeyVal = bDefin[i].split(":");
                    if (bKeyVal[0].equals("symbol")) {
                        blockSymbols.add(bKeyVal[1]);
                    } else {
                        blockDefinitions.get(j).put(bKeyVal[0], bKeyVal[1]);
                    }
                }
                j++;
            } else if (s.startsWith("sdef")) {          // get spacer definitions
                sDefin = s.split("sdef ");
                sDefin = sDefin[1].split(" ");
                temp = sDefin[0].split(":");
                sKeyVal[0] = temp[1];
                temp = sDefin[1].split(":");
                sKeyVal[1] = temp[1];
                spaceDefinitions.put(sKeyVal[0], sKeyVal[1]);
            }
        }

        // generating maps for factory
        // spaces
        for (String symbol : spaceDefinitions.keySet()) {
            spacerWidths.put(symbol, Integer.valueOf(spaceDefinitions.get(symbol)));
        }

        // blocks
        int i = 0;
        for (Map blockMap : blockDefinitions) {
            BlockCreator blockCreator = BlocksDefinitionReader.generateBlockCreator(defaultArgs, blockMap);
            blockCreators.put(blockSymbols.get(i), blockCreator);
            i++;
        }


        // returning factory
        return new BlocksFromSymbolsFactory(spacerWidths, blockCreators);
    }

    /**
     * Generate block creator block creator.
     *
     * @param defaultArgs      the default args
     * @param blockDefinitions the block definitions
     * @return the block creator
     */
    public static BlockCreator generateBlockCreator(Map<String, String> defaultArgs,
                                                    Map<String, String> blockDefinitions) {
        // blocks
        Map<Integer, Color> colorFillMap = new TreeMap<>();
        Map<Integer, Image> imageFillMap = new TreeMap<>();
        Image fillImage = null;
        Image fillKImage = null;
        Color fillColor = null;
        Color fillKColor = null;
        Color stroke = null;
        int height = 0;
        int width = 0;
        int hitPoints = 0;

        String val;
        String[] temp;
        ColorsParser colorsParser = new ColorsParser();
        int fillK;

        // setting every field that has definitions in block definitions
        for (String string : blockDefinitions.keySet()) {
            if (string.equals("hit_points") && hitPoints == 0) {
                hitPoints = Integer.valueOf(blockDefinitions.get(string));
            } else if (string.equals("fill")) {
                if (blockDefinitions.get(string).startsWith("image") && fillImage == null) {
                    try {
                        val = blockDefinitions.get(string);
                        val = val.substring(6, val.length() - 1);
                        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(val);
                        fillImage = ImageIO.read(is);
                        is.close();
                    } catch (IOException e) {
                        System.out.println("error loading image");
                    }
                } else if (blockDefinitions.get(string).startsWith("color") && fillColor == null) {
                    val = blockDefinitions.get(string);
                    temp = val.split("color");
                    fillColor = colorsParser.colorFromString(temp[1]);
                }
            } else if (string.equals("height") && height == 0) {
                height = Integer.valueOf(blockDefinitions.get(string));
            } else if (string.equals("width") && width == 0) {
                width = Integer.valueOf(blockDefinitions.get(string));
            } else if (string.equals("stroke") && stroke == null) {
                val = blockDefinitions.get(string);
                temp = val.split("color");
                temp = temp[1].split("\\(");
                temp = temp[1].split("\\)");
                stroke = colorsParser.colorFromString(temp[0]);
            } else if (string.startsWith("fill-")) {                // redundant while using the default args
                if (blockDefinitions.get(string).startsWith("image")) {
                    try {
                        val = blockDefinitions.get(string);
                        val = val.substring(6, val.length() - 1);
                        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(val);
                        fillKImage = ImageIO.read(is);
                        is.close();
                    } catch (IOException e) {
                        System.out.println("error loading image");
                    }
                } else if (blockDefinitions.get(string).startsWith("color")) {
                    val = blockDefinitions.get(string);
                    temp = val.split("color");
                    fillKColor = colorsParser.colorFromString(temp[1]);
                }
                temp = string.split("fill-");
                fillK = Integer.valueOf(temp[1]);
                if (fillKColor == null) {
                    imageFillMap.put(fillK, fillKImage);
                } else {
                    colorFillMap.put(fillK, fillKColor);
                }
            }
        }


        // setting every field that wasn't initialized (prevent setting all to default)
        for (String string : defaultArgs.keySet()) {
            if (string.equals("hit_points") && hitPoints == 0) {
                hitPoints = Integer.valueOf(defaultArgs.get(string));
            } else if (string.equals("fill")) {
                if (defaultArgs.get(string).startsWith("image") && fillImage == null) {
                    try {
                        val = defaultArgs.get(string);
                        val = val.substring(6, val.length() - 1);
                        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(val);
                        fillImage = ImageIO.read(is);
                        is.close();
                    } catch (IOException e) {
                        System.out.println("error loading image");
                    }
                } else if (defaultArgs.get(string).startsWith("color") && fillColor == null) {
                    val = defaultArgs.get(string);
                    temp = val.split("color");
                    fillColor = colorsParser.colorFromString(temp[1]);
                }
            } else if (string.equals("height") && height == 0) {
                height = Integer.valueOf(defaultArgs.get(string));
            } else if (string.equals("width") && width == 0) {
                width = Integer.valueOf(defaultArgs.get(string));
            } else if (string.equals("stroke") && stroke == null) {
                val = defaultArgs.get(string);
                temp = val.split("color");
                stroke = colorsParser.colorFromString(temp[1]);
            } else if (string.startsWith("fill-")) {                // redundant while using the default args
                if (defaultArgs.get(string).startsWith("image")) {
                    try {
                        val = defaultArgs.get(string);
                        val = val.substring(6, val.length() - 1);
                        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(val);
                        fillKImage = ImageIO.read(is);
                        is.close();
                    } catch (IOException e) {
                        System.out.println("error loading image");
                    }
                } else if (defaultArgs.get(string).startsWith("color")) {
                    val = defaultArgs.get(string);
                    temp = val.split("color");
                    fillColor = colorsParser.colorFromString(temp[1]);
                }
                temp = string.split("fill-");
                fillK = Integer.valueOf(temp[1]);
                if (fillColor == null) {
                    imageFillMap.put(fillK, fillKImage);
                } else {
                    colorFillMap.put(fillK, fillColor);
                }
            }
        }

        // default hit points
        if (hitPoints == 0) {
            hitPoints = 1;
        }

        // create new block creator
        NewBlockCreator newBlockCreator =
                new NewBlockCreator(colorFillMap, imageFillMap, fillImage, fillColor, stroke, hitPoints);
        newBlockCreator.setHeight(height);
        newBlockCreator.setWidth(width);
        return newBlockCreator;
    }
}
