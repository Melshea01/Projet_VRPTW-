package Solution;

import Logistique.Route;
import Logistique.Transport;
import Logistique.*;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Operateur {

    //Operateur capable de bouger un client entre deux routes choisies aléatoirement
    //TODO : Finir la fonction  capable de bouger un client entre deux routes aléatoirement
    public Solution generateNeighbor_MoveClient(Solution solution, int tabuTenure, int capacity) {
        // Sélectionner une route au hasard
        Route route1 = solution.getRandomRoute();
        // Vérifier que la route sélectionnée contient au moins deux clients
        if (route1.getRoute().size() < 2) {
            return null;
        }
        // Sélectionner un client au hasard dans la route
        Client client1 = route1.getRandomClient();

        // Vérifier que le client sélectionné n'est pas le dépôt
        if (client1.getIdName() == "d0") {
            return null;
        }
        // Sélectionner une autre route au hasard
        Route route2 = solution.getRandomRoute();
        // Vérifier que la deuxième route est différente de la première
        if (route2 == route1) {
            return null;
        }
        // Vérifier que la deuxième route peut accueillir le client
        if (!route2.addClient(route1,client1,capacity)) {
            return null;
        }
        // Créer une nouvelle solution en déplaçant le client de la première route à la deuxième route
        Route newRoute1 = new Route(route1.getVehicle(), new ArrayList<Client>(route1.getClients()));
        newRoute1.removeClient(client1);
        Route newRoute2 = new Route(route2.getVehicle(), new ArrayList<Client>(route2.getClients()));
        newRoute2.addClient(client1);
        Solution newSolution = new Solution(solution.getProblem(), solution.getRoutes());
        newSolution.removeRoute(route1);
        newSolution.removeRoute(route2);
        newSolution.addRoute(newRoute1);
        newSolution.addRoute(newRoute2);
        // Ajouter le mouvement à la liste tabou
        //TODO : Créer la liste Tabou
        TabuMove move = new TabuMove(client1, client1, route1, client2, route2,);
        newSolution.addTabuMove(move, tabuTenure);
        return newSolution;
    }


/*    Cette méthode va parcourir toutes les positions possibles d'insertion du client dans la route, puis calculer l'heure
    d'arrivée à cette position avec la méthode calculateArrivalTime. Ensuite, elle vérifie si l'heure d'arrivée respecte la
    contrainte de temps du client avec la méthode isFeasible. Si c'est le cas, elle calcule un score représentant l'écart entre
    l'heure d'arrivée et le début de la fenêtre de temps, et garde en mémoire la position où ce score est le plus faible.
    Finalement, elle insère le client à cette position et retourne true, ou bien retourne false si l'ajout n'a pas été possible.*/


    //Vérifie si l'échange entre deux clients est possible
    public static boolean isFeasibleSwap(Client c1, Client c2, Route route1, Route route2,int capacity) {

        //On récupère une liste client de chaque route
        ArrayList<Client> clientroute1 = route1.getRoute();
        ArrayList<Client> clientroute2 = route2.getRoute();

        int demand1 = route1.getTotalDemandRoute();
        int demand2 = route2.getTotalDemandRoute();

        //On verifie la contrainte de capacité, il faut qu'on calcule la somme des demandes pour chaque route qu'on enlève l'ancienne
        //valeur et qu'on la remplace avec la nouvelle
        if(demand1- c1.getDemand() + c2.getDemand()>capacity ){
            return false ;
        }

        if(demand2- c2.getDemand() + c1.getDemand()>capacity ){
            return false ;
        }
        //Récupération de l'index de c1 et c2 pour récupérer le client avant eux dans la liste
        int idC1 = clientroute1.indexOf(c1);
        int idC2 = clientroute2.indexOf(c2);
        //Contraintes de temps
        //Calcul du temps d'arrivée du client 1 chez c2
        double arrivaltime_c1 =sqrt(pow(clientroute1.get(idC1-1).getX() - c2.getX(), 2) + pow(clientroute1.get(idC1-1).getY()-c2.getY(), 2));

        //Calcul du temps d'arrivée du client 2 chez c1
        double arrivaltime_c2 =sqrt(pow(clientroute2.get(idC2-1).getX() - c1.getX(), 2) + pow(clientroute2.get(idC2-1).getY()-c1.getY(), 2));


        if (c1.getDueTime() < arrivaltime_c2 || c2.getDueTime() < arrivaltime_c1) {
            // l'échange n'est pas possible car les fenêtres de temps ne se chevauchent pas
            return false ;
        }

        if (c1.getReadyTime() > arrivaltime_c2 || c2.getReadyTime()> arrivaltime_c1) {
            // l'échange n'est pas possible car les fenêtres de temps ne se chevauchent pas
            return false ;
        }
        return true;
    }


}
