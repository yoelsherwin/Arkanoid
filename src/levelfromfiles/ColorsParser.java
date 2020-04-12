package levelfromfiles;

import java.awt.Color;

/**
 * The type Colors parser.
 */
public class ColorsParser {
    /**
     * Color from string java . awt . color.
     *
     * @param s the s
     * @return the java . awt . color
     */
    public java.awt.Color colorFromString(String s) {
        String[] temp;
        String temp1;

        if (s.startsWith("(RGB")) {
            temp1 = s.substring(4, s.length() - 1);
            temp = temp1.split("\\(");
            temp = temp[1].split("\\)");
            temp = temp[0].split(",");
            return new Color(Integer.valueOf(temp[0]), Integer.valueOf(temp[1]), Integer.valueOf(temp[2]));
        } else {
            temp = s.split("\\(");
            temp = temp[1].split("\\)");
            if (temp[0].equals("blue")) {
                return Color.blue;
            } else if (temp[0].equals("green")) {
                return Color.green;
            } else if (temp[0].equals("pink")) {
                return Color.pink;
            } else if (temp[0].equals("yellow")) {
                return Color.yellow;
            } else if (temp[0].equals("black")) {
                return Color.black;
            } else if (temp[0].equals("white")) {
                return Color.white;
            } else if (temp[0].equals("red")) {
                return Color.red;
            } else if (temp[0].equals("cyan")) {
                return Color.cyan;
            } else if (temp[0].equals("gray")) {
                return Color.gray;
            } else if (temp[0].equals("orange")) {
                return Color.orange;
            } else if (temp[0].equals("magenta")) {
                return Color.magenta;
            } else if (temp[0].equals("light_grey")) {
                return Color.lightGray;
            } else if (temp[0].equals("dark_grey")) {
                return Color.darkGray;
            } else {
                return null;
            }
        }
    }
}
