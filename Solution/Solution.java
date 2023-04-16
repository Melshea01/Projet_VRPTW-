package Solution;

import Logistique.Route;
import Logistique.Transport;

import java.util.ArrayList;

public class Solution {

    private ArrayList<Route> routes;
    private ArrayList<Transport> transports;

    public Solution() {
        this.routes = new ArrayList<Route>();
        this.transports = new ArrayList<>();
    }

    public void addRoute(Route route) {
        this.routes.add(route);
    }

    public void addTransport(Transport transport) {
        this.transports.add(transport);
    }

    public ArrayList<Route> getRoutes() {
        return this.routes;
    }

    public double getTotalDistance() {
        double totalDistance = 0.0;
        for (Route route : this.routes) {
            totalDistance += route.getDistance();
        }
        return totalDistance;
    }

    public Route getRandomRoute() {
        int index = (int) (Math.random() * routes.size());
        return routes.get(index);
    }

    public void removeRoute(Route route) {
        routes.remove(route);
        //Update les numéros des autres routes
    }

}

