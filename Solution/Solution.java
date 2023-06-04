package Solution;

import Logistique.InstanceVRP;
import Logistique.Route;
import Logistique.Transport;

import java.util.ArrayList;


public class Solution {

    private ArrayList<Route> routes;
    private ArrayList<Transport> transports;
    private double distanceSolution ;
    protected InstanceVRP instanceVRP;


    public Solution() {
        this.routes = new ArrayList<Route>();
        this.transports = new ArrayList<>();
    }

    public Solution(InstanceVRP instance) {
        this.routes = new ArrayList<Route>();
        this.transports = new ArrayList<>();
        this.instanceVRP = instance;
    }

    public ArrayList<Route> getRoutes() {
        return this.routes;
    }
    public Route getRouteIndex(int index) {
        Route route = (Route) this.routes.toArray()[index];
        return route;
    }

    public InstanceVRP getInstanceVRP() {
        return this.instanceVRP;
    }

    public double getTotalDistance() {
        double totalDistance = 0.0;
        for (Route route : routes) {
            totalDistance += route.calculateDistance();
        }
        this.distanceSolution = totalDistance;
        return distanceSolution;
    }

    //Modifie la liste des routes d'une solution
    public void setRoutes(ArrayList routes) {
        this.routes = routes;
        this.distanceSolution = this.getTotalDistance();
    }

    public Solution cloneSolution() {
        Solution clonedSolution = new Solution();

        // Copier les propriétés de l'autre solution
        clonedSolution.setRoutes(new ArrayList<>(this.getRoutes()));
        // Assurez-vous de copier en profondeur les objets imbriqués si nécessaire

        return clonedSolution;
    }

    public void addRoute(Route route) {
        this.routes.add(route);
    }

    public void addTransport(Transport transport) {
        this.transports.add(transport);
    }
}

