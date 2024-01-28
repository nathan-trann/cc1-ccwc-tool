package coding.challenge.service;

import coding.challenge.dto.FileStatistics;
import coding.challenge.service.api.FileProcessingService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileProcessingServiceImpl implements FileProcessingService {


    @Override
    public FileStatistics processFile(Path path) {
        long fileBytes = countFileBytes(path);
        long fileLines = countFileLines(path);
        long fileWords = countFileWords(path);
        long fileCharacters = countFileCharacters(path);

        return new FileStatistics(fileBytes, fileLines, fileWords, fileCharacters);
    }

    @Override
    public long countFileBytes(Path path) {
        long count = 0;

        try(InputStream inputStream = Files.newInputStream(path)) {
            while (inputStream.read() != -1) {
                count++;
            }
        } catch (IOException e) {
            System.err.println("Error in reading the file: " + path.getFileName());
        } catch (Exception e) {
            System.err.println("Cannot count the file bytes");
        }

        return count;
    }

    @Override
    public long countFileLines(Path path) {
        long count = 0;

        try(InputStream inputStream = Files.newInputStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            while (reader.readLine() != null) {
                count++;
            }
        } catch (IOException e) {
            System.err.println("Error in reading the file: " + path.getFileName());
        } catch (Exception e) {
            System.err.println("Cannot count the file lines");
        }

        return count;
    }

    @Override
    public long countFileWords(Path path) {
        long count = 0;

        try(InputStream inputStream = Files.newInputStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] words = line.split("\\s+");
                for (String word : words) {
                    if (!word.isEmpty()) {
                        count++;
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Error in reading the file: " + path.getFileName());
        } catch (Exception e) {
            System.err.println("Cannot count the file words");
        }

        return count;
    }

    @Override
    public long countFileCharacters(Path path) {
        long count = 0;

        try(InputStream inputStream = Files.newInputStream(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            while (reader.read() !=  -1) {
                count++;
            }
        } catch (IOException e) {
            System.err.println("Error in reading the file: " + path.getFileName());
        } catch (Exception e) {
            System.err.println("Cannot count the file characters");
        }

        return count;
    }
}
