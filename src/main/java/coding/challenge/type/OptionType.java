package coding.challenge.type;

public enum OptionType {

    COUNT_BYTES("c", "Count the the number of bytes in a file", false),
    COUNT_LINES("l", "Count the number of lines in a file", false),
    COUNT_WORDS("w", "Count the number of words in a file", false),
    COUNT_CHARS("m", "Count the number of characters in a file", false),
    HELP("h", "help", "Print this help and exit", false);

    private final String opt;
    private final String longOpt;
    private final String description;
    private final boolean hasArgs;

    OptionType(String opt, String description, boolean hasArgs) {
        this.opt = opt;
        this.longOpt = null;
        this.description = description;
        this.hasArgs = hasArgs;
    }

    OptionType(String opt, String longOpt, String description, boolean hasArgs) {
        this.opt = opt;
        this.longOpt = longOpt;
        this.description = description;
        this.hasArgs = hasArgs;
    }

    public String getOpt() {
        return opt;
    }

    public String getLongOpt() {
        return longOpt;
    }

    public String getDescription() {
        return description;
    }
    
    public boolean hasArgs() {
        return hasArgs;
    }
}
