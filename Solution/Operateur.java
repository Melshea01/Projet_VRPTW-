package Solution;

import Logistique.Route;
import Logistique.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static java.lang.Math.*;

public class Operateur {


    //Créer la fonction reverse qui va inverser une route, il faut ensuite si c'est possible en terme de fenêtre de temps (impossible car pas orienter)
    //TODO : Créer la fonction capable d'échanger deux aretes, two_opt
    public Route twoOpt(Route route){
        boolean improve = true;
        while (improve){
            improve = false;
            Client clientinitial = route.getListClient().get(0);
            //Pour tous les clients de la route
            for (Client c : route.getListClient()){
                int index = route.getListClient().indexOf(c);
                //Essayer de le placer sur toutes les ok
                //pour tous les clients ayant un index différent de i+1 et i-1 et de 0
            }

        }
        Route newroute = new Route();
        return newroute;
    }

    //TODO : Renvoyer une solution
    public Route twoOptSameRoute(Route route) {
        int size = route.getListClient().size();
        Route newRoute = route.cloneRoute(route);
        ArrayList<Route> routesPossibles = new ArrayList<>();

        //Calcule de toutes les routes randoms
        for (int i = 1; i < size - 2; i++) {
            for (int j = i + 2; j < size - 1; j++) {
                Route tempRoute = newRoute.cloneRoute(newRoute);
                Collections.reverse(tempRoute.getListClient().subList(i, j));
                if (tempRoute.isFeasible()) {
                    routesPossibles.add(tempRoute);
                }
            }
        }

        //Choix d'une route possible
        Random random = new Random();
        int randomIndex = random.nextInt(routesPossibles.size());
        newRoute= route.cloneRoute(routesPossibles.get(randomIndex));
        return newRoute;
    }



/*    Cette méthode va parcourir toutes les positions possibles d'insertion du client dans la route, puis calculer l'heure
    d'arrivée à cette position avec la méthode calculateArrivalTime. Ensuite, elle vérifie si l'heure d'arrivée respecte la
    contrainte de temps du client avec la méthode isFeasible. Elle ne renvoie rien si il n'y aucun changement*/

    /* Cette méthode va parcourir toutes les positions possibles d'insertion du client dans la route, puis calculer l'heure
    d'arrivée à cette position avec la méthode calculateArrivalTime. Ensuite, elle vérifie si l'heure d'arrivée respecte la
    contrainte de temps du client avec la méthode isFeasible. Elle ne renvoie rien si il n'y aucun changement*/

    /*
     * Route1 : Route de d'origine du client
     * Client à changer de route
     * capacité de l'instance VRP
     * */

    //Retourner une solution avec deux routes modifier
    public ArrayList<Route> relocateInter(Solution solution, Route routeOrigine, int capacity) {
        ArrayList<Route> routes = solution.getRoutes();
        Route newRouteArrive = new Route();
        int indexRouteOrigine = routes.indexOf(routeOrigine);

        // On copie la liste de clients liée à la route
        ArrayList<Client> listClientsOrigine = new ArrayList<>(routeOrigine.getListClient());

        //On choisit un client random dans la route1
        if(routeOrigine.getListClient().size() < 3) {
            return  null;
        }
        Random random = new Random();
        int randomIndex = random.nextInt(listClientsOrigine.size() - 2) + 1; // Génère un index aléatoire dans la plage valide
        Client client = listClientsOrigine.get(randomIndex);


        // Pour toutes les routes de la solution, on teste où on peut insérer le client
        for (int i = 0; i < routes.size(); i++) {
            //On saute la route d'origine
            if (i == indexRouteOrigine) {
                continue;
            }
            Route route = routes.get(i);

            if (route.getTotalDemandRoute() + client.getDemand() > capacity ) {
                return null;
            } else {
                //On copie la liste de client de la nouvelle route
                ArrayList<Client> ListClientTemp = (ArrayList<Client>) new ArrayList<>(route.getListClient()).clone();

                // On insère le client au début de la route
                ListClientTemp.add(1, client);
                newRouteArrive.setClients(ListClientTemp);

                // On essaie d'insérer le client à une place
                if (relocateIntra(newRouteArrive) != null) {
                    newRouteArrive = relocateIntra(newRouteArrive);

                    // On enlève le client de la première route
                    listClientsOrigine.remove(client);
                    routeOrigine.setClients(listClientsOrigine);

                    // Remplacement des deux routes l'ArrayList 'routes'
                    routes.set(i, newRouteArrive);
                    return routes;
                } else {
                    return null;
                }
            }
        }
        return null;
    }

    //Vérifie si l'échange entre deux clients est possible
    /*Si c'est le cas, elle calcule un score représentant l'écart entre
    l'heure d'arrivée et le début de la fenêtre de temps, et garde en mémoire la position où ce score est le plus faible.
     Finalement, elle insère le client à cette position et retourne true, ou bien retourne false si l'ajout n'a pas été possible.*/

    public Route relocateIntra(Route route){
        Route newRoute = new Route();
        int size = route.getListClient().size();
        ArrayList<Client> originalListClient = new ArrayList<>(route.getListClient());
        ArrayList<Client> ListClientTemp = new ArrayList<>();
        ArrayList<Route> routesPossibles = new ArrayList<>();

        for (int i=1;i<size-1;i++){
            Client client = route.getListClient().get(i);
            for(int j=i+1; j < size-2;j++){
                ListClientTemp.addAll(originalListClient);
                //enlève le client concerné
                ListClientTemp.remove(i) ;
                //Ajout du client dans la liste
                ListClientTemp.add(j,client);
                //Modification de la route
                Route tempRoute = new Route();
                tempRoute.cloneRoute(route);
                tempRoute.setClients(ListClientTemp);
                //Calcule du temps d'arrivée
                double arrivalTime = tempRoute.calculateArrivalTime(ListClientTemp.get(j-1), ListClientTemp.get(j));
                if (client.isFeasible(arrivalTime) && i!=j){
                    System.out.println("Taille route " + tempRoute.getListClient().size() + " i = "+i+" j = "+j );
                    routesPossibles.add(tempRoute);
                }
                //On enlève les client
                ListClientTemp.clear();
            }

        }

        if (!routesPossibles.isEmpty() ) {
            Random random = new Random();
            System.out.println("taille de la route = " + routesPossibles.size());
            int randomIndex = random.nextInt(routesPossibles.size());
            System.out.println("randomIndex = " + randomIndex);
            newRoute= routesPossibles.get(randomIndex);
            return newRoute;
        } else {
            return null;
        }

    }

    /*
    * Méthode qui échange deux client de la même route
    * */
    public ArrayList<Route> exchangeIntra(ArrayList<Route> routes) {
        // On choisit une route au hasard parmi celles disponibles
        Random random = new Random();
        int indexAleatoire = random.nextInt(routes.size());
        Route selectedRoute = routes.get(indexAleatoire);


        // Liste des solutions possibles
        ArrayList<Route> solutions = new ArrayList<>();

        // On teste tous les clients de la route pour les échanges possibles
        for (int i = 1; i <  selectedRoute.getListClient().size() - 1; i++) {
            for (int j = i + 1; j <  selectedRoute.getListClient().size() - 1; j++) {

                Route clonedRoute = selectedRoute.cloneRoute(selectedRoute);

                // On effectue l'échange de place entre les clients
                Client client1 = clonedRoute.getListClient().get(i);
                Client client2 = clonedRoute.getListClient() .get(j);

                // On échange les positions des clients dans la liste
                clonedRoute.clients.set(i, client2);
                clonedRoute.clients.set(j, client1);

                // Si la solution est réalisable, on l'ajoute à la liste des solutions
                if (clonedRoute.isFeasible()) {
                    solutions.add(clonedRoute.cloneRoute(clonedRoute));
                }

                // On restaure l'ordre initial des clients
                clonedRoute.clients.set(i, client2);
                clonedRoute.clients.set(j, client1);
            }
        }

        // Si des solutions sont disponibles, on en choisit une au hasard
        if (!solutions.isEmpty()) {
            Random random2 = new Random();
            int randomIndex = random2.nextInt(solutions.size());
            Route selectedSolution = solutions.get(randomIndex);
            System.out.println("Index route modif " +  indexAleatoire);

            // On remplace la route sélectionnée dans l'ArrayList d'origine par la solution choisie
            routes.set(indexAleatoire, selectedSolution);
            return routes;
        }

        // On null s'il n'y aucune modification
        return null;

    }


    //TODO : Retourner une solution pour stocker les deux nouvelles routes
    public static boolean exchangeInter(Client c1, Client c2, Route route1, Route route2,int capacity) {

        //On récupère une liste client de chaque route
        ArrayList<Client> clientroute1 = route1.getListClient();
        ArrayList<Client> clientroute2 = route2.getListClient();

        int demand1 = route1.getTotalDemandRoute();
        int demand2 = route2.getTotalDemandRoute();

        //On verifie la contrainte de capacité, il faut qu'on calcule la somme des demandes pour chaque route qu'on enlève l'ancienne
        //valeur et qu'on la remplace avec la nouvelle
        if(demand1- c1.getDemand() + c2.getDemand()>capacity ){
            return false ;
        }

        if(demand2- c2.getDemand() + c1.getDemand()>capacity ){
            return false ;
        }
        //Récupération de l'index de c1 et c2 pour récupérer le client avant eux dans la liste
        int idC1 = clientroute1.indexOf(c1);
        int idC2 = clientroute2.indexOf(c2);
        //Contraintes de temps
        //Calcul du temps d'arrivée du client 1 chez c2
        double arrivaltime_c1 =sqrt(pow(clientroute1.get(idC1-1).getX() - c2.getX(), 2) + pow(clientroute1.get(idC1-1).getY()-c2.getY(), 2));

        //Calcul du temps d'arrivée du client 2 chez c1
        double arrivaltime_c2 =sqrt(pow(clientroute2.get(idC2-1).getX() - c1.getX(), 2) + pow(clientroute2.get(idC2-1).getY()-c1.getY(), 2));


        if (c1.getDueTime() < arrivaltime_c2 || c2.getDueTime() < arrivaltime_c1) {
            // l'échange n'est pas possible car les fenêtres de temps ne se chevauchent pas
            return false ;
        }

        if (c1.getReadyTime() > arrivaltime_c2 || c2.getReadyTime()> arrivaltime_c1) {
            // l'échange n'est pas possible car les fenêtres de temps ne se chevauchent pas
            return false ;
        }
        return true;
    }


}
