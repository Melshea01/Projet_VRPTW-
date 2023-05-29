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
        for (Route route : this.routes) {
            this.distanceSolution += route.getDistance();
            //On ajoute la distance entre le dernier client et le dépot
            this.distanceSolution += sqrt(pow(route.getListClient().get(route.getListClient().size()-1).getX() -route.getListClient().get(0).getX(), 2) + pow(route.getListClient().get(route.getListClient().size()-1).getY() -route.getListClient().get(0).getY(), 2));

        }
        return this.distanceSolution;
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
        Pair<ArrayList<Route>, ArrayList<String>> modifiedRoutes;
        modifiedRoutes = operateur.exchangeInter(routesToModify);
//        switch (randomOperator) {
//            case 0:
//                operateur.twoOptSameRoute();
//        }
        return modifiedRoutes;
    }
}

