import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;

public class Permutation {

    public static void main(String[] args) {
        int times = Integer.parseInt(args[0]);
        RandomizedQueue<String> rq = new RandomizedQueue<>();
        while (StdIn.hasNextLine()) {
            String[] words = StdIn.readLine().split(" ");
            for (String word : words) {
                rq.enqueue(word);
            }
        }
        Iterator<String> iterator = rq.iterator();

        for (int i = 0; i < times; i++) {
            System.out.println(iterator.next());
        }

    }
}

