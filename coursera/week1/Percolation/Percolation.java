import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid;
    private final WeightedQuickUnionUF uf;
    private int openSitesCnt = 0;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n should be larger than zero");
        }

        grid = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = false; // blocked
            }
        }

        uf = new WeightedQuickUnionUF(n * n + 1);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row <= 0 || row > grid.length || col <= 0 || col > grid.length) {
            throw new IllegalArgumentException("the range of row and col should be [1, n]");
        }

        if (!grid[row - 1][col - 1]) {
            grid[row - 1][col - 1] = true;
            openSitesCnt++;
            unionNearbyOpenSites(row, col);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row <= 0 || row > grid.length || col <= 0 || col > grid.length) {
            throw new IllegalArgumentException("the range of row and col should be [1, n]");
        }

        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row <= 0 || row > grid.length || col <= 0 || col > grid.length) {
            throw new IllegalArgumentException("the range of row and col should be [1, n]");
        }

        if (isOpen(row, col)) {
            if (row == 1) {
                return true;
            }

            int findRet = uf.find(getUFIdx(row, col));
            for (int i = 1; i <= grid.length; i++) {
                if (isOpen(1, i)) {
                    if (uf.find(i) == findRet) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSitesCnt;
    }

    // does the system perlocates?
    public boolean percolates() {
        for (int col = 1; col <= grid.length; col++) {
            if (isFull(grid.length, col)) {
                return true;
            }
        }

        return false;
    }

    private int getUFIdx(int row, int col) {
        return (row - 1) * grid.length + col;
    }

    private void unionNearbyOpenSites(int row, int col) {
        final int IDX = getUFIdx(row, col);
        int[][] nearbySites = new int[4][2];
        // upper site
        nearbySites[0][0] = row - 1;
        nearbySites[0][1] = col;
        // left site
        nearbySites[1][0] = row;
        nearbySites[1][1] = col - 1;
        // right site
        nearbySites[2][0] = row;
        nearbySites[2][1] = col + 1;
        // down site
        nearbySites[3][0] = row + 1;
        nearbySites[3][1] = col;

        for (int i = 0; i < nearbySites.length; i++) {
            int nearbyRow = nearbySites[i][0];
            int nearbyCol = nearbySites[i][1];
            if (nearbyRow > 0 && nearbyRow <= grid.length && nearbyCol > 0 && nearbyCol <= grid.length) {
                if (isOpen(nearbyRow, nearbyCol)) {
                    uf.union(IDX, getUFIdx(nearbyRow, nearbyCol));
                }
            }
        }
    }
}
