import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

import javax.swing.text.AbstractDocument.BranchElement;

import java.util.Comparator;

public class Main {
    // Define the maximum temperature and cooling rate for the annealing process
    private static final double MAX_TEMPERATURE = 10000;
    private static final double COOLING_RATE = 0.03;
    private static int knapsackCapacity = 300;
    private static int NUM_ITER = 300;
    private static int[] values = { 68, 64, 47, 55, 72, 53, 81, 60, 72, 80, 62, 42, 48, 47, 68, 51, 48, 68, 83, 55, 48,
            44, 49, 68, 63, 71, 82, 55, 60, 63, 56, 75, 42, 76, 42, 60, 75, 68, 67, 42, 71, 58, 66, 72, 67, 78, 49, 50,
            51 };
    private static int[] weights = { 21, 11, 11, 10, 14, 12, 12, 14, 17, 13, 11, 13, 17, 14, 16, 10, 18, 10, 16, 17, 19,
            12, 12, 16, 16, 13, 17, 12, 16, 13, 21, 11, 11, 10, 14, 12, 12, 14, 17, 13, 11, 13, 17, 14, 16, 10, 18, 10,
            16 };

    // Define the solution state variables
    private static boolean[] currentSolution;
    private static boolean[] bestSolution;
    private static int currentValue;
    private static int bestValue;
    // Initialize the random number generator
    private static Random random = new Random();

    public static void main(String[] args) {
        // Initialize the solution state variables
        currentSolution = new boolean[values.length];
        bestSolution = new boolean[values.length];
        currentValue = 0;
        bestValue = 0;

        // Start the simulated annealing process
        bestSolution = simulatedAnnealing();
        bestValue = calculateValue(bestSolution);

        // Print the best solution found
        System.out.println("Best Solution: " + Arrays.toString(bestSolution));
        System.out.println("Best Value: " + bestValue);
    }

    private static boolean[] simulatedAnnealing() {

        bestSolution = generateRandomSolution();
        double temperature = MAX_TEMPERATURE;

        for (int i = 0; i < NUM_ITER; i++) {
            int bestValue = calculateValue(bestSolution);
            boolean[] newSolution = generateNeighbour(bestSolution);
            int newValue = calculateValue(newSolution);

            if (calculateAcceptanceProbability(bestValue, newValue, temperature) >= random.nextDouble()) {
                bestSolution = newSolution.clone();
            } else {
                bestSolution = bestSolution.clone();
            }
            temperature = temperature * COOLING_RATE;

        }
        return bestSolution;
    }

    // generate the initial random solution in regards to constraints
    public static boolean[] generateRandomSolution() {
        boolean[] randomSolution = new boolean[values.length];

        for (int i = 0; i < values.length; i++) {
            randomSolution[i] = random.nextBoolean();
        }
        return randomSolution;
    }

    // generate neighbour
    public static boolean[] generateNeighbour(boolean[] solution){
        boolean[] neighborSolution = solution.clone()
        int weight = 0;
        int index = random.nextInt(values.length);
        neighborSolution[index]= random.nextBoolean();

        return neighborSolution;
    }

    // Helper method to calculate the fitness value of a solution
    private static int calculateValue(boolean[] solution) {
        int value = 0;
        int weight = 0;
        for (int i = 0; i < solution.length; i++) {
            if (solution[i]) {
                value += values[i];
                weight += weights[i];
            }
        }
        if (weight > knapsackCapacity) {
            return 0;
        } else {
            return value;
        }
    }

    // Helper method to calculate the acceptance probability of a neighbor solution
    private static double calculateAcceptanceProbability(int currentValue, int neighborValue, double temperature) {
        if (neighborValue > currentValue) {
            return 1;
        } else {
            return Math.exp((neighborValue - currentValue) / temperature);
        }
    }
}
