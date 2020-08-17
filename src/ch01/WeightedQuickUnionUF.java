class WeightedQuickUnionUF extends QuickUnionUF
{
    private int[] sz; // size of component for roots (site indexed)
    
    public WeightedQuickUnionUF(int N)
    {
        super(N);
        sz = new int[N];
        for (int i = 0; i < N; i++)
            sz[i] = 1;
    }
    
    @Override
    public void union(int p, int q)
    {
        int rootp = find(p);
        int rootq = find(q);

        if (rootp != rootq)
        { // Make smaller root point to larger root
            if (sz[rootp] < sz[rootq])
            {
                id[rootp] = rootq;
                sz[rootq] += sz[rootp];
            }
            else
            {
                id[rootq] = rootp;
                sz[rootp] += sz[rootq];
            }

            count--;
        }
    }
}
