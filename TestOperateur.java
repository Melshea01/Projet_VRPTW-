import Graphique.Visualisation2;
import Logistique.*;
import Solution.Operateur;
import Solution.Solution;
import Solution.SolutionAleatoire;
import org.graphstream.graph.Graph;

import java.util.ArrayList;

public class TestOperateur {
    public static void main(String[] args) {
        //Logistique
        System.setProperty("org.graphstream.ui", "swing");
        Parsing p = new Parsing();
        SolutionAleatoire solution = new SolutionAleatoire();
        Operateur o = new Operateur();
        int total_poid = 0;
        int nb_camion = 0;
        ArrayList<Transport> distance;

        //Choix du fichier
        //TODO : Correction Solution generate random consomme les données de Instance VRP d'ou des erreurs, corriger cette erreur
        InstanceVRP instanceVRP1 = Parsing.ParsingClientsFromFile("Data/datatest.vrp");
        InstanceVRP instanceVRP2 = Parsing.ParsingClientsFromFile("Data/datatest.vrp");

        //Instanciation de la solution 1
        Solution solution1 = solution.generateRandomSolution(instanceVRP1);
        Solution newSolution = new Solution();
        ArrayList<Route> routes = solution1.getRoutes();
        System.out.println(routes.size());

        Visualisation2 visu = new Visualisation2(solution1.getRoutes(), instanceVRP2.getClients());

        //Test des opérateurs twoOpt

        /*for (int i=0; i<solution1.getRoutes().size(); i++ ){
            //Variable stockant la route testée
            Route routetest = solution1.getRoutes().get(i);
            System.out.println("route de base "+ routetest.clients);
            for (Client client : routetest.clients) {
                System.out.println("Client " + client.getIdName() + " - temps de début: (" + client.getReadyTime() + ", temps de fin " + client.getDueTime()+ ")");
            }
            if(o.twoOptSameRoute(routetest) != null){
                //Changement de la route
                routetest= o.twoOptSameRoute(routetest);
                solution1.replaceRoute(solution1,routetest);
                ArrayList<Client> clients = routetest.clients;
                System.out.println("route modifié :");
                for (Client client : clients) {
                    System.out.println("Client " + client.getIdName() + " - temps de début: (" + client.getReadyTime() + ", temps de fin " + client.getDueTime()+ ")");
                }
                Visualisation2.show(solution1.getRoutes(),instanceVRP);
            }
        }*/

        int i1 = 0;
        Route routetest = solution1.getRoutes().get(0);
        while (i1 < 5) {
            System.out.println("route de base " + routetest.clients);
            for (Client client : routetest.clients) {
                System.out.println("Client " + client.getIdName() + " - temps de début: (" + client.getReadyTime() + ", temps de fin " + client.getDueTime() + ")");
            }
            if (o.twoOptSameRoute(routetest) != null) {
                routetest = o.twoOptSameRoute(routetest);
                newSolution = solution1.replaceRoute(solution1, routetest);
                ArrayList<Client> clients = routetest.clients;
                System.out.println("route modifié :");
                for (Client client : clients) {
                    System.out.println("Client " + client.getIdName() + " - temps de début: (" + client.getReadyTime() + ", temps de fin " + client.getDueTime() + ")");
                }

                try {
                    visu.updateGraph(newSolution.getRoutes());
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }


            }
            i1++;
        }


            //Test opérateur
/*
        for (int i=0; i<solution1.getRoutes().size(); i++ ){
            Route routetest = solution1.getRoutes().get(i);
            System.out.println("route de base "+ routetest.clients);
            for (Client client : routetest.clients) {
                System.out.println("Client " + client.getIdName() + " - temps de début: (" + client.getReadyTime() + ", temps de fin " + client.getDueTime()+ ")");
            }
            if(o.twoOptSameRoute(routetest) != null){
                routetest= o.twoOptSameRoute(routetest);
                ArrayList<Client> clients = routetest.clients;
                System.out.println("route modifié :");
                for (Client client : clients) {
                    System.out.println("Client " + client.getIdName() + " - temps de début: (" + client.getReadyTime() + ", temps de fin " + client.getDueTime()+ ")");
                }
            }
        }

 */
        }
    }