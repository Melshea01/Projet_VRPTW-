import Logistique.*;
import Solution.Operateur;
import Solution.Solution;
import Solution.SolutionAleatoire;

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
        InstanceVRP instanceVRP = p.ParsingClientsFromFile("Data/datatest.vrp");

        //Instanciation de la solution 1
        Solution solution1 = solution.generateRandomSolution(instanceVRP);
        ArrayList<Route> routes = solution1.getRoutes();
        System.out.println(routes.size());

        //Test des opérateurs

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
            };
        }
    }
}
