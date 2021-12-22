import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdIn;

public class Permutation {
    public static void main(String[] args) {
        if (args.length == 1) {
            RandomizedQueue<String> queue = new RandomizedQueue<String>();
            boolean isReadOver = false;
            while (!isReadOver) {
                try {
                    queue.enqueue(StdIn.readString());
                } catch (NoSuchElementException e) {
                    isReadOver = true;
                }
            }

            int k = Integer.parseInt(args[0]);
            if (k > queue.size()) {
                k = queue.size();
            }
            for (int i = 0; i < k; i++) {
                System.out.println(queue.dequeue());
            }
        }
    }
}
