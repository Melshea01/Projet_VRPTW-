package Logistique;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class Route {

public double distance;
public ArrayList<Client> clients = new ArrayList<>();
//TODO : Peut être utilisé une hashmap
HashMap<Client, Point> hashMap = new HashMap<>();
private static final AtomicInteger ID_FACTORY = new AtomicInteger();
private final int id;


    /*Logistique.Route à faire
* Ajouter les destinations au fur et à mesure
* les sauvegarder pour éviter de repasser sur une route passer
* */

    public Route() {
        this.id = ID_FACTORY.getAndIncrement();
        this.distance = 0;
    }


    public  void addDestination (Client Destination) {
        clients.add(Destination);
    }

    public void removeDestination (Client Destination){
        clients.remove(Destination);
    }

    public void setClients(ArrayList<Client> clients) {
        this.clients.clear();
        this.clients.addAll(clients);
    }

    public ArrayList<Client> getListClient() {
        return clients;
    }

    public int getIndexOfClient(Client searchClient) {
        int index = 0;
        for (int i = 0 ; i < this.getListClient().size()-1 ; i++) {
            if(this.getListClient().get(i).getIdName().equals(searchClient.getIdName())) {
                index = i;
            }
        }
        return index;
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

    public final int getId() {
        return id;
    }

    /*
    Ajout de la distance parcourue
     */
    public void addRouteDistance(Double d) {
        this.distance += d;
    }


    //clone un objet route
    public void cloneRoute(Route originalRoute) {
//        Route newRoute = new Route();
//        for (Client client : originalRoute.getListClient()) {
//            newRoute.addDestination(client);
//        }
        this.clients = (ArrayList<Client>) originalRoute.getListClient().clone();
    }

    public Route cloneRoute() {
        Route newRoute = new Route();
        for (Client client : this.getListClient()) {
            newRoute.addDestination(client);
        }
        return newRoute;
    }

    //Modifier un objet route
    public Route setRoute(ArrayList<Client> newClients) {
        Route newRoute = new Route();
        for (Client client : newClients) {
            newRoute.addDestination(client);
        }
        return newRoute;
    }

    public boolean routesAreEquals(Route routeToCompare) {
        ArrayList<String> routeNameClients = new ArrayList<>();
        ArrayList<String> routeToCompareNameClients = new ArrayList<>();
        boolean areEquals = true;

        if(this.getListClient().size() != routeToCompare.getListClient().size()) {
            areEquals = false;
            return areEquals;
        } else {
            for (Client client1: this.getListClient()) {
                routeNameClients.add(client1.getIdName());
            }
            for (Client client2: routeToCompare.getListClient()) {
                routeToCompareNameClients.add(client2.getIdName());
            }
            for(int i = 0 ; i < this.getListClient().size() ; i++) {
                if(! routeNameClients.get(i).equals(routeToCompareNameClients.get(i))) {
                    areEquals = false;
                    return areEquals;
                }
            }
        }
        return areEquals;
    }


    public double calculateArrivalTime(Client prevClient, Client currClient) {
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

    public double calculateDistance() {
        double distance = 0.0;

        for (int i = 0; i < clients.size() - 1; i++) {
            Client currentClient = clients.get(i);
            Client nextClient = clients.get(i + 1);

            // Calculer la distance entre le client actuel et le client suivant
            double clientDistance = getDistanceBetweenTwoClient(currentClient,nextClient);

            distance += clientDistance;

        }
        this.distance= distance;
        return distance;
    }
    //fonction relocate capable d'ajouter un client d'une route vers une autre

    public double getDistanceBetweenTwoClient(Client c1, Client c2){
        double x1 = c1.getX();
        double y1 = c1.getY();
        double x2 = c2.getX();
        double y2 = c2.getY();
        double distance = Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
        return distance;
    }

    public boolean isFeasible(int capacity) {
        int currentTime = 0; // On commence à l'entrepôt à l'heure 0
        int totalDemand = 0;
        for (int i = 1; i < getListClient().size(); i++) { // On parcourt la route (en ignorant l'entrepôt)
            Client currentClient = getListClient().get(i);
            double travelTime = getDistanceBetweenTwoClient(getListClient().get(i - 1), currentClient); // Temps de trajet entre les clients i-1 et i
            currentTime += travelTime; // On ajoute le temps de trajet au temps actuel + le temps de la livraison

            // Vérification de la contrainte de temps
            if (currentTime <= currentClient.getReadyTime()) { // On arrive trop tôt
                currentTime = currentClient.getReadyTime()+currentClient.getService(); // On attend jusqu'à l'heure de début de la fenêtre de temps
                totalDemand += currentClient.getDemand();
            } else if (currentTime > currentClient.getDueTime()) { // On arrive trop tard
                return false; // La route n'est pas réalisable
            }
        }
        if(totalDemand <= capacity) {
            return true; // La route est réalisable
        } else { return false;}
    }



}
