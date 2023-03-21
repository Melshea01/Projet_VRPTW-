import java.util.ArrayList;

public class Route {
static int id_route =0 ;
int distance ;
private ArrayList<String> route;

/*Route à faire
* Ajouter les destinations au fur et à mesure
* les sauvegarder pour éviter de repasser sur une route passer
* */

    public Route(int distance, ArrayList<String> route) {
        this.distance = distance;
        this.route = route;
    }

    public  void addDestination (String Destination) {
        route.add(Destination);
    }

    public void removeDestination (String Destination){
        route.remove(Destination);
    }

    public ArrayList<String> getRoute() {
        return route;
    }

    /*
* Calcul de la distance parcourue par le camion
* */
    public int getDistance() {
        return distance;
    }
}
