import java.util.Arrays;

import stdlib.In;
import stdlib.StdIn;
import stdlib.StdOut;

public class Autocomplete {
    private Term[] terms; // Array of terms

    // Constructs an autocomplete data structure from an array of terms.
    public Autocomplete(Term[] terms) {
        // if terms is null then throw a NullPointerException("terms is null")
        if (terms == null) {
            throw new NullPointerException("terms is null");
        }
        // Initialise this.terms to a defensive copy of terms.
        this.terms = new Term[terms.length];
        for (int i = 0; i < terms.length; i++) {
            this.terms[i] = terms[i];
        }
        // sort the array in the lexicographic order of terms.
        Arrays.sort(this.terms);
    }

    // Returns all terms that start with prefix, in descending order of their weights.
    public Term[] allMatches(String prefix) {
        // If prefix is null then throw a NullPointerException("prefix is null")
        if (prefix == null) {
            throw new NullPointerException("prefix is null");
        }
        // Creating a Term object 'object' with query as prefix and weight as 0.
        Term object = new Term(prefix, 0);
        // Finding the first index 'i' of the first term in this.terms that start with the given
        // prefix
        int i = BinarySearchDeluxe.firstIndexOf(this.terms, object,
                Term.byPrefixOrder(prefix.length()));
        // n represents the number of terms in this.terms that start with the given prefix.
        int n = numberOfMatches(prefix);
        // Creating an array matches[] whose size is equals to the n. It stores the n elements from
        // this.terms that start with index i.(that is all the terms in this.terms that starts with
        // the given prefix)
        Term[] matches = new Term[n];
        for (int k = 0; k < n; k++) {
            matches[k] = this.terms[i];
            i++;

        }
        // Then, sort the array matches[] by the reverse weight order.
        Arrays.sort(matches, Term.byReverseWeightOrder());
        // return the array matches[].
        return matches;
    }

    // Returns the number of terms that start with prefix.
    public int numberOfMatches(String prefix) {
        // If prefix is null then throw a NullPointerException("prefix is null")
        if (prefix == null) {
            throw new NullPointerException("prefix is null");
        }
        // Creating a Term object - object with the query as prefix and weight as 0.
        Term object = new Term(prefix, 0);
        // Finding the index i of the first term in this.terms that start with
        // the given prefix.
        int i = BinarySearchDeluxe.firstIndexOf(this.terms, object,
                Term.byPrefixOrder(prefix.length()));
        // Finding the index j of the last term in this.terms that start with the given prefix.
        int j = BinarySearchDeluxe.lastIndexOf(this.terms, object,
                Term.byPrefixOrder(prefix.length()));
        // If there is no term in this.terms that start with the given prefix, then return 0.
        if (i == -1 && j == -1) {
            return 0;
        }
        // else return the number of terms in this.terms that start with the given prefix.
        return j - i + 1;
    }

    // Unit tests the data type. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        int k = Integer.parseInt(args[1]);
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = in.readLong();
            in.readChar();
            String query = in.readLine();
            terms[i] = new Term(query.trim(), weight);
        }
        Autocomplete autocomplete = new Autocomplete(terms);
        StdOut.print("Enter a prefix (or ctrl-d to quit): ");
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);
            String msg = " matches for \"" + prefix + "\", in descending order by weight:";
            if (results.length == 0) {
                msg = "No matches";
            } else if (results.length > k) {
                msg = "First " + k + msg;
            } else {
                msg = "All" + msg;
            }
            StdOut.printf("%s\n", msg);
            for (int i = 0; i < Math.min(k, results.length); i++) {
                StdOut.println("  " + results[i]);
            }
            StdOut.print("Enter a prefix (or ctrl-d to quit): ");
        }
    }
}
