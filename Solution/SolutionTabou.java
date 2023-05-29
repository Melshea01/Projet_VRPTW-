package Solution;

import Logistique.Route;
import org.apache.commons.math3.util.Pair;

import java.sql.SQLOutput;
import java.util.ArrayList;

public class SolutionTabou extends Solution {

    private Solution initialSolution;
    private int sizeTabu;

    public SolutionTabou(Solution initialSolution, int sizeTabu) {
        this.initialSolution = initialSolution;
        this.sizeTabu = sizeTabu;
    }

    public Solution Tabu_search(){
        Solution bestSolution = this.initialSolution;
        double bestDistance = this.initialSolution.getTotalDistance();
        Solution currentSolution = this.initialSolution;
        ArrayList<ArrayList<String>> tabuList = new ArrayList<>();
        ArrayList<Solution> visitedSolution = new ArrayList<>();

        while(!visitedSolution.contains(currentSolution)) {
            visitedSolution.add(currentSolution);
            //On modifie la solution et on récupère l'action qui a été faite
            Pair<ArrayList<Route>, ArrayList<String>> modificationToTest;
            modificationToTest = currentSolution.modifySolution();
            int tryModification = 0;
            while(modificationToTest.getFirst() == null) {
                modificationToTest = currentSolution.modifySolution();
                System.out.println("modify");
                tryModification ++;
                if(tryModification == 100) {
                    return bestSolution;
                }
            }
            Solution solutionToTest = new Solution();
            solutionToTest.setRoutes(modificationToTest.getFirst());
            ArrayList<String> actionToTest= new ArrayList<>(modificationToTest.getSecond());
            int nbClients = 0;
            for (Route route : solutionToTest.getRoutes()) {
                int clientparroute = route.getListClient().size();
                System.out.println("clients parroute " + clientparroute);
                nbClients += clientparroute;
            }
            System.out.println("client " + nbClients);
            double modifiedDistance = solutionToTest.getTotalDistance();
            //si la solution qui vient d'être calculée est meilleure
            if (modifiedDistance < bestDistance) {
                bestDistance = modifiedDistance;
                bestSolution = solutionToTest;
                currentSolution = solutionToTest;

            //si la solution qui vient d'être calculée est moins bonne
            } else if(modifiedDistance > bestDistance) {
                //On vérifie que la modification n'est pas dans la liste tabou
                boolean actionInTabuList = false;
                for (ArrayList<String> tabouAction : tabuList) {
                    if(actionToTest.get(0).equals(tabouAction.get(0))) {
                        //si l'opérateur est twoOpt
                        if(actionToTest.get(0).equals("TwoOptSameRoute")) {
                            if(actionToTest.get(1).equals(tabouAction.get(1))){
                                if((actionToTest.get(2).equals(tabouAction.get(2)) || actionToTest.get(2).equals(tabouAction.get(3)))
                                        && (actionToTest.get(3)==tabouAction.get(2) || actionToTest.get(3).equals(tabouAction.get(3)))) {
                                    actionInTabuList = true;
                                }
                            }
                        //si l'opérateur est exchange inter
                        } else if (actionToTest.get(0).equals("ExchangeInter")) {
                            if((actionToTest.get(1).equals(tabouAction.get(1)) && actionToTest.get(3).equals(tabouAction.get(3)))
                            ||(actionToTest.get(1).equals(tabouAction.get(3)) && actionToTest.get(3).equals(tabouAction.get(1)))) {
                                if ((actionToTest.get(2).equals(tabouAction.get(2)) && actionToTest.get(4).equals(tabouAction.get(4)))
                                        ||(actionToTest.get(2).equals(tabouAction.get(4)) && actionToTest.get(4).equals(tabouAction.get(2)))) {
                                    actionInTabuList = true;
                                }
                            }
                        }
                    }
                }
                //si l'action n'est pas dans la liste
                if(!actionInTabuList) {
                    currentSolution = solutionToTest;
                    // on ajoute l'action à la liste tabou
                    if(tabuList.size() == this.sizeTabu) {
                        tabuList.remove(tabuList.size()-1);
                    }
                    tabuList.add(0, actionToTest);
                }

            //si la solution qui vient d'être calculée est équivalente
            } else if (modifiedDistance == bestDistance) {
                currentSolution = solutionToTest;
            }
            System.out.println(bestDistance);
        }
        return bestSolution;
    }
}