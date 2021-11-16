import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private final double[] openSitesFraction;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("n or trials should be larger than zero.");
        }

        final int totalSites = n * n;
        openSitesFraction = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation percolation = new Percolation(n);
            while (!percolation.percolates()) {
                int uni = StdRandom.uniform(totalSites) + 1;
                int remainder = uni % n;
                int row = uni / n + (remainder == 0 ? 0 : 1);
                int col = (remainder == 0) ? n : remainder;
                percolation.open(row, col);
            }

            double openSites = percolation.numberOfOpenSites();
            openSitesFraction[i] = openSites / totalSites;
        }

    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(openSitesFraction);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(openSitesFraction);
    }
    
    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - CONFIDENCE_95 * stddev() / Math.sqrt(openSitesFraction.length);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + CONFIDENCE_95 * stddev() / Math.sqrt(openSitesFraction.length);
    }

    // test client
    public static void main(String[] args) {
        if (args.length != 2) {
            StdOut.println("Usage: java-algs4 PercolationStats n trials");
            return;
        }

        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats stats = new PercolationStats(n, trials);

        StdOut.printf("mean\t= %f\n", stats.mean());
        StdOut.printf("stddev\t= %f\n", stats.stddev());
        StdOut.print("95% confidence interval\t= [");
        StdOut.print(stats.confidenceLo());
        StdOut.print(", ");
        StdOut.print(stats.confidenceHi());
        StdOut.println("]");
    }
}
