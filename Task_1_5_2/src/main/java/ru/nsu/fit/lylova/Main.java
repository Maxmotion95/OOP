package ru.nsu.fit.lylova;

import picocli.CommandLine;

/**
 * Class Main with method main that implements the logic of the notebook console application.
 */
public class Main {
    /**
     * The main method that implements the logic of the notebook console application.
     *
     * @param args arguments of command line
     */
    public static void main(String[] args) {
        new CommandLine(new NotebookApp("data.json")).execute(args);
    }
}