package coding.challenge.service;

import coding.challenge.dto.FileStatistics;
import coding.challenge.service.api.FileProcessingService;
import coding.challenge.service.api.FileStatisticsProcessingService;
import coding.challenge.service.api.InputProcessingService;
import coding.challenge.type.OptionType;
import org.apache.commons.cli.CommandLine;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class FileStatisticsProcessingServiceImpl implements FileStatisticsProcessingService {

    private final InputProcessingService inputProcessingService;
    private final FileProcessingService fileProcessingService;

    public FileStatisticsProcessingServiceImpl(InputProcessingService inputProcessingService, FileProcessingService fileProcessingService) {
        this.inputProcessingService = inputProcessingService;
        this.fileProcessingService = fileProcessingService;
    }

    @Override
    public Optional<String> processFileStatistics(CommandLine cmd) {
        List<Long> result;

        if (cmd.getOptions().length == 0) {
            FileStatistics fileStatistics = inputProcessingService.countAllStatistics(System.in);

            result = new ArrayList<>(List.of(fileStatistics.getBytes(),
                    fileStatistics.getLines(),
                    fileStatistics.getWords(),
                    fileStatistics.getCharacters()));
        } else {
            result = calculateResult(cmd, option -> switch (option) {
                case COUNT_BYTES -> List.of(inputProcessingService.countFileBytes(System.in));
                case COUNT_LINES -> List.of(inputProcessingService.countFileLines(System.in));
                case COUNT_WORDS -> List.of(inputProcessingService.countFileWords(System.in));
                case COUNT_CHARS -> List.of(inputProcessingService.countFileCharacters(System.in));
                default -> List.of();
            });
        }

        return processResult(result, null);
    }

    @Override
    public Optional<String> processFileStatistics(CommandLine cmd, Path path) {
        List<Long> result;

        if (cmd.getOptions().length == 0) {
            FileStatistics fileStatistics = fileProcessingService.processFile(path);
            result = new ArrayList<>(List.of(fileStatistics.getBytes(),
                    fileStatistics.getLines(),
                    fileStatistics.getWords(),
                    fileStatistics.getCharacters()));
        } else {
            result = calculateResult(cmd, option -> switch (option) {
                case COUNT_BYTES -> List.of(fileProcessingService.countFileBytes(path));
                case COUNT_LINES -> List.of(fileProcessingService.countFileLines(path));
                case COUNT_WORDS -> List.of(fileProcessingService.countFileWords(path));
                case COUNT_CHARS -> List.of(fileProcessingService.countFileCharacters(path));
                default -> List.of();
            });
        }

        return processResult(result, path.getFileName().toString());
    }

    private Optional<String> processResult(List<Long> result, String fileName) {
        if (result.isEmpty()) {
            return Optional.empty();
        }

        if (result.size() == 1) {
            return Optional.of(getOutput(String.valueOf(result.get(0)), fileName));
        }

        String stats = result.stream()
                .map(String::valueOf)
                .reduce((a, b) -> a + " " + b)
                .orElse("");

        return Optional.of(getOutput(stats, fileName));
    }

    private String getOutput(String input, String fileName) {
        return Optional.ofNullable(fileName)
                .map(name -> input + " " + name)
                .orElse(input);
    }


    private List<Long> calculateResult(CommandLine cmd, Function<OptionType, List<Long>> counter) {
        List<Long> resultList = new ArrayList<>();

        for (OptionType option : OptionType.values()) {
            if (cmd.hasOption(option.getOpt()) || cmd.hasOption(option.getLongOpt())) {
                resultList.addAll(counter.apply(option));
            }
        }

        return resultList;
    }
}
