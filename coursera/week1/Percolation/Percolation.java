import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final WeightedQuickUnionUF uf;
    private int openSitesCnt = 0;
    private enum GRIDSTATE { BLOCKED, OPEN, FULL };
    private GRIDSTATE[][] grid;
    private boolean isPercolated = false;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n should be larger than zero");
        }

        grid = new GRIDSTATE[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                grid[i][j] = GRIDSTATE.BLOCKED;
            }
        }

        uf = new WeightedQuickUnionUF(n * n + 1);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row <= 0 || row > grid.length || col <= 0 || col > grid.length) {
            throw new IllegalArgumentException("the range of row and col should be [1, n]");
        }

        if (grid[row - 1][col - 1] == GRIDSTATE.BLOCKED) {
            grid[row - 1][col - 1] = (row == 1) ? GRIDSTATE.FULL : GRIDSTATE.OPEN;
            openSitesCnt++;
            unionNearbyOpenSites(row, col);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row <= 0 || row > grid.length || col <= 0 || col > grid.length) {
            throw new IllegalArgumentException("the range of row and col should be [1, n]");
        }

        return grid[row - 1][col - 1] != GRIDSTATE.BLOCKED;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row <= 0 || row > grid.length || col <= 0 || col > grid.length) {
            throw new IllegalArgumentException("the range of row and col should be [1, n]");
        }
        
        if (grid[row - 1][col - 1] == GRIDSTATE.OPEN) {
            int root = uf.find(getUFIdx(row, col));
            int rootRow = getRowByIdx(root);
            int rootCol = getColByIdx(root);
            if (grid[rootRow - 1][rootCol - 1] == GRIDSTATE.FULL) {
                grid[row - 1][col - 1] = GRIDSTATE.FULL;
            }
        }

        return grid[row - 1][col - 1] == GRIDSTATE.FULL;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSitesCnt;
    }

    // does the system percolates?
    public boolean percolates() {
        for (int col = 1; col <= grid.length && !isPercolated; col++) {
            if (isFull(grid.length, col)) {
                isPercolated = true;
            }
        }

        return isPercolated;
    }

    private int getUFIdx(int row, int col) {
        return (row - 1) * grid.length + col;
    }

    private void setSiteFull(int row, int col) {
        grid[row - 1][col - 1] = GRIDSTATE.FULL;
    }

    private int getRowByIdx(int idx) {
        return idx / grid.length + (idx % grid.length == 0 ? 0 : 1);
    }

    private int getColByIdx(int idx) {
        int remainder = idx % grid.length;
        return  remainder == 0 ? grid.length : remainder;
    }

    private void unionNearbyOpenSites(int row, int col) {
        final int IDX = getUFIdx(row, col);
        int[][] nearbySites = {
            { row - 1, col }, // upper site
            { row, col - 1 }, // left site
            { row, col + 1 }, // right site
            { row + 1, col }  // down site
        };
        boolean isSiteFull = isFull(row, col);
        
        for (int i = 0; i < nearbySites.length; i++) {
            int nearbyRow = nearbySites[i][0];
            int nearbyCol = nearbySites[i][1];
            if (nearbyRow <= 0          ||
                nearbyRow > grid.length ||
                nearbyCol <= 0          ||
                nearbyCol > grid.length ||
                !isOpen(nearbyRow, nearbyCol)) {
                continue;
            }

            if (isSiteFull) {
                setSiteFull(nearbyRow, nearbyCol);
            } else {
                if (isFull(nearbyRow, nearbyCol)) {
                    setSiteFull(row, col);
                    isSiteFull = true;
                }
            }

            if (!isPercolated && isSiteFull && (row == grid.length || nearbyRow == grid.length)) {
                isPercolated = true;
            }

            uf.union(IDX, getUFIdx(nearbyRow, nearbyCol));
        }

        if (isSiteFull) {
            int root = uf.find(IDX);
            int rootRow = getRowByIdx(root);
            int rootCol = getColByIdx(root);
            setSiteFull(rootRow, rootCol);
            if (!isPercolated && (row == grid.length || rootRow == grid.length)) {
                isPercolated = true;
            }
        }
    }
}
