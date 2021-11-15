public class UFTest
{
    public static void main(String[] args)
    {
        Stopwatch timer = new Stopwatch();

        int N = StdIn.readInt();
        // UF uf = new QuickFindUF(N);
        //UF uf = new QuickUnionUF(N);
        UF uf = new WeightedQuickUnionUF(N);

        while (!StdIn.isEmpty())
        {
            int p = StdIn.readInt();
            int q = StdIn.readInt();
            if (uf.connected(p, q)) continue;
            uf.union(p, q);
            StdOut.println(p + " " + q);
        }

        StdOut.println(uf.count + " components, " + timer.elapsedTime() + "s");
    }
}
