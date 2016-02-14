package nl.yrck;

import com.sun.istack.internal.Nullable;

import java.io.*;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Utility class
 *
 * @author Yorick de Boer
 */
public class Util {
    /**
     * Counts the amount of lines in a file, first word is count 0
     *
     * @param path the path to a list of words
     * @return an integer representing the line count
     */
    public static int countWordList(Path path) {
        int count = -1;
        try {
            FileInputStream input = new FileInputStream(path.toFile());
            CharsetDecoder decoder = StandardCharsets.UTF_8.newDecoder();
            decoder.onMalformedInput(CodingErrorAction.IGNORE);
            InputStreamReader reader = new InputStreamReader(input, decoder);
            BufferedReader bufferedReader = new BufferedReader(reader);
            while (bufferedReader.readLine() != null) {
                count++;
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Gets the paths of all files in a directory
     * @param dir a directory containing files
     * @return an array of Path of all the file files in a directory. Returns null when pathname doesn't denote a
     * directory or when an I/O error occurred.
     */
    @Nullable
    public static Path[] listSampleFilePaths(String dir) {
        File folder = new File(dir);
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles != null) {
            Path[] paths = new Path[listOfFiles.length];
            for (int i = 0; i < paths.length; i++) {
                paths[i] = Paths.get(listOfFiles[i].getPath());
            }
            return paths;
        }
        System.err.println("Pathname does not denote a directory, or an I/O error occurred");
        return null;
    }

    /**
     * Calculate the average from an array of integers
     *
     * @param numbers integers where the average needs to be computed from
     * @return the average
     */
    public static double calculateAverage(double[] numbers) {
        double sum = 0;
        for (double number : numbers) {
            sum += number;
        }
        return sum / (double) numbers.length;
    }
}
