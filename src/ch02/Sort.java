abstract public class Sort
{
    public abstract void sort(Comparable[] a);

    protected boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;            
    }

    protected void exch(Comparable[] a, int i, int j) {
        Comparable t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public void show(Comparable[] a) {
        for (Comparable v : a) {
            StdOut.print(v + " "); 
        }

        StdOut.println();
    }

    public boolean isSorted(Comparable[] a) {
        for (int i = 1; i < a.length; i++) {
            if (less(a[i], a[i-1])) return false;
        }

        return true;
    }
}
