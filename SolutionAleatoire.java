import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.*;

public class SolutionAleatoire {
    int time;

    /*
    Génère une solution aléatoire avec un nombre de véhicules et de routes indéfini
     */

    //TODO : gérer le chargement, gérer les véhicules et les routes, gérer les questions (destinations et liste clients)
    public void generateRandomSolution(InstanceVRP VRP) {
        //le reste des clients à livrer
        ArrayList<Client> toDeliver = VRP.getClients();
        //les clients à bonne distance
        ArrayList<Client> potentials = null;
        //la distance des clients potentiels
        ArrayList<Double> distances = null;
        //l'heure qu'il est actuellement
        time = 0;
        //le premier véhicule A UPDATE
        Transport vehicule1 = new Transport(1, VRP.getCapacity(), 1);
        //la première route A UPDATE
        Route route1 = new Route(0, null);

        //tant qu'il reste des clients à livrer
        while(toDeliver.size()>1) {
            //on regarde tous les clients
            for (int i = 1; i < toDeliver.size(); i++) {
                //la distance entre le véhicule et le client
                Double distance = sqrt(pow(toDeliver.get(i).getX()- vehicule1.getX_vehicule(),2) + pow(toDeliver.get(i).getY()- vehicule1.getY_vehicule(),2));
                //à add dans le if, si la demande rentre dans le camion
                if(time+distance>=toDeliver.get(i).getReadyTime() && time+distance<toDeliver.get(i).getDueTime()) {
                    potentials.add(toDeliver.get(i));
                    distances.add(distance);
                }
            }
            if(potentials!=null){
                //on choisit un nombre random parmis les potentiels
                //je suis obligée de créer un objet random sinon nextInt râle à cause de pb static. Ca marche pt-ê si on le sort du
                //while mais faudra voir quand on pourra faire tourner
                Random r = new Random();
                int choosen = r.nextInt(potentials.size()+1);
                //on ajoute le client choisi à la route CF QUESTION BLOC NOTE
                route1.addDestination(potentials.get(choosen));
                route1.addDistance(distances.get(choosen));

                //on update le véhicule
                vehicule1.setX_vehicule(potentials.get(choosen).getX());
                vehicule1.setY_vehicule(potentials.get(choosen).getY());
                vehicule1.addChargement(potentials.get(choosen).getDemand());
                //ajout du client choosen à la liste de clients livrés (cf questions bloc notes)

                //on enlève le client choisi
                toDeliver.remove(choosen);
                //on ajoute le temps de trajet et de livraison
                time+=distances.get(choosen) + 10;
            } else {
                Transport vehicule2 = new Transport(2, VRP.getCapacity(), 2);
                Route route2 = new Route(0,null);
            }
        }
    }
}
