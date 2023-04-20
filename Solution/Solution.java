package Solution;

import Logistique.Route;
import Logistique.Transport;

import java.util.ArrayList;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

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
            //On ajoute la distance entre le dernier client et le dépot
            totalDistance += sqrt(pow(route.getRoute().get(route.getRoute().size()-1).getX() -route.getRoute().get(0).getX(), 2) + pow(route.getRoute().get(route.getRoute().size()-1).getY() -route.getRoute().get(0).getY(), 2));

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

