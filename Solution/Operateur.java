package Solution;

import Logistique.Route;
import Logistique.Transport;
import Logistique.*;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class Operateur {


    //Créer la fonction reverse qui va inverser une route, il faut ensuite si c'est possible en terme de fenêtre de temps (impossible car pas orienter)
    //TODO : Créer la fonction capable d'échanger deux aretes, two_opt
    public Route twoOpt(Route route){
        boolean improve = true;
        while (improve){
            improve = false;
            //Pour tous les clients de la route
            for (Client c : route.getRoute()){
                int index = route.getRoute().indexOf(c);
                //pour tous les clients ayant un index différent de i+1 et i-1 et de 0
                //revoir 2opt peut-être modulo par la taille du machine -1 pour retomber sur le dépot qui est en première place
                //
            }

        }
        Route newroute = new Route();
        return newroute;
    }

    //Créer une fonction swap afin de faire le changement de tour s'inspier du code suivant https://github.com/jackspyder/2-opt/blob/master/src/sample/TwoOpt.java



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

        for (int i = 1; i <= route.getRoute().size(); i++) {
            route.getRoute().add(i, client);
            //Calcul du temps d'arrivée entre l'ancien client et le nouveau
            double arrivalTime = route.calculateArrivalTime(route, route.getRoute().get(i-1),   route.getRoute().get(i));
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
            route.getRoute().remove(i);
        }

        if (positionToInsert != -1) {
            route.getRoute().add(positionToInsert, client);
            return route;
        } else {
            return null;
        }
    }

    //TODO : Créer l'opérateur, exchange qui va échanger de place deux client entre deux routes
    public static boolean exchangeInter(Client c1, Client c2, Route route1, Route route2,int capacity) {

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
