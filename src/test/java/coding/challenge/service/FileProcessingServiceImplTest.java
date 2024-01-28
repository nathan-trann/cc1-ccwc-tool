package coding.challenge.service;

import coding.challenge.service.api.FileProcessingService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class FileProcessingServiceImplTest {

    private FileProcessingService fileProcessingService;
    private ByteArrayOutputStream errContent;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    public void setUp() {
        fileProcessingService = new FileProcessingServiceImpl();
        errContent = new ByteArrayOutputStream();
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setErr(originalErr);
    }

    @Test
    public void processFile_validPath_returnFileStatistics() {
        // Arrange
        Path path = Paths.get("test/resources/test.txt");
        String[] expectedErr = new String[] {
                "Error in reading the file: test.txt",
                "Error in reading the file: test.txt",
                "Error in reading the file: test.txt",
                "Error in reading the file: test.txt"
        };

        // Act
        fileProcessingService.processFile(path);

        // Assert
        String[] actualErr = errContent.toString().split("\n");

        assertEquals(Arrays.toString(expectedErr), Arrays.toString(actualErr));
    }

    @Test
    public void countFileBytes_validPath_returnFileBytes() {
        // Arrange
        Path path = Paths.get("src/test/resources/test.txt");
        long expected = 342190;

        // Act
        long count = fileProcessingService.countFileBytes(path);

        // Assert
        assertEquals(expected, count);
    }

    @Test
    public void countFileLines_validPath_returnFileLines() {
        // Arrange
        Path path = Paths.get("src/test/resources/test.txt");
        long expected = 7145;

        // Act
        long count = fileProcessingService.countFileLines(path);

        // Assert
        assertEquals(expected, count);
    }

    @Test
    public void countFileWords_validPath_returnFileWords() {
        // Arrange
        Path path = Paths.get("src/test/resources/test.txt");
        long expected = 58164;

        // Act
        long count = fileProcessingService.countFileWords(path);

        // Assert
        assertEquals(expected, count);
    }

    @Test
    public void countFileCharacters_validPath_returnFileCharacters() {
        // Arrange
        Path path = Paths.get("src/test/resources/test.txt");
        long expected = 339292;

        // Act
        long count = fileProcessingService.countFileCharacters(path);

        // Assert
        assertEquals(expected, count);
    }
}