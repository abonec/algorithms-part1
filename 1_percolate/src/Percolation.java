import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] system;
    private int size;
    private int internalSize;
    private int top;
    private int bottom;
    private WeightedQuickUnionUF unionFull;
    private WeightedQuickUnionUF unionTop;

    // create n-by-n grid, with all sites blocked
    public Percolation(int n) {
        if (n < 1) {
            throw new IllegalArgumentException();
        }
        size = n;
        internalSize = n - 1;
        system = new boolean[n][n];
        unionFull = new WeightedQuickUnionUF(n * n + 2);
        unionTop = new WeightedQuickUnionUF(n * n + 1);

        top = size * size;
        bottom = size * size + 1;
    }

    // open site (row, col) if it is not open already
    public void open(int row, int col) {
        checkBounds(row, col);

        int internalRow = row - 1;
        int internalCol = col - 1;

        if (system[internalRow][internalCol]) {
            return;
        }
        system[internalRow][internalCol] = true;

        if (internalRow == 0) {
            unionFull.union(top, translate(internalRow, internalCol));
            unionTop.union(top, translate(internalRow, internalCol));
        }

        if (internalRow == internalSize) {
            unionFull.union(bottom, translate(internalRow, internalCol));
        }

        int translatedPosition = translate(internalRow, internalCol);
        if (internalRow > 0) {
            if (system[internalRow - 1][internalCol]) {
                unionFull.union(translate(internalRow - 1, internalCol), translatedPosition);
                unionTop.union(translate(internalRow - 1, internalCol), translatedPosition);
            }
        }
        if (internalRow < internalSize) {
            if (system[internalRow + 1][internalCol]) {
                unionFull.union(translate(internalRow + 1, internalCol), translatedPosition);
                unionTop.union(translate(internalRow + 1, internalCol), translatedPosition);
            }
        }
        if (internalCol > 0) {
            if (system[internalRow][internalCol - 1]) {
                unionFull.union(translate(internalRow, internalCol - 1), translatedPosition);
                unionTop.union(translate(internalRow, internalCol - 1), translatedPosition);
            }
        }
        if (internalCol < internalSize) {
            if (system[internalRow][internalCol + 1]) {
                unionFull.union(translate(internalRow, internalCol + 1), translatedPosition);
                unionTop.union(translate(internalRow, internalCol + 1), translatedPosition);
            }
        }
    }

    // is site (row, col) open?
    public boolean isOpen(int row, int col) {
        checkBounds(row, col);

        int internalRow = row - 1;
        int internalCol = col - 1;

        return system[internalRow][internalCol];
    }

    // is site (row, col) full?
    public boolean isFull(int row, int col) {
        checkBounds(row, col);

        int internalRow = row - 1;
        int internalCol = col - 1;

        return system[internalRow][internalCol] && unionTop.connected(top, translate(internalRow, internalCol));

    }

    // does the system percolate?
    public boolean percolates() {
        return unionFull.connected(top, bottom);
    }

    private void checkBounds(int row, int col) {
        if (row > size || col > size || row < 1 || col < 1) {
            throw new IndexOutOfBoundsException();
        }
    }

    private int translate(int row, int col) {
        return (size * row + col);
    }

    // test client (optional)
    public static void main(String[] args) {
        System.out.println("Hello world");
    }
}
