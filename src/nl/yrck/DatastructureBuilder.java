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
 * This class is a wrapper around the datastructures. Its function is to read in a list of words and add a timer
 * to the datastructure.
 *
 * @author Yorick de Boer
 */
public class DatastructureBuilder {

    private Datastructure datastructure;
    private Path path;

    /**
     * Constructor for DatastructeBuilder
     *
     * @param path      path to a list of words, words seperated by a new line
     * @param hashtable datastructure which implements the datastructure interface
     */
    DatastructureBuilder(Path path, Datastructure hashtable) {
        this.path = path;
        this.datastructure = hashtable;
        this.datastructure = readInWordList();
    }

    /**
     * Reads in a wordlist, line by line and returns the datastructure
     *
     * @return datastructure
     */
    private Datastructure readInWordList() {
        try {
            FileInputStream input = new FileInputStream(path.toFile());
            CharsetDecoder decoder = StandardCharsets.UTF_8.newDecoder();
            decoder.onMalformedInput(CodingErrorAction.IGNORE);
            InputStreamReader reader = new InputStreamReader(input, decoder);
            BufferedReader bufferedReader = new BufferedReader(reader);
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                datastructure.put(line);
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return datastructure;
    }

    /**
     * Returns the name of a datastructure
     *
     * @return the name of the datastructure
     */
    public String printName() {
        return datastructure.getClass().getSimpleName();
    }

    /**
     * Adds a timer to the datastructure
     *
     * @param file path to a test file
     * @return a datastructure timer
     */
    int[] timer(Path file) {
        return new DatastructureTimer(datastructure).timer(file);
    }
}
