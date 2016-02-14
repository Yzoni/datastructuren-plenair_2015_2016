package nl.yrck.datastructures;

/**
 * Interface for all datastructures
 *
 * @author Yorick de Boer
 */
public interface Datastructure {
    /**
     * Checks if a string is saved in the datastructure
     *
     * @param key the string to check against the datastructure
     * @return true if the string is found and false when the string does not appear in the datastructure
     */
    boolean contains(String key);

    /**
     * Saves a string in the datastructure
     * @param key the string to save
     */
    void put(String key);

    /**
     * Gets the total size of the datastructure, also includes not used entries
     * @return the size of the datastructure
     */
    int size();
}
