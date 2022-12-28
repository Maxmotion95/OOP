package ru.nsu.fit.lylova;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

class NotebookAppTest {
    private void assertFilesEquals(
            String expectedFileFilepath,
            String actualFileFilepath) throws IOException {
        byte[] f1 = Files.readAllBytes(new File(expectedFileFilepath).toPath());
        byte[] f2 = Files.readAllBytes(new File(actualFileFilepath).toPath());
        assertArrayEquals(f1, f2);
    }

    private void smartAssertFileEquals(
            String expectedFileFilepath,
            String actualFileFilepath) throws IOException {
        byte[] f1 = Files.readAllBytes(new File(expectedFileFilepath).toPath());
        byte[] f2 = Files.readAllBytes(new File(actualFileFilepath).toPath());
        int i = 0;
        int j = 0;
        boolean dateReading = false;
        while (i < f1.length && j < f2.length) {
            if (dateReading) {
                if (f2[j] == '\"') {
                    dateReading = false;
                    ++i;
                    continue;
                }
                ++j;
            } else {
                assertEquals(f1[i], f2[j]);
                ++i;
                ++j;
                if (i < f1.length && f1[i] == '*' && f1[i - 1] == '\"') {
                    dateReading = true;
                }
            }
        }
        assertEquals(f1.length, i);
        assertEquals(f2.length, j);
    }

    @Test
    void testShowAll() throws IOException {
        File source = new File("src/test/resources/data/data1.json");
        File dest = new File("src/test/resources/tmp/data132432.json");
        FileUtils.copyFile(source, dest);

        System.setOut(new PrintStream(
                new FileOutputStream("src/test/resources/tmp/result132432.txt")
        ));
        var cmd = new CommandLine(
                new NotebookApp("src/test/resources/tmp/data132432.json")
        );
        cmd.execute("-show");
        System.out.close();

        assertFilesEquals(
                "src/test/resources/results/testShowAll.txt",
                "src/test/resources/tmp/result132432.txt"
        );
    }

    @Test
    void testShowWithFilter1() throws IOException {
        File source = new File("src/test/resources/data/data2.json");
        File dest = new File("src/test/resources/tmp/data4543534.json");
        FileUtils.copyFile(source, dest);

        System.setOut(new PrintStream(
                new FileOutputStream("src/test/resources/tmp/result4543534.txt")
        ));
        var cmd = new CommandLine(
                new NotebookApp("src/test/resources/tmp/data4543534.json")
        );
        cmd.execute("-show", "01.12.2022 00:00", "31.12.2022 00:00", "title");
        System.out.close();

        // check result of show
        assertFilesEquals(
                "src/test/resources/results/testShowWithFilter1.txt",
                "src/test/resources/tmp/result4543534.txt"
        );

        // check that data don't changed
        assertFilesEquals(
                "src/test/resources/data/data2.json",
                "src/test/resources/tmp/data4543534.json"
        );
    }

    @Test
    void testShowWithFilter2() throws IOException {
        File source = new File("src/test/resources/data/data2.json");
        File dest = new File("src/test/resources/tmp/data645654.json");
        FileUtils.copyFile(source, dest);

        System.setOut(new PrintStream(
                new FileOutputStream("src/test/resources/tmp/result645654.txt")
        ));
        var cmd = new CommandLine(
                new NotebookApp("src/test/resources/tmp/data645654.json")
        );
        cmd.execute("-show", "02.12.2022 00:00", "31.12.2022 00:00", "title");
        System.out.close();

        // check result of show
        assertFilesEquals(
                "src/test/resources/results/testShowWithFilter2.txt",
                "src/test/resources/tmp/result645654.txt"
        );

        // check that data don't changed
        assertFilesEquals(
                "src/test/resources/data/data2.json",
                "src/test/resources/tmp/data645654.json"
        );
    }

    @Test
    void testAdd1() throws IOException {
        File source = new File("src/test/resources/data/data3.json");
        File dest = new File("src/test/resources/tmp/data1544853.json");
        FileUtils.copyFile(source, dest);

        System.setOut(new PrintStream(
                new FileOutputStream("src/test/resources/tmp/result1544853.txt")
        ));
        var cmd = new CommandLine(
                new NotebookApp("src/test/resources/tmp/data1544853.json")
        );
        cmd.execute("-add", "Good News", "Manic Drive");
        System.out.close();

        // check result of add
        assertFilesEquals(
                "src/test/resources/results/empty.txt",
                "src/test/resources/tmp/result1544853.txt"
        );

        // check changed data
        smartAssertFileEquals(
                "src/test/resources/resultsData/testAdd1.json",
                "src/test/resources/tmp/data1544853.json"
        );

        System.setOut(new PrintStream(
                new FileOutputStream("src/test/resources/tmp/result1544853.txt")
        ));
        cmd = new CommandLine(
                new NotebookApp("src/test/resources/tmp/data1544853.json")
        );
        cmd.execute("-add", "Supernatural", "World's First Cinema");
        System.out.close();

        // check result of add
        assertFilesEquals(
                "src/test/resources/results/empty.txt",
                "src/test/resources/tmp/result1544853.txt"
        );

        // check changed data
        smartAssertFileEquals(
                "src/test/resources/resultsData/testAdd2.json",
                "src/test/resources/tmp/data1544853.json"
        );
    }

    @Test
    void testRemove1() throws IOException {
        File source = new File("src/test/resources/data/data4.json");
        File dest = new File("src/test/resources/tmp/data41638332.json");
        FileUtils.copyFile(source, dest);

        System.setOut(new PrintStream(
                new FileOutputStream("src/test/resources/tmp/result41638332.txt")
        ));
        var cmd = new CommandLine(
                new NotebookApp("src/test/resources/tmp/data41638332.json")
        );
        cmd.execute("-rm", "title1");
        System.out.close();

        // check result of add
        assertFilesEquals(
                "src/test/resources/results/empty.txt",
                "src/test/resources/tmp/result41638332.txt"
        );

        // check changed data
        assertFilesEquals(
                "src/test/resources/resultsData/testRemove1.json",
                "src/test/resources/tmp/data41638332.json"
        );
    }
}