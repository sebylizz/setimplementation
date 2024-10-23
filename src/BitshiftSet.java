public class BitshiftSet implements OperatingSet {
    // The bitset is represented as an array of longs
    private long[] bitset;

    // Constructor to initialize the bitset array. The size of the array is based on the maximum element
    public BitshiftSet(int maxElement) {
        bitset = new long[(maxElement / 64) + 1];
    }

    // Adds a number to the set by setting the appropriate bit in the set using bitshifting
    public void add(int num) {
        bitset[num / 64] |= (1L << num % 64);
    }

    // Removes a number from the set by clearing the appropriate bit.
    public void remove(int num) {
        bitset[num / 64] &= ~(1L << num % 64);
    }

    // Checks if the number is present in the set by testing if the relevant bit is set.
    public boolean contains(int num) {
        return (bitset[num / 64] & (1L << num % 64)) != 0;
    }

    // Returns the size of the set, which is the total number of bits set to 1 in the bitset.
    public int size() {
        int size = 0;
        for (int i = 0; i < bitset.length; i++) {
            size += Long.bitCount(bitset[i]);
        }
        return size;
    }

    // Checks if this set includes all elements of another set with the bitwise OR
    public boolean includes(BitshiftSet other) {
        boolean contains = true;
        for (int i = 0; i < bitset.length; i++) {
            // If other has a 1 bit where this set has a 0 bit, the result will be false.
            if ((bitset[i] | other.bitset[i]) != bitset[i]) {
                contains = false;
                break;
            }
        }
        return contains;
    }

    // Returns a new BitshiftSet that represents the intersection of this set and another set using bitwise AND
    public BitshiftSet intersection(BitshiftSet other, int maxElement) {
        BitshiftSet intersection = new BitshiftSet(maxElement);
        for (int i = 0; i < bitset.length; i++) {
            intersection.bitset[i] = bitset[i] & other.bitset[i];
        }
        return intersection;
    }

    // Returns a new BitshiftSet that represents the union of this set and another set using bitwise OR
    public BitshiftSet union(BitshiftSet other, int maxElement) {
        BitshiftSet union = new BitshiftSet(maxElement);
        for (int i = 0; i < bitset.length; i++) {
            union.bitset[i] = bitset[i] | other.bitset[i];
        }
        return union;
    }
}
