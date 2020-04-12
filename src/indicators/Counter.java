package indicators;

/**
 * The type Counter.
 */
public class Counter {
    private int value;

    /**
     * Instantiates a new Counter.
     *
     * @param value the value
     */
    public Counter(int value) {
        this.value = value;
    }

    /**
     * Add number to current count.
     *
     * @param number the number
     */
    public void increase(int number) {
        this.value += number;
    }

    /**
     * Subtract number from current count.
     *
     * @param number the number
     */
    public void decrease(int number) {
        this.value -= number;
    }

    /**
     * Get current count.
     *
     * @return the value
     */
    public int getValue() {
        return this.value;
    }
}