package ru.nsu.fit.lylova;

import ru.nsu.fit.lylova.OccurencesFinder.KnuthMorrisPrattAlgorithmWithZFunction;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Class Main that contains function main.
 */
public class Main {
    /**
     * The main function that counts all indexes of the occurrence
     * of some string in the text that is contained in some file.
     * The path to the file should be entered in the first line.
     * In the second line, the string to be found should be entered.
     *
     * @param args command line arguments (not used)
     * @throws IOException when something bad with files
     */
    public static void main(String[] args)
            throws IOException {
        Scanner sc = new Scanner(System.in, StandardCharsets.UTF_8);
        String filename = sc.nextLine();

        File file = new File(filename);
        InputStream input = new FileInputStream(file);

        String str;
        str = sc.nextLine();
        var ans = KnuthMorrisPrattAlgorithmWithZFunction.find(input, str);
        System.out.println(ans);
    }
}