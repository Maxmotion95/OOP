package ru.nsu.fit.lylova.OccurencesFinder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;


class KnuthMorrisPrattAlgorithmWithZFunctionTest {

    @ParameterizedTest
    @ValueSource(ints = {1})
    void mainTest(int testNumber) throws IOException {
        File textFile = new File("src/test/resources/texts/text" + testNumber + ".txt");
        File patternFile = new File("src/test/resources/patterns/pattern" + testNumber + ".txt");

        InputStream textInputStream = new FileInputStream(textFile);

        Scanner patternScanner = new Scanner(patternFile, StandardCharsets.UTF_8);
        String pattern = patternScanner.nextLine();

        var result = KnuthMorrisPrattAlgorithmWithZFunction.find(textInputStream, pattern);

        File answerFile = new File("src/test/resources/answers/answer" + testNumber + ".txt");
        Scanner answerScanner = new Scanner(answerFile);

        int i = 0;
        while (answerScanner.hasNextInt() && i < result.size()) {
            assertEquals(answerScanner.nextInt(), result.get(i));
            ++i;
        }
        assertTrue(!answerScanner.hasNextInt() && i == result.size());
    }
}