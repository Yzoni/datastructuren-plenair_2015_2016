package nl.yrck.datastructures;

/**
 * Interface for datastructures that require hashing
 *
 * @author Yorick de Boer
 */
public interface Hasher {
    /**
     * Creates a hash of a string
     *
     * @param word String to be hashed
     * @return the hashed string
     */
    int doHash(String word);

    /**
     * Gets the classname of a hash class
     * @return the class name of the hasher class
     */
    String printHasher();
}
