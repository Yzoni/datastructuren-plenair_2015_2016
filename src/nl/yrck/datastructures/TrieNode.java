package nl.yrck.datastructures;

/**
 * A node in a Trie saves a character
 *
 * @author Yorick de Boer
 */
public class TrieNode {

    private char aChar;
    private TrieNode[] nextNodes;
    private int currentNextNodeCount;

    /**
     * Default constructor for a node in a Trie datastructure
     */
    public TrieNode() {
        this.nextNodes = new TrieNode[255]; // Enough for all ascii characters
    }

    /**
     * Constructor for a node in a Trie datastructure
     *
     * @param aChar character to store
     */
    public TrieNode(char aChar) {
        this.aChar = aChar;
        this.nextNodes = new TrieNode[255]; // Enough for all ascii characters
    }

    /**
     * Getter for character saved in node
     *
     * @return gets character
     */
    public char getaChar() {
        return aChar;
    }

    /**
     * Getter for the next node that contains a specific character
     *
     * @param aChar character to search for
     * @return the nextnode if the node is found, else returns null
     */
    public TrieNode getNextNode(char aChar) {
        for (TrieNode nextNode : nextNodes) {
            if (nextNode == null) {
                return null;
            } else if (nextNode.getaChar() == aChar) {
                return nextNode;
            }
        }
        return null;
    }

    /**
     * Setter for the next node
     *
     * @param aChar the character to save in the node
     */
    public void setNewNextNode(char aChar) {
        this.nextNodes[currentNextNodeCount] = new TrieNode(aChar);
        this.currentNextNodeCount += 1;
    }
}
