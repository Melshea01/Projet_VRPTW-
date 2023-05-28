package Solution;

import java.util.ArrayList;

public class SolutionTabou extends Solution {

    private Solution initialSolution;
    private int sizeTabu;

    public SolutionTabou(Solution initialSolution, int sizeTabu) {
        this.initialSolution = initialSolution;
        this.sizeTabu = sizeTabu;
    }

//    public Solution Tabu_search(){
//        Solution bestSolution = this.initialSolution;
//        double bestDistance = this.initialSolution.getTotalDistance();
//        Solution currentSolution = this.initialSolution;
//        //TODO gérer la liste tabou
//        ArrayList<Solution> tabuList = new ArrayList<>();
//        ArrayList<Solution> visitedSolution = new ArrayList<>();
//
//        while(!visitedSolution.contains(currentSolution)) {
//            visitedSolution.add(currentSolution);
//            double currentDistance = currentSolution.getTotalDistance();
//            if (currentDistance < bestDistance) {
//                bestDistance = currentDistance;
//                bestSolution = currentSolution;
//            } else {
//                tabuList.add(0,currentSolution);
//                if(tabuList.size() > sizeTabu) {
//                    tabuList.remove(tabuList.size());
//                }
//            }
//            Solution modifiedSolution = currentSolution.modifySolution();
//            while(tabuList.contains(modifiedSolution)){
//                //currentSolution.modifySolution()
//            }
//
//        }
//
//        return solution;
//    }
}