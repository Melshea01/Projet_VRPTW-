import java.util.ArrayList;
import java.util.List;

public class Transport {
    int id_vehicule = 1 ;
    int chargement;
    int id_route;
    private ArrayList<Integer> clientlivre;

    public Transport(int id_vehicule, int chargement, int id_route){
        this.id_vehicule = id_vehicule;
        this.chargement = chargement;
        this.id_route = id_route;
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
}
