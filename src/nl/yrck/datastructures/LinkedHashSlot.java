package nl.yrck.datastructures;

/**
 * This class saves a string, but also contains a reference to the next slot containing a string. Acts as a linked list.
 *
 * @author Yorick de Boer
 */
public class LinkedHashSlot extends HashSlot {

    private LinkedHashSlot nextSlot;

    /**
     * Constructor for LinkedHashSlot, the next slot reference is set to null.
     *
     * @param key the to be saved string
     */
    LinkedHashSlot(String key) {
        super(key);
        this.nextSlot = null;
    }

    /**
     * Getter for the next linkedslot
     *
     * @return the next linkedslot
     */
    public LinkedHashSlot getNextSlot() {
        return nextSlot;
    }

    /**
     * Setter for the next slot
     *
     * @param nextSlot a linked slot
     */
    public void setNextSlot(LinkedHashSlot nextSlot) {
        this.nextSlot = nextSlot;
    }
}
