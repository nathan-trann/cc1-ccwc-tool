## Introduction
`ccwc` is a command-line tool inspired by the classic Unix utility `wc`. It provides a simple yet powerful way to analyze text files, offering functionalities such as counting the number of lines, words, characters, and bytes in a given file. Written in Java, `ccwc` is designed to be portable across various platforms including Linux, macOS, and Windows.

The idea to build this tool comes from https://codingchallenges.fyi/challenges/challenge-wc.

## Features
Currently, the features in this tool only supports **`.txt`** files, and you can only process one file at a time:

- **Count Lines**: Use **`-l`** option to calculate the number of lines in a text file.
- **Count Words**: Use **`-w`** option to calculate the number of words in a text file.
- **Count Characters**: Use **`-m`** option to calculate the number of characters in a text file.
- **Count Bytes**: Use **`-c`** option to calculate the number of bytes in a text file.
- **Count All**: Leave out the option to calculate all of the things mentioned above in a text file. The result will be printed in the order: **Bytes Lines Words Characters**.
## Setup
1. **Executable JAR**
    - Clone the code and run `mvn clean compile assembly:single`.
    - Or download the `ccwc.jar` directly from this repo.
2. **Script for Execution**
    - **On Linux and MacOS**
        - Create a script named `ccwc` with the following content:
          ```
          #!/bin/bash 
          java -jar /path/to/your/ccwc.jar "$@"
          ```
        - Make the script executable: `chmod +x ccwc`.
        - Move the script to a directory in your PATH, like `/usr/local/bin`.
        - Edit `~/.bashrc`, `~/.bash_profile`, or `~/.zshrc` and add:

          `export PATH="$PATH:/path/to/script"`
    - **On Windows**
        - Create a batch file named `ccwc.bat` with the following content:

          ```
          @echo off 
          java -jar \path\to\your\ccwc.jar %*
          ```

        - Add the batch file's directory to your system's PATH through System Properties -> Environment Variables.
## Example Usage
You can download the test.txt file from `src/resources`
```
ccwc test.txt
ccwc test.txt -l
cat test.txt | ccwc
curl https://www.gutenberg.org/cache/epub/71831/pg71831.txt | ccwc -l
```