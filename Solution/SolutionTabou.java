package Solution;

import Logistique.InstanceVRP;
import Logistique.Route;
import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;
import java.util.Iterator;

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
            ArrayList<Pair<Solution, ArrayList<ArrayList<String>>>> visitedSolutions = new ArrayList<>();
            Pair<Solution, ArrayList<String>> currentPair = new Pair<>(currentSolution, currentAction);

            int k= 0;
            // Si une route a été vidée, on la supprime
            while (!visitedSolutions.contains(currentPair) && k<max) {
                    Iterator<Route> iterator = currentSolution.getRoutes().iterator();
                    while (iterator.hasNext()) {
                        Route route = iterator.next();
                        if (route.getListClient().size() < 3) {
                            iterator.remove(); // Utilisation de la méthode remove() de l'itérateur pour supprimer les routes vides
                        }
                    }

                    visitedSolutions.add(new Pair<>(currentSolution, tabuList));

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

                    if(k>495){
                        System.out.println("distance current solution "+ currentSolution.getTotalDistance());
                        System.out.println("distance distance modifiée "+ modifiedDistance);
                        System.out.println("solutiontoTest "+ solutionToTest.getTotalDistance());
                    }

                    modifiedDistance = solutionToTest.getTotalDistance();

                    if (modifiedDistance < bestDistance) {
                        bestDistance = modifiedDistance;
                        bestSolution = solutionToTest.cloneSolution();
                        bestSolution.getTotalDistance();
                        currentSolution = solutionToTest.cloneSolution();
                        currentSolution.getTotalDistance();
                        currentAction.clear();
                        currentAction.addAll(actionToTest);
                        currentPair = new Pair<>(currentSolution, currentAction);

                    } else if (modifiedDistance >= bestDistance) {
                        //Le mouvement est interdit
                        boolean isForbidden = true;
                        //Tant qu'il est interdit
                        while (isForbidden) {

                            if(!neighbors.isEmpty()) {
                                solutionToTest = neighbors.get(0).getFirst().cloneSolution();
                                actionToTest = neighbors.get(0).getSecond();
                            } else { return bestSolution;}
                            boolean actionInTabuList = false;

                            for (ArrayList<String> tabuAction : tabuList) {
                                if (actionToTest.size() == tabuAction.size()) {
                                    if (actionToTest.get(0).equals(tabuAction.get(0))) {
                                        switch (actionToTest.get(0)) {
                                            case "TwoOptSameRoute":
                                                if (actionToTest.get(1).equals(tabuAction.get(1))) {
                                                    if ((actionToTest.get(2).equals(tabuAction.get(2)) || actionToTest.get(2).equals(tabuAction.get(3))) &&
                                                            (actionToTest.get(3).equals(tabuAction.get(2)) || actionToTest.get(3).equals(tabuAction.get(3)))) {
                                                        actionInTabuList = true;

                                                        if(!neighbors.isEmpty()) {
                                                            neighbors.remove(0);
                                                        } else { return bestSolution;}
                                                        break;
                                                    }
                                                }
                                                break;
                                            case "Exchange":
                                                if ((actionToTest.get(1).equals(tabuAction.get(1)) && actionToTest.get(3).equals(tabuAction.get(3))) ||
                                                        (actionToTest.get(1).equals(tabuAction.get(3)) && actionToTest.get(3).equals(tabuAction.get(1)))) {
                                                    if ((actionToTest.get(2).equals(tabuAction.get(2)) && actionToTest.get(4).equals(tabuAction.get(4))) ||
                                                            (actionToTest.get(2).equals(tabuAction.get(4)) && actionToTest.get(4).equals(tabuAction.get(2)))) {
                                                        actionInTabuList = true;
                                                        if(!neighbors.isEmpty()) {
                                                            neighbors.remove(0);
                                                        } else { return bestSolution;}
                                                        break;
                                                    }
                                                }
                                                break;

                                            case "Relocate":
                                                //Cas d'un relocate intra
                                                if(actionToTest.get(1).equals(actionToTest.get(3))){
                                                    if( (actionToTest.get(2).equals(tabuAction.get(2))&&(actionToTest.get(4).equals(tabuAction.get(4))))
                                                            || (actionToTest.get(2).equals(tabuAction.get(4))&&(actionToTest.get(4).equals(tabuAction.get(2))))) {
                                                        actionInTabuList = true;
                                                        if(!neighbors.isEmpty()) {
                                                            neighbors.remove(0);
                                                        } else { return bestSolution;}
                                                        break;
                                                    }
                                                }else{
                                                    if ((actionToTest.get(2).equals(tabuAction.get(2))&&(actionToTest.get(4).equals(tabuAction.get(4)))&&(actionToTest.get(3).equals(tabuAction.get(3))))||
                                                            actionToTest.get(1).equals(tabuAction.get(3)) && (actionToTest.get(2).equals(tabuAction.get(4))) && (actionToTest.get(3).equals(tabuAction.get(1)) && (actionToTest.get(4).equals(tabuAction.get(2))))){
                                                        actionInTabuList = true;
                                                        if(!neighbors.isEmpty()) {
                                                            neighbors.remove(0);
                                                        } else { return bestSolution;}
                                                        break;
                                                    }
                                                }
                                                break;
                                            case "CrossExchange":
                                                if (actionToTest.get(1).equals(tabuAction.get(1)) && actionToTest.get(4).equals(tabuAction.get(4))) {
                                                    if (actionToTest.get(2).equals(tabuAction.get(2)) && actionToTest.get(5).equals(tabuAction.get(5))) {
                                                        if (actionToTest.get(3).equals(tabuAction.get(3)) && actionToTest.get(6).equals(tabuAction.get(6))) {
                                                            actionInTabuList = true;
                                                            if(!neighbors.isEmpty()) {
                                                                neighbors.remove(0);
                                                            } else { return bestSolution;}
                                                            break;
                                                        }
                                                    }
                                                }

                                                //Mouvement inverse
                                                //Calucle de la différence des indices

                                                int dif1 = Integer.parseInt(tabuAction.get(3)) - Integer.parseInt(tabuAction.get(2));
                                                int dif2 = Integer.parseInt(tabuAction.get(6)) - Integer.parseInt(tabuAction.get(5));
                                                String newIndex1 = String.valueOf(Integer.parseInt(actionToTest.get(2)) + dif2);
                                                String newIndex2 = String.valueOf(Integer.parseInt(actionToTest.get(5)) + dif1);
                                                //Vérification du mouvement inverse
                                                if (actionToTest.get(1).equals(tabuAction.get(1)) && actionToTest.get(4).equals(tabuAction.get(4))) {
                                                    if (actionToTest.get(2).equals(tabuAction.get(2)) && actionToTest.get(5).equals(tabuAction.get(5))) {
                                                        if (actionToTest.get(3).equals(newIndex1) && actionToTest.get(6).equals(newIndex2)) {
                                                            actionInTabuList = true;
                                                            if(!neighbors.isEmpty()) {
                                                                neighbors.remove(0);
                                                            } else { return bestSolution;}
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
                            //Si l'action n'est pas dans la liste Tabou on l'ajoute
                            if (!actionInTabuList) {
                                isForbidden = false;
                                currentSolution = solutionToTest.cloneSolution();
                                currentAction.clear();
                                currentAction.addAll(actionToTest);
                                currentPair = new Pair<>(currentSolution, currentAction);

                                if (tabuList.size() == this.sizeTabu) {
                                    tabuList.remove(tabuList.size() - 1);
                                }
                                tabuList.add(0, actionToTest);
                            }
                        }
                    } else {
                        currentSolution = solutionToTest.cloneSolution();
                        currentAction.clear();
                        currentAction.addAll(actionToTest);
                        currentPair = new Pair<>(currentSolution, currentAction);
                    }
                System.out.println("k = " + k);
                k++;
            }
        System.out.println("Tabou List size "+ tabuList.size());
        return bestSolution;
    }

}