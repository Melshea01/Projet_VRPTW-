package Logistique;

public class Client {
    private String idName;
    private int x;
    private int y;
    private int readyTime;
    private int dueTime;
    private int demand;
    private int service;

    public Client(String idName, int x, int y, int readyTime, int dueTime, int demand, int service) {
        this.idName = idName;
        this.x = x;
        this.y = y;
        this.readyTime = readyTime;
        this.dueTime = dueTime;
        this.demand = demand;
        //Temps de livraison
        this.service = service;
    }

    public String getIdName() {
        return this.idName;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getReadyTime() {
        return this.readyTime;
    }

    public int getDueTime() {
        return this.dueTime;
    }

    public int getDemand() {
        return this.demand;
    }

    public int getService() {
        return this.service;
    }

    public boolean isFeasible(double arrivalTime) {
        return arrivalTime >= this.readyTime && arrivalTime <= this.dueTime;
    }
}
