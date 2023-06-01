package Solution;

import Logistique.InstanceVRP;
import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;

public class SolutionTabou extends Solution {

    private final Solution initialSolution;
    private final int sizeTabu;

    public SolutionTabou(Solution initialSolution, int sizeTabu, InstanceVRP vrp) {
        this.initialSolution = initialSolution;
        this.sizeTabu = sizeTabu;
        this.instanceVRP = vrp;
    }

    public Solution Tabu_search(int max) {
        InstanceVRP vrp = initialSolution.getInstanceVRP();
        Solution bestSolution = this.initialSolution;
        double bestDistance = this.initialSolution.getTotalDistance();
        Solution currentSolution = this.initialSolution;
        ArrayList<String> currentAction = new ArrayList<>();
        ArrayList<ArrayList<String>> tabuList = new ArrayList<>();
        ArrayList<Pair<Solution, ArrayList<String>>> visitedSolutions = new ArrayList<>();
        Pair<Solution, ArrayList<String>> currentPair = new Pair<>(currentSolution, currentAction);

        int k = 0;
        // Si une route a été vidée, on la supprime
        while (!visitedSolutions.contains(currentPair) && k < max) {

            currentSolution.getRoutes().removeIf(route -> route.getListClient().size() < 3);

            visitedSolutions.add(new Pair<>(currentSolution, currentAction));

            Operateur o = new Operateur(vrp);
            ArrayList<Pair<Solution, ArrayList<String>>> neighbors = new ArrayList<>();

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

            //Renvoie null à partir d'un moment
            if (neighbors.isEmpty()) {
                return bestSolution;
            }
            Solution solutionToTest = new Solution();
            ArrayList<String> actionToTest = new ArrayList<>();

            if (neighbors.contains(currentSolution)) {
                neighbors.remove(currentSolution);
            }

            double modifiedDistance = Double.POSITIVE_INFINITY;
            // On récupère le meilleur voisin
            for (int i = 0; i < neighbors.size(); i++) {
                if (neighbors.get(i).getFirst() != null) {
                    double neighborDistance = neighbors.get(i).getKey().getTotalDistance();
                    if (neighborDistance <= modifiedDistance) {
                        modifiedDistance = neighborDistance;
                        if (modifiedDistance != currentSolution.getTotalDistance()) {
                            solutionToTest = neighbors.get(i).getFirst();
                            actionToTest = neighbors.get(i).getSecond();
                        }
                    }
                }
            }

            modifiedDistance = solutionToTest.getTotalDistance();

            if (modifiedDistance < bestDistance) {
                bestDistance = modifiedDistance;
                bestSolution = solutionToTest;
                currentSolution = solutionToTest;
                currentAction.addAll(actionToTest);
                currentPair = new Pair<>(currentSolution, currentAction);

            } else if (modifiedDistance > bestDistance) {
                boolean actionInTabuList = false;
                for (ArrayList<String> tabuAction : tabuList) {
                    if (actionToTest.size() == tabuAction.size()) {
                        if (actionToTest.get(0).equals(tabuAction.get(0))) {
                            switch (actionToTest.get(0)) {
                                case "TwoOptSameRoute":
                                    if (actionToTest.get(1).equals(tabuAction.get(1))) {
                                        if ((actionToTest.get(2).equals(tabuAction.get(2)) && actionToTest.get(3).equals(tabuAction.get(3)))) {
                                            actionInTabuList = true;
                                            break;
                                        }
                                    }
                                    break;
                                case "Exchange":
                                    if (actionToTest.get(1).equals(tabuAction.get(1)) && actionToTest.get(3).equals(tabuAction.get(3))) {
                                        if (actionToTest.get(2).equals(tabuAction.get(2)) && actionToTest.get(4).equals(tabuAction.get(4))) {
                                            actionInTabuList = true;
                                            break;
                                        }
                                    }
                                    if (actionToTest.get(1).equals(tabuAction.get(3)) && actionToTest.get(3).equals(tabuAction.get(1))) {
                                        if (actionToTest.get(2).equals(tabuAction.get(4)) && actionToTest.get(4).equals(tabuAction.get(2))) {
                                            actionInTabuList = true;
                                            break;
                                        }
                                    }
                                    break;
                                case "Relocate":
                                    if ((actionToTest.get(3).equals(tabuAction.get(1))) && (actionToTest.get(1).equals(tabuAction.get(3)))) {
                                        if (actionToTest.get(4).equals(tabuAction.get(2)) && actionToTest.get(2).equals(tabuAction.get(4))) {
                                            actionInTabuList = true;
                                            break;
                                        }
                                    }
                                    break;
                                case "CrossExchange":
                                    if (actionToTest.get(1).equals(tabuAction.get(1)) && actionToTest.get(4).equals(tabuAction.get(4))) {
                                        if (actionToTest.get(2).equals(tabuAction.get(5)) && actionToTest.get(3).equals(tabuAction.get(6))) {
                                            if (actionToTest.get(5).equals(tabuAction.get(2)) && actionToTest.get(6).equals(tabuAction.get(3))) {
                                                actionInTabuList = true;
                                                break;
                                            }
                                        }
                                    }
                                    break;

                                default:
                                    break;
                            }

                        }
                    }
                }
                if (!actionInTabuList) {
                    currentSolution = solutionToTest;
                    currentAction.addAll(actionToTest);
                    currentPair = new Pair<>(currentSolution, currentAction);

                    if (tabuList.size() == this.sizeTabu) {
                        tabuList.remove(tabuList.size() - 1);
                    }

                    tabuList.add(0, actionToTest);
                }
            } else {
                currentSolution = solutionToTest;
                currentAction.addAll(actionToTest);
                currentPair = new Pair<>(currentSolution, currentAction);
            }
            System.out.println("k = " + k);
            k++;
            System.out.println(bestDistance);
        }
        System.out.println("Tabou List size " + tabuList.size());
        return bestSolution;
    }

}