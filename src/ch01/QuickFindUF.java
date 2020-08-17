final class QuickFindUF extends UF
{
    public QuickFindUF(int N)
    {
        super(N); 
    }
    
    @Override
    public int find(int p)
    {
        return id[p];
    }
    
    @Override
    public void union(int p, int q)
    {
        int vp = find(p);
        int vq = find(q);

        if (vp != vq)
        {
            for (int i = 0; i < id.length; i++)
            {
                if (id[i] == vp)
                {
                    id[i] = vq; 
                }
            }

            count--;
        }
    }
}
