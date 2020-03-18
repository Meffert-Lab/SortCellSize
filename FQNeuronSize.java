import java.util.Scanner;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class FQNeuronSize2 {
   public static void main(String[] args) throws IOException {
      
      System.out.println("FQNeuronSize -- sort neurons in FQ Outline file by size.");
      System.out.println("Written by Sreenivas Eadara for the Meffert Lab.");
      
      Scanner kb = new Scanner(System.in);
      System.out.println("Please enter the directory of an outline folder.");
      String dirLocus = kb.next();
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
      int countSmall = 0;
      int countMedium = 0;
      int countLarge = 0;
      for (HashMap.Entry<Integer, Double> cell : cellAreas.entrySet()) {
         
         if (cell.getValue() > 25000.0) {
            countLarge++;
         }
         else if (cell.getValue() < 15000.0) {
            countSmall++;
         }
         else {
            countMedium++;
         }
      }
       
      FileOutputStream smallCells = new FileOutputStream(dirLocus + "/SMALL/" + nameOfFile);
      PrintWriter writeSmall = new PrintWriter(smallCells);
      if (countSmall > 0) {
         for (int i = 0; i < 12; i++) {
            writeSmall.println(contents.get(i));
         }
      }
      FileOutputStream mediumCells = new FileOutputStream(dirLocus + "/MEDIUM/" + nameOfFile);
      PrintWriter writeMedium = new PrintWriter(mediumCells);
      if (countMedium > 0) {
         for (int i = 0; i < 12; i++) {
            writeMedium.println(contents.get(i));
         }
      }
      FileOutputStream largeCells = new FileOutputStream(dirLocus + "/LARGE/" + nameOfFile);
      PrintWriter writeLarge = new PrintWriter(largeCells);
      if (countLarge > 0) {
         for (int i = 0; i < 12; i++) {
            writeLarge.println(contents.get(i));
         }
      }
      for (HashMap.Entry<Integer, Double> cell : cellAreas.entrySet()) {
         
         if (cell.getValue() > 25000.0) {
            for (int i = cell.getKey(); i < cell.getKey() + 10; i++) {
               writeLarge.println(contents.get(i));
            }
         }
         else if (cell.getValue() < 15000.0) {
            for (int i = cell.getKey(); i < cell.getKey() + 10; i++) {
               writeSmall.println(contents.get(i));
            }
         }
         else {
            for (int i = cell.getKey(); i < cell.getKey() + 10; i++) {
               writeMedium.println(contents.get(i));
            }
         }
      }
      writeSmall.flush();
      smallCells.flush();
      smallCells.close();
      writeMedium.flush();
      mediumCells.flush();
      mediumCells.close();
      writeLarge.flush();
      largeCells.flush();
      largeCells.close();
      
   }
   
}