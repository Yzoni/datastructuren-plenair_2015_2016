package nl.yrck.datastructures;

/**
 * This class implements a trie datastructure
 *
 * @author Yorick de Boer
 */
public class TrieDatastructure implements MyDatastructure {

    TrieNode rootNode;

    /**
     * Constructor for the Trie datastructure
     */
    public TrieDatastructure() {
        this.rootNode = new TrieNode();
    }

    /**
     * Checks if a string is saved in the datastructure
     *
     * @param key the string to check against the datastructure
     * @return true if the string is found and false when the string does not appear in the datastructure
     */
    @Override
    public boolean contains(String key) {
        TrieNode node = rootNode;
        for (int i = 0; i < key.length(); i++) {
            node = node.getNextNode(key.charAt(i));
            if (node == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * Saves a string in the datastructure
     *
     * @param key the string to save
     */
    @Override
    public void put(String key) {
        TrieNode node = rootNode;
        for (int i = 0; i < key.length(); i++) {
            if (node.getNextNode(key.charAt(i)) == null) {
                node.setNewNextNode(key.charAt(i));
            }
            node = node.getNextNode(key.charAt(i));
        }
    }

    /**
     * Gets the total size of the datastructure, also includes not used entries
     *
     * @return the size of the datastructure
     */
    @Override
    public int size() {
        System.err.println("Size not implemented");
        return -1;
    }
}
