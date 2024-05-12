package org.example;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.NotDirectoryException;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class HomeworkImpl {
    /**
     * This method requests the user to input an absolute path to a directory.
     * If the input is empty, it keeps requesting until a non-empty string is entered.
     *
     * @return File object representing the directory
     */
    public static File askDirPath() {
        Scanner sc = new Scanner(System.in);
        String absPath;
        File outputFile = null;

        do {
            System.out.print("Enter the absolute path to the directory: ");
            absPath = sc.nextLine();
            try {
                outputFile = new File(absPath);
            } catch (NullPointerException e) {
                System.out.println("Failed to create File object. Please check the path for errors.");
            }
        } while (outputFile == null || absPath.isEmpty());

        return outputFile;
    }

    /**
     * This method checks if the entered path exists and if it's a directory.
     *
     * @param path File object representing the directory
     * @return -2 if path is not a directory;
     * -1 if path does not exist;
     * 0 if path exists and is a directory.
     */
    public static int checkDir(File path) {
        try {
            if (!path.exists()) {
                System.out.println("The specified path does not exist.");
                return -1;
            }
            if (!path.isDirectory()) {
                System.out.println("The specified path is not a directory.");
                return -2;
            }
        } catch (SecurityException e) {
            System.out.println("Access to the specified path is denied.");
            return -1;
        }
        return 0;
    }

    /**
     * This private method asks the user whether to include subdirectories when filtering files.
     *
     * @return True if user wants to include subdirectories, false otherwise
     */
    private static boolean askSubDirs() {
        Scanner sc = new Scanner(System.in);

        System.out.println("Do you want to include subdirectories (if they exist)?");
        System.out.print("Enter 'yes' or 'no': ");
        while (true) {
            String input = sc.nextLine();
            if (input.equalsIgnoreCase("yes")) {
                return true;
            } else if (input.equalsIgnoreCase("no")) {
                return false;
            } else {
                System.out.println("Please enter 'yes' or 'no'.");
            }
        }
    }

    /**
     * This method asks the user for a size limit in MB for file filtering.
     *
     * @return double value entered by the user
     */
    private static double askMbLimit() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the size limit in MB for filtering: ");
        while (true) {
            try {
                String strDouble = sc.nextLine();
                double mbLimit = Double.parseDouble(strDouble);
                if (mbLimit <= 0) {
                    System.out.print("Please enter a positive number: ");
                } else {
                    return mbLimit;
                }
            } catch (NumberFormatException e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }

    /**
     * This method filters files based on their size in the specified directory.
     *
     * @param path File object representing the directory
     */
    public static void filterFilesAt(File path) {
        Iterator<File> itr = FileUtils.iterateFiles(path, null, askSubDirs());
        int index = 0;
        double mbLimit = askMbLimit();
        while (itr.hasNext()) {
            File element = itr.next();
            double fileSize = FileUtils.sizeOf(element) * 0.000001;
            if (fileSize >= mbLimit) {
                index++;
                System.out.printf("%d. %s -> %.2f MB%n", index, element.getName(), fileSize);
            }
        }
        System.out.printf("Found %d file(s) larger than %.2f MB.%n", index, mbLimit);
    }
}