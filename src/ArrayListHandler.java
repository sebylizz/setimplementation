import java.util.ArrayList;

public class ArrayListHandler implements Handler {
    private ArrayList<ArrayList<Integer>> lists;
    private int membershipSuccesses = 0;
    private int inclusionSuccesses = 0;
    private ArrayList<Integer> intersectionSizes = new ArrayList<>();
    private ArrayList<Integer> unionSizes = new ArrayList<>();

    public ArrayListHandler() {
        lists = new ArrayList<ArrayList<Integer>>();
    }

    // Constructs sets from input strings, processing add, remove, and membership commands
    public void construct(String[] inp) {
        long starttime = System.nanoTime();
        lists.add(new ArrayList<Integer>());
        ArrayList<Integer> cur = lists.getLast();

        int i = 0;
        while (i < inp.length) {
            if (inp[i].length() == 0) {
                lists.add(new ArrayList<Integer>());
                cur = lists.getLast(); // Start a new set when encountering an empty line
            } else {
                String[] data = inp[i].split("[()]+");
                String op = data[0];
                int nr = Integer.parseInt(data[1]);

                // Handle add, remove, and membership operations
                if (op.equals("add") && !cur.contains(nr)) {
                    cur.add(nr);
                } else if (op.equals("rem") && cur.contains(nr)) {
                    cur.remove(Integer.valueOf(nr));
                } else if (op.equals("mem") && cur.contains(nr)) {
                    membershipSuccesses++;
                }
            }
            i++;
        }
        long endtime = System.nanoTime();
        System.out.println("Construction time: " + (endtime - starttime) / 1000000 + "ms");
    }

    // Checks if each set includes the previous set
    public void include() {
        long starttime = System.nanoTime();
        for (int i = 0; i < lists.size() - 1; i++) {
            if (lists.get(i + 1).containsAll(lists.get(i))) {
                inclusionSuccesses++;
            }
        }
        long endtime = System.nanoTime();
        System.out.println("Include time: " + (endtime - starttime) / 1000000 + "ms");
    }

    // Calculates intersection sizes between consecutive sets
    public void intersection() {
        long starttime = System.nanoTime();
        for (int i = 0; i < lists.size() - 1; i++) {
            ArrayList<Integer> intersection = new ArrayList<Integer>(lists.get(i));
            intersection.retainAll(lists.get(i + 1));
            intersectionSizes.add(intersection.size());
        }
        long endtime = System.nanoTime();
        System.out.println("Intersection time: " + (endtime - starttime) / 1000000 + "ms");
    }

    // Calculates union sizes between consecutive sets
    public void union() {
        long starttime = System.nanoTime();
        for (int i = 0; i < lists.size() - 1; i++) {
            ArrayList<Integer> union = new ArrayList<Integer>(lists.get(i));
            // Add elements from the next set if not already present
            for (int j = 0; j < lists.get(i + 1).size(); j++) {
                if (!union.contains(lists.get(i + 1).get(j))) {
                    union.add(lists.get(i + 1).get(j));
                }
            }
            unionSizes.add(union.size());
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
        for (int i = 0; i < lists.size() - 1; i++) {
            System.out.print(lists.get(i).size() + ", ");
        }
        System.out.println(lists.getLast().size());
    }

    public void printMembershipSuccesses() {
        System.out.println("Membership: " + membershipSuccesses);
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
