import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Racetrack {
    /** Racetrack from text file **/
    private char[][] track;
    /** Weights of the track position **/
    private int[][] weights;

    /** Constant Value for finish line weight **/
    final private int FINISH_WEIGHT = 0;
    /** Constant Value for wall weight **/
    final private int WALL_WEIGHT = 9999;
    /** Constant Value for pre initialized track weight **/
    final private int INIT_TRACK_WEIGHT = -1;

    public Racetrack(String filename) {
        ArrayList<String> lines = new ArrayList<>();
        try {
            Scanner fileScanner = new Scanner(new File(filename));
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (!line.isEmpty()) {
                    lines.add(line);
                }
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error: File " + filename + " not found.");
            // Initializing multiple empty arrays so the methods don't crash
            this.track = new char[0][0];
            this.weights = new int[0][0];
            return;
        }

        // Determines the dimensions
        int rows = lines.size();
        int cols = lines.get(0).length();

        // Initializes the structures
        this.track = new char[rows][cols];
        this.weights = new int[rows][cols];

        //  track layout and set up for initial weights
        for (int r = 0; r < rows; r++) {
            String currentLine = lines.get(r);
            for (int c = 0; c < cols; c++) {
                char symbol = currentLine.charAt(c);
                setTrack(r, c, symbol);

                // Initial weights being assigned
                if (symbol == 'X') {
                    setWeight(r, c, WALL_WEIGHT);
                } else if (symbol == 'F') {
                    setWeight(r, c, FINISH_WEIGHT);
                } else {
                    setWeight(r, c, INIT_TRACK_WEIGHT);
                }
            }
        }
    }

    /*
     * Calculaing the weight
     */
    public void calculateWeights() {
        boolean changed = true;
        int currentWeight = 0;
        while (changed) {
            changed = false;
            for (int r = 0; r < height(); r++) {
                for (int c = 0; c < width(); c++) {
                    // Find cells matching the active distance step
                    if (getWeight(r, c) == currentWeight) {
                        // Checking 4 directional cardinal neighbors
                        changed |= checkAndSetNeighbor(r - 1, c, currentWeight + 1); // Up
                        changed |= checkAndSetNeighbor(r + 1, c, currentWeight + 1); // Down
                        changed |= checkAndSetNeighbor(r, c - 1, currentWeight + 1); // Left
                        changed |= checkAndSetNeighbor(r, c + 1, currentWeight + 1); // Right
                    }
                }
            }
            currentWeight++;
        }
    }

    //  Helper validation method to securely check bounds and set unvisited tracks

    private boolean checkAndSetNeighbor(int r, int c, int nextWeight) {
        if (r >= 0 && r < height() && c >= 0 && c < width()) {
            // Updating to check if neighbor is a track path 'T' or a start 'S'
            if ((getTrack(r, c) == 'T' || getTrack(r, c) == 'S') && getWeight(r, c) == INIT_TRACK_WEIGHT) {
                setWeight(r, c, nextWeight);
                return true;
            }
        }
        return false;
    }


    //Formats and prints out the calculated weights grid
    public void printWeights() {
        System.out.println("----------------TRACK WEIGHTS----------------");
        for (int r = 0; r < height(); r++) {
            for (int c = 0; c < width(); c++) {
                int w = getWeight(r, c);
                if (w == WALL_WEIGHT) {
                    System.out.print("X\t");
                } else {
                    System.out.print(w + "\t");
                }
            }
            System.out.println();
        }
    }

    /**
     * Formating the displays that create a visual chartcer map layout
     */
    public void printTrack() {
        System.out.println("----------------TRACK MAP----------------");
        for (int r = 0; r < height(); r++) {
            for (int c = 0; c < width(); c++) {
                System.out.print(getTrack(r, c) + " ");
            }
            System.out.println();
        }
        System.out.println("Track Dimensions: " + height() + "x" + width());
    }

    public char getTrack(int row, int col) { return track[row][col]; }
    public void setTrack(int row, int col, char value) { track[row][col] = value; }
    public int getWeight(int row, int col) { return weights[row][col]; }
    public void setWeight(int row, int col, int value) { weights[row][col] = value; }
    public int height() { return track.length; }
    public int width() { return track.length > 0 ? track[0].length : 0; }

    public static void displayBanner(){
        String art = 	"   ______     _     __   ____                           \n" +
                "  / ____/____(_)___/ /  / __ \\____ _________  __________\n" +
                " / / __/ ___/ / __  /  / /_/ / __ `/ ___/ _ \\/ ___/ ___/\n" +
                "/ /_/ / /  / / /_/ /  / _, _/ /_/ / /__/  __/ /  (__  ) \n" +
                "\\____/_/  /_/\\__,_/  /_/ |_|\\__,_/\\___/\\___/_/  /____/  \n" +
                "                                                        ";

        System.out.println(art);
    }
}