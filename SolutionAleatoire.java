import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.*;

public class SolutionAleatoire {
    int time;

    /*
    Génère une solution aléatoire avec un nombre de véhicules et de routes indéfini
     */

    //TODO : gérer le chargement, gérer les véhicules et les routes, gérer les questions (destinations et liste clients)
    public ArrayList<Transport> generateRandomSolution(InstanceVRP VRP) {
        //le reste des clients à livrer
        ArrayList<Client> toDeliver = VRP.getClients();

        //la distance des clients potentiels
        ArrayList<Double> distances = new ArrayList<>();

        //les clients à bonne distance
        ArrayList<Client> potentials = new ArrayList<>();

        //l'heure qu'il est actuellement
        int time = 0;

        //le premier véhicule A UPDATE
        Transport vehicule0 = new Transport(0, VRP.getCapacity(), 1);
        //la liste des véhicules utilisés
        ArrayList<Transport> vehiculesUsed = new ArrayList<>();
        vehiculesUsed.add(vehicule0);
        Transport vehiculeDelivering = new Transport();
        vehiculeDelivering = vehicule0;

        //la première route A UPDATE
        Route route0 = new Route(0, 0);
        ArrayList<Route>routesUsed = new ArrayList<>();
        routesUsed.add(route0);
        Route routeDelivering = new Route();
        routeDelivering = route0;

        ArrayList<Client> delivered = new ArrayList<>();

        // l'ID du véhicule
        int k = 1;

        //tant qu'il reste des clients à livrer
        while (toDeliver.size() > 1) {

            //on regarde tous les clients
            for (int i = 1; i < toDeliver.size(); i++) {
                //la distance entre le véhicule et le client
                Double distance = sqrt(pow(toDeliver.get(i).getX() - vehiculeDelivering.getX_vehicule(), 2) + pow(toDeliver.get(i).getY() - vehiculeDelivering.getY_vehicule(), 2));
                //à add dans le if, si la demande rentre dans le camion
                if (time + distance >= toDeliver.get(i).getReadyTime() && time + distance < toDeliver.get(i).getDueTime()) {
                    potentials.add(toDeliver.get(i));
                    distances.add(distance);
                }
            }
            if (potentials.size() > 0) {
                //on choisit un nombre random parmis les potentiels
                //je suis obligée de créer un objet random sinon nextInt râle à cause de pb static. Ca marche pt-ê si on le sort du
                //while mais faudra voir quand on pourra faire tourner
                Random r = new Random();
                int choosen = r.nextInt(potentials.size());
                //on ajoute le client choisi à la route CF QUESTION BLOC NOTE
                routeDelivering.addDestination(potentials.get(choosen));
                routeDelivering.addDistance(distances.get(choosen));

                //on update le véhicule
                vehiculeDelivering.setX_vehicule(potentials.get(choosen).getX());
                vehiculeDelivering.setY_vehicule(potentials.get(choosen).getY());
                vehiculeDelivering.addChargement(potentials.get(choosen).getDemand());
                //ajout du client choosen à la liste de clients livrés (cf questions bloc notes)

                //on enlève le client choisi
                toDeliver.remove(potentials.get(choosen));

                //on ajoute le temps de trajet et de livraison
                time += distances.get(choosen) + 10;

                delivered.add(potentials.get(choosen));
            } else {
                vehiculesUsed.add(new Transport(k, VRP.getCapacity(), k));
                vehiculeDelivering = vehiculesUsed.get(k);
                routesUsed.add(new Route(0, k));
                routeDelivering = routesUsed.get(k);
                k++;
            }
            System.out.println(toDeliver.size());
            potentials.clear();
        }
        return vehiculesUsed;
    }
}

