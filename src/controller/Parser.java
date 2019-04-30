package controller;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * This class is used to retrieve all relevant operation codes from a text file and return them in an String array
 */
public class Parser {

    BufferedReader br;
    StringBuilder sb;


    /**
     * Is used to run all necessary methods to parse a text file into usable assembler commands
     *
     * @param pathToFile an absolute path to a text file containing all assembler commands
     * @return an array containing all assembler commands in order
     */
    public int[] toParse(String pathToFile) {
        //calling all methods to compose a command string array
        int[] commands = convertParserExport(cleanUpArray(retrieveCommands(readCommands(pathToFile))));
        return commands;
    }

    public int[] convertParserExport(String[] commandArray) {
        int[] converted = new int[commandArray.length];
        for (int x = 0; x < commandArray.length; x++) {
            converted[x] = Integer.decode("0x" + commandArray[x]);
        }
        return converted;
    }

    /**
     * Is used to retrieve all assembler commands from a textfile and filter them for operation codes
     *
     * @param path an absolute path to a text file containing all assembler commands
     * @return an array containing all information retreived from the text file sorted by /n
     */
    public String[] readCommands(String path) {

        String line;
        String[] commandArray = new String[1000];
        int x = -1;

        {
            try {
                br = new BufferedReader(new FileReader(path));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        {
            try {
                line = br.readLine();
                //if the line doesn't contain a " " save it to the array
                while (line != null) {
                    x++;
                    if (!line.startsWith(" ")) {
                        commandArray[x] = line;
                    }

                    line = br.readLine();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return commandArray;
    }

    /**
     * Is used to only retrieve relevant operation code data from a given text file
     *
     * @param toRetrieveFrom an array containing everything parsed from a text file
     * @return a cleaned up array containing only operation codes in order of parsing
     */
    public String[] retrieveCommands(String[] toRetrieveFrom) {
        String[] stArr = new String[1000];

        int x = -1;
        //add all command operations to a string array
        for (String s : toRetrieveFrom) {
            if (!(s == null)) {
                sb = new StringBuilder();
                x++;
                sb.append(s.substring(5, 9));
                stArr[x] = sb.toString();
            }
        }
        return stArr;
    }

    /**
     * Is used to eliminate all empty fields inside of an array
     *
     * @param toClean an array containing unwanted empty fields
     * @return an array only containing usable data
     */
    public String[] cleanUpArray(String[] toClean) {
        int x = -1;
        int y = -1;

        String[] toCleanArray = new String[1000];
        //filter all null fields
        for (String s : toClean) {
            if (s != null) {
                x++;
                toCleanArray[x] = s;
            }
        }
        int count = x + 1;
        String[] cleanedArray = new String[count];
        //add non null fields to new array
        for (String s : toClean) {
            if (s != null) {
                y++;
                cleanedArray[y] = s;
            }
        }
        return cleanedArray;

    }
}
