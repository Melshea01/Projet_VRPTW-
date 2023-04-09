import Logistique.Client;
import Logistique.InstanceVRP;
import Logistique.Route;
import Logistique.Transport;

import java.util.ArrayList;
import java.util.Collections;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class twoOpt {
    public void twOptSolution(InstanceVRP VRP){
        ArrayList<Client> toDeliver = VRP.getClients();
        Route route = new Route();
        SolutionAleatoire solution = new SolutionAleatoire();
        int size = toDeliver.size();
        ArrayList<Transport> solutionFinal = new ArrayList<>();
        Route route1= new Route();
        Route route2= new Route();
        int capacity = VRP.getCapacity();


        //Somme de toutes les routes venant de la solution aléatoire ?
        double newDist;
        int swaps = 1;
        int improve = 0;
        int iterations = 0;
        long comparisons = 0;
        boolean improved = true;

        //Itération
        ArrayList<Transport> solutionIni= solution.generateRandomSolution(VRP);
        double bestDistance = solution.calculateDistance(solutionIni);

        //Boucle itérative
        while (improved) {
            improved = false;
            //On parcourt chaque route
            for (int i = 1; i < solutionIni.size() - 2; i++) {
                for (int j = i + 1; j < solutionIni.size() - 1; j++) {
                    route1 = solution.transports.get(i).getRoute();
                    route2 = solution.transports.get(i).getRoute();

                    //On parcourt chaque client de chaque route et on teste si un échange se finit
                    for (int k = 1; i < route1.getRoute().size(); i++) {
                        for (int l = 1; j < route2.getRoute().size(); j++) {
                            Client c1 = route1.getRoute().get(k);
                            Client c2 = route2.getRoute().get(l);
                            if(isFeasibleTwoOPT(c1, c2, route1, route2, capacity)){

                                //Créer deux nouvelles routes qui seront des copies des deux anciennes jusqu'à l'endroit ou on ajoute le nouveau
                                //TODO : voir
                                Route newRoute1 = route1.cloneRoute(route1);
                                Route newRoute2 = route2.cloneRoute(route2);

                                //On ajoute les nouveaux clients et on supprime l'ancien


                                //Ensuite on fait la création de la nouvelle solution, en copiant la précédente et en cherchant à savoir si elle est plus rentable



                            }
                        }
                    }
                    ArrayList<Transport> newSolution = twoOPTSwap(solutionIni, i, j);
                    double newDistance = solution.calculateDistance(newSolution);
                    if (newDistance < bestDistance) {
                        solutionFinal = newSolution;
                        bestDistance = newDistance;
                        improved = true;
                    }
                }
            }
        }
      //  return solutionFinal;
      /*  repeat until no improvement is made {
            best_distance = calculateTotalDistance(existing_route)
            start_again:
            for (i = 0; i <= number of nodes eligible to be swapped - 1; i++) {
                for (j = i + 1; j <= number of nodes eligible to be swapped; j++) {
                    new_route = 2optSwap(existing_route, i, j)
                    new_distance = calculateTotalDistance(new_route)
                    if (new_distance < best_distance) {
                        existing_route = new_route
                        best_distance = new_distance
                goto start_again
                    }
                }
            }
        }*/

    }

    //Vérifie si l'échange est faisable entre deux clients
    public static boolean isFeasibleTwoOPT(Client c1, Client c2, Route route1, Route route2,int capacity) {

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

        //Echange deux clients de deux routes différentes
    public static ArrayList<Transport> twoOPTSwap(ArrayList<Transport> solution, int i, int j) {
        ArrayList<Transport> newSolution = new ArrayList<>(solution);
        Collections.reverse(newSolution.subList(i, j + 1));
        return newSolution;
    }
}
