package Solution;

import Logistique.InstanceVRP;
import Logistique.Route;
import org.apache.commons.math3.util.Pair;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class SolutionTabou extends Solution {

    private Solution initialSolution;
    private int sizeTabu;

    public SolutionTabou(Solution initialSolution, int sizeTabu, InstanceVRP vrp) {
        this.initialSolution = initialSolution;
        this.sizeTabu = sizeTabu;
        this.instanceVRP = vrp;
    }

    public Solution Tabu_search() {
        Solution bestSolution = this.initialSolution;
        double bestDistance = this.initialSolution.getTotalDistance();
        Solution currentSolution = this.initialSolution;
        ArrayList<ArrayList<String>> tabuList = new ArrayList<>();
        ArrayList<Solution> visitedSolutions = new ArrayList<>();

        while (!visitedSolutions.contains(currentSolution)) {
            visitedSolutions.add(currentSolution);

            Pair<ArrayList<Route>, ArrayList<String>> modificationToTest = new Pair<>(null,null);
            int tryModification = 0;

            do {
                modificationToTest = currentSolution.modifySolution();

                //Renvoie null à partir d'un moment
                tryModification++;
                if (tryModification == 100) {
                    return bestSolution;
                }
            } while (modificationToTest.getFirst() == null);


            Solution solutionToTest = new Solution();
            solutionToTest.setRoutes(modificationToTest.getFirst());
            ArrayList<String> actionToTest = new ArrayList<>(modificationToTest.getSecond());
            double modifiedDistance = solutionToTest.getTotalDistance();


            if (modifiedDistance < bestDistance) {
                bestDistance = modifiedDistance;
                bestSolution = solutionToTest;
                currentSolution = solutionToTest;
            } else if (modifiedDistance > bestDistance) {
                boolean actionInTabuList = false;

                for (ArrayList<String> tabuAction : tabuList) {
                    if (actionToTest.get(0).equals(tabuAction.get(0))) {
                        if (actionToTest.get(0).equals("TwoOptSameRoute")) {
                            if (actionToTest.get(1).equals(tabuAction.get(1))) {
                                if ((actionToTest.get(2).equals(tabuAction.get(2)) || actionToTest.get(2).equals(tabuAction.get(3))) &&
                                        (actionToTest.get(3).equals(tabuAction.get(2)) || actionToTest.get(3).equals(tabuAction.get(3)))) {
                                    actionInTabuList = true;
                                    break;
                                }
                            }
                        } else if (actionToTest.get(0).equals("ExchangeInter")) {
                            if ((actionToTest.get(1).equals(tabuAction.get(1)) && actionToTest.get(3).equals(tabuAction.get(3))) ||
                                    (actionToTest.get(1).equals(tabuAction.get(3)) && actionToTest.get(3).equals(tabuAction.get(1)))) {
                                if ((actionToTest.get(2).equals(tabuAction.get(2)) && actionToTest.get(4).equals(tabuAction.get(4))) ||
                                        (actionToTest.get(2).equals(tabuAction.get(4)) && actionToTest.get(4).equals(tabuAction.get(2)))) {
                                    actionInTabuList = true;
                                    break;
                                }
                            }
                        }else if (actionToTest.get(0).equals("RelocateInter")) {
                            if ((actionToTest.get(1).equals(tabuAction.get(1)) && actionToTest.get(3).equals(tabuAction.get(3))) ||
                                    (actionToTest.get(1).equals(tabuAction.get(3)) && actionToTest.get(3).equals(tabuAction.get(1)))) {
                                if ((actionToTest.get(2).equals(tabuAction.get(2)) && actionToTest.get(4).equals(tabuAction.get(4))) ||
                                        (actionToTest.get(2).equals(tabuAction.get(4)) && actionToTest.get(4).equals(tabuAction.get(2)))) {
                                    actionInTabuList = true;
                                    break;
                                }
                            }
                        }
                    }
                }

                if (!actionInTabuList) {
                    currentSolution = solutionToTest;

                    if (tabuList.size() == this.sizeTabu) {
                        tabuList.remove(tabuList.size() - 1);
                    }

                    tabuList.add(0, actionToTest);
                }
            } else {
                currentSolution = solutionToTest;
            }

            System.out.println(bestDistance);
        }

        return bestSolution;
    }

}