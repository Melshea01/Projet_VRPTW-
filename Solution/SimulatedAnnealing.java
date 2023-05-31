package Solution;

import Logistique.InstanceVRP;
import Logistique.Route;

import java.util.ArrayList;

public class SimulatedAnnealing extends Solution{
    private Solution initialSolution;
    private double initialTemperature;
    private double finalTemperature;
    private int movesAtTTemp;

    private double decrease;

    public SimulatedAnnealing(Solution initialSolution, double finalTemperature, int movesAtTTemp, double decrease, InstanceVRP vrp) {
        this.initialSolution = initialSolution;
        this.finalTemperature = finalTemperature;
        this.movesAtTTemp = movesAtTTemp;
        this.decrease = decrease;
        this.instanceVRP = vrp;
    }

//    //On calcule la température initiale telle que la probabilité d'accepter une solution moins bonnesoit de 0.8
//    public double setInitialTemperature() {
//       double delta = 0.0;
//        ArrayList<Solution> neighbors = new ArrayList<>();
//        //On génère 10 voisins pour avoir un échantillon
//        for(int i =0; i<10; i++) {
//            ArrayList<Route> newRoutes = new ArrayList<>();
//            newRoutes = initialSolution.modifySolution().getFirst();
//            while (newRoutes== null){
//                newRoutes = initialSolution.modifySolution().getFirst();
//            }
//            Solution newNeighbor = new Solution();
//            newNeighbor.setRoutes(newRoutes);
//            neighbors.add(newNeighbor);
//        }
//        for(Solution neighbor : neighbors) {
//            if (delta < neighbor.getTotalDistance() - this.initialSolution.getTotalDistance())
//                delta = neighbor.getTotalDistance() - this.initialSolution.getTotalDistance();
//        }
//        this.initialTemperature = delta/Math.log(0.8);
//        return delta;
//    }
//
//
//    public Solution generateSimulatedAnnealing() {
//        Solution bestSolution = this.initialSolution;
//        double bestDistance = this.initialSolution.getTotalDistance();
//        double currentTemp = this.setInitialTemperature();
//        Solution currentSolution = this.initialSolution;
//        System.out.println("distance = " + bestDistance);
//        while(currentTemp > finalTemperature) {
//            //on modifie la solution
//            System.out.println("temperatre : "+currentTemp);
//            //TODO à gérer
//            ArrayList<Route> newRoutes = new ArrayList<>();
//            newRoutes = initialSolution.modifySolution().getFirst();
//            while (newRoutes== null){
//                newRoutes = initialSolution.modifySolution().getFirst();
//            }
//            currentSolution.setRoutes(newRoutes);
//
//
//            Logistique.Route randRoute = currentSolution.getRandomRoute();
////
//            double currentDistance = currentSolution.getTotalDistance();
//
//            for(int l=0; l>movesAtTTemp; l++) {
//                //on fait une modif
//                //TODO les opérateurs de voisinnage lààààà
//                //Solution solutionToTest = la modif
//                Solution solutionToTest = null;
//
//                //Choper la liste de route
//                // l'insérer dans la solution
//
//                //on regarde si la nouvelle solution a une plus petite distance totale que l'autre
//                double distDifference = solutionToTest.getTotalDistance() - currentDistance;
//                if(distDifference <= 0) {
//                    currentSolution = solutionToTest;
//                    currentDistance = solutionToTest.getTotalDistance();
//                    if (currentDistance < bestDistance) {
//                        bestSolution = currentSolution;
//                        bestDistance = currentDistance;
//                    }
//                } else {
//                    double p = Math.random();
//                    if (p <= Math.exp(-distDifference/currentTemp)) {
//                        currentSolution = solutionToTest;
//                        currentDistance = solutionToTest.getTotalDistance();
//                    }
//                }
//            }
//            currentTemp -= decrease;
//        }
//        return bestSolution;
//    }
}