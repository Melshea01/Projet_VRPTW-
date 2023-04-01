import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class SolutionAleatoire {
    ArrayList <Transport> transports;

    public SolutionAleatoire() {
        this.transports = new ArrayList<>();
    }

    public Double generateRandomSolution(InstanceVRP VRP) {
        ArrayList<Client> toDeliver = VRP.getClients();
        ArrayList<Double> distances = new ArrayList<>();
        ArrayList<Client> potentials = new ArrayList<>();
        int x_depot = toDeliver.get(0).getX();
        int y_depot = toDeliver.get(0).getY();
        Double Distancefinal = 0.00;


        while (toDeliver.size()!=1){
            int time = 0;
            //Création du transport i
            Transport transportUsed = new Transport();
            transportUsed.setY_vehicule(y_depot);
            transportUsed.setX_vehicule(x_depot);

            //Remplissage du camion
            while (time <=230 && transportUsed.chargement <= VRP.getCapacity() ){
                //Calculer les clients potentiel
                for (int i = 1; i < toDeliver.size(); i++) {
                    //la distance entre le véhicule et le client
                    Double distance = sqrt(pow(toDeliver.get(i).getX() - transportUsed.getX_vehicule(), 2) + pow(toDeliver.get(i).getY()-transportUsed.getY_vehicule(), 2));
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

                    //Ajout de la distance entre l'ancien
                    Distancefinal += sqrt(pow(toDeliver.get(choosen).getX() - transportUsed.getX_vehicule(), 2) + pow(toDeliver.get(choosen).getY()-transportUsed.getY_vehicule(), 2));
                    //on update le véhicule
                    transportUsed.setX_vehicule(potentials.get(choosen).getX());
                    transportUsed.setY_vehicule(potentials.get(choosen).getY());
                    transportUsed.addChargement(potentials.get(choosen).getDemand());
                    //On update la route lié au véhicule
                    transportUsed.route.addDestination(potentials.get(choosen));


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
        return Distancefinal;
        }



        //TODO : Renvoyer une arraylist
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

        //Itération
        double bestdistance = generateRandomSolution(VRP);


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

    //Fonction capable de calculer la distance a partir d'une solution
    public static double calculateDistance(ArrayList<Integer> solution, double[][] distances, double[][] times, double[] demands, double capacity) {
        double totalDistance = 0;
        double[] arrivalTime = new double[distances.length];

        // Parcours de la solution
        for (int i = 1; i < solution.size(); i++) {
            int from = solution.get(i - 1);
            int to = solution.get(i);

            // Calcul de la distance entre deux clients
            double distance = distances[from][to];

            // Calcul du temps de service (temps d'attente inclus)
            double serviceTime = Math.max(arrivalTime[from] + times[from][to], times[from][to]);

            // Vérification de la faisabilité de la capacité
            if (i < solution.size() - 1) {
                int next = solution.get(i + 1);
                if (demands[to] > capacity || demands[to] + demands[next] > capacity) {
                    return Double.MAX_VALUE;
                }
            }

            // Mise à jour du temps d'arrivée
            arrivalTime[to] = serviceTime + demands[to];

            // Mise à jour de la distance totale
            totalDistance += distance;
        }

        return totalDistance;
    }


}
