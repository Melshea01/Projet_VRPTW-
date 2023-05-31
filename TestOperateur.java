import Graphique.Visualisation;
import Graphique.Visualisation2;
import Logistique.*;
import Solution.Operateur;
import Solution.Solution;
import Solution.SolutionAleatoire;
import org.apache.commons.math3.util.Pair;
import org.graphstream.graph.Graph;

import java.util.ArrayList;

public class TestOperateur {
    public static void main(String[] args) {
        //Logistique
        System.setProperty("org.graphstream.ui", "swing");
        Parsing p = new Parsing();
        int total_poid = 0;
        int nb_camion = 0;
        ArrayList<Transport> distance;

        //Choix du fichier
        //TODO : Correction Solution generate random consomme les données de Instance VRP d'ou des erreurs, corriger cette erreur
        InstanceVRP instanceVRP1 = Parsing.ParsingClientsFromFile("Data/datatestcross.vrp");
        InstanceVRP instanceVRP2 = Parsing.ParsingClientsFromFile("Data/datatestcross.vrp");
        Operateur o = new Operateur();
        SolutionAleatoire initSolution = new SolutionAleatoire();
        //Instanciation de la solution 1
        Solution solution1 = initSolution.generateRandomSolution(instanceVRP1);
        Solution solution2 = new Solution();

        Visualisation visu = new Visualisation(solution1.getRoutes(), instanceVRP2.getClients());

        /*
         * Test Opérateur sur jeu de données
         * */

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
            for (Client client : route.clients) {
                System.out.println("Client " + client.getIdName() + " - temps de début: (" + client.getReadyTime() + ", temps de fin " + client.getDueTime() + ")");
            }
            System.out.println(" ");
        }

        if(neighbors == null){
            System.out.println("Pas de voisin");
        }

        if (!neighbors.isEmpty()) {
            for(int i = 0 ; i < neighbors.size() ; i++) {
                double disttemp = soltemp.getTotalDistance();
                double distNeighbor = neighbors.get(i).getFirst().getTotalDistance();
                if(distNeighbor < disttemp) {
                    soltemp = neighbors.get(i).getFirst();
                    System.out.println(neighbors.get(i).getSecond());
                    for(Route route: soltemp.getRoutes()) {
                        System.out.println("route base :");
                        for (Client client : route.clients) {
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
                }
                else {
                    visu.updateGraph(soltemp.getRoutes());
                    for(Route route: soltemp.getRoutes()) {
                        System.out.println("route modif :");
                        for (Client client : route.clients) {
                            System.out.println("Client " + client.getIdName() + " - temps de début: (" + client.getReadyTime() + ", temps de fin " + client.getDueTime() + ")");
                        }
                        System.out.println(" ");
                    }
                }
            }
        }

//        ArrayList<Route> routetemp = new ArrayList<>();
//        int i1 = 0;
//        solution2.setRoutes(soltemp.getRoutes());
//        while (i1 < 100) {
//            for(int i =0; i<solution2.getRoutes().size(); i++){
//                ArrayList<Client> clients = solution2.getRoutes().get(i).getListClient();
//                System.out.println("route base :" + i );
////                for (Client client : clients) {
////                    System.out.println("Client " + client.getIdName() + " - temps de début: (" + client.getReadyTime() + ", temps de fin " + client.getDueTime() + ")");
////                }
//            }
//            //routetemp = o.crossExchange(routes).getFirst();
//            //routetemp = o.exchangeIntra(routes).getFirst();
//            //routetemp = o.relocateIntra(routes).getFirst();
//            //routetemp = o.twoOptSameRoute(routes).getFirst();
//            routetemp = soltemp.getRoutes();
//            if (routetemp != null) {
//                solution2.setRoutes(routetemp);
//
////                for(int i =0; i<solution2.getRoutes().size(); i++){
////                    ArrayList<Client> clients = routetemp.get(i).getListClient();
////                    System.out.println("route modifié :" + i );
////                    for (Client client : clients) {
////                        System.out.println("Client " + client.getIdName() + " - temps de début: (" + client.getReadyTime() + ", temps de fin " + client.getDueTime() + ")");
////                    }
////                }
//
//                //Visualisation
//                try {
//                    visu.updateGraph(soltemp.getRoutes());
//                    Thread.sleep(3000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//            else visu.updateGraph(solution2.getRoutes());
//            i1++;
//        }

        System.out.println("Finish");

    }

    /*
     * Test Opérateur RelocateInter
     * */


//        ArrayList<Route> routetemp = new ArrayList<>();
//        int i1 = 0;
//        Route routetest = solution1.getRoutes().get(1);
//        solution2.setRoutes(solution1.getRoutes());
//        while (i1 < 5) {
//            System.out.println("route de base " + routetest.clients);
//            for (Client client : routetest.clients) {
//                System.out.println("Client " + client.getIdName() + " - temps de début: (" + client.getReadyTime() + ", temps de fin " + client.getDueTime() + ")");
//            }
//            routetemp = o.relocateInter(solution2,routetest , instanceVRP1.getCapacity());
//            if (routetemp != null) {
//                solution2.setRoutes(routetemp);
//                ArrayList<Client> clients = routetest.clients;
//                System.out.println("route modifié :");
//                for (Client client : clients) {
//                    System.out.println("Client " + client.getIdName() + " - temps de début: (" + client.getReadyTime() + ", temps de fin " + client.getDueTime() + ")");
//                }
//                try {
//                    visu.updateGraph(solution2.getRoutes());
//                    Thread.sleep(3000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//            else visu.updateGraph(solution2.getRoutes());
//            i1++;
//        }

}