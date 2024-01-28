package coding.challenge.service.api;

import coding.challenge.dto.FileStatistics;

import java.io.InputStream;

public interface InputProcessingService {

    FileStatistics countAllStatistics(InputStream inputStream);

    long countFileBytes(InputStream inputStream);

    long countFileLines(InputStream inputStream);

    long countFileWords(InputStream inputStream);

    long countFileCharacters(InputStream inputStream);
}
