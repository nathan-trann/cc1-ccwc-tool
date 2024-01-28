package coding.challenge.service;

import coding.challenge.service.api.*;
import coding.challenge.type.OptionType;
import org.apache.commons.cli.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class CommandLineServiceImpl implements CommandLineService {
    private final CommonOptionService commonOptionService;
    private final FileStatisticsProcessingService fileStatisticsProcessingService;

    public CommandLineServiceImpl(CommonOptionService commonOptionService, FileStatisticsProcessingService fileStatisticsProcessingService) {
        this.commonOptionService = commonOptionService;
        this.fileStatisticsProcessingService = fileStatisticsProcessingService;
    }


    @Override
    public Optional<String> processInput(String[] args, Options options) {
        CommandLineParser parser = new DefaultParser();

        try {
            CommandLine cmd = parser.parse(options, args);
            return handleCommandLineInput(cmd, options);
        } catch (UnrecognizedOptionException e) {
            System.err.println("Invalid option: " + args[0]);
            printHelp(options);
        } catch (ParseException e) {
            printHelp(options);
        } catch(Exception e) {
            System.err.println(e.getMessage());
        }

        return Optional.empty();
    }

    private Optional<String> handleCommandLineInput(CommandLine cmd, Options options) {
        if (cmd.hasOption(OptionType.HELP.getOpt()) || cmd.hasOption(OptionType.HELP.getLongOpt())) {
            printHelp(options);
            return Optional.empty();
        }

        if (cmd.getArgList().size() > 1) {
            System.err.println("Multiple files processing not supported yet.");
            return Optional.empty();
        }

        if (cmd.getArgList().isEmpty()) {
            return fileStatisticsProcessingService.processFileStatistics(cmd);
        }

        return handleArgInput(cmd, cmd.getArgList().get(0));
    }

    private Optional<String> handleArgInput(CommandLine cmd, String input) {
        Path path = Paths.get(input);
        if (Files.notExists(path)) {
            System.err.println("File " + path.getFileName() + " does not exist.");
            return Optional.empty();
        }

        if (Files.isDirectory(path)) {
            System.err.println("The " + path + " is a directory.");
            return Optional.empty();
        }


        return fileStatisticsProcessingService.processFileStatistics(cmd, path);
    }

    private void printHelp(Options options) {
        HelpFormatter formatter = new HelpFormatter();
        Options supportedOptions = commonOptionService.createOptions();

        if (options.getOptions().isEmpty() || !isOptionSupported(supportedOptions, options)) {
            formatter.printHelp("ccwc [OPTION] [FILE]", supportedOptions);
            return;
        }

        formatter.printHelp("ccwc [OPTION] [FILE]", options);
    }

    private boolean isOptionSupported(Options supportedOptions, Options inputOptions) {
        Collection<Option> supportedList = supportedOptions.getOptions();
        Collection<Option> inputList = inputOptions.getOptions();

        if (supportedList.size() != inputList.size()) {
            return false;
        }

        for (Option option : inputList) {
            if (!supportedList.contains(option)) {
                return false;
            }
        }

        return true;
    }
}
