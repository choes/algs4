public class MergeSort extends Sort {
    private Comparable[] aux;

    // Abstract in-place merge
    private void merge(Comparable[] a, int lo, int mid, int hi) { // Merge a[lo..mid] with a[mid+1..hi]
        int i = lo, j = mid + 1;

        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }

        for (int k = lo; k <= hi; k++) {
            if (i > mid)                    a[k] = aux[j++];
            else if (j > hi)                a[k] = aux[i++];
            else if (less(aux[j], aux[i]))  a[k] = aux[j++];
            else                            a[k] = aux[i++];
        }
    }

    // Top-down mergesort
    private void merge_sort(Comparable[] a, int lo, int hi) {
        if (hi <= lo) return;

        int mid = lo + (hi - lo)/2;
        merge_sort(a, lo, mid); // Sort left half.
        merge_sort(a, mid+1, hi); // Sort right half.
        merge(a, lo, mid, hi); // Merge results.
    }

    // Bottom-up mergesort
    private void merge_sort(Comparable[] a) {
        int N = a.length;

        // Do lgN passes of pairwise merges.
        for (int sz = 1; sz < N; sz = sz + sz) { // sz: subarray size
            for (int lo = 0; lo < N - sz; lo += sz + sz) { // lo: subarray index
                merge(a, lo, lo + sz - 1, Math.min(lo + sz + sz - 1, N - 1));
            }
        }
    }

    @Override
    public void sort(Comparable[] a) {
        aux = new Comparable[a.length];

        // merge_sort(a, 0, a.length - 1);
        merge_sort(a);
    }
}
