package Solution;

import Logistique.Client;
import Logistique.InstanceVRP;
import Logistique.Route;
import Logistique.Transport;
import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Solution {

    private ArrayList<Route> routes;
    private ArrayList<Transport> transports;

    private double distanceSolution ;

    public InstanceVRP instanceVRP;

    private int nbClients;

    public Solution() {
        this.routes = new ArrayList<Route>();
        this.transports = new ArrayList<>();
    }

    public Solution(InstanceVRP instance) {
        this.routes = new ArrayList<Route>();
        this.transports = new ArrayList<>();
        this.instanceVRP = instance;
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
    public Route getRouteIndex(int index) {
        Route route = (Route) this.routes.toArray()[index];
        return route;
    }

    public int getIndexOfRoute(Route searchRoute) {
        int index = 0;
        for (int i = 0 ; i < this.getRoutes().size()-1 ; i++) {
            Route route = this.getRoutes().get(i);
            if(searchRoute.routesAreEquals(route)) {
                index = i;
            }
        }
        return index;
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

    public int getNbClients() {
        for (Route route : this.routes) {
            this.nbClients += route.getListClient().size()-2;
        }
        return this.nbClients;
    }

    //Modifie la liste des routes d'une solution
    public void setRoutes(ArrayList routes) {
        this.routes = routes;
        this.distanceSolution = this.getTotalDistance();
    }

    //Utiliser l'id
    public Solution replaceRoute(Route newRoute, int indice) {
        //On récupère les routes de la soltuion
        ArrayList<Route> routes = getRoutes();
        //On l'a change dans cette solution
        routes.set(indice,newRoute);
        this.setRoutes(routes);
        return this;
    }

    protected Route getRandomRoute() {
        Random rand = new Random();
        int randomIndex = rand.nextInt(routes.size());
        return routes.get(randomIndex);
    }

    public Solution cloneSolution() {
        Solution clonedSolution = new Solution();

        // Copier les propriétés de l'autre solution
        clonedSolution.setRoutes(new ArrayList<>(this.getRoutes()));
        // Assurez-vous de copier en profondeur les objets imbriqués si nécessaire

        return clonedSolution;
    }

}

