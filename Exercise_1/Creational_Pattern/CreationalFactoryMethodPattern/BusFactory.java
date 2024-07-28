abstract class BusFactory {
    public abstract Bus createBus();

    public void bookBusSeat(String passengerName) {
        Bus bus = createBus();
        bus.bookSeat(passengerName);
    }
}