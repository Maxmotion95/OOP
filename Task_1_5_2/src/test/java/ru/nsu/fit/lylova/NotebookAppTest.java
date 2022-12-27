package ru.nsu.fit.lylova;

import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class NotebookAppTest {
    @Test
    void test() throws FileNotFoundException {
        System.setOut(new PrintStream(new FileOutputStream("src/test/resources/tmp/file.txt")));
        var cmd = new CommandLine(new NotebookApp("src/test/resources/test1.json"));//
        cmd.execute("-show");
    }
}