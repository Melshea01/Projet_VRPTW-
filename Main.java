import Graphique.Visualisation2;
import Logistique.Client;
import Logistique.InstanceVRP;
import Logistique.Parsing;
import Logistique.Transport;
import Solution.Solution;
import Solution.SolutionAleatoire;

import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {

        System.setProperty("org.graphstream.ui", "swing");

        Parsing p = new Parsing();
        SolutionAleatoire solution = new SolutionAleatoire();
        int total_poid = 0;
        int nb_camion = 0;
        ArrayList<Transport> distance;

        InstanceVRP instanceVRP = p.ParsingClientsFromFile("Data/data102.vrp");

        /*Calcul de la capacité totale demandée par les clients*/
        for(Client c : instanceVRP.clients)
        {
            total_poid += c.getDemand();
        }

        /*Calcul du nombre minimum de véhicule nécessaire pour répondre aux besoins des clients*/
        if(nb_camion % instanceVRP.getCapacity() == 1){
            nb_camion = (total_poid/ instanceVRP.getCapacity());
        } else {nb_camion = (total_poid/ instanceVRP.getCapacity()) + 1;};

        System.out.println("nombre de camion nécessaire :"+ nb_camion + " Poids total transporté :" + total_poid);

        Solution transports = solution.generateRandomSolution(instanceVRP);

        /*
        //Vérification des routes
        double distance3 = 0;
        int i=0;
        for (Logistique.Transport transport: solution.transports) {
            Logistique.Route route = solution.transports.get(transport.getId()-1 ).getRoute();
            i++;
            System.out.println(i);
            System.out.println(route.getCoordonnees() + "distance total de chaque route "+ solution.transports.get(transport.getId()-1 ).getRoute().getDistance());
            distance3 += solution.transports.get(transport.getId()-1 ).getRoute().getDistance();
        }
        ArrayList<Client> clientroute1= new ArrayList<Client>();
        clientroute1 = distance.get(0).getRoute().getRoute();
        */


        Visualisation2.show(transports.getRoutes(),instanceVRP);


        System.out.println("nombre de transport final " + transports.getRoutes().size());
        System.out.println("distance final " + transports.getTotalDistance()) ;
    }

}

