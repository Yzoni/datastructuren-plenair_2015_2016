package nl.yrck.datastructures;

/**
 * Implementation of a Collision Chainging hashtable.
 * <p>
 * Collision chaining works by using linked lists when a collision of hashes occurs.
 * </p>
 *
 * @author Yorick de Boer
 */
public class CollisionChainingDatastructure implements Datastructure {

    private LinkedHashSlot[] hashTable;
    private int tableSize;
    private Hasher hasher;

    /**
     * Default constructor for Collision Chaining datastructure
     */
    public CollisionChainingDatastructure() {
        this(new DefaultHasher());
    }

    /**
     * Constructor for Collision Chaining datastructure
     *
     * @param hasher the hasher to use
     */
    public CollisionChainingDatastructure(Hasher hasher) {
        this(hasher, 255);
    }

    /**
     * Construcotr for Collision Chaining datastructure
     * @param tableSize the initial table size
     * @param hasher the hasher to use
     */
    public CollisionChainingDatastructure(Hasher hasher, int tableSize) {
        this.tableSize = tableSize;
        this.hasher = hasher;
        this.hashTable = new LinkedHashSlot[this.tableSize];
        for (int i = 0; i < this.tableSize; i++) {
            hashTable[i] = null;
        }
    }

    /**
     * Checks if a string is saved in the datastructure
     * @param key the string to check against the datastructure
     * @return true if the string is found and false when the string does not appear in the datastructure
     */
    @Override
    public boolean contains(String key) {
        int hash = Math.abs(hasher.doHash(key)) % tableSize;
        LinkedHashSlot slot = hashTable[hash];
        while (!(slot == null || slot.getKey().equals(key))) {
            slot = slot.getNextSlot();
        }
        if (slot == null) {
            return false;
        }
        return true;
    }

    /**
     * Saves a string in the datastructure
     * @param key the string to save
     */
    @Override
    public void put(String key) {
        int hash = Math.abs(hasher.doHash(key)) % tableSize;
        LinkedHashSlot slot = hashTable[hash];
        if (slot != null) {
            while (!(slot.getNextSlot() == null || slot.getKey().equals(key))) {
                slot = slot.getNextSlot();
            }
            slot.setNextSlot(new LinkedHashSlot(key));
        } else {
            hashTable[hash] = new LinkedHashSlot(key);
        }
    }

    /**
     * Gets the total size of the datastructure, also includes not used entries
     * @return the size of the datastructure
     */
    @Override
    public int size() {
        return tableSize;
    }
}
