public class ShellSort extends InsertionSort {
    @Override
    public void sort(Comparable[] a) {
        int N = a.length;
        int h = 1;
        while (h < N / 3) h = 3*h + 1; //  1, 4, 13, 40, 121, 364, 1093, ...
        while (h >= 1) {
            gap = h;
            super.sort(a);

            h = h / 3;
        }
    }
}
