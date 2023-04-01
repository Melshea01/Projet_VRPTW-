import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Route {
static int id_route =0 ;

//Distance total
int distance ;
private ArrayList<Client> clients = new ArrayList<>();
//Stocke les emplacement des clients
private ArrayList<Point> coordonnees= new ArrayList<Point>();


    /*Route à faire
* Ajouter les destinations au fur et à mesure
* les sauvegarder pour éviter de repasser sur une route passer
* */

    public Route() {
        this.distance = 0;
        this.clients = clients;
        this.coordonnees = coordonnees;
    }



    public  void addDestination (Client Destination) {

        clients.add(Destination);
        Point point = new Point(Destination.getX(), Destination.getY());
        this.coordonnees.add((point));
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

    public ArrayList<Point> getCoordonnees() {
        return coordonnees;
    }






}
