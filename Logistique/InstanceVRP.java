package Logistique;

import java.util.ArrayList;

public class InstanceVRP {
    int capacity;
    int nb_client;

    public ArrayList<Client> clients;

    public InstanceVRP(int capacity, int nb_client, ArrayList<Client> clients ){
        this.capacity = capacity;
        this.nb_client= nb_client;
        this.clients = clients;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getNb_client() {
        return nb_client;
    }

    public ArrayList<Client> getClients() {
        return clients;
    }

    public Client getClientByIndex(int index) {
        return clients.get(index);
    }
}
