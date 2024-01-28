package coding.challenge.service.api;

import coding.challenge.dto.FileStatistics;

import java.io.InputStream;
import java.nio.file.Path;

public interface FileProcessingService {

    FileStatistics processFile(Path path);

    long countFileBytes(Path path);

    long countFileLines(Path path);

    long countFileWords(Path path);

    long countFileCharacters(Path path);
}
