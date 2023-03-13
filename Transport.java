import java.util.ArrayList;
import java.util.List;

public class Transport {
    int id_vehicule;
    int chargement;
    private List<Integer> clientlivre;

    public Transport(){
        clientlivre = new ArrayList<Integer>();
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
