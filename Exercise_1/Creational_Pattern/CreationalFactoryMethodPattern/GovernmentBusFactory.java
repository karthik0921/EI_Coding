class GovernmentBusFactory extends BusFactory {
    @Override
    public Bus createBus() {
        return new GovernmentBus();
    }
}