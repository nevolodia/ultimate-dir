# Ultimate-Dir

This is a simple application developed for a JetBrains internship application.

The application indexes files within a folder, enables searching for files containing a specified word, and supports folder navigation.

## Structure

| Class         | Description                                                  |
|---------------|--------------------------------------------------------------|
| `DirScanner`  | Keeps track of files and their content inside a directory    |
| `DirWorker`   | Used in a static way to index files and their content        |
| `Tokenizer`   | Interface for tokenizer classes                              |
| `WordTokenizer` | Tokenizes strings into words by splitting on non-word characters |

## Supported commands

| Command      | Description                     |
|--------------|---------------------------------|
| `cd`         | Select a new path.              |
| `list`       | List files in directory.        |
| `this`       | Print current directory path.   |
| `contains x` | List files containing word x.   |
| `../`        | Navigate to a parent directory. |
| `exit`       | Exit the application.           |
