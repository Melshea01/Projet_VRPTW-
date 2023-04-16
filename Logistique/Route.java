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

    public double calculateArrivalTime(Route route, Client prevClient, Client currClient) {
        double distance = Math.sqrt(Math.pow(currClient.getX() - prevClient.getX(), 2) + Math.pow(currClient.getY() - prevClient.getY(), 2));
        double arrivalTime = prevClient.getService() + distance;
        if (arrivalTime < currClient.getReadyTime()) {
            return currClient.getReadyTime();
        } else if (arrivalTime <= currClient.getDueTime()) {
            return arrivalTime;
        } else {
            return Double.POSITIVE_INFINITY;
        }
    }

    public boolean addClient(Route route, Client client, int capacity) {
        // Vérifier si la demande du client dépasse la capacité restante de la route
        if (route.getTotalDemandRoute()+ client.getDemand() > capacity) {
            return false;
        }

        //Verification de la contrainte de temps
        int positionToInsert = -1;
        double bestScore = Double.MAX_VALUE;

        for (int i = 1; i <= route.getRoute().size(); i++) {
            route.getRoute().add(i, client);
            //Calcul du temps d'arrivée entre l'ancien client et le nouveau
            double arrivalTime = route.calculateArrivalTime(route, route.getRoute().get(i-1),   route.getRoute().get(i));
            //Ajout temporaire du client
            if (client.isFeasible(arrivalTime)) {
                //calcule le score d'un client à une position donnée dans une route en fonction de sa contrainte de temps,
                // utilisé pour évaluer la qualité de l'insertion du client à cette position dans la route
                double score = Math.abs(client.getReadyTime() - arrivalTime);
                if (score < bestScore) {
                    bestScore = score;
                    positionToInsert = i;
                }
            }
            //On enlève le client si aucun placement n'est possible
            route.getRoute().remove(i);
        }

        if (positionToInsert != -1) {
            route.getRoute().add(positionToInsert, client);
            return true;
        } else {
            return false;
        }
    }



}
