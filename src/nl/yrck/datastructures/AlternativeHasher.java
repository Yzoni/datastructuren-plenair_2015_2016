package nl.yrck.datastructures;

/**
 * Alternative hasher from somewhere
 * TODO needs to be replaced
 *
 * @author Yorick de Boer
 */
public class AlternativeHasher implements Hasher {

    @Override
    public int doHash(String word) {
        int hash = 7;
        for (int i = 0; i < word.length(); i++) {
            hash += hash * 31 + word.charAt(i);
        }
        return Math.abs(hash);
    }

    @Override
    public String printHasher() {
        return this.getClass().getSimpleName();
    }
}
