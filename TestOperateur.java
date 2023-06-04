import Graphique.Visualisation;
import Logistique.*;
import Solution.Operateur;
import Solution.Solution;
import Solution.SolutionAleatoire;
import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;

public class TestOperateur {
    public static void main(String[] args) {
        //Logistique
        System.setProperty("org.graphstream.ui", "swing");


        //Choix du fichier
        InstanceVRP instanceVRP1 = Parsing.ParsingClientsFromFile("Data/datatestcross.vrp");
        Operateur o = new Operateur(instanceVRP1);
        SolutionAleatoire initSolution = new SolutionAleatoire();
        //Instanciation de la solution 1
        Solution solution1 = initSolution.generateRandomSolution(instanceVRP1);

        Visualisation visu = new Visualisation(solution1.getRoutes(), instanceVRP1.getClients());


        //Test Opérateur sur jeu de données
//********************************décommenter l'opérateur à tester***********************
        ArrayList<Pair<Solution, ArrayList<String>>> neighbors = new ArrayList<>();
        //neighbors = o.crossExchange(solution1);
        //neighbors = o.twoOpt(solution1);
        //neighbors = o.exchange(solution1);
        //neighbors = o.relocateIntra(solution1);
        neighbors = o.relocateInter(solution1);
        Solution soltemp = new Solution();
        soltemp = solution1.cloneSolution();

        for(Route route: solution1.getRoutes()) {
            System.out.println("route base :");
            for (Client client : route.getListClient()) {
                System.out.println("Client " + client.getIdName() + " - temps de début: (" + client.getReadyTime() + ", temps de fin " + client.getDueTime() + ")");
            }
            System.out.println(" ");
        }

        if(neighbors.get(0).getFirst() == null){
            System.out.println("Pas de voisin");
        }
        int i1 =0;
        while ( i1<10){

//*******************remplacer par l'opérateur à tester********************************
            neighbors = o.relocateInter(soltemp);
        if (neighbors.get(0).getFirst() != null) {
            for (int i = 0; i < neighbors.size(); i++) {
                double disttemp = soltemp.getTotalDistance();
                double distNeighbor = neighbors.get(i).getFirst().getTotalDistance();
                if (distNeighbor < disttemp) {
                    soltemp = neighbors.get(i).getFirst();
                    System.out.println(neighbors.get(i).getSecond());
                    for (Route route : soltemp.getRoutes()) {
                        System.out.println("route base :");
                        for (Client client : route.getListClient()) {
                            System.out.println("Client " + client.getIdName() + " - temps de début: (" + client.getReadyTime() + ", temps de fin " + client.getDueTime() + ")");
                        }
                        System.out.println(" ");
                    }
                    try {
                        visu.updateGraph(soltemp.getRoutes());
                        Thread.sleep(3000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    visu.updateGraph(soltemp.getRoutes());
                    for (Route route : soltemp.getRoutes()) {
                        System.out.println("route modif :");
                        for (Client client : route.getListClient()) {
                            System.out.println("Client " + client.getIdName() + " - temps de début: (" + client.getReadyTime() + ", temps de fin " + client.getDueTime() + ")");
                        }
                        System.out.println(" ");
                    }
                }
            }
            i1 ++;
        }
        }
        System.out.println("Finish");
    }
}