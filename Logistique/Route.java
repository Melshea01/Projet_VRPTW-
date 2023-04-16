package Logistique;

import java.awt.*;
import java.util.ArrayList;

public class Route {
static int id;
static int  nextId;
//Distance total
public double distance ;
public ArrayList<Client> clients = new ArrayList<>();
//Stocke les emplacement des clients
public ArrayList<Point> coordonnees= new ArrayList<Point>();


    /*Logistique.Route à faire
* Ajouter les destinations au fur et à mesure
* les sauvegarder pour éviter de repasser sur une route passer
* */

    public Route() {
        this.id = generateId();;
        this.distance = 0;
        this.clients = clients;
        this.coordonnees = coordonnees;
    }

    public Route( ArrayList<Client> clients,ArrayList<Point> coordonnees ) {
        this.id = generateId();;
        this.distance = 0;
        this.clients = clients;
        this.coordonnees = coordonnees;
    }

    public static int getId() {
        return id;
    }

    public  void addDestination (Client Destination) {
        clients.add(Destination);
        Point point = new Point(Destination.getX(), Destination.getY());
        this.coordonnees.add((point));
    }

    public void removeDestination (Client Destination){
        Point point = new Point(Destination.getX(), Destination.getY());
        this.coordonnees.remove(point);
        clients.remove(Destination);
    }

    public ArrayList<Client> getRoute() {
        return clients;
    }

    public int getTotalDemandRoute() {
        int totaldemand = 0;
        for (int i =0; i<clients.size(); i++){
             totaldemand += clients.get(i).getDemand();
        }
        return totaldemand;
    }

    /*
* Calcul de la distance parcourue par le camion
* */
    public double getDistance() {
        return distance;
    }

    /*
    Ajout de la distance parcourue
     */
    public void addDistance(Double d) {
        distance += d;
    }
    public void addRouteDistance(Double d) {
        this.distance += d;
    }

    public ArrayList<Point> getCoordonnees() {
        return coordonnees;
    }

    public Route cloneRoute(Route originalRoute) {
        Route newRoute = new Route();
        for (Client client : originalRoute.getRoute()) {
            newRoute.addDestination(client);
        }
        return newRoute;
    }

    private static synchronized int generateId() {
        return nextId++;
    }

    public Client getRandomClient() {
        int index = (int) (Math.random() * clients.size());
        return clients.get(index);
    }


}
