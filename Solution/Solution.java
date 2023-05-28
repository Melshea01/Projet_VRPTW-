package Solution;

import Logistique.InstanceVRP;
import Logistique.Route;
import Logistique.Transport;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Solution {

    private ArrayList<Route> routes;
    private ArrayList<Transport> transports;

    private double distanceSolution ;

    private InstanceVRP InstanceVRP;

    public Solution() {
        this.routes = new ArrayList<Route>();
        this.transports = new ArrayList<>();
        distanceSolution = getTotalDistance();
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
    public double getTotalDistance() {
        for (Route route : this.routes) {
            this.distanceSolution += route.getDistance();
            //On ajoute la distance entre le dernier client et le dépot
            this.distanceSolution += sqrt(pow(route.getListClient().get(route.getListClient().size()-1).getX() -route.getListClient().get(0).getX(), 2) + pow(route.getListClient().get(route.getListClient().size()-1).getY() -route.getListClient().get(0).getY(), 2));

        }
        return this.distanceSolution;
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
//    public Solution modifySolution() {
//        Random rand = new Random();
//        Operateur operateur = new Operateur();
//        int randomOperator = rand.nextInt(3);
//        switch (randomOperator) {
//            case 0:
//                operateur.twoOptSameRoute();
//        }
//
//
//        return null;
//    }

}

