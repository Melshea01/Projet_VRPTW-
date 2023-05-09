package Solution;

import Logistique.Client;
import Logistique.InstanceVRP;
import Logistique.Transport;
import Solution.Solution;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class SolutionAleatoire extends Solution{
    ArrayList <Transport> transports;

    public SolutionAleatoire() {
        this.transports = new ArrayList<>();
    }

    //Fonction qui prend en paramètre l'instance VRP
    public Solution generateRandomSolution(InstanceVRP VRP) {
        Solution solution = new Solution();
        ArrayList<Client> toDeliver = VRP.getClients();
        ArrayList<Double> distances = new ArrayList<>();
        ArrayList<Client> potentials = new ArrayList<>();
        int x_depot = toDeliver.get(0).getX();
        int y_depot = toDeliver.get(0).getY();
        int max_time = toDeliver.get(0).getDueTime();
        double Distancefinal = 0.00;


        while (toDeliver.size()!=1){
            int time = 0;
            //Création du transport i
            Transport transportUsed = new Transport();
            transportUsed.setY_vehicule(y_depot);
            transportUsed.setX_vehicule(x_depot);
            transportUsed.route.addDestination(toDeliver.get(0));

            //Remplissage du camion
            while (time <= max_time && transportUsed.chargement <= VRP.getCapacity() ){
                //Calculer les clients potentiel
                for (int i = 1; i < toDeliver.size(); i++) {
                    //la distance entre le véhicule et le client
                    double distance = sqrt(pow(toDeliver.get(i).getX() - transportUsed.getX_vehicule(), 2) + pow(toDeliver.get(i).getY()-transportUsed.getY_vehicule(), 2));
                    //à add dans le if, si la demande rentre dans le camion
                    if (time + distance >= toDeliver.get(i).getReadyTime() && time + distance < toDeliver.get(i).getDueTime()) {
                        potentials.add(toDeliver.get(i));
                        distances.add(distance);
                    }
                }

                if(potentials.isEmpty()  ){
                    time += 1;
                    }
                else {         //Choisir une valeur random parmis les possibilités
                    Random r = new Random();
                    int choosen = r.nextInt(potentials.size());

                    //Ajout de la distance entre l'ancien et le nouveau point
                    Distancefinal += sqrt(pow(toDeliver.get(choosen).getX() - transportUsed.getX_vehicule(), 2) + pow(toDeliver.get(choosen).getY()-transportUsed.getY_vehicule(), 2));
                    //on update le véhicule
                    transportUsed.setX_vehicule(potentials.get(choosen).getX());
                    transportUsed.setY_vehicule(potentials.get(choosen).getY());
                    transportUsed.addChargement(potentials.get(choosen).getDemand());

                    //On update la route lié au véhicule et la distance actuelle de la route
                    transportUsed.route.addDestination(potentials.get(choosen));
                    transportUsed.route.addRouteDistance(distances.get(choosen));

                    //On enlève le client de la liste des gens à livrer
                    toDeliver.remove(potentials.get(choosen));
                    //On incrémente avec le temps de livraison et le temps
                    time += distances.get(choosen) + 10;
                    //On vide les listes potentiels
                    potentials.clear();
                    distances.clear();}

                }
            solution.addRoute(transportUsed.route);
            solution.addTransport(transportUsed);
            this.transports.add(transportUsed);

            }
        return solution;
        }


}
