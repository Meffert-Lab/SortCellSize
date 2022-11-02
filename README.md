Written by Sreenivas Eadara for the Meffert Lab

## SortCellSize: Separate Cells by Area from FISHquant Outlines

# Dependencies

SortCellSize is written in Java. It requires the Java Runtime Environment to run. If not already installed, it can be found at:
https://www.java.com/en/download/

# Usage

In order to use SortCellSize, you must compile then run the provided `SortCellSize.java`.

1. Download SortCellSize.java (or clone this repository) and navigate to the file location of SortCellSize within your shell.
2. SortCellSize must first be compiled. This is done by typing the following:
`javac SortCellSize.java`

3. SortCellSize can then be run after compiling. This is done by typing the following:
`java SortCellSize.java`

4. SortCellSize will prompt for the directory where outlines are located. Enter this address.
Example input (OSX): `/Users/sreenivaseadara/Documents/Outlines`
5. SortCellSize will attempt to locate the outlines and produce 3 folders containing small, medium, and large sized cells.
6. If an error message "Could not find directory." appears, an inaccessible or invalid address was entered. You can attempt to run the program again with:
`java SortCellSize.java`.
