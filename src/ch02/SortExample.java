public class SortExample
{
    public static void main(String[] args)
    {
        String[] a = In.readStrings();
        //Sort sort = new SelectionSort();
        //Sort sort = new InsertionSort();
        //Sort sort = new ShellSort();
        Sort sort = new MergeSort();
        sort.sort(a);
        assert sort.isSorted(a);
        sort.show(a);
    }
}
