package Solution;

import Logistique.InstanceVRP;
import Logistique.Route;
import Logistique.Transport;

import java.util.ArrayList;

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
    public Solution replaceRoute(Solution solution, Route newRoute) {
        ArrayList<Route> routes = solution.getRoutes();
        for (int i = 0; i < routes.size(); i++) {
            if (routes.get(i) == newRoute) {
                routes.set(i, newRoute);
                break;
            }
        }
        solution.setRoutes(routes);
        return solution;
    }

}

