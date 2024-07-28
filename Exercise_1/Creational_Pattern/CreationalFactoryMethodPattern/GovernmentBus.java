class GovernmentBus implements Bus {
    @Override
    public void bookSeat(String passengerName) {
        System.out.println("Seat booked for " + passengerName + " on a Government Bus.");
    }
}