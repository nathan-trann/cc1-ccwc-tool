package coding.challenge.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import coding.challenge.dto.FileStatistics;
import coding.challenge.service.api.InputProcessingService;

public class InputProcessingServiceImpl implements InputProcessingService {

    @Override
    public FileStatistics countAllStatistics(InputStream inputStream) {
        Charset charset = StandardCharsets.UTF_8;
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, charset))) {
            long fileBytes = 0;
            long fileLines = 0;
            long fileWords = 0;
            long fileCharacters = 0;
            int c = 0;
            boolean isWord = false;

            while((c = reader.read()) != -1) {
                fileCharacters++;
                byte[] bytes = Character.toString((char) c).getBytes(charset);
                fileBytes += bytes.length;

                if (c == '\n') {
                    fileLines++;
                }

                String currChar = Character.toString((char) c);
                Pattern pattern = Pattern.compile("\\s");
                Matcher matcher = pattern.matcher(currChar);
                if (!currChar.isEmpty() && matcher.matches()) {
                    if (!isWord) {
                        isWord = true;
                        fileWords++;
                    }
                } else {
                        isWord = false;
                    }
            }

            return new FileStatistics(fileBytes, fileLines, fileWords, fileCharacters);
        } catch (IOException e) {
            System.err.println("Error in reading the file");
        } catch (Exception e) {
            System.err.println("Cannot count the file statistics");
        }
        return null;
    }

    @Override
    public long countFileBytes(InputStream inputStream) {
        long count = 0;

        try(inputStream) {
            while (inputStream.read() != -1) {
                count++;
            }
        } catch (Exception e) {
            System.err.println("Cannot count the file bytes");
        }
        
        return count;
    }

    @Override
    public long countFileLines(InputStream inputStream) {
        long count = 0;

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            while (reader.readLine() != null) {
                count++;
            }
        } catch (IOException e) {
            System.err.println("Cannot count the file lines");
        }

        return count;
    }

    @Override
    public long countFileWords(InputStream inputStream) {
        long count = 0;

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
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
            System.err.println("Cannot count the file words");
        }

        return count;
    }

    @Override
    public long countFileCharacters(InputStream inputStream) {
        long count = 0;

        try(BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            while (reader.read() !=  -1) {
                count++;
            }
        } catch (IOException e) {
            System.err.println("Cannot count the file characters");
        }

        return count;
    }
}
