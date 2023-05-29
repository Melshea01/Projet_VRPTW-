package Solution;

import Logistique.Route;
import Logistique.*;
import org.apache.commons.math3.util.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static java.lang.Math.*;

public class Operateur {
    private int capacity;


    public Operateur(int capacity) {
        this.capacity = capacity;
    }

    /*
    * Opérateur qui va echanger deux arêtes de la route
    * */
    public Pair<ArrayList<Route>, ArrayList<String>>  twoOptSameRoute(ArrayList<Route> routes) {
        // On choisit une route au hasard parmi celles disponibles
        Random random = new Random();
        int indexAleatoire = random.nextInt(routes.size());
        Route selectedRoute = routes.get(indexAleatoire);
        ArrayList<String> action = new ArrayList<>();

        // Vérifier si la route sélectionnée a au moins deux clients
        if (selectedRoute.getListClient().size() < 5) {
            action.add(0,"Pas assez de clients dans les routes selectionnées");
            return new Pair<>(null, action);
        }

        int size = selectedRoute.getListClient().size();
        Route newRoute = selectedRoute.cloneRoute();
        ArrayList<Route> routesPossibles = new ArrayList<>();
        ArrayList<Pair<String, String>> clientsExchanged = new ArrayList<>();

        //Calcule de toutes les routes randoms
        for (int i = 1; i < size - 2; i++) {
            for (int j = i + 2; j < size - 1; j++) {
                Route tempRoute = newRoute.cloneRoute(newRoute);
                Collections.reverse(tempRoute.getListClient().subList(i, j));
                if (tempRoute.isFeasible(this.capacity)) {
                    routesPossibles.add(tempRoute);
                    clientsExchanged.add(new Pair<>(Integer.toString(i), Integer.toString(j)));
                }
            }
        }

        //Choix d'une route possible
        if (!routesPossibles.isEmpty() ) {
            int randomIndex = random.nextInt(routesPossibles.size());
            newRoute = routesPossibles.get(randomIndex);
            routes.set(indexAleatoire, newRoute);
            //System.out.println(listeMouvement.get(randomIndex));
            String client1 = clientsExchanged.get(indexAleatoire).getKey();
            String client2 = clientsExchanged.get(indexAleatoire).getValue();
            action.add("TwoOptSameRoute");
            action.add(Integer.toString(indexAleatoire));
            action.add(client1);
            action.add(client2);

            return new Pair<>(routes, action);
        } else {
            // On retourne null s'il n'y aucune modification
            action.add(0,"Aucun échange réalisable");
            return new Pair<>(routes, action);
        }

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

    public ArrayList<Route> relocateInter( ArrayList<Route>  routes, int capacity) {
        Route newRouteArrive = new Route();

        //On choisit au hasard une route origine
        Random random = new Random();
        int indexRouteOrigine = random.nextInt(routes.size());
        Route selectedRoute = routes.get(indexRouteOrigine);

        // On copie la liste de clients liée à la route
        ArrayList<Client> listClientsOrigine = new ArrayList<>(selectedRoute.getListClient());

        //On choisit un client random dans la route1
        if(selectedRoute.getListClient().size() < 3) {
            return  null;
        }

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
                    selectedRoute.setClients(listClientsOrigine);

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

    /*
    * Change de place un client dans une route précise
    * */

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
    * change de place un client d'une des routes choisie aléaoirement parmis celle d'une solution
    * */

    public Pair<ArrayList<Route>, String> relocateIntra(ArrayList<Route> routes){
        // On choisit une route au hasard parmi celles disponibles
        Random random = new Random();
        int indexAleatoire = random.nextInt(routes.size());
        Route selectedRoute = routes.get(indexAleatoire);

        int size = selectedRoute.getListClient().size();
        ArrayList<Client> originalListClient = new ArrayList<>(selectedRoute.getListClient());
        ArrayList<Client> ListClientTemp = new ArrayList<>();
        ArrayList<Route> routesPossibles = new ArrayList<>();
        List<String> listeMouvement = new ArrayList<>();

        // Vérifier si la route sélectionnée a au moins deux clients
        if (selectedRoute.getListClient().size() < 4) {
            return new Pair<>(null, "Pas assez de clients dans les routes selectionnées");
        }


        for (int i=1;i<size-1;i++){
            Client client = selectedRoute.getListClient().get(i);
            for(int j=i+1; j < size-2;j++){
                ListClientTemp.addAll(originalListClient);
                //enlève le client concerné
                ListClientTemp.remove(i) ;
                //Ajout du client dans la liste
                ListClientTemp.add(j,client);
                //Modification de la route
                Route tempRoute = new Route();
                tempRoute.cloneRoute(selectedRoute);
                tempRoute.setClients(ListClientTemp);
                //Calcule du temps d'arrivée
                double arrivalTime = tempRoute.calculateArrivalTime(ListClientTemp.get(j-1), ListClientTemp.get(j));
                if (client.isFeasible(arrivalTime) && i!=j){
                    routesPossibles.add(tempRoute);
                    listeMouvement.add("Opérateur : RelocateIntra ; "+"Route : "+ indexAleatoire +" ; Clients  "+client.getIdName());

                }
                //On enlève les client
                ListClientTemp.clear();
            }
        }

        if (!routesPossibles.isEmpty() ) {
            int randomIndex = random.nextInt(routesPossibles.size());
            Route newRoute = routesPossibles.get(randomIndex);
            routes.set(indexAleatoire, newRoute);
            System.out.println(listeMouvement.get(randomIndex));
            return new Pair<>(routes, listeMouvement.get(randomIndex));
        } else {
            // On retourne null s'il n'y aucune modification
            return new Pair<>(routes, "Aucun échange réalisable");
        }

    }

    /*
    * Méthode qui échange deux client de la même route
    * */
    public Pair<ArrayList<Route>, String> exchangeIntra(ArrayList<Route> routes) {
        // On choisit une route au hasard parmi celles disponibles
        Random random = new Random();
        int indexAleatoire = random.nextInt(routes.size());
        Route selectedRoute = routes.get(indexAleatoire);


        // Vérifier si la route sélectionnée a au moins deux clients
        if (selectedRoute.getListClient().size() < 4) {
            return new Pair<>(null, "Pas assez de clients dans les routes selectionnées");
        }

        // Liste des solutions possibles
        ArrayList<Route> solutions = new ArrayList<>();
        List<String> listeMouvement = new ArrayList<>();

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
                if (clonedRoute.isFeasible(this.capacity)) {
                    solutions.add(clonedRoute.cloneRoute(clonedRoute));
                    listeMouvement.add("Opérateur : ExchangeIntra ; "+"Route : "+ indexAleatoire +" ; Clients  "+client1.getIdName()+" et "+client2.getIdName() );
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
            // On remplace la route sélectionnée dans l'ArrayList d'origine par la solution choisie
            routes.set(indexAleatoire, selectedSolution);
            System.out.println(listeMouvement.get(randomIndex));
            return new Pair<>(routes, listeMouvement.get(randomIndex));
        }

        // On null s'il n'y aucune modification
        return new Pair<>(routes, "Aucun échange réalisable");
    }


    /*
    * Vérifie l'échange de client entre deux routes distinctes
    * */

    public Pair<ArrayList<Route>, ArrayList<String>> exchangeInter(ArrayList<Route> routes) {
        ArrayList<String> action = new ArrayList<>();

        // Vérifier s'il y a au moins deux routes disponibles
        if (routes.size() < 2) {
            action.add(0,"Pas assez de routes disponibles");
            return new Pair<>(null, action);
        }

        // Choisir deux routes au hasard parmi celles disponibles
        Random random = new Random();
        int indexRoute1 = random.nextInt(routes.size());
        int indexRoute2 = random.nextInt(routes.size());

        // S'assurer que les deux index de route sont différents
        while (indexRoute2 == indexRoute1) {
            indexRoute2 = random.nextInt(routes.size());
        }

        // Récupérer les deux routes sélectionnées
        Route route1 = routes.get(indexRoute1);
        Route route2 = routes.get(indexRoute2);

        // Vérifier si les routes sélectionnées ont au moins un client
        if (route1.getListClient().size() < 3 || route2.getListClient().size() < 3) {
            action.add(0,"Pas assez de clients dans les routes selectionnées");
            return new Pair<>(null, action);
        }

        // Liste des solutions possibles
        List<ArrayList<Route>> listeRoutes = new ArrayList<>();
        ArrayList<Pair<String, String>> clientsExchanged = new ArrayList<>();

        // Parcourir tous les clients de la route1 pour les échanges possibles
        for (int i = 1; i < route1.getListClient().size() - 1; i++) {
            Client client1 = route1.getListClient().get(i);
            // Parcourir tous les clients de la route2 pour les échanges possibles
            for (int j = 1; j < route2.getListClient().size() - 1; j++) {
                Client client2 = route2.getListClient().get(j);

                // Créer des clones des routes sélectionnées
                Route clonedRoute1 = route1.cloneRoute();
                Route clonedRoute2 = route2.cloneRoute();

                // Échanger les clients entre les routes
                clonedRoute1.getListClient().set(i, client2);
                clonedRoute2.getListClient().set(j, client1);

                // Vérifier si les nouvelles routes sont réalisables
                if (clonedRoute1.isFeasible(this.capacity) && clonedRoute2.isFeasible(this.capacity)) {
                    // Créer une nouvelle solution contenant les routes modifiées
                    ArrayList<Route> solution = new ArrayList<>(routes);
                    solution.set(indexRoute1, clonedRoute1);
                    solution.set(indexRoute2, clonedRoute2);
                    clientsExchanged.add(new Pair(Integer.toString(i), Integer.toString(j)));
                    listeRoutes.add(solution);
                }
            }
        }

        // Si des solutions sont disponibles, choisir une solution au hasard
        if (!listeRoutes.isEmpty()) {
            int randomIndex = random.nextInt(listeRoutes.size());
            action.add("ExchangeInter");
            action.add(Integer.toString(indexRoute1));
            action.add(clientsExchanged.get(randomIndex).getFirst());
            action.add(Integer.toString(indexRoute2));
            action.add(clientsExchanged.get(randomIndex).getSecond());
            Solution chosenSolution = new Solution();
            chosenSolution.setRoutes(listeRoutes.get(randomIndex));
            System.out.println("distance route modif "+chosenSolution.getTotalDistance());
            System.out.println(listeRoutes);
            return new Pair<>(listeRoutes.get(randomIndex), action);
        }

        // Aucun échange réalisable, retourner null
        action.add("Pas d'échange possible");
        return new Pair<>(null, action);
    }




    /*
    * Echange deux parties de deux routes distinctes
    * */
    public Pair<ArrayList<Route>, String> crossExchange(ArrayList<Route> routes) {
        // Vérifier s'il y a au moins deux routes disponibles
        if (routes.size() < 2) {
            return new Pair<>(null, "Pas assez de routes disponibles");
        }

        // Choisir deux routes au hasard parmi celles disponibles
        Random random = new Random();
        int indexRoute1 = random.nextInt(routes.size());
        int indexRoute2 = random.nextInt(routes.size());

        // S'assurer que les deux index de route sont différents
        while (indexRoute2 == indexRoute1) {
            indexRoute2 = random.nextInt(routes.size());
        }

        // Récupérer les deux routes sélectionnées de la liste temporaire
        Route route1 = routes.get(indexRoute1).cloneRoute();
        Route route2 = routes.get(indexRoute2).cloneRoute();

        // Vérifier si les routes sélectionnées ont au moins deux clients
        if (route1.getListClient().size() < 4 && route2.getListClient().size() < 4) {
            return new Pair<>(null, "Pas assez de clients dans les routes selectionnées");
        }

        // Liste des solutions possibles
        List<ArrayList<Route>> listeRoutes = new ArrayList<>();
        List<String> listeMouvement = new ArrayList<>();

        // Générer aléatoirement les indices de début et de fin des parties à échanger
        Random random2 = new Random();
        int size1 = route1.getListClient().size();
        int size2 = route2.getListClient().size();

        // Parcourir toutes les sous-parties de la route 1
        for (int startIndex1 = 1; startIndex1 < route1.getListClient().size() - 1; startIndex1++) {
            for (int endIndex1 = startIndex1 + 2; endIndex1 < route1.getListClient().size() - 1; endIndex1++) {
                // Parcourir toutes les sous-parties de la route 2
                for (int startIndex2 = 1; startIndex2 < route2.getListClient().size() - 1; startIndex2++) {
                    for (int endIndex2 = startIndex2 + 1; endIndex2 < route2.getListClient().size() - 1; endIndex2++) {
                        // Extraire les parties des routes à échanger
                        ArrayList<Client> part1 = new ArrayList<>(route1.getListClient().subList(startIndex1, endIndex1 + 1));
                        ArrayList<Client> part2 = new ArrayList<>(route2.getListClient().subList(startIndex2, endIndex2 + 1));

                        // Échanger les parties entre les deux routes
                        Route newRoute1 = route1.cloneRoute();
                        newRoute1.getListClient().subList(startIndex1, endIndex1 + 1).clear();
                        newRoute1.getListClient().addAll(startIndex1, part2);

                        Route newRoute2 = route2.cloneRoute();
                        newRoute2.getListClient().subList(startIndex2, endIndex2 + 1).clear();
                        newRoute2.getListClient().addAll(startIndex2, part1);

                        // Vérifier si les nouvelles routes sont réalisables
                        if (newRoute1.isFeasible(this.capacity) && newRoute2.isFeasible(this.capacity)) {
                            // Créer une nouvelle solution contenant les routes modifiées
                            ArrayList<Route> solution = new ArrayList<>(routes);
                            solution.set(indexRoute1, newRoute1);
                            solution.set(indexRoute2, newRoute2);
                            // Ajouter la solution à la liste des solutions
                            listeMouvement.add("Opérateur : CrossExchange ; "+"Route "+indexRoute1+" index "+startIndex1+" à "+endIndex1+" ; Route " +indexRoute2+" index "+startIndex2+" à "+ endIndex2 );
                            listeRoutes.add(solution);
                        }
                    }
                }
            }
        }

        // Si des solutions sont disponibles, en choisir une au hasard
        if (!listeRoutes.isEmpty()) {
            int randomIndex = random.nextInt(listeRoutes.size());
            System.out.println( listeMouvement.get(randomIndex));
            return new Pair<>(listeRoutes.get(randomIndex), listeMouvement.get(randomIndex));
        }

        // Aucun échange réalisable, retourner null
        return new Pair<>(null, "Pas d'échange possible");
    }

}
