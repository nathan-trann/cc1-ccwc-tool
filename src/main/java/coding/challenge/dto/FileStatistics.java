package coding.challenge.dto;

public class FileStatistics {
    private final long bytes;
    private final long lines;
    private final long words;
    private final long characters;

    public FileStatistics(long bytes, long lines, long words, long characters) {
        this.bytes = bytes;
        this.lines = lines;
        this.words = words;
        this.characters = characters;
    }

    public long getBytes() {
        return bytes;
    }

    public long getLines() {
        return lines;
    }

    public long getWords() {
        return words;
    }

    public long getCharacters() {
        return characters;
    }
}
