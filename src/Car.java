import java.util.Scanner;

abstract public class Car {

    /** Car ID **/
    private final char idNumber;
    /** row position **/
    private int row;
    /** column position **/
    private int col;
    /** Max distance a car can travel vertically **/
    private int rowVelocity;
    /** Max distance a car can travel horizontally **/
    private int colVelocity;
    /** Max distance a car can travel any direction **/
    private int maxSpeed;
    /** Determines if car reached finish line **/
    private boolean isWinner;
    /** Track weight of cars current position **/
    private int weightPosition;
    /** Number determining when it is a cars turn to move **/
    private int moveOrder;
    /** Static member used for order initialization **/
    public static int order;

    /** Constructor */
    Car(char id){
        idNumber = id;
        maxSpeed = 5;
        rowVelocity = 1;
        colVelocity = 1;
        isWinner = false;
        row = 0;
        col = 0;
        weightPosition = 0;
        order++;
        moveOrder = order;
    }

    /** Move car on Racetrack **/
    abstract public void move(Racetrack track);

    /** Display stats for car */
    public void displayCarInfo(int place) {
        System.out.print( "Place: " + place + " Car ID: " + idNumber + " Coordinates: " + row + "," + col);
        System.out.println( " Max Speed: " + maxSpeed + " Velocity: " + rowVelocity + "," + colVelocity);
    }

    /** Set or move car to position on the track **/
    public void setCarMove(Racetrack track) {
        track.setTrack(this.getRow(),this.getCol(), this.getIdNumber());
    }

    /** Update cars current position **/
    public void updateCoordinates(int row, int col) {
        this.setRow(row);
        this.setCol(col);
    }

    /** Update cars current velocities **/
    public void updateVelocity(int row, int col) {
        this.setRowVelocity(Math.min(row, maxSpeed));
        this.setColVelocity(Math.min(col, maxSpeed));
    }

    /** Wrapper Method updating cars current stats and position
     ** pre - requires the racetrack and new row/col position of car
     ** post - updates car information and moves car to new row/col position
     **/
    public void updateCarInfo(Racetrack track, int newRow, int newCol) {

        int rowVelocity = Math.abs(this.getRow() - newRow);
        int colVelocity = Math.abs(this.getCol() - newCol);

        track.setTrack(this.getRow(), this.getCol(),'T');
        this.updateCoordinates(newRow,newCol);
        this.setWeightPosition(track.getWeight(newRow,newCol));
        this.updateVelocity(this.getRowVelocity() + rowVelocity,this.getColVelocity() + colVelocity);
        this.setCarMove(track);

        /***************************************************************
         /* Pause race after each move.  The code below is needed only  *
         /* for CPU cars in Program 2.  You may remove it for Program 3 *
         /***************************************************************/
        System.out.print("Press any key and enter to continue: ");
        new Scanner(System.in).next();
    }

    /** Get / Set Methods **/
    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) { this.maxSpeed = maxSpeed; }

    public int getRowVelocity() {
        return rowVelocity;
    }

    public void setRowVelocity(int rowVelocity) {
        this.rowVelocity = rowVelocity;
    }

    public int getColVelocity() {
        return colVelocity;
    }

    public void setColVelocity(int colVelocity) {
        this.colVelocity = colVelocity;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    public char getIdNumber() {
        return idNumber;
    }

    public boolean getWinner() {
        return isWinner;
    }

    public void setWinner(boolean winner) {
        isWinner = winner;
    }

    public int getWeightPosition() {
        return weightPosition;
    }

    public void setWeightPosition(int weightPosition) {
        this.weightPosition = weightPosition;
    }

    public int getMoveOrder() { return moveOrder; }

    public void setMoveOrder(int moveOrder) { this.moveOrder = moveOrder; }
}