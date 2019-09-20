public class SortCompare {
    public static double time(Sort o, Comparable[] a) {
        Stopwatch timer = new Stopwatch();
        o.sort(a);
        return timer.elapsedTime();
    }

    /*
        Use alg sort T random arrays of length N.
     */
    public static double timeRandomInput(String alg, int N, int T) {
        Sort o;
        if (alg.equals("Insertion"))        o = new InsertionSort();
        else if (alg.equals("Selection"))   o = new SelectionSort();
        else if (alg.equals("Shell"))       o = new ShellSort();
        else if (alg.equals("Merge"))       o = new MergeSort();
        else if (alg.equals("Quick"))       o = new QuickSort();
        else return 0.0;

        double total = 0.0;
        Double[] a = new Double[N];
        for (int t = 0; t < T; t++) {
            for (int i = 0; i < N; i++) {
                a[i] = StdRandom.uniform();
            }

            total += time(o, a);
        }

        return total;
    }

    public static void main(String[] args)
    {
        /*String alg1 = args[0];
        String alg2 = args[1];
        int N = Integer.parseInt(args[2]);
        int T = Integer.parseInt(args[3]);*/

        String alg1 = "Merge";
        String alg2 = "Shell";
        int N = 1000000;
        int T = 100;

        double t1 = timeRandomInput(alg1, N, T);
        double t2 = timeRandomInput(alg2, N, T);
        StdOut.printf("For %d random Doubles\n\t%s is", N, alg1);
        StdOut.printf(" %.1f time faster than %s\n", t2/t1, alg2);
    }
}
