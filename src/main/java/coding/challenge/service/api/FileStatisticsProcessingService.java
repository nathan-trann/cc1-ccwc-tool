package coding.challenge.service.api;

import org.apache.commons.cli.CommandLine;

import java.nio.file.Path;
import java.util.Optional;

public interface FileStatisticsProcessingService {

    Optional<String> processFileStatistics(CommandLine cmd);
    Optional<String> processFileStatistics(CommandLine cmd, Path path);
}
