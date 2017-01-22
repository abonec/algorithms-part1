import java.util.ArrayList;

/**
 * Created by abonec on 21.01.17.
 */
public class Board {
    private enum SwapDirection {LEFT, RIGHT, UP, DOWN}

    ;
    private int[][] blocks;
    private int n;
    private int cachedManhattan = -1;
    private int cachedHamming = -1;

    public Board(int[][] blocks) {
        this.n = blocks.length;
        this.blocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            System.arraycopy(blocks[i], 0, this.blocks[i], 0, n);
        }
    }

    public int dimension() {
        return n;
    }

    public int hamming() {
        if (cachedHamming == -1) {
            cachedHamming = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    if (i == n - 1 && j == n - 1) break;
                    if ((n * i + j + 1) != blocks[i][j]) cachedHamming++;
                }
            }
        }
        return cachedHamming;
    }

    public int manhattan() {
        if (cachedManhattan == -1) {
            cachedManhattan = 0;
            for (int i = 0; i < n; i++) {
                for (int j = 0; j < n; j++) {
                    cachedManhattan += distance(blocks[i][j], i, j);
                }
            }
        }
        return cachedManhattan;
    }

    private int distance(int value, int curLine, int curPosition) {
        if (value == 0) return 0;
        int targetLine = (value - 1) / n;
        int targetPos = value - (targetLine) * n - 1;
        return abs(curLine - targetLine) + abs(curPosition - targetPos);
    }

    public boolean isGoal() {
        return manhattan() == 0;
    }

    public Board twin() {
        int line = 0;
        int pos = 0;

        if (blocks[line][pos] == 0) return spawnSwapped(line, pos + 1, SwapDirection.DOWN);
        if (blocks[line][pos + 1] == 0) return spawnSwapped(line, pos, SwapDirection.DOWN);
        if (blocks[line + 1][pos] == 0) return spawnSwapped(line, pos, SwapDirection.RIGHT);


        return spawnSwapped(line, pos, SwapDirection.RIGHT);

    }

    public boolean equals(Object y) {
        if (y == null) return false;
        if (y == this) return true;
        if (getClass() != y.getClass()) return false;

        Board h = (Board) y;
        if (h.n != n) return false;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] != h.blocks[i][j]) return false;
            }
        }

        return true;
    }

    public Iterable<Board> neighbors() {
        ArrayList<Board> neighbors = new ArrayList<>();
        int[] coord = findZero();
        int line = coord[0];
        int pos = coord[1];

        if (line > 0) neighbors.add(spawnSwapped(line, pos, SwapDirection.UP));
        if (line < (n - 1)) neighbors.add(spawnSwapped(line, pos, SwapDirection.DOWN));
        if (pos > 0) neighbors.add(spawnSwapped(line, pos, SwapDirection.LEFT));
        if (pos < (n - 1)) neighbors.add(spawnSwapped(line, pos, SwapDirection.RIGHT));


        return neighbors;
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", blocks[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    private static int abs(int a) {
        return (a < 0) ? -a : a;
    }

    private int[] findZero() {
        int[] coord = new int[2];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (blocks[i][j] == 0) {
                    coord[0] = i;
                    coord[1] = j;
                    return coord;
                }

            }
        }
        return coord;
    }

    private Board spawnSwapped(int line, int pos, SwapDirection direction) {
        Board swapped = dup();

        switch (direction) {
            case UP:
                swapped.swapTile(line, pos, line - 1, pos);
                break;
            case DOWN:
                swapped.swapTile(line, pos, line + 1, pos);
                break;
            case LEFT:
                swapped.swapTile(line, pos, line, pos - 1);
                break;
            case RIGHT:
                swapped.swapTile(line, pos, line, pos + 1);
                break;
        }

        return swapped;
    }

    private Board dup() {
        int[][] copyBlocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                copyBlocks[i][j] = this.blocks[i][j];
            }
        }
        return new Board(copyBlocks);
    }

    private void swapTile(int zeroLine, int zeroPos, int swapLine, int swapPos) {
        int tmp = blocks[zeroLine][zeroPos];
        blocks[zeroLine][zeroPos] = blocks[swapLine][swapPos];
        blocks[swapLine][swapPos] = tmp;
    }

    public static void main(String[] args) {
        int[][] blocks = {{1, 4, 3}, {0, 5, 2}, {7, 8, 6}};
        Board board = new Board(blocks);
        System.out.println(board.toString());
        for (Board b : board.neighbors()) {
            System.out.println(b.hamming());
            System.out.println(b.toString());
        }
    }

}
