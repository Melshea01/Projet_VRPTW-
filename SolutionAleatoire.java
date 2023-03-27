import java.util.ArrayList;
import java.util.Random;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

public class SolutionAleatoire {
    ArrayList <Transport> transports;

    public SolutionAleatoire() {
        this.transports = new ArrayList<>();
    }

    public void generateRandomSolution(InstanceVRP VRP) {
        ArrayList<Client> toDeliver = VRP.getClients();
        ArrayList<Double> distances = new ArrayList<>();
        ArrayList<Client> potentials = new ArrayList<>();
        int time = 0;
        int x_depot = toDeliver.get(0).getX();
        int y_depot = toDeliver.get(0).getY();


        while (toDeliver.size()!=1){
            time = 0;
            //Création du transport i
            Transport transportUsed = new Transport();
            transportUsed.setY_vehicule(y_depot);
            transportUsed.setX_vehicule(x_depot);

            //Remplissage du camion
            while (time <=230){
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

                if(potentials.isEmpty()){
                    time += 1;
                    }
                else {         //Choisir une valeur random parmis les possibilités
                    Random r = new Random();
                    int choosen = r.nextInt(potentials.size());

                    //on update le véhicule
                    transportUsed.setX_vehicule(potentials.get(choosen).getX());
                    transportUsed.setY_vehicule(potentials.get(choosen).getY());
                    transportUsed.addChargement(potentials.get(choosen).getDemand());

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

        }

    public ArrayList<Client> getPotential( ArrayList<Client> toDeliver, double time, ArrayList<Double> distances, Transport transport){
        ArrayList<Client> potentials = new ArrayList<>();
        for (int i = 1; i < toDeliver.size(); i++) {
            //la distance entre le véhicule et le client
            Double distance = sqrt(pow(toDeliver.get(i).getX() - transport.getX_vehicule(), 2) + pow(toDeliver.get(i).getY()-transport.getY_vehicule(), 2));
            //à add dans le if, si la demande rentre dans le camion
            if (time + distance >= toDeliver.get(i).getReadyTime() && time + distance < toDeliver.get(i).getDueTime()) {
                potentials.add(toDeliver.get(i));
                distances.add(distance);
            }
        }
        return potentials ;
    }
}
