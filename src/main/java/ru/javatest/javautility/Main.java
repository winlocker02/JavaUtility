package ru.javatest.javautility;

import joptsimple.OptionException;
import joptsimple.OptionParser;
import joptsimple.OptionSet;
import ru.javatest.javautility.data.SortDataWalker;
import ru.javatest.javautility.data.type.DebugType;
import ru.javatest.javautility.util.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

public class Main {

    public static void main(String[] args) throws IOException {
        OptionParser optionParser = new OptionParser();

        optionParser.accepts("help", "Show commands of list").forHelp();
        optionParser.accepts("debug", "Output information about the result").withOptionalArg().describedAs("(minimal/full)").defaultsTo("minimal");

        optionParser.accepts("input", "Select file(s) to sort").withRequiredArg().describedAs("File or directory").ofType(File.class).required();
        optionParser.accepts("output", "Choose where to save the results").withRequiredArg().describedAs("Directory").ofType(File.class).required();

        optionParser.accepts("prefix", "The prefix that the files will have after the result").withOptionalArg().describedAs("prefix").defaultsTo("result_");
        optionParser.accepts("ref", "Replace existing result file");

        OptionSet option;

        try {
            option = optionParser.parse(args);
        } catch (OptionException e) {
            System.out.println("ERROR: " + e.getMessage() + " (try --help)");
            return;
        }

        if (option.has("help")) {
            optionParser.printHelpOn(System.out);
            return;
        }

        String debugTypeStr = (String) option.valueOf("debug");
        DebugType debugType;

        try {
            debugType = DebugType.valueOf(debugTypeStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("ERROR: Debug type " + debugTypeStr + " not found, please check your spelling. the default will be MINIMAL debug");
            debugType = DebugType.MINIMAL;
        }

        File input = (File) option.valueOf("input");
        Optional<Collection<File>> inputFiles = FileUtils.getFiles(input);

        if(inputFiles.isEmpty()) {
            System.out.println("Error input: " + input);
            System.out.println("The input file must have a file or directory with files, with the extension: " + FileUtils.getAllowedExtensionsToString());
            return;
        }

        File output = (File) option.valueOf("output");
        String prefix = (String) option.valueOf("prefix");

        boolean replaceExists = option.has("ref");

        SortDataWalker sortDataWalker = new SortDataWalker(debugType);

        inputFiles.get().forEach(sortDataWalker::readFile);
        sortDataWalker.distributeToFiles(output, prefix, replaceExists);
    }
}