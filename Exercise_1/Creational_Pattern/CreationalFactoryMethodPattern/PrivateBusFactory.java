class PrivateBusFactory extends BusFactory {
    @Override
    public Bus createBus() {
        return new PrivateBus();
    }
}