import java.util.Arrays;
import java.util.Random;

public class Main {
    // Define the maximum temperature and cooling rate for the annealing process
    private static double MAX_TEMPERATURE = 10000;
    private static double[] STOP_TEMPERATURE = { 5000, 1000, 500, 250, 100, 10, 0 };
    private static double[] COOLING_RATE = { 0.02, 0.1, 0.5, 0.9 };

    private static int knapsackCapacity = 300;
    private static int[] values = { 68, 64, 47, 55, 72, 53, 81, 60, 72, 80, 62, 42, 48, 47, 68, 51, 48, 68, 83, 55, 48,
            44, 49, 68, 63, 71, 82, 55, 60, 63, 56, 75, 42, 76, 42, 60, 75, 68, 67, 42, 71, 58, 66, 72, 67, 78, 49, 50,
            51 };
    private static int[] weights = { 21, 11, 11, 10, 14, 12, 12, 14, 17, 13, 11, 13, 17, 14, 16, 10, 18, 10, 16, 17, 19,
            12, 12, 16, 16, 13, 17, 12, 16, 13, 21, 11, 11, 10, 14, 12, 12, 14, 17, 13, 11, 13, 17, 14, 16, 10, 18, 10,
            16 };

    // Define the solution state variables
    private static boolean[] bestSolution;
    private static int bestValue;
    // Initialize the random number generator
    private static Random random = new Random();

    public static void main(String[] args) {

        // Start the simulated annealing process
        long[] timeList_temp = new long[STOP_TEMPERATURE.length];
        int[] bestValues_temp = new int[STOP_TEMPERATURE.length];
        for (int i = 0; i < STOP_TEMPERATURE.length; i++) {
            double stop_temp = STOP_TEMPERATURE[i];
            double cool_rat = COOLING_RATE[0];
            long startTime = System.nanoTime();
            bestSolution = simulatedAnnealing(stop_temp, cool_rat);
            bestValue = calculateValue(bestSolution);
            long endTime = System.nanoTime();
            long timeElapsed = endTime - startTime;
            timeList_temp[i] = timeElapsed;
            bestValues_temp[i] = bestValue;
        }
        System.out.println("Best Solution: " + Arrays.toString(bestSolution));
        System.out.println("Best Value: " + bestValue);

        System.out.println(
                "Time elapsed for each stop temperature. Temperatures are tested in descending order. Cooling rate fixed at: "
                        + COOLING_RATE[0]);
        for (int i = 0; i < STOP_TEMPERATURE.length; i++) {
            System.out.println("Best Value:" + bestValues_temp[i] + " | Stop Temperature:" + STOP_TEMPERATURE[i]
                    + " | Time elapsed:" + timeList_temp[i]);
        }

        long[] timeList_cool = new long[COOLING_RATE.length];
        int[] bestValues_cool = new int[COOLING_RATE.length];
        for (int i = 0; i < COOLING_RATE.length; i++) {
            double stop_temp = STOP_TEMPERATURE[0];
            double cool_rat = COOLING_RATE[i];
            long startTime = System.nanoTime();
            bestSolution = simulatedAnnealing(stop_temp, cool_rat);
            bestValue = calculateValue(bestSolution);
            long endTime = System.nanoTime();
            long timeElapsed = endTime - startTime;
            timeList_cool[i] = timeElapsed;
            bestValues_cool[i] = bestValue;
        }

        System.out.println("Best Solution: " + Arrays.toString(bestSolution));
        System.out.println("Best Value: " + bestValue);
        System.out.println(
                "Time elapsed for each cooling reate. Cooling rates are tested in ascending order. Stopping temperature fixed at: "
                        + STOP_TEMPERATURE[0]);
        int j = 0;
        for (int i = 0; i < COOLING_RATE.length; i++) {
            System.out.println("Best Value:" + bestValues_temp[i] + " | Cooling Rate:" + COOLING_RATE[i]
                    + " | Time elapsed:" + timeList_cool[i]);
        }

        /*
         * long startTime = System.nanoTime();
         * bestSolution = simulatedAnnealing();
         * bestValue = calculateValue(bestSolution);
         * long endTime = System.nanoTime();
         * long timeElapsed = endTime - startTime;
         * 
         * // Print the best solution found
         * System.out.println("Best Solution: " + Arrays.toString(bestSolution));
         * System.out.println("Best Value: " + bestValue);
         * System.out.println("time elapsed:" + timeElapsed);
         */
    }

    private static boolean[] simulatedAnnealing(double stop_temp, double cool_rat) {

        bestSolution = generateRandomSolution();
        double temperature = MAX_TEMPERATURE;

        while (temperature > stop_temp) {
            int bestValue = calculateValue(bestSolution);
            boolean[] currentSolution = generateNeighbour(bestSolution);
            int currentValue = calculateValue(currentSolution);

            if (calculateAcceptanceProbability(bestValue, currentValue, temperature) >= random.nextDouble()) {
                bestSolution = currentSolution.clone();
            } else {
                bestSolution = bestSolution.clone();
            }
            temperature = temperature * cool_rat;
            if (temperature < stop_temp) {
                break;
            }
        }
        return bestSolution;
    }

    public static boolean[] generateRandomSolution() {
        boolean[] randomSolution = new boolean[values.length];

        for (int i = 0; i < values.length; i++) {
            randomSolution[i] = random.nextBoolean();
        }
        return randomSolution;
    }

    // generate neighbour
    public static boolean[] generateNeighbour(boolean[] solution) {
        boolean[] neighborSolution = solution.clone();
        while (true) {
            int index = random.nextInt(values.length);
            neighborSolution[index] = random.nextBoolean();

            if (calculateValue(neighborSolution) != 0) {
                break;
            }
        }
        return neighborSolution;
    }

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

    private static double calculateAcceptanceProbability(int currentValue, int neighborValue, double temperature) {
        if (neighborValue > currentValue) {
            return 1;
        } else {
            return Math.exp((neighborValue - currentValue) / temperature);
        }
    }
}
