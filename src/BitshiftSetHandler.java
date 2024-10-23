import java.util.ArrayList;

public class BitshiftSetHandler implements Handler {
    private ArrayList<BitshiftSet> sets;
    private int membershipSuccess = 0;
    private int inclusionSuccesses = 0;
    private ArrayList<Integer> intersectionSizes = new ArrayList<>();
    private ArrayList<Integer> unionSizes = new ArrayList<>();

    public BitshiftSetHandler() {
        sets = new ArrayList<BitshiftSet>();
    }

    // Overloaded construct method with default maxInt
    public void construct(String[] inp) {
        try {
            construct(inp, 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Constructs BitshiftSets from input strings, adding/removing elements based on commands
    public void construct(String[] inp, int maxInt) throws Exception {
        long starttime = System.nanoTime();
        sets.add(new BitshiftSet(maxInt));
        BitshiftSet cur = sets.getLast();

        for (String line : inp) {
            if (line.length() == 0) {
                // Start a new set when encountering an empty line
                sets.add(new BitshiftSet(maxInt));
                cur = sets.getLast();
            } else {
                // Parse element and perform operations: add, remove, or check membership
                int nr = Integer.parseInt(line.substring(4, line.length() - 1));
                char op = line.charAt(0);
                if (op == 'a' && !cur.contains(nr)) {
                    cur.add(nr);
                } else if (op == 'r' && cur.contains(nr)) {
                    cur.remove(nr);
                } else if (op == 'm' && cur.contains(nr)) {
                    membershipSuccess++;
                }
            }
        }
        long endtime = System.nanoTime();
        System.out.println("Construction time: " + (endtime - starttime) / 1000000 + "ms");
    }

    // Includes operation: checks if each set includes the next set
    public void include() {
        long starttime = System.nanoTime();
        for (int i = 0; i < sets.size() - 1; i++) {
            if (sets.get(i + 1).includes(sets.get(i))) {
                inclusionSuccesses++;
            }
        }
        long endtime = System.nanoTime();
        System.out.println("Include time: " + (endtime - starttime) / 1000000 + "ms");
    }

    // Default intersection operation with max element 1000
    public void intersection() {
        intersection(1000);
    }

    // Calculates intersection size between consecutive sets
    public void intersection(int maxElement) {
        long starttime = System.nanoTime();
        for (int i = 0; i < sets.size() - 1; i++) {
            intersectionSizes.add(sets.get(i).intersection(sets.get(i + 1), maxElement).size());
        }
        long endtime = System.nanoTime();
        System.out.println("Intersection time: " + (endtime - starttime) / 1000000 + "ms");
    }

    // Default union operation with max element 1000
    public void union() {
        union(1000);
    }

    // Calculates union size between consecutive sets
    public void union(int maxElement) {
        long starttime = System.nanoTime();
        for (int i = 0; i < sets.size() - 1; i++) {
            unionSizes.add(sets.get(i).union(sets.get(i + 1), maxElement).size());
        }
        long endtime = System.nanoTime();
        System.out.println("Union time: " + (endtime - starttime) / 1000000 + "ms");
    }

    // Prints all tracked statistics
    public void printer() {
        printSizes();
        printMembershipSuccesses();
        printInclusionSuccesses();
        printIntersectionSizes();
        printUnionSizes();
    }

    public void printSizes() {
        System.out.print("Sizes: ");
        for (int i = 0; i < sets.size() - 1; i++) {
            System.out.print(sets.get(i).size() + ", ");
        }
        System.out.println(sets.getLast().size());
    }

    public void printMembershipSuccesses() {
        System.out.println("Membership: " + membershipSuccess);
    }

    public void printInclusionSuccesses() {
        System.out.println("Inclusion: " + inclusionSuccesses);
    }

    public void printIntersectionSizes() {
        System.out.println("Intersection: " + intersectionSizes);
    }

    public void printUnionSizes() {
        System.out.println("Union: " + unionSizes);
    }
}
