package Solution;

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

    protected static InstanceVRP instanceVRP;

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

    public static InstanceVRP getInstanceVRP() {
        return instanceVRP;
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

    //TODO update le max du random avec nb opérateurs
    public Pair<ArrayList<Route>, ArrayList<String>> modifySolution() {
        Random rand = new Random();
        Operateur operateur = new Operateur(this.instanceVRP.getCapacity());
        int randomOperator = rand.nextInt(3);
        ArrayList<Route> routesToModify = new ArrayList<>(this.getRoutes());
        Pair<ArrayList<Route>, ArrayList<String>> modifiedRoutes = new Pair<>(null, null);
        switch (randomOperator) {
            case 0:
                modifiedRoutes = operateur.twoOptSameRoute(routesToModify);
                break;
            case 1:
                modifiedRoutes = operateur.relocateInter(routesToModify);
                break;
            case 2:
                modifiedRoutes = operateur.relocateIntra(routesToModify);
                break;
            case 3:
                modifiedRoutes = operateur.exchangeInter(routesToModify);
                break;
            case 4:
                modifiedRoutes = operateur.exchangeIntra(routesToModify);
                break;
            case 5:
                modifiedRoutes = operateur.crossExchange(routesToModify);
                break;
        }
        return modifiedRoutes;
    }
}

