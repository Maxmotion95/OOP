package ru.nsu.fit.lylova;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {
    @ParameterizedTest
    @ValueSource(ints = {1})
    void mainTest(int testNumber) throws IOException {
        System.setIn(new FileInputStream("src/test/test" + testNumber + ".txt"));
        System.setOut(new PrintStream(new FileOutputStream("src/test/outSource/test" + testNumber + ".txt")));
        Main.main(new String[0]);
        System.out.close();
        File res = new File("src/test/outSource/test" + testNumber + ".txt");
        File expectedRes = new File("src/test/answers/test" + testNumber + ".txt");
        Scanner sc1 = new Scanner(res);
        Scanner sc2 = new Scanner(expectedRes);
        while (sc1.hasNext() && sc2.hasNext()) {
            assertEquals(sc1.next(), sc2.next());
        }
        assertTrue(!sc1.hasNext() && !sc2.hasNext());
//        assertTrue(Arrays.equals(Files.readAllBytes(expectedReRes.toPath()), Files.readAllBytes(res.toPath())));
    }
}