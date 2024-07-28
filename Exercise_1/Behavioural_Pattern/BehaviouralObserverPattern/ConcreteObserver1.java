class ConcreteObserver1 implements Observer {
    private String name;

    public ConcreteObserver1(String name) {
        this.name = name;
    }

    @Override
    public void update(String message) {
        System.out.println(name + " received message: " + message);
    }
}