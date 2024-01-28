package coding.challenge.service;

import coding.challenge.service.api.CommandLineService;
import coding.challenge.service.api.CommonOptionService;
import coding.challenge.service.api.FileProcessingService;
import coding.challenge.service.api.InputProcessingService;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CommandLineServiceImplTest {

    private InputProcessingService inputProcessingService;
    private FileProcessingService fileProcessingService;
    private CommonOptionService commonOptionService;
    private FileStatisticsProcessingServiceImpl fileStatisticsProcessingService;
    private CommandLineService commandLineService;
    private ByteArrayOutputStream outContent;
    private ByteArrayOutputStream errContent;
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;

    @BeforeEach
    public void setUpStreams() {
        inputProcessingService = new InputProcessingServiceImpl();
        fileProcessingService = new FileProcessingServiceImpl();
        commonOptionService = new CommonOptionServiceImpl();
        fileStatisticsProcessingService = new FileStatisticsProcessingServiceImpl(inputProcessingService, fileProcessingService);
        commandLineService = new CommandLineServiceImpl(commonOptionService, fileStatisticsProcessingService);

        outContent = new ByteArrayOutputStream();
        errContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void processInput_countLines_returnResult() {
        // Arrange
        String[] args = {"-l", "src/test/resources/test.txt"};
        Options options = commonOptionService.createOptions();
        String expected = "7145 test.txt";

        // Act
        Optional<String> result = commandLineService.processInput(args, options);

        // Assert
        assertTrue(result.isPresent());
        String actual = result.get();
        assertEquals(expected, actual);
    }

    @Test
    public void processInput_countCharacters_returnResult() {
        // Arrange
        String[] args = {"-m", "src/test/resources/test.txt"};
        Options options = commonOptionService.createOptions();
        String expected = "339292 test.txt";

        // Act
        Optional<String> result = commandLineService.processInput(args, options);

        // Assert
        assertTrue(result.isPresent());
        String actual = result.get();
        assertEquals(expected, actual);
    }

    @Test
    public void processInput_countWords_returnResult() {
        // Arrange
        String[] args = {"-w", "src/test/resources/test.txt"};
        Options options = commonOptionService.createOptions();
        String expected = "58164 test.txt";

        // Act
        Optional<String> result = commandLineService.processInput(args, options);

        // Assert
        assertTrue(result.isPresent());
        String actual = result.get();
        assertEquals(expected, actual);
    }

    @Test
    public void processInput_countBytes_returnResult() {
        // Arrange
        String[] args = {"-c", "src/test/resources/test.txt"};
        Options options = commonOptionService.createOptions();
        String expected = "342190 test.txt";

        // Act
        Optional<String> result = commandLineService.processInput(args, options);

        // Assert
        assertTrue(result.isPresent());
        String actual = result.get();
        assertEquals(expected, actual);
    }

    @Test
    public void processInput_pathIsDirectory_returnErrorMessage() {
        // Arrange
        String[] args = {"src/test/resources"};
        Options options = commonOptionService.createOptions();
        String expected = "The src/test/resources is a directory.\n";

        // Act
        Optional<String> result = commandLineService.processInput(args, options);
        String actual = errContent.toString();

        // Assert
        assertTrue(result.isEmpty());
        assertEquals(expected, actual);
    }

    @Test
    public void processInput_fileNotExists_returnErrorMessage() {
        // Arrange
        String[] args = {"file.txt"};
        Options options = commonOptionService.createOptions();
        String expected = "File file.txt does not exist.\n";

        // Act
        Optional<String> result = commandLineService.processInput(args, options);
        String actual = errContent.toString();

        // Assert
        assertTrue(result.isEmpty());
        assertEquals(expected, actual);
    }

    @Test
    public void processInput_cmdArgListMoreThanOne_returnErrorMessage() {
        // Arrange
        String[] args = {"-c", "file1.txt", "file2.txt"};
        Options options = commonOptionService.createOptions();
        String expected = "Multiple files processing not supported yet.\n";

        // Act
        Optional<String> result = commandLineService.processInput(args, options);
        String actual = errContent.toString();

        // Assert
        assertTrue(result.isEmpty());
        assertEquals(expected, actual);
    }

    @Test
    public void processInput_helpOptionProvided_returnHelpMessage() {
        // Arrange
        String[] args = {"-h"};
        Options options = commonOptionService.createOptions();
        String expected = "usage: ccwc [OPTION] [FILE]\n" +
                " -c          Count the the number of bytes in a file\n" +
                " -h,--help   Print this help and exit\n" +
                " -l          Count the number of lines in a file\n" +
                " -m          Count the number of characters in a file\n" +
                " -w          Count the number of words in a file\n";

        // Act
        Optional<String> result = commandLineService.processInput(args, options);

        // Assert
        assertTrue(result.isEmpty());
        String actual = outContent.toString();
        assertEquals(expected, actual);
    }

    @Test
    public void processInput_noOptionsProvided_returnAllElementsCalculated() {
        // Arrange
        String[] args = {"src/test/resources/test.txt"};
        Options options = new Options();
        String expected = "342190 7145 58164 339292 test.txt";

        // Act
        Optional<String> result = commandLineService.processInput(args, options);

        // Assert
        assertTrue(result.isPresent());
        String actual = result.get();
        assertEquals(expected, actual);
    }

    @Test
    public void processInput_unrecognizedOptions_returnErrorMessage() {
        // Arrange
        String[] args = {"-x", "src/test/resources/test.txt"};
        Options options = new Options();
        options.addOption(Option.builder("test")
                .build());
        String expectedErr = "Invalid option: -x\n";
        String expectedOut = "usage: ccwc [OPTION] [FILE]\n" +
                " -c          Count the the number of bytes in a file\n" +
                " -h,--help   Print this help and exit\n" +
                " -l          Count the number of lines in a file\n" +
                " -m          Count the number of characters in a file\n" +
                " -w          Count the number of words in a file\n";

        // Act
        Optional<String> result = commandLineService.processInput(args, options);
        String actualErr = errContent.toString();
        String actualOut = outContent.toString();

        // Assert
        assertTrue(result.isEmpty());
        assertEquals(expectedErr, actualErr);
        assertEquals(expectedOut, actualOut);
    }
}
