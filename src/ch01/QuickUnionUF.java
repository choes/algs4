class QuickUnionUF extends UF
{
    public QuickUnionUF(int N)
    {
        super(N); 
    }
    
    @Override
    public int find(int p)
    {
        while (p != id[p]) p = id[p];

        return p;
    }
    
    @Override
    public void union(int p, int q)
    {
        int rootp = find(p);
        int rootq = find(q);

        if (rootp != rootq)
        {
            id[rootp] = rootq;
            count--;
        }
    }
}
