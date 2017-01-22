import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {
    private SearchNode root;
    private SearchNode goal;
    private MinPQ<SearchNode> pq;
    private MinPQ<SearchNode> twinPq;
    private boolean solvable = false;

    private class SearchNode implements Comparable<SearchNode> {
        private Board board;
        private int moves;
        private SearchNode parent;
        private int cachedScore = -1;

        public SearchNode(Board board, int moves, SearchNode parent) {
            this.board = board;
            this.moves = moves;
            this.parent = parent;
        }

        @Override
        public int compareTo(SearchNode o) {
            if (o.score() < score()) return 1;
            if (o.score() > score()) return -1;
            return 0;
        }

        private int score() {
            if (cachedScore == -1) {
                cachedScore = board.manhattan() + moves;
            }
            return cachedScore;
        }

        private boolean isGoal() {
            return board.isGoal();
        }

        private void print() {
            System.out.print("manhattan: ");
            System.out.println(board.manhattan());
            System.out.print("score: ");
            System.out.println(score());
            System.out.print(board.toString());
        }
    }

    private void solve(SearchNode goal) {
        this.goal = goal;
        this.solvable = true;
    }

    public Solver(Board initial) {
        if (initial == null) throw new NullPointerException();
        this.root = new SearchNode(initial, 0, null);
        this.pq = new MinPQ<>();
        this.twinPq = new MinPQ<>();

        pq.insert(root);
        twinPq.insert(new SearchNode(initial.twin(), 0, null));


        while (!solvable) {
            SearchNode node = pq.delMin();
            SearchNode twinNode = twinPq.delMin();
//            node.print();
            if (node.isGoal()) solve(node);
            if (twinNode.isGoal()) break;
            addNeighbors(pq, node);
            addNeighbors(twinPq, twinNode);
        }

    }

    private void addNeighbors(MinPQ<SearchNode> queue, SearchNode node) {
        for (Board neighbor : node.board.neighbors()) {
            SearchNode parent = node.parent;
            boolean dub = false;
            while (parent != null) {
                if (parent.board.equals(neighbor)) {
                    dub = true;
                    break;
                }
                parent = parent.parent;
            }
            if(dub) continue;
            queue.insert(new SearchNode(neighbor, node.moves + 1, node));
        }
    }

    public boolean isSolvable() {
        return solvable;
    }

    public int moves() {
        return goal != null ? goal.moves : -1;
    }

    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        SearchNode curr = goal;
        Stack<Board> solution = new Stack<>();
        while (curr != null) {
            solution.push(curr.board);
            curr = curr.parent;
        }

        return solution;
    }

    public static void main(String[] args) {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
//        int[][] blocks = {{0, 3, 1}, {4, 2, 5}, {7, 8, 6}};
//        int[][] blocks = {{7,5,4}, {8,2,3}, {0,6,1}};

//        Board initial = new Board(blocks);
        System.out.println(initial.toString());
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }

    } // solve a slider puzzle (given below)
}
