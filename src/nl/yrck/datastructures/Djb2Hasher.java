package nl.yrck.datastructures;

/**
 * Alternative hasher
 * http://www.cse.yorku.ca/~oz/hash.html
 *
 * @author Yorick de Boer
 */
public class Djb2Hasher implements Hasher {

    @Override
    public int doHash(String word) {
        int hash = 5381;
        for (int i = 0; i < word.length(); i++) {
            hash = ((hash << 5) + hash) + word.charAt(i);
        }
        return Math.abs(hash);
    }

    @Override
    public String printHasher() {
        return this.getClass().getSimpleName();
    }
}
