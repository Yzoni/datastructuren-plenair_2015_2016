package nl.yrck;

import nl.yrck.datastructures.Datastructure;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

/**
 * This class computes the time it takes to lookup all words in a file in a datastructure
 *
 * @author Yorick de Boer
 */
public class DatastructureTimer {

    Datastructure datastructure;

    /**
     * Constructor for datastrucure timer
     *
     * @param datastructure datastructure which implements the datastructure interface
     */
    public DatastructureTimer(Datastructure datastructure) {
        this.datastructure = datastructure;
    }

    /**
     * Computes the time it takes to lookup all words in a datastructure. It also counts the amount of correct
     * and incorrect words
     * @param path path to the test file
     * @return words in dictionary, words not in dictionary, delta time in milliseconds
     */
    public int[] timer(Path path) {
        int countRight = 0;
        int countWrong = 0;
        long startTime = System.currentTimeMillis();

        try {
            FileInputStream input = new FileInputStream(path.toFile());
            CharsetDecoder decoder = StandardCharsets.UTF_8.newDecoder();
            decoder.onMalformedInput(CodingErrorAction.IGNORE);
            InputStreamReader reader = new InputStreamReader(input, decoder);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (checkWord(line)) {
                    countRight++;
                } else {
                    countWrong++;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        long stopTime = System.currentTimeMillis();
        int deltaTime = (int) (stopTime - startTime);

        return new int[]{countRight, countWrong, deltaTime};
    }

    /**
     * Checks if a datastructure contains a word
     *
     * @param sampleWord the word to lookup
     * @return true if the datastructure contains the word
     */
    boolean checkWord(String sampleWord) {
        return datastructure.contains(sampleWord);
    }
}
