import java.util.ArrayList;

public class Route {
static int id_route =0 ;
int distance ;
private ArrayList<Client> route;

/*Route à faire
* Ajouter les destinations au fur et à mesure
* les sauvegarder pour éviter de repasser sur une route passer
* */

    public Route(int distance, ArrayList<Client> route) {
        this.distance = distance;
        this.route = route;
    }

    public  void addDestination (Client Destination) {
        route.add(Destination);
    }

    public void removeDestination (Client Destination){
        route.remove(Destination);
    }

    public ArrayList<Client> getRoute() {
        return route;
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
