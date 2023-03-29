import java.util.ArrayList;
import java.util.List;

public class Transport {
    int id_vehicule = 1 ;
    int chargement;
    int id_route;
    int x_vehicule;
    int y_vehicule;

    Route route;
    private ArrayList<Integer> clientlivre;

    public Transport(){
        this.id_vehicule = 0;
        this.chargement = 0;
        this.x_vehicule =0;
        this.y_vehicule=0;
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
}
