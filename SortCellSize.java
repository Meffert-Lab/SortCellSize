import java.util.Scanner;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class SortCellSize {
   public static void main(String[] args) throws IOException {
      
      System.out.println("SortCellSize -- sort cells in FQ Outline file by size.");
      System.out.println("Written by Sreenivas Eadara for the Meffert Lab.");
      
      Scanner kb = new Scanner(System.in);
      System.out.println("Please enter the directory of an outline folder.");
      String dirLocus = kb.next();
      
      try {
         File[] defaultDir = new File(dirLocus).listFiles();
         File SMALL = new File(dirLocus + "/SMALL");
         if (SMALL.mkdir())  {
            System.out.println("Created SMALL directory");
         }
         File MEDIUM = new File(dirLocus + "/MEDIUM");
         if (MEDIUM.mkdir())  {
            System.out.println("Created MEDIUM directory");
         }
         File LARGE = new File(dirLocus + "/LARGE");
         if (LARGE.mkdir())  {
            System.out.println("Created LARGE directory");
         }
      
         List<String> filenames = new ArrayList<String>();
      
         for (File outlineFile : defaultDir) {
            if (outlineFile.isFile()) {
               filenames.add(outlineFile.getName());
               String nameOfFile = outlineFile.getName();
               FileInputStream readOutlineFile = new FileInputStream(outlineFile);
               Scanner fileIn = new Scanner(readOutlineFile);
               ArrayList<String> contents = readInputFile(fileIn);
               HashMap<Integer, Double> cellAreas = findCellAreas(contents);
               createThresholdedOutlines(contents, cellAreas, dirLocus, nameOfFile);
            }
         }
      
         System.out.println("Found " + filenames.size() + " files in directory.");
      }
      catch (NullPointerException e) {
         System.out.println("Could not find directory.");
      }
      catch (Exception e) {
         System.out.println("An unknown error occured.");
      }
         
   }
   
   public static ArrayList<String> readInputFile(Scanner fileIn) {
      
      ArrayList<String> contents = new ArrayList<String>();
      
      while (fileIn.hasNextLine()) {
         contents.add(fileIn.nextLine());
      }
      
      return contents;
   }
   
   public static HashMap<Integer, Double> findCellAreas(ArrayList<String> contents) {
      int numCells = 0;
      HashMap<Integer, Double> cellAreas = new HashMap<Integer, Double>();
      
      for (int i = 0; i < contents.size(); i++) {
         if (contents.get(i).indexOf("CELL_START") != -1) {
            Scanner getX = new Scanner(contents.get(i + 1));
            ArrayList<Integer> xCoords = new ArrayList<Integer>();
            String ignored = getX.next();
            while (getX.hasNextInt()) {
               xCoords.add(getX.nextInt());
            }
            Scanner getY = new Scanner(contents.get(i + 2));
            ArrayList<Integer> yCoords = new ArrayList<Integer>();
            String ignored2 = getY.next();
            while (getY.hasNextInt()) {
               yCoords.add(getY.nextInt());
            }
            double area = calculateArea(xCoords, yCoords);
            cellAreas.put(i, area);
            numCells++;
         }
      }
      
      System.out.println(numCells);
      System.out.println(cellAreas);   
      return cellAreas;
   }
   
   public static double calculateArea(ArrayList<Integer> xCoords, ArrayList<Integer> yCoords) {
      double sum = 0;
      
      for (int i = 0; i < xCoords.size(); i++) {
         if (i < xCoords.size() - 1) {
            sum += ((double) (xCoords.get(i) * yCoords.get(i + 1)) - (double) (xCoords.get(i + 1) * yCoords.get(i)));
         }
         else if (i == xCoords.size() - 1) {
            sum += ((double) (xCoords.get(i) * yCoords.get(0)) - (double) (xCoords.get(0) * yCoords.get(i)));
         }
      }
      
      double area = sum / 2.0;
      if (area < 0.0) {
         area = area * -1.0;
      }
      return area;
   }
   
   public static void createThresholdedOutlines(ArrayList<String> contents, HashMap<Integer, Double> cellAreas, String dirLocus, String nameOfFile) throws IOException {
      ArrayList<Integer> small = new ArrayList<Integer>();
      ArrayList<Integer> medium = new ArrayList<Integer>();
      ArrayList<Integer> large = new ArrayList<Integer>();
      
      for (HashMap.Entry<Integer, Double> cell : cellAreas.entrySet()) {
         
         if (cell.getValue() > 40113.43) {
            large.add(cell.getKey());
         }
         /*else if (cell.getValue() < 15000.0) {
            small.add(cell.getKey());
         }*/
         else {
            medium.add(cell.getKey());
         }
      }
      if (small.size() > 0) {
         makeOutlineFile(contents, small, dirLocus, nameOfFile, "/SMALL/");
      }
      if (medium.size() > 0) {
         makeOutlineFile(contents, medium, dirLocus, nameOfFile, "/MEDIUM/");
      }
      if (large.size() > 0) {
         makeOutlineFile(contents, large, dirLocus, nameOfFile, "/LARGE/");
      }
   }
   
   public static void makeOutlineFile(ArrayList<String> contents, ArrayList<Integer> lineNums, String dirLocus, String nameOfFile, String subdir) throws IOException {
      FileOutputStream sizeCells = new FileOutputStream(dirLocus + subdir + nameOfFile);
      PrintWriter writeSize = new PrintWriter(sizeCells);
      
      for (int i = 0; i < 12; i++) {
         writeSize.println(contents.get(i));
      }
      for (int i : lineNums) {
         for (int j = i; j < i + 10; j++) {
            writeSize.println(contents.get(j));
         }
      }
      
      writeSize.flush();
      sizeCells.flush();
      sizeCells.close();
   }
}