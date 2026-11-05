import java.util.ArrayList;
import java.util.Comparator;
public class Race {
    /** Number of cars in the race (at least 3 required) **/
    private static final int NUM_CARS = 3;
    /** Safety stop so cars that block each other can never loop forever **/
    private static final int MAX_ROUNDS = 500;

    /** Track the race is run on (weights must already be calculated) **/
    private final Racetrack track;
    /** Every car in the race **/
    private final ArrayList<Car> cars = new ArrayList<>();

    public Race(Racetrack track) {
        this.track = track;
    }

    /** Create the cars with ids '1','2','3',... and place them at the start **/
    private void setupCars() {
        for (int i = 1; i <= NUM_CARS; i++) {
            Car car = new CPU_CAR ((char) ('0' + i));
            placeAtHighestWeight(car);
            cars.add(car);
        }
        // Cars move in the order they were created
        cars.sort(Comparator.comparingInt(Car::getMoveOrder));
    }

    /**
     * Put a car on the open space with the highest weight (farthest from the finish).
     * Spaces already holding a car show the car's id instead of 'T'/'S', so they are
     * skipped automatically and no two cars start in the same space.
     */
    private void placeAtHighestWeight(Car car) {
        int bestRow = -1;
        int bestCol = -1;
        int bestWeight = -1;

        for (int r = 0; r < track.height(); r++) {
            for (int c = 0; c < track.width(); c++) {
                char symbol = track.getTrack(r, c);
                if ((symbol == 'T' || symbol == 'S') && track.getWeight(r, c) > bestWeight) {
                    bestWeight = track.getWeight(r, c);
                    bestRow = r;
                    bestCol = c;
                }
            }
        }

        if (bestRow == -1) {
            throw new IllegalStateException("Not enough open track spaces to place all cars.");
        }
        car.updateCoordinates(bestRow, bestCol);
        car.setWeightPosition(bestWeight);
        car.setCarMove(track);   // writes the car's id onto the track
    }

    /** Run the race from start to finish **/
    public void run() {
        setupCars();

        System.out.println("\n*****CARS START RACE*****");
        displayTrack();
        displayCarStats();

        for (int round = 1; round <= MAX_ROUNDS; round++) {
            for (Car car : cars) {
                System.out.println("\n*****CAR " + car.getIdNumber() + "'s TURN!*****");
                car.move(track);   // Car.updateCarInfo pauses for the user after a move

                if (car.getWinner()) {
                    System.out.println("CAR " + car.getIdNumber() + " WINS!");
                    displayTrack();
                    displayCarStats();
                    return;   // race over as soon as a car lands on the finish line
                }
            }
            // End of the round: show the track and where everyone stands
            System.out.println("\n----- End of round " + round + " -----");
            displayTrack();
            displayCarStats();
        }
        System.out.println("Race stopped after " + MAX_ROUNDS + " rounds - no car could reach the finish line.");
    }

    /** Print the track: walls 'X', finish 'F', car ids, and blanks for open road **/
    private void displayTrack() {
        for (int r = 0; r < track.height(); r++) {
            for (int c = 0; c < track.width(); c++) {
                char symbol = track.getTrack(r, c);
                System.out.print((symbol == 'T' || symbol == 'S' ? ' ' : symbol) + " ");
            }
            System.out.println();
        }
    }

   // Print every car's stats from lowest weight first
    private void displayCarStats() {
        ArrayList<Car> standings = new ArrayList<>(cars);
        // Lower weight = closer to the finish = better place. Sort is stable, so
        // cars on the same weight keep their move order.
        standings.sort(Comparator.comparingInt(Car::getWeightPosition));

        System.out.println();
        for (int i = 0; i < standings.size(); i++) {
            standings.get(i).displayCarInfo(i + 1);
        }
    }
}



