package com.iztech.ringtracker.bus;

public class BusLocationUpdateEvent {

        private final Bus bus;
        private final Location newLocation;

        public BusLocationUpdateEvent(Bus bus, Location newLocation) {
            this.bus = bus;
            this.newLocation = newLocation;
        }

        // Getters
        public Bus getBus() {
            return bus;
        }

        public Location getNewLocation() {
            return newLocation;
        }

}
