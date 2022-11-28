import java.util.Arrays;
import java.util.Comparator;

import stdlib.In;
import stdlib.StdOut;

public class BinarySearchDeluxe {
    // Returns the index of the first key in a that equals the search key, or -1, according to
    // the order induced by the comparator c.
    public static <Key> int firstIndexOf(Key[] a, Key key, Comparator<Key> c) {
        // If the array is null, or key is null, or comparator is null then throw a
        // NullPointerException("a, key, or c is null").
        if (a == null || key == null || c == null) {
            throw new NullPointerException("a, key, or c is null");
        }
        // set lo to 0.
        int lo = 0;
        // set index hi to the last index of the array.
        int hi = a.length - 1;
        // set index to -1(Index stores the first index of the given key in the array)
        int index = -1;

        // As long as lo and hi have not crosses paths(i.e. search is not exhausted)
        while (lo <= hi) {
            // set mid to the middle index in the array
            int mid = lo + (hi - lo) / 2;
            // compare the given key with that of the item at the index mid in the array.
            int cmp = c.compare(key, a[mid]);
            // if the given key is smaller than the item at the index mid, then that means the
            // probability to find the given key is to the left of the array. As the array is sorted
            if (cmp < 0) {
                // set index hi to mid - 1.
                hi = mid - 1;
            } else if (cmp > 0) {
                // else if the given key is bigger than the item at the index mid, then the
                // probability to find the given key is to the right of the array. As the array is
                // sorted.
                // Set index lo to mid+1.
                lo = mid + 1;
            } else {
                // else if the given key matches with the item at the index mid then store the value
                // of the index mid in the variable index and continue the search to the left of the
                // array to find the first index in the array.
                // set index to mid.
                index = mid;
                // set hi to mid-1.
                hi = mid - 1;
            }
            // if there is no such index in the array at which the item matches with the given key
            // then return -1.
        }
        return index;
    }

    // Returns the index of the first key in a that equals the search key, or -1, according to
    // the order induced by the comparator c.
    public static <Key> int lastIndexOf(Key[] a, Key key, Comparator<Key> c) {
        // If the array is null, or key is null, or comparator is null then throw a
        // NullPointerException("a, key, or c is null").
        if (a == null || key == null || c == null) {
            throw new NullPointerException("a, key, or c is null");
        }
        // set index lo to 0
        int lo = 0;
        // set index hi to the last index of the array.
        int hi = a.length - 1;
        // set index to -1
        int index = -1;

        // As long as lo and hi have not crosses paths(i.e. search is not exhausted)
        while (lo <= hi) {
            // set mid to the middle index in the array.
            int mid = lo + (hi - lo) / 2;
            // compare the given key with that of the item at the index mid in the array.
            int cmp = c.compare(key, a[mid]);
            // if the given key is smaller than the item at the index mid, then that means the
            // probability to find the given key is to the left of the array. As the array is sorted
            if (cmp < 0) {
                // set hi to mid-1
                hi = mid - 1;
            } else if (cmp > 0) {
                // else if the given key is bigger than the item at the index mid, then the
                // probability to find the given key is to the right of the array. As the array is
                // sorted.
                // set lo to mid+1
                lo = mid + 1;
            } else {
                // else if the given key matches with the item at the index mid then store the value
                // of the index mid in the variable index and continue the search to the right of
                // the array to find the last index in the array.
                // set index to mid
                index = mid;
                // set lo to mid+1
                lo = mid + 1;
            }

        }
        // if there is no such index in the array at which the item matches with the given key then
        // return -1.
        return index;

    }

    // Unit tests the library. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        String prefix = args[1];
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = in.readLong();
            in.readChar();
            String query = in.readLine();
            terms[i] = new Term(query.trim(), weight);
        }
        Arrays.sort(terms);
        Term term = new Term(prefix);
        Comparator<Term> prefixOrder = Term.byPrefixOrder(prefix.length());
        int i = BinarySearchDeluxe.firstIndexOf(terms, term, prefixOrder);
        int j = BinarySearchDeluxe.lastIndexOf(terms, term, prefixOrder);
        int count = i == -1 && j == -1 ? 0 : j - i + 1;
        StdOut.println("firstIndexOf(" + prefix + ") = " + i);
        StdOut.println("lastIndexOf(" + prefix + ")  = " + j);
        StdOut.println("frequency(" + prefix + ")    = " + count);
    }
}
