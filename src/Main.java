import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Usage: java -cp classes Main <file>");
        } else {
            runFile(args[0]);
        }
    }

    public static void runFile(String file) throws Exception {

        System.out.println("\nRunning file " + file + "\n");
        ArrayListHandler arrayListHandler = new ArrayListHandler();
        HashSetHandler hashSetHandler = new HashSetHandler();
        BitshiftSetHandler bitshiftSetHandler = new BitshiftSetHandler();

        String[] input = readFile(file);

        // Finding the biggest integer in input file for optimization purposes
        int maxInt = 0;
        for (int i = 0; i < input.length; i++) {
            maxInt = input[i].length() == 0 ? maxInt : Math.max(maxInt, Integer.parseInt(input[i].split("[()]+")[1]));
        }

        // Calling each implementation in order

        //bitshiftSetHandler.printer();
        System.out.println("Starting ArrayList implementation:");
        arrayListHandler.construct(input);
        arrayListHandler.include();
        arrayListHandler.intersection();
        arrayListHandler.union();
        //arrayListHandler.printer();

        System.out.println("\nStarting HashSet implementation:");
        hashSetHandler.construct(input);
        hashSetHandler.include();
        hashSetHandler.intersection();
        hashSetHandler.union();
        //hashSetHandler.printer();

        System.out.println("\nStarting BitshiftSet implementation:");
        bitshiftSetHandler.construct(input, maxInt);
        bitshiftSetHandler.include();
        bitshiftSetHandler.intersection(maxInt);
        bitshiftSetHandler.union(maxInt);

        System.out.println();
    }

    public static String[] readFile(String input) throws Exception {
        String[] readInput = Files.readString(Paths.get(input)).split("\n");

        // Errorhandling for input file
        for (int i = 0; i < readInput.length; i++) {
            String str = readInput[i];
            if (str.length() == 0) {
                continue;
            }

            // Self-explanatory error messages
            if (!(str.substring(0, 3).equals("add") || str.substring(0, 3).equals("rem")
                    || str.substring(0, 3).equals("mem"))) {
                throw new WronglyFormattedFileException("First three characters must be 'add', 'rem' or 'mem'");
            }

            if (str.indexOf('(') == -1 || str.indexOf(')') == -1) {
                throw new WronglyFormattedFileException("Line must contain '(' and ')'");
            }

            if (str.indexOf(')') != str.length() - 1) {
                throw new WronglyFormattedFileException("First ')' must be the last character of the line");
            }

            String possiblyNumber = str.substring(str.indexOf('(') + 1, str.length() - 1);
            if (!possiblyNumber.matches("[0-9]+")) {
                throw new IOException("Number must be an integer");
            }
        }
        return readInput;
    }
}
