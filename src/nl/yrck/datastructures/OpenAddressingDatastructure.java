package nl.yrck.datastructures;

/**
 * Implementation of Open Addressing hashtable
 * <p>
 * An Open Addressing works by probing the next open hash when a hash collision occurs. This class uses Linear
 * probing for this.
 * </p>
 *
 * @author Yorick de Boer
 */
public class OpenAddressingDatastructure implements MyDatastructure {

    HashSlot[] hashTable;
    private int tableSize;
    private Hasher hasher;
    private int fillCount;
    private float loadFactor;

    /**
     * Default contstructor for OpenAddressing datastructure
     */
    public OpenAddressingDatastructure() {
        this(new DefaultHasher(), 0.8f);
    }

    /**
     * Contstructor for OpenAddressing datastructure
     *
     * @param hasher the hasher to use
     */
    public OpenAddressingDatastructure(Hasher hasher) {
        this(hasher, 0.8f);
    }

    /**
     * Contstructor for OpenAddressing datastructure
     *
     * @param hasher the hasher to use
     * @param loadFactor the maximal loadfactor
     */
    public OpenAddressingDatastructure(Hasher hasher, float loadFactor) {
        this(255, hasher, loadFactor);
    }

    /**
     * Contstructor for OpenAddressing datastructure
     *
     * @param initialTableSize initial table size
     * @param hasher the hasher to use
     * @param loadFactor the maximal loadfactor
     */
    public OpenAddressingDatastructure(int initialTableSize, Hasher hasher, float loadFactor) {
        this.hasher = hasher;
        this.tableSize = initialTableSize;
        this.hashTable = new HashSlot[this.tableSize];
        for (int i = 0; i < this.tableSize; i++) {
            hashTable[i] = null;
        }
        this.loadFactor = loadFactor;
    }

    /**
     * Checks if a string is saved in the datastructure
     *
     * @param key the string to check against the datastructure
     * @return true if the string is found and false when the string does not appear in the datastructure
     */
    @Override
    public boolean contains(String key) {
        int hash = hasher.doHash(key) % tableSize;
        HashSlot slot = hashTable[hash];
        while (!(slot == null || slot.getKey().equals(key))) {
            hash = (hash + 1) % tableSize;
            slot = hashTable[hash];
        }
        if (slot == null) {
            return false;
        }
        return true;
    }

    /**
     * Saves a string in the datastructure. Checks before insertion whether the datastructure exceeds its load factor.
     * When the datastructure exceeds this loadfactor the datastructure will resize and rehash all its entries.
     *
     * @param key the string to save
     */
    @Override
    public void put(String key) {
        if (exceedsLoadFactor()) {
            resizeTable();
        }
        int hash = findHashSlot(key, hashTable);
        hashTable[hash] = new HashSlot(key);
        fillCount += 1;
    }

    /**
     * Gets the total size of the datastructure, also includes not used entries
     *
     * @return the size of the datastructure
     */
    @Override
    public int size() {
        return tableSize;
    }

    /**
     * Tries to find a free slot by using linear probing.
     *
     * @param key the to be inserted key
     * @param hashSlots the hashtable
     * @return a hash unique to the hashtable hash
     */
    private int findHashSlot(String key, HashSlot[] hashSlots) {
        int hash = hasher.doHash(key) % tableSize;
        HashSlot slot = hashSlots[hash];
        while (!(slot == null || slot.getKey().equals(key))) {
            hash = (hash + 1) % tableSize;
            slot = hashSlots[hash];
        }
        return hash;
    }

    /**
     * Checks if the loadfactor is exceeded.
     * @return true if the loadfactor is exceeded otherwise false
     *
     */
    private boolean exceedsLoadFactor() {
        int load = Math.round(fillCount / tableSize);
        return load > loadFactor;
    }

    /**
     * Resizes the hashtable by doubling the size. After resize all previous entries are rehashed for the new table.
     */
    private void resizeTable() {
        tableSize = tableSize * 2;
        HashSlot[] newHashtable = new HashSlot[tableSize];
        String key;
        int hash;
        for (HashSlot hashSlot : hashTable) {
            key = hashSlot.getKey();
            hash = findHashSlot(hashSlot.getKey(), newHashtable);
            newHashtable[hash] = new HashSlot(key);
        }
        hashTable = newHashtable;
    }
}
