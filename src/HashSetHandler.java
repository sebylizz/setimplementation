import java.util.ArrayList;
import java.util.HashSet;

import static java.lang.System.currentTimeMillis;

// HashSetHandler class implements the Handler interface
public class HashSetHandler implements Handler {
    private ArrayList<HashSet<Integer>> hashSets;
    private int membershipCounter = 0;
    private int inclusion = 0;
    private ArrayList<HashSet<Integer>> intersectionSets = new ArrayList<>();
    private ArrayList<HashSet<Integer>> unionSets = new ArrayList<>();

    public HashSetHandler() {
        hashSets = new ArrayList<>();
    }

    public void construct(String[] input) {
        long constructTime = currentTimeMillis();

        hashSets.add(new HashSet<>());
        HashSet<Integer> hashSetsArr = hashSets.get(hashSets.size() - 1);

        for (String line : input) {
            if (line.isEmpty()) {
                // Create a new set when there's an empty line (denotes a new set)
                hashSets.add(new HashSet<>());
                hashSetsArr = hashSets.get(hashSets.size() - 1);
                continue;
            }

            // Extract the number from the command, e.g., from "add(5)" or "rem(3)"
            int number = Integer.parseInt(line.split("\\(")[1].replace(")", ""));

            if (line.startsWith("add")) {
                hashSetsArr.add(number);
            } else if (line.startsWith("rem")) {
                hashSetsArr.remove(number);
            } else if (line.startsWith("mem")) {
                boolean exists = hashSetsArr.contains(number);
                if (exists) {
                    membershipCounter++;
                }
            }
        }

        System.out.println("Construction time: " + (currentTimeMillis() - constructTime) + "ms");
    }

    public void include() {
        long inclusionTime = currentTimeMillis();

        try {
            for (int i = 0; i < hashSets.size() - 1; i++) {
                // Compare each set with the next set to check inclusion
                HashSet<Integer> currentSet = hashSets.get(i);
                HashSet<Integer> nextSet = hashSets.get(i + 1);

                // If nextSet contains all elements of currentSet, increment the inclusion counter
                if (nextSet.containsAll(currentSet)) {
                    inclusion++;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Inclusion time: " + (currentTimeMillis() - inclusionTime) + "ms");
    }

    public void intersection() {
        long intersectionTime = currentTimeMillis();

        try {
            for (int i = 0; i < hashSets.size() - 1; i++) {
                HashSet<Integer> currentSet = hashSets.get(i);
                HashSet<Integer> nextSet = hashSets.get(i + 1);

                // Add a new set to store the intersection result
                intersectionSets.add(new HashSet<>());
                HashSet<Integer> intersectionSet = intersectionSets.get(intersectionSets.size() - 1);

                // Add elements to the intersection set if they exist in both currentSet and nextSet
                for (Integer number : currentSet) {
                    if (nextSet.contains(number)) {
                        intersectionSet.add(number);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Intersection time: " + (currentTimeMillis() - intersectionTime) + "ms");
    }

    public void union() {
        long unionTime = currentTimeMillis();

        try {
            for (int i = 0; i < hashSets.size() - 1; i++) {
                HashSet<Integer> currentSet = hashSets.get(i);
                HashSet<Integer> nextSet = hashSets.get(i + 1);

                // Add a new set to store the union result
                unionSets.add(new HashSet<>());
                HashSet<Integer> unionSet = unionSets.get(unionSets.size() - 1);

                // Add all elements from both currentSet and nextSet to the unionSet
                unionSet.addAll(currentSet);
                unionSet.addAll(nextSet);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Union time: " + (currentTimeMillis() - unionTime) + "ms");
    }

    public void printer() {
        System.out.print("Sizes: ");
        for (int i = 0; i < hashSets.size(); i++) {
            System.out.print(hashSets.get(i).size() + ", ");
        }

        System.out.println("\nMembership: " + membershipCounter);
        System.out.println("Inclusion: " + inclusion);

        System.out.print("Intersection: ");
        for (int i = 0; i < intersectionSets.size(); i++) {
            System.out.print(intersectionSets.get(i).size() + ", ");
        }

        System.out.print("\nUnion: ");
        for (int i = 0; i < unionSets.size(); i++) {
            System.out.print(unionSets.get(i).size() + ", ");
        }

        System.out.println();
    }
}
