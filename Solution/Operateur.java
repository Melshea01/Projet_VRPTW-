package Solution;

import Logistique.Route;
import Logistique.*;

import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Operateur {


    //Créer la fonction reverse qui va inverser une route, il faut ensuite si c'est possible en terme de fenêtre de temps (impossible car pas orienter)
    //TODO : Créer la fonction capable d'échanger deux aretes, two_opt
    public Route twoOpt(Route route){
        boolean improve = true;
        while (improve){
            improve = false;
            Client clientinitial = route.getListClient().get(0);
            //Pour tous les clients de la route
            for (Client c : route.getListClient()){
                int index = route.getListClient().indexOf(c);
                //Essayer de le placer sur toutes les ok
                //pour tous les clients ayant un index différent de i+1 et i-1 et de 0
            }

        }
        Route newroute = new Route();
        return newroute;
    }

    //TODO : Renvoyer une solution
    public Route twoOptSameRoute(Route route) {
        int size = route.getListClient().size();
        Route newRoute = route.cloneRoute(route);
        boolean improved = true;
        while (improved) {
            improved = false;
            for (int i = 1; i < size - 2; i++) {
                for (int j = i + 1; j < size - 1; j++) {
                    Route tempRoute = newRoute.cloneRoute(newRoute);
                    Collections.reverse(tempRoute.getListClient().subList(i, j + 1));
                    if (tempRoute.isFeasible()) {
                        newRoute = tempRoute;
                        improved = true;
                    }
                }
                if (improved) {
                    break;
                }
            }
            if (improved) {
                break;
            }
        }
        return newRoute;
    }



/*    Cette méthode va parcourir toutes les positions possibles d'insertion du client dans la route, puis calculer l'heure
    d'arrivée à cette position avec la méthode calculateArrivalTime. Ensuite, elle vérifie si l'heure d'arrivée respecte la
    contrainte de temps du client avec la méthode isFeasible. Elle ne renvoie rien si il n'y aucun changement*/

    public Route RelocateInter(Route route, Client client, int capacity) {
        // Vérifier si la demande du client dépasse la capacité restante de la route
        if (route.getTotalDemandRoute()+ client.getDemand() > capacity ) {
            return null;
        }

        if(relocateIntra(route,client) == null ){
            return null;
        }
        return route;
    }

    //Vérifie si l'échange entre deux clients est possible
    /*Si c'est le cas, elle calcule un score représentant l'écart entre
    l'heure d'arrivée et le début de la fenêtre de temps, et garde en mémoire la position où ce score est le plus faible.
            Finalement, elle insère le client à cette position et retourne true, ou bien retourne false si l'ajout n'a pas été possible.*/
    public Route relocateIntra(Route route, Client client){
        //Verification de la contrainte de temps
        int positionToInsert = -1;
        double bestScore = Double.MAX_VALUE;

        for (int i = 1; i <= route.getListClient().size(); i++) {
            route.getListClient().add(i, client);
            //Calcul du temps d'arrivée entre l'ancien client et le nouveau
            double arrivalTime = route.calculateArrivalTime(route.getListClient().get(i-1), route.getListClient().get(i));
            //Ajout temporaire du client si la fenetre de temps est respectée
            if (client.isFeasible(arrivalTime)) {
                //calcule le score d'un client à une position donnée dans une route en fonction de sa contrainte de temps,
                // utilisé pour évaluer la qualité de l'insertion du client à cette position dans la route
                double score = Math.abs(client.getReadyTime() - arrivalTime);
                if (score < bestScore) {
                    bestScore = score;
                    positionToInsert = i;
                }
            }
            //On enlève le client si aucun placement n'est possible
            route.getListClient().remove(i);
        }

        if (positionToInsert != -1) {
            route.getListClient().add(positionToInsert, client);
            return route;
        } else {
            return null;
        }
    }

    //TODO : Retourner une solution pour stocker les deux nouvelles routes

    public static boolean exchangeInter(Client c1, Client c2, Route route1, Route route2,int capacity) {

        //On récupère une liste client de chaque route
        ArrayList<Client> clientroute1 = route1.getListClient();
        ArrayList<Client> clientroute2 = route2.getListClient();

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
