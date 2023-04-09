package Logistique;

import Logistique.Client;
import Logistique.Route;

import java.util.ArrayList;
import java.util.List;

public class Transport {
    private static int nextId = 1;
    private int id;
    public int chargement;
    int x_vehicule;
    int y_vehicule;

    double distance;

    public Route route = new Route();

    public Route getRoute() {
        return route;
    }


    private ArrayList<Integer> clientlivre;

    public int getId() {
        return id;
    }

    public Transport(){
        this.id = generateId();;
        this.chargement = 0;
        this.x_vehicule =0;
        this.y_vehicule=0;
        new Route();

    }

    public int getChargement() {
        return chargement;
    }

    public void livrerClient(int idClient) {
        clientlivre.add(idClient);
    }
    public List<Integer> getClientlivre() {
        return clientlivre;
    }

    public int calculnbtransportmin(ArrayList<Client> clients ){
        int total_poid = 0;
        for(Client c : clients)
        {
            total_poid += c.getDemand();
        }
        return total_poid;
    }

    public int getX_vehicule() {
        return x_vehicule;
    }

    public int getY_vehicule() {
        return y_vehicule;
    }

    public void setX_vehicule(int x_vehicule) {
        this.x_vehicule = x_vehicule;
    }

    public void setY_vehicule(int y_vehicule) {
        this.y_vehicule = y_vehicule;
    }

    public void addChargement(int chargement) {
        this.chargement += chargement;
    }

    public void setClientlivre(ArrayList<Integer> clientlivre) {
        this.clientlivre = clientlivre;
    }

    public void reinitializeTransport(){
           this.setY_vehicule(0);
           this.setX_vehicule(0);
    }

    //Garantie que chaque véhicule aura un ID unique
    private static synchronized int generateId() {
        return nextId++;
    }

    //Fonction capable de calculer la distance a partir d'une liste de transport

}
