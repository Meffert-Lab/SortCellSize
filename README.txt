Written by Sreenivas Eadara for the Meffert Lab


How to use SortCellSize:


SortCellSize is written in Java. It requires the Java Runtime Environment to run. If not already installed, it can be found at:
https://www.java.com/en/download/


SortCellSize can be run in Terminal or Command Line.


Download SortCellSize.java and navigate to the file location of SortCellSize.


In MacOSX, this is in Terminal.


1. Find the directory where SortCellSize is located in Finder.
2. Launch Terminal.
3. Navigate to the location of SortCellSize within Terminal.
   1. This is performed using the cd, ls, and pwd commands.
   2. cd Documents, for example, will navigate to the Documents subfolder.
   3. ls will list the contents of the current folder.
   4. pwd will print the address of the current folder.
4. Once you have successfully navigated to the folder where SortCellSize is located, run SortCellSize.
   1. SortCellSize must first be compiled. This is done by typing the following:
javac SortCellSize.java
   2. SortCellSize can then be run after compiling. This is done by typing the following:
java SortCellSize.java
5. SortCellSize will prompt for the directory where outlines are located. Enter this address.
   1. Example input (OSX): /Users/sreenivaseadara/Documents/Outlines
   2. If the outlines are on removable storage, the directory will differ. Example is as follows:
   3. /Volumes/NAME_OF_DRIVE/Subfolder/Outlines
6. SortCellSize will attempt to locate the outlines and produce 3 folders containing small, medium, and large sized cells.
   1. If a NullPointerException error message appears in Terminal, the address was incorrect. You can attempt to run the program again by typing java SortCellSize.java into the Terminal.