package levelfromfiles;

import collision.Point;
import collision.Velocity;
import gamelogic.Ball;
import gamelogic.Block;
import gamelogic.Sprite;
import levels.LevelInformation;

import javax.imageio.ImageIO;
import java.awt.Color;
import java.awt.Image;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Level from strings.
 */
public class LevelFromStrings implements LevelInformation {
    private List<Ball> balls;
    private List<Block> blocks;
    private String levelName;
    private int paddleSpeed;
    private int paddleWidth;
    private Sprite background;
    private int numBlocks;


    /**
     * Instantiates a new Level from strings.
     *
     * @param background       the background
     * @param ballVelocities   the ball velocities
     * @param blocksDefinition the blocks definition
     * @param blockStartX      the block start x
     * @param blockStartY      the block start y
     * @param rowHeight        the row height
     * @param blocks           the blocks
     */
    public LevelFromStrings(String background, String ballVelocities, String blocksDefinition, int blockStartX,
                            int blockStartY, int rowHeight, List<String> blocks) {

        // background
        String[] temp;
        String val;
        Image image = null;
        Color color = null;
        ColorsParser colorsParser = new ColorsParser();

        if (background.contains("image")) {
            try {
                val = background.substring(6, background.length() - 1);
                InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(val);
                image = ImageIO.read(is);
                is.close();
                this.background = new Background(image);
            } catch (IOException e) {
                System.out.println("error loading image");
            }
        } else if (background.contains("color")) {
            temp = background.split("color");
            color = colorsParser.colorFromString(temp[1]);
            this.background = new Background(color);
        } else {
            this.background = null;
        }



        // creating balls
        // generating velocities
        List<Velocity> velocityList = new ArrayList<>();
        String[] velocities = ballVelocities.split(" ");
        int angle;
        int speed;
        Velocity vel;
        Ball ball;

        for (int i = 0; i < velocities.length; i++) {
            String[] angleAndSpeed = velocities[i].split(",");
            angle = Integer.parseInt(angleAndSpeed[0]);
            speed = Integer.parseInt(angleAndSpeed[1]);
            vel = Velocity.fromAngleAndSpeed(angle, speed);
            velocityList.add(vel);
        }

        List<Ball> tempBalls = new ArrayList<>();
        for (Velocity v : velocityList) {
            ball = new Ball(new Point(400, 550), 5, Color.WHITE);
            ball.setVelocity(v);
            tempBalls.add(ball);
        }

        this.balls = tempBalls;

        // blocks list
        String[] chars;
        int currX = blockStartX;
        int currY = blockStartY;
        InputStream is = ClassLoader.getSystemClassLoader().getResourceAsStream(blocksDefinition);
        InputStreamReader tempReader = new InputStreamReader(is);
        BufferedReader reader = new BufferedReader(tempReader);
        BlocksFromSymbolsFactory factory = BlocksDefinitionReader.fromReader(reader);
        try {
            reader.close();
        } catch (IOException e) {
            System.out.println("error");
        }
        List<Block> blockList = new ArrayList<>();
        for (String line : blocks) {
            chars = line.split("");
            for (int i = 0; i < chars.length; i++) {
                if (factory.isBlockSymbol(chars[i])) {
                    Block block = factory.getBlock(chars[i], currX, currY);
                    blockList.add(block);
                    currX += block.getCollisionRectangle().getWidth();
                } else if (factory.isSpaceSymbol(chars[i])) {
                    currX += factory.getSpaceWidth(chars[i]);
                }
            }
            // going down one row
            currY += rowHeight;
            // setting x back to beggining point
            currX = blockStartX;
        }
        this.blocks = blockList;
    }

    /**
     * Sets level name.
     *
     * @param tempLevelName the temp level name
     */
    public void setLevelName(String tempLevelName) {
        this.levelName = tempLevelName;
    }

    /**
     * Sets paddle speed.
     *
     * @param tempPaddleSpeed the temp paddle speed
     */
    public void setPaddleSpeed(int tempPaddleSpeed) {
        this.paddleSpeed = tempPaddleSpeed;
    }

    /**
     * Sets paddle width.
     *
     * @param tempPaddleWidth the temp paddle width
     */
    public void setPaddleWidth(int tempPaddleWidth) {
        this.paddleWidth = tempPaddleWidth;
    }

    /**
     * Sets num blocks.
     *
     * @param tempNumBlocks the temp num blocks
     */
    public void setNumBlocks(int tempNumBlocks) {
        this.numBlocks = tempNumBlocks;
    }

    /**
     * Number of balls int.
     *
     * @return the int
     */
    public int numberOfBalls() {
        return this.balls.size();
    }

    /**
     * The initial velocity of each ball.
     *
     * @return the list
     */
    public List<Velocity> initialBallVelocities() {
        List<Velocity> velocities = new ArrayList<>();
        for (Ball b : this.balls) {
            velocities.add(b.getVelocity());
        }
        return velocities;
    }

    /**
     * Paddle speed int.
     *
     * @return the int
     */
    public int paddleSpeed() {
        return this.paddleSpeed;
    }

    /**
     * Paddle width int.
     *
     * @return the int
     */
    public int paddleWidth() {
        return this.paddleWidth;
    }

    /**
     * The level name will be displayed at the top of the screen.
     *
     * @return the string
     */
    public String levelName() {
        return this.levelName;
    }

    /**
     * Returns a sprite with the background of the level.
     *
     * @return the background
     */
    public Sprite getBackground() {
        return this.background;
    }

    /**
     * The Blocks that make up this level, each block contains its size, color and location.
     *
     * @return the list
     */
    public List<Block> blocks() {
        return this.blocks;
    }

    /**
     * Number of levels that should be removed before the level is considered to be "cleared".
     * This number should be <= blocks.size();
     *
     * @return the int
     */
    public int numberOfBlocksToRemove() {
        return this.numBlocks;
    }


}
