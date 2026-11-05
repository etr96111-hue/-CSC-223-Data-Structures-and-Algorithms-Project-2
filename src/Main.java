
import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Path;


public class Main {
    public static void display() {
        System.out.println("track1.txt........press 1");
        System.out.println("track2.txt........press 2");
        System.out.println("track3.txt........press 3");
        System.out.println("Select a track:");
        System.out.println("Exit ......4");
    }

    public static void main(String[] args) throws FileNotFoundException {
        Scanner input = new Scanner(System.in);
        boolean running = true;
        String selectedFile = "";
        // CHANGED: removed the three FileInputStream lines (Racetrack opens the file itself)

        // Main loop to display the menu
        while (running) {
            Racetrack.displayBanner();
            display();

            if (!input.hasNextInt()) {
                System.out.println("Invalid input! Please enter a number.\n");
                input.next();
                continue;
            }

            int choice = input.nextInt();

            if (choice == 1) {
                selectedFile = "track1.txt";   // CHANGED
                break; // Exit loop to process the file
            } else if (choice == 2) {
                selectedFile = "track2.txt";   // CHANGED
                break;
            } else if (choice == 3) {
                selectedFile = "track3.txt";   // CHANGED
                break;
            } else if (choice == 4) {
                System.out.println("Exiting program.");
                running = false;
                break; // Exit loop

            } else {
                System.out.println("Invalid choice! Please choose a number between 1 and 4.\n");
            }
        }

        // Only instantiate and run calculations if a file was actually chosen
        if (!selectedFile.isEmpty()) {
            Racetrack race = new Racetrack(selectedFile);   // CHANGED: moved inside the if

            // Check if track initialized correctly before printing
            if (race.height() > 0) {
                race.calculateWeights();
                race.printTrack();
                System.out.println(); // Space buffer
                race.printWeights();
            }
        }

        input.close();
    }
}