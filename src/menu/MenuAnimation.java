package menu;

import animation.AnimationRunner;
import biuoop.DrawSurface;
import biuoop.KeyboardSensor;
import gamelogic.Sprite;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 * The type Menu animation.
 *
 * @param <T> the type parameter
 */
public class MenuAnimation<T> implements Menu<T> {
    private boolean stop;
    private String head;
    private KeyboardSensor sensor;
    private List<String> keys;
    private List<String> messages;
    private List<T> values;
    private T currStatus;
    private Map<String, Menu<T>> subMenus;
    private AnimationRunner ar;


    /**
     * Instantiates a new Menu animation.
     *
     * @param head the head
     * @param ar   the ar
     */
    public MenuAnimation(String head, AnimationRunner ar) {
        this.head = head;
        this.stop = false;
        this.keys = new ArrayList<>();
        this.messages = new ArrayList<>();
        this.values = new ArrayList<>();
        this.sensor = ar.getGui().getKeyboardSensor();
        this.ar = ar;
        this.currStatus = null;
        this.subMenus = new TreeMap<>();
    }

    @Override
    public void doOneFrame(DrawSurface d) {
        boolean openedSubMenu = false;

        for (String key : this.keys) {
            if (this.sensor.isPressed(key)) {
                if (this.sensor.isPressed("s")) {
                    openedSubMenu = true;
                }
                if (this.subMenus.get(key) != null && openedSubMenu) {
                    this.ar.run(this.subMenus.get(key));
                    openedSubMenu = false;
                    this.currStatus = this.subMenus.get(key).getCurrStatus();
                    this.subMenus.get(key).setStop();
                    ((Task) this.currStatus).run();
                    this.currStatus = null;
                } else {
                    if (!openedSubMenu) {
                        this.currStatus = this.values.get(this.keys.indexOf(key));
                        this.stop = true;
                    }
                }
            }
        }
        if (!stop) {
            Sprite b = new MenuBackground();
            b.drawOn(d);

            d.setColor(Color.WHITE);
            d.drawText(d.getWidth() / 3, d.getHeight() / 5, this.head, 50);

            for (String key : this.keys) {
                // drawing every option
                d.drawText(d.getWidth() / 3, d.getHeight() - 300 + this.keys.indexOf(key) * 70,
                        "press - " + key + " to " + this.messages.get(this.keys.indexOf(key)), 25);
                // checking if this option is pressed

            }
        }
    }

    @Override
    public boolean shouldStop() {
        return this.stop;
    }

    @Override
    public void addSelection(String key, String message, T returnVal) {
        this.keys.add(key);
        this.messages.add(message);
        this.values.add(returnVal);
    }

    @Override
    public T getCurrStatus() {
        return currStatus;
    }

    @Override
    public void addSubMenu(String key, String message, Menu<T> subMenu) {
        this.keys.add(key);
        this.messages.add(message);
        this.subMenus.put(key, subMenu);
        this.values.add(null);
    }

    /**
     * Sets stop.
     */
    public void setStop() {
        this.stop = false;
        this.currStatus = null;
    }


    /**
     * Copy menu animation.
     *
     * @return the menu animation
     */
    public MenuAnimation<T> copy() {

        MenuAnimation<T> copy = new MenuAnimation<>(this.head, this.ar);

        String tempKey = null;
        String tempMessage = null;
        T tempTask = null;

        for (String key : this.keys) {
            tempKey = key;
            tempMessage = this.messages.get(this.keys.indexOf(key));
            tempTask = this.values.get(this.keys.indexOf(key));
            copy.addSelection(tempKey, tempMessage, tempTask);
        }

        return copy;
    }

}
