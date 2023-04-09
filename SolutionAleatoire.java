import Logistique.Client;
import Logistique.InstanceVRP;
import Logistique.Route;
import Logistique.Transport;

import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class SolutionAleatoire {
    ArrayList <Transport> transports;

    public SolutionAleatoire() {
        this.transports = new ArrayList<>();
    }

    //TODO : Renvoyer une arraylist, la distance renvoyé par cette variable est fausse
    public ArrayList<Transport> generateRandomSolution(InstanceVRP VRP) {
        ArrayList<Client> toDeliver = VRP.getClients();
        ArrayList<Double> distances = new ArrayList<>();
        ArrayList<Client> potentials = new ArrayList<>();
        int x_depot = toDeliver.get(0).getX();
        int y_depot = toDeliver.get(0).getY();
        double Distancefinal = 0.00;


        while (toDeliver.size()!=1){
            int time = 0;
            //Création du transport i
            Transport transportUsed = new Transport();
            transportUsed.setY_vehicule(y_depot);
            transportUsed.setX_vehicule(x_depot);
            transportUsed.route.addDestination(toDeliver.get(0));

            //Remplissage du camion
            while (time <=230 && transportUsed.chargement <= VRP.getCapacity() ){
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

            this.transports.add(transportUsed);
            }
        return transports;
        }




    public void twOptSolution(InstanceVRP VRP){
        ArrayList<Client> toDeliver = VRP.getClients();
        Route route = new Route();
        int size = toDeliver.size();

        //Somme de toutes les routes venant de la solution aléatoire ?
        double newDist;
        int swaps = 1;
        int improve = 0;
        int iterations = 0;
        long comparisons = 0;
        int i =0;
        //Itération
        ArrayList<Transport> solution = generateRandomSolution(VRP);
        double bestdistance = calculateDistance(solution);
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

    //Fonction permettant d'échanger la positionde deux routes (deux arêtes)

    public double calculateDistance(ArrayList<Transport> transports) {
        double totalDistance = 0;
        int i =0;
        //Pour chaque transport de la solution on ajoute la distance parcourue
        for (Transport transport: transports) {
            i=0;
            totalDistance += transport.getRoute().distance;
            System.out.println(totalDistance);
        }
        return totalDistance;
    }

}
