import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Parsing p = new Parsing();
        SolutionAleatoire solution = new SolutionAleatoire();
        ArrayList<Transport> transports = new ArrayList<>();
        int total_poid = 0;
        int nb_camion = 0;
        double distance = 0.0;

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

        distance = solution.generateRandomSolution(instanceVRP);

        /*
        //Vérification des routes
        int i=0;
        for (Transport transport: solution.transports) {
            Route route = solution.transports.get(transport.getId()-1 ).getRoute();
            i++;
            System.out.println(i);
            System.out.println(route.getCoordonnees());
        }
        */



        System.out.println("nombre de transport final " + solution.transports.size());
        System.out.println("distance final " + distance);
    }

}

