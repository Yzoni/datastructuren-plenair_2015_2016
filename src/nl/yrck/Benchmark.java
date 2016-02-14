package nl.yrck;

import nl.yrck.datastructures.*;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Yorick de Boer
 */
public class Benchmark {

    private static final String DEFAULTHASHER = "defaulthasher";
    private static final String ALTERNATIVEHASHER = "alternativehasher";
    private Path dictionaryList;
    private Path[] sampleLists;

    public Benchmark(Path dictionaryList, Path[] sampleLists) {
        this.dictionaryList = dictionaryList;
        this.sampleLists = sampleLists;
    }

    public static void main(String[] args) {
        Path dictionaryList = Paths.get("/home/yorick/IdeaProjects/plenair-datastructuren/raw/wordlist.txt");
        Path[] samplefilepath = Util.listSampleFilePaths("/home/yorick/IdeaProjects/plenair-datastructuren/raw/Samples/");

        Benchmark benchmark = new Benchmark(dictionaryList, samplefilepath);
        Path benchmarkResults = Paths.get("benchmark_results.csv");
        benchmark.saveBenchmarks(benchmarkResults);
    }

    private void displayResults(String dataType, int[] results, String hasher) {
        System.out.println(dataType);
        if (hasher != null) System.out.println(hasher);
        System.out.println(results[0] + " correct; " + results[1] + " incorrect");
        System.out.println(results[2] + " ns");
        System.out.println();
    }

    private void benchmarkArray(BufferedWriter bufferedWriter) throws IOException {
        int tableSize = Util.countWordList(dictionaryList);
        DatastructureBuilder arrayBuilder =
                new DatastructureBuilder(dictionaryList, new ArrayDatastructure(tableSize));
        makePrintable(bufferedWriter, arrayBuilder, "na", tableSize);
    }

    private void benchmarkOpenAddressingHashtable(BufferedWriter bufferedWriter)
            throws IOException {
        // Benchmark different loadfactors
        for (int tableSize = Util.countWordList(dictionaryList); tableSize < (Util.countWordList(dictionaryList) * 2.5);
             tableSize += 50000) {
            DatastructureBuilder openAddressingHashtableBuilderDefaultHasher =
                    new DatastructureBuilder(dictionaryList, new OpenAddressingDatastructure(
                            new DefaultHasher()));
            makePrintable(bufferedWriter, openAddressingHashtableBuilderDefaultHasher, DEFAULTHASHER, tableSize);
        }

        // Benchmark alternative hasher
        DatastructureBuilder openAddressingHashtableBuilderAlternativeHasher =
                new DatastructureBuilder(dictionaryList, new OpenAddressingDatastructure(new AlternativeHasher()));
        makePrintable(bufferedWriter, openAddressingHashtableBuilderAlternativeHasher, ALTERNATIVEHASHER, Util.countWordList(dictionaryList));
    }

    private void benchmarkCollisionChaingHashtable(BufferedWriter bufferedWriter) throws IOException {
        // Benchmark different table sizes
        for (int tableSize = 1; tableSize < (Util.countWordList(dictionaryList) * 2.5); tableSize += 50000) {
            DatastructureBuilder collisionChainingHashtableBuilder =
                    new DatastructureBuilder(dictionaryList, new CollisionChainingDatastructure(new DefaultHasher(), tableSize));
            makePrintable(bufferedWriter, collisionChainingHashtableBuilder, DEFAULTHASHER, tableSize);
        }
        // Benchmark alternative hasher
        DatastructureBuilder collisionChainingHashtableBuilder =
                new DatastructureBuilder(dictionaryList, new CollisionChainingDatastructure(new AlternativeHasher()));
        makePrintable(bufferedWriter, collisionChainingHashtableBuilder, ALTERNATIVEHASHER, 255);
    }

    private void benchmarkTrie(BufferedWriter bufferedWriter) throws IOException {
        DatastructureBuilder trieBuilder =
                new DatastructureBuilder(dictionaryList, new TrieDatastructure());
        makePrintable(bufferedWriter, trieBuilder, "na", -1);
    }

    private void makePrintable(BufferedWriter writer, DatastructureBuilder datastructureBuilder, String hashtype, int tableSize)
            throws IOException {
        double[] timeResults = new double[sampleLists.length + 1];
        int currentFile;
        for (int i = 0; i < sampleLists.length; i++) {
            int[] arrayResults = datastructureBuilder.timer(sampleLists[i]);
            double normalizedResult = (double) (Util.countWordList(sampleLists[i]) + 1) / (double) arrayResults[2];
            timeResults[i] = normalizedResult;
            currentFile = i + 1;
            System.out.println("Status: " + datastructureBuilder.printName() + " file (" + currentFile +
                    "/" + sampleLists.length + "); time: " + normalizedResult);
        }
        double averageArray = Util.calculateAverage(timeResults);
        createCSVLine(writer, datastructureBuilder.printName(), hashtype, tableSize, averageArray);
    }

    private void createCSVLine(BufferedWriter writer, String datastructure, String hasher, int tableSize, double timeResult) throws IOException {
        System.out.println(datastructure + "," + hasher + "," + String.valueOf(tableSize) + "," + String.valueOf(timeResult));
        writer.write(datastructure + "," + hasher + "," + String.valueOf(tableSize) + "," + String.valueOf(timeResult));
        writer.newLine();
        writer.flush();
    }

    public void saveBenchmarks(Path exportPath) {
        try (BufferedWriter bufferedWriter = Files.newBufferedWriter(exportPath)) {
            benchmarkArray(bufferedWriter);
            benchmarkOpenAddressingHashtable(bufferedWriter);
            benchmarkCollisionChaingHashtable(bufferedWriter);
            benchmarkTrie(bufferedWriter);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void benchmark(Path dictionaryList, Path[] sampleLists, int initialSize, Hasher hasher, float loadFactor) {
        DatastructureBuilder trieBuilder =
                new DatastructureBuilder(dictionaryList, new TrieDatastructure());
        DatastructureBuilder arrayBuilder =
                new DatastructureBuilder(dictionaryList, new ArrayDatastructure(initialSize));
        DatastructureBuilder openAddressingHashtableBuilder =
                new DatastructureBuilder(dictionaryList, new OpenAddressingDatastructure(hasher, loadFactor));
        DatastructureBuilder collisionChainingHashtableBuilder =
                new DatastructureBuilder(dictionaryList, new CollisionChainingDatastructure(hasher, initialSize));

        for (Path sample : sampleLists) {
            int[] trieResults = trieBuilder.timer(sample);
            displayResults(trieBuilder.printName(), trieResults, null);

            int[] arrayResults = arrayBuilder.timer(sample);
            displayResults(arrayBuilder.printName(), arrayResults, null);

            int[] openaddressingResults = openAddressingHashtableBuilder.timer(sample);
            displayResults(openAddressingHashtableBuilder.printName(), openaddressingResults, hasher.printHasher());

            int[] collisionchainingResults = collisionChainingHashtableBuilder.timer(sample);
            displayResults(collisionChainingHashtableBuilder.printName(), collisionchainingResults, hasher.printHasher());
        }
    }
}
