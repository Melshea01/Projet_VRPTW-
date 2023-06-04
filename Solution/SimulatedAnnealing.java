package Solution;

import Logistique.Route;
import org.apache.commons.math3.util.Pair;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class SimulatedAnnealing extends Solution{
    private Solution initialSolution;
    private double initialTemperature;
    private double finalTemperature;
    private int movesAtTTemp;
    private double decrease;

    public SimulatedAnnealing(Solution initialSolution, double finalTemperature, int movesAtTTemp, double decrease) {
        this.initialSolution = initialSolution;
        this.finalTemperature = finalTemperature;
        this.movesAtTTemp = movesAtTTemp;
        this.decrease = decrease;
    }

    //On calcule la température initiale telle que la probabilité d'accepter une solution moins bonnesoit de 0.8
    public void setInitialTemperature() {
       double delta = 0.0;
        ArrayList<Pair<Solution, ArrayList<String>>> neighbors = new ArrayList<>();
        Operateur o = new Operateur(this.initialSolution.getInstanceVRP());

        // On génère tous les voisins
        if (o.twoOpt(initialSolution).get(0).getFirst() != null) {
            neighbors.addAll(o.twoOpt(initialSolution));
        }
        if (o.relocateIntra(initialSolution).get(0).getFirst() != null) {
            neighbors.addAll(o.relocateIntra(initialSolution));
        }
        if (o.relocateInter(initialSolution).get(0).getFirst() != null) {
            neighbors.addAll(o.relocateInter(initialSolution));
        }
        if (o.exchange(initialSolution).get(0).getFirst() != null) {
            neighbors.addAll(o.exchange(initialSolution));
        }
        if (o.crossExchange(initialSolution).get(0).getFirst() != null) {
            neighbors.addAll(o.crossExchange(initialSolution));
        }

        // On récupère le pire voisin
        for (int i = 0; i < neighbors.size(); i++) {
            if (neighbors.get(i).getFirst() != null) {
                double neighborDistance = neighbors.get(i).getKey().getTotalDistance();
                if (delta < neighborDistance - this.initialSolution.getTotalDistance()) {
                    delta = neighborDistance - this.initialSolution.getTotalDistance();
                }
            }
        }
        this.initialTemperature = -delta/Math.log(0.8);
    }


    public Solution generateSimulatedAnnealing() {
        Solution bestSolution = this.initialSolution;
        double bestDistance = this.initialSolution.getTotalDistance();
        this.setInitialTemperature();
        double currentTemp = this.initialTemperature;
        Solution currentSolution = this.initialSolution;
        double currentDistance = this.initialSolution.getTotalDistance();

        while(currentTemp > this.finalTemperature) {
            System.out.println("Meilleure solution intermédiaire : " + bestSolution);
            for(int l=0; l < this.movesAtTTemp; l++) {

                currentSolution.getRoutes().removeIf(route -> route.getListClient().size() < 3);

                // On génère tous les voisins
                ArrayList<Pair<Solution, ArrayList<String>>> neighbors = new ArrayList<>();
                Operateur o = new Operateur(this.initialSolution.getInstanceVRP());

                // On génère tous les voisins
                if (o.twoOpt(currentSolution).get(0).getFirst() != null) {
                    neighbors.addAll(o.twoOpt(currentSolution));
                }
                if (o.relocateIntra(currentSolution).get(0).getFirst() != null) {
                    neighbors.addAll(o.relocateIntra(currentSolution));
                }
                if (o.relocateInter(currentSolution).get(0).getFirst() != null) {
                    neighbors.addAll(o.relocateInter(currentSolution));
                }
                if (o.exchange(currentSolution).get(0).getFirst() != null) {
                    neighbors.addAll(o.exchange(currentSolution));
                }
                if (o.crossExchange(currentSolution).get(0).getFirst() != null) {
                    neighbors.addAll(o.crossExchange(currentSolution));
                }

                Solution solutionToTest = new Solution();

                // On en choisit un au hasard
                Random randNeighborIndex = new Random();
                Pair<Solution, ArrayList<String>> randNeighbor = neighbors.get(randNeighborIndex.nextInt(neighbors.size()));
                solutionToTest = randNeighbor.getKey();


                // On regarde si la nouvelle solution a une plus petite distance totale que l'autre
                double distDifference = solutionToTest.getTotalDistance() - currentDistance;
                if(distDifference <= 0) {
                    currentSolution = solutionToTest;
                    currentDistance = solutionToTest.getTotalDistance();
                    if (currentDistance < bestDistance) {
                        bestSolution = currentSolution;
                        bestDistance = currentDistance;
                    }
                } else {
                    double p = Math.random();
                    if (p <= Math.exp(-distDifference/currentTemp)) {
                        currentSolution = solutionToTest;
                        currentDistance = solutionToTest.getTotalDistance();
                    }
                }
            }
            currentTemp *= decrease;
        }
        System.out.println("Température initiale " + this.initialTemperature);
        return bestSolution;
    }
}