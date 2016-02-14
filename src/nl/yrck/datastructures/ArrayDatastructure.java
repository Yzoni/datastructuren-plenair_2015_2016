package nl.yrck.datastructures;

/**
 * A wrapper around a string array.
 * <p>
 * This class implements dynamic resizing, which means that when the array is full a new entry needs to be put in
 * the array resizes to support additional entries. The array will be doubled in size upon extend.
 * </p>
 *
 * @author Yorick de Boer
 */
public class ArrayDatastructure implements Datastructure {

    private String[] array;
    private int currentUsedSize;

    /**
     * Default constructor initializes an array structure with 256 possible entries.
     */
    public ArrayDatastructure() {
        this(255);
    }

    /**
     * Constructs an array with set size
     * @param tableSize the initial size
     */
    public ArrayDatastructure(int tableSize) {
        this.array = new String[tableSize + 1];
        this.currentUsedSize = 0;
    }

    /**
     * Checks if a string is saved in the datastructure by iterating over every entry of the array and comparing the
     * strings.
     *
     * @param key the string to check against the datastructure
     * @return true if the string is found and false when the string does not appear in the datastructure
     */
    @Override
    public boolean contains(String key) {
        for (String word : array) {
            if (word.equals(key)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Saves a string in the datastructure. Checks if the array is fulle before insertion, if full then resize.
     *
     * @param key the string to save
     */
    @Override
    public void put(String key) {
        if (currentUsedSize == size()) {
            resizeArray();
        }
        array[currentUsedSize] = key;
        currentUsedSize += 1;
    }

    /**
     * Gets the total size of the datastructure, also includes not used entries
     *
     * @return the size of the datastructure
     */
    @Override
    public int size() {
        return array.length;
    }

    /**
     * Resizes an array by doubling its size
     */
    private void resizeArray() {
        String[] newArray = new String[size() * 2];
        for (int i = 0; i < array.length; i++) {
            newArray[i] = array[i];
        }
        array = newArray;
    }
}
