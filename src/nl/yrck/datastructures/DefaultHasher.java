package nl.yrck.datastructures;

/**
 * The default Java object hasher
 *
 * @author Yorick de Boer
 */
public class DefaultHasher implements Hasher {

    /**
     * Utilizes the hash function from Object
     *
     * @param word String to be hashed
     * @return default java object hash
     */
    @Override
    public int doHash(String word) {
        return word.hashCode();
    }

    /**
     * Gets the classname of a hash class
     * @return the class name of the hasher class
     */
    @Override
    public String printHasher() {
        return this.getClass().getSimpleName();
    }
}
