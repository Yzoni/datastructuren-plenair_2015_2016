package nl.yrck.datastructures;

/**
 * Saves a string
 *
 * @author Yorick de Boer
 */
public class HashSlot {

    private String key;

    /**
     * Creates a new slot
     *
     * @param key string to save
     */
    HashSlot(String key) {
        this.key = key;
    }

    /**
     * Getter for key
     * @return the key
     */
    public String getKey() {
        return key;
    }
}
