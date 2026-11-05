



    public  class CPU_CAR extends Car {

        /** Constructor - Car's constructor assigns the id, velocities and move order **/
        public CPU_CAR(char id) {
            super(id);
        }

        /**
         * Move this car one turn.
         * track has had calculateWeights() run and this car has been placed on it
         * car sits on the lowest weight open space in range (or stays put if
         *  nothing in range is better than where it already is); winner flag is
         *    set if the car landed on the finish line
         */
        @Override
        public void move(Racetrack track) {
            int bestRow = -1;
            int bestCol = -1;
            int bestWeight = Integer.MAX_VALUE;

            // Search the rectangle of spaces this car can reach with its velocities
            for (int r = getRow() - getRowVelocity(); r <= getRow() + getRowVelocity(); r++) {
                for (int c = getCol() - getColVelocity(); c <= getCol() + getColVelocity(); c++) {

                    if (!isOpenSpace(track, r, c)) {
                        continue;
                    }
                    int w = track.getWeight(r, c);
                    if (w < bestWeight) {   // strictly less: first lowest space found wins ties
                        bestWeight = w;
                        bestRow = r;
                        bestCol = c;
                    }
                }
            }

            // Only move if it actually gets the car closer to the finish line.
            // (If blocked by other cars, the car waits a turn instead of backing up.)
            if (bestRow != -1 && bestWeight < getWeightPosition()) {
                // updateCarInfo clears the old space, updates position/velocity and redraws the car
                updateCarInfo(track, bestRow, bestCol);

                // Weight 0 is the finish line
                if (bestWeight == 0) {
                    setWinner(true);
                }
            }
        }

        /**
         * A space is open if it is on the track, is a road/start/finish space
         * (not a wall 'X' and not another car's id) and was reached by calculateWeights.
         */
        private boolean isOpenSpace(Racetrack track, int r, int c) {
            if (r < 0 || r >= track.height() || c < 0 || c >= track.width()) {
                return false;
            }
            char symbol = track.getTrack(r, c);
            boolean drivable = (symbol == 'T' || symbol == 'S' || symbol == 'F');
            return drivable && track.getWeight(r, c) >= 0;
        }
    }





