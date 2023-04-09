package Logistique;

import java.util.ArrayList;

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
        this.service = service;
    }

    public String getIdName() {
        return idName;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getReadyTime() {
        return readyTime;
    }

    public int getDueTime() {
        return dueTime;
    }

    public int getDemand() {
        return demand;
    }

    public int getService() {
        return service;
    }
    public boolean isFeasible(double arrivalTime) {
        return arrivalTime >= readyTime && arrivalTime <= dueTime;
    }

    //Permet de calculer la distance total entre les clients et les dépots


}
