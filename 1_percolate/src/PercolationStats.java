import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {
    private int size;
    private int trials;
    private double[] trialResults;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        size = n;
        this.trials = trials;
        trialResults = new double[trials];
        startSimulations();
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(trialResults);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(trialResults);
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(trials);
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(trials);
    }

    // test client (described below)
    public static void main(String[] args) {

    }

    private void startSimulations() {
        for (int i = 0; i < trials; i++) {
            startSimulation(i);
        }
    }

    private void startSimulation(int trialNumber) {
        Percolation percolation = new Percolation(size);
        int i = 1;
        while (!percolation.percolates()) {
            int row = StdRandom.uniform(1, size + 1);
            int col = StdRandom.uniform(1, size + 1);
            if (!percolation.isFull(row, col)) {
                percolation.open(row, col);
                if (percolation.percolates()) {
                    trialResults[trialNumber] = (double) (i) / (size * size);
                    return;
                }
                i++;
            }
        }
    }
}
