import Graphique.Visualisation;

import Logistique.*;
import Solution.Solution;
import Solution.SolutionAleatoire;
import Solution.SimulatedAnnealing;
import Solution.SolutionTabou;


import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        System.setProperty("org.graphstream.ui", "swing");

        Parsing p = new Parsing();

        int total_poid = 0;
        int nb_camion = 0;


//*********************************************** FICHIER A TESTER ************************************************************************
        InstanceVRP instanceVRP = p.ParsingClientsFromFile("Data/data101.vrp");

        /*Calcul de la capacité totale demandée par les clients*/
        for(Client c : instanceVRP.clients)
        {
            total_poid += c.getDemand();
        }
        SolutionAleatoire solution = new SolutionAleatoire();



//*********************************************** NOMBRE MINIMUM DE VEHICULES************************************************************************

        /*Calcul du nombre minimum de véhicule nécessaire pour répondre aux besoins des clients*/
        if(nb_camion % instanceVRP.getCapacity() == 1){
            nb_camion = (total_poid/ instanceVRP.getCapacity());
        } else {nb_camion = (total_poid/ instanceVRP.getCapacity()) + 1;}

        System.out.println("nombre de camion nécessaire :"+ nb_camion + " Poids total transporté :" + total_poid);

        Solution solution1 = solution.generateRandomSolution(instanceVRP);


//*********************************************** SOLUTION ALEATOIRE INITALE ************************************************************************
        Visualisation visu = new Visualisation(solution1.getRoutes(), instanceVRP.getClients());
        visu.updateGraph(solution1.getRoutes());

        ArrayList<Route> routes = solution1.getRoutes();
        int nbclients1 = 0;
        for (Route route: routes) {
            nbclients1 += route.getListClient().size();
        }
        System.out.println("nb de client au total = "+ nbclients1);
        System.out.println("nb route aléatoire" + routes.size());


        //décommenter l'algorithme à tester :
//**********************************************ALGORITHME RECUIT SIMULE**********************************************************************
        Instant start = Instant.now();
        // en paramètres : la solution initiale, la température finale, le nombre d'itérations par température, le taux de diminution
        SimulatedAnnealing recuit = new SimulatedAnnealing(solution1, 2, 50, 0.8);
        Solution solutionUpgrade = recuit.generateSimulatedAnnealing();
        Instant end = Instant.now();
        Duration interval = Duration.between(start, end);
        System.out.println("Execution time in seconds: " + interval.getSeconds());


//*****************************************************ALGORITHME TABOU**********************************************************************

//        Instant start = Instant.now();
//        SolutionTabou solutionTabou = new SolutionTabou(solution1, 50, solution.getInstanceVRP());
//        int max = 500;
//        Solution solutionUpgrade = solutionTabou.Tabu_search(max);
//        Instant end = Instant.now();
//        Duration interval = Duration.between(start, end);
//        System.out.println("Execution time in seconds: " + interval.getSeconds());

//*****************************************************FIN TABOU**********************************************************************


        try {
            visu.updateGraph(solutionUpgrade.getRoutes());
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        System.out.println("nombre de transport initial :" + solution1.getRoutes().size());
        System.out.println("fitness initiale :" + solution1.getTotalDistance()) ;

        System.out.println("nombre de transport final :" + solutionUpgrade.getRoutes().size());
        System.out.println("fitness finale :" + solutionUpgrade.getTotalDistance()) ;
    }

}

