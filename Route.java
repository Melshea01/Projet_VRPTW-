import java.util.ArrayList;

public class Route {
static int id_route =0 ;
int distance ;
private ArrayList<Client> clients = new ArrayList<>();



/*Route à faire
* Ajouter les destinations au fur et à mesure
* les sauvegarder pour éviter de repasser sur une route passer
* */

    public Route() {
        this.distance = 0;
    }

    public Route(int distance, int id) {
        this.distance = distance;
        this.id_route = id;
    }

    public  void addDestination (Client Destination) {
        clients.add(Destination);
    }

    public void removeDestination (Client Destination){
        clients.remove(Destination);
    }

    public ArrayList<Client> getRoute() {
        return clients;
    }

    /*
* Calcul de la distance parcourue par le camion
* */
    public int getDistance() {
        return distance;
    }

    /*
    Ajout de la distance parcourue
     */
    public void addDistance(Double d) {
        distance += d;
    }
}
