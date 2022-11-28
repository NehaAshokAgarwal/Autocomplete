import java.util.Arrays;
import java.util.Comparator;

import stdlib.In;
import stdlib.StdOut;


public class Term implements Comparable<Term> {
    private String query; // Query String
    private long weight; // Query weight


    // Constructs a term given the associated query string, having weight 0.
    public Term(String query) {
        // If query is null, then throw NullPointerException with a message
        // saying that query is null.
        if (query == null) {
            throw new NullPointerException("query is null");
        }
        // Initialise this.query to the given query.
        this.query = query;
        // Initialise this.weight to 0.
        this.weight = 0;
    }

    // Constructs a term given the associated query string and weight.
    public Term(String query, long weight) {
        // If the query is null, then throw an NullPointerException saying that
        // the query is null.
        if (query == null) {
            throw new NullPointerException("query is null");
        } else if (weight < 0) {
            // If weight is less than 0 (i.e. negative) then throw an
            // IllegalArgumentException saying Illegal weight.
            throw new IllegalArgumentException("Illegal weight");
        }
        // Initialise this.query to the given query.
        this.query = query;
        // Initialise this.weight to the given weight.
        this.weight = weight;
    }

    // Returns a string representation of this term.
    public String toString() {
        return weight + "\t" + query;
    }

    // Returns a comparison of this term and other by query.
    public int compareTo(Term other) {
        return this.query.compareTo(other.query);
    }

    // Returns a comparator for comparing two terms in reverse order of their weights.
    public static Comparator<Term> byReverseWeightOrder() {
        return new ReverseWeightOrder();
    }

    // Returns a comparator for comparing two terms by their prefixes of length r.
    public static Comparator<Term> byPrefixOrder(int r) {
        // If the length of the prefix is less than 0(i.e. negative) then throw
        // an IllegalArgumentException with a messaging saying illegal r.
        if (r < 0) {
            throw new IllegalArgumentException("Illegal r");
        }
        return new PrefixOrder(r);
    }

    // Reverse-weight comparator.
    private static class ReverseWeightOrder implements Comparator<Term> {
        // Returns a comparison of terms v and w by their weights in reverse order.
        public int compare(Term v, Term w) {
            return Long.compare(w.weight, v.weight);
        }
    }

    // Prefix-order comparator.
    private static class PrefixOrder implements Comparator<Term> {
        private int r; // Prefix length r

        // Constructs a new prefix order given the prefix length.
        PrefixOrder(int r) {
            // Initialise this.r to the given r
            this.r = r;
        }

        // Returns a comparison of terms v and w by their prefixes of length r.
        public int compare(Term v, Term w) {
            // 'a' is a substring of v of length 0 to r. However, if the length
            // of v is less than r, then 'a' is the substring of v of length 0 to
            // v.length()(i.e. its full length).
            String a = v.query.substring(0, Math.min(r, v.query.length()));
            // 'b' is a substring of w of length 0 to r. However, if the length
            // w is less than r, then 'a' is the substring of w of length 0 to
            // w.length()(i.e. its full length).
            String b = w.query.substring(0, Math.min(r, w.query.length()));
            return a.compareTo(b);
        }
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
        StdOut.printf("Top %d by lexicographic order:\n", k);
        Arrays.sort(terms);
        for (int i = 0; i < k; i++) {
            StdOut.println(terms[i]);
        }
        StdOut.printf("Top %d by reverse-weight order:\n", k);
        Arrays.sort(terms, Term.byReverseWeightOrder());
        for (int i = 0; i < k; i++) {
            StdOut.println(terms[i]);
        }
    }
}
