import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class HelloGoodbye {
    public static void main(String[] args) {
        StdOut.println("Hello " + String.join(" and ", args) + (args.length > 0 ? "." : ""));

        List<String> argsList = Arrays.asList(args);
        Collections.reverse(argsList);
        StdOut.println("Goodbye " + String.join(" and ", argsList) + (args.length > 0 ? "." : ""));
    }
}
