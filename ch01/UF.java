abstract public class UF
{
    protected int[] id;
    protected int count;

    public UF(int N)
    {
        count = N;
        id = new int[N];
        for (int i = 0; i < N; i++)
        {
            id[i] = i; 
        }
    }

    public boolean connected(int p, int q)
    {
        return find(p) == find(q); 
    }

    public abstract int find(int p);
    public abstract void union(int p, int q);
}
