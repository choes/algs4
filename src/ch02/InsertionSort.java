public class InsertionSort extends Sort {
    protected int gap = 1;

    @Override
    public void sort(Comparable[] a) {
        int N = a.length;
        for (int i = gap; i < N; i++) {
            for (int j = i; j >= gap && less(a[j], a[j - gap]); j -= gap) {
                exch(a, j, j - gap);
            }
        }
    }
}
