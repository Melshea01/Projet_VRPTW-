import java.util.ArrayList;
import java.util.List;

public class Transport {
    int id_vehicule = 1 ;
    int chargement;
    int id_route;
    int x_vehicule;
    int y_vehicule;

    private ArrayList<Integer> clientlivre;

    public Transport(){
        this.id_vehicule = 0;
        this.chargement = 0;
        this.id_route = 0;
    }

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
}
