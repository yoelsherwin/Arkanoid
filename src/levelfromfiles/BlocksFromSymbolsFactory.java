package levelfromfiles;

import gamelogic.Block;

import java.util.Map;

/**
 * The type Blocks from symbols factory.
 */
public class BlocksFromSymbolsFactory {
    private Map<String, Integer> spacerWidths;
    private Map<String, BlockCreator> blockCreators;

    /**
     * Instantiates a new Blocks from symbols factory.
     *
     * @param spacerWidths  the spacer widths
     * @param blockCreators the block creators
     */
    public BlocksFromSymbolsFactory(Map<String, Integer> spacerWidths, Map<String, BlockCreator> blockCreators) {
        this.spacerWidths = spacerWidths;
        this.blockCreators = blockCreators;
    }


    /**
     * Is space symbol boolean.
     *
     * @param s the s
     * @return the boolean
     */
    public boolean isSpaceSymbol(String s) {
        for (String key : this.spacerWidths.keySet()) {
            if (key.equals(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Is block symbol boolean.
     *
     * @param s the s
     * @return the boolean
     */
    public boolean isBlockSymbol(String s) {
        for (String key : this.blockCreators.keySet()) {
            if (key.equals(s)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Gets block.
     *
     * @param s the s
     * @param x the x
     * @param y the y
     * @return the block
     */
    public Block getBlock(String s, int x, int y) {
        return this.blockCreators.get(s).create(x, y);
    }

    /**
     * Gets space width.
     *
     * @param s the s
     * @return the space width
     */
    public int getSpaceWidth(String s) {
        return this.spacerWidths.get(s);
    }
}