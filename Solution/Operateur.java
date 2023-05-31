package Solution;

import Logistique.Route;
import Logistique.*;

import org.apache.commons.math3.util.Pair;
import java.util.ArrayList;
import java.util.Random;

public class Operateur {


    /*
    * Opérateur qui va echanger deux arêtes de la route
    * */

    public ArrayList<Pair<Solution, ArrayList<String>>>  twoOpt(Solution solution){
        ArrayList<Pair <Solution, ArrayList<String>>> neighbors = new ArrayList<>();

        for(int i1 =0;i1 < solution.getRoutes().size()-1 ; i1++) {
                //Vérification qu'il y a au moins 3 clients dans la  route
                if (solution.getRoutes().get(i1).getListClient().size() < 5) {
                    break;
                }

                int size = solution.getRoutes().get(i1).getListClient().size();
                Route newRoute = solution.getRoutes().get(i1).cloneRoute();

                //Calcule de toutes les routes randoms
                for (int i = 1; i < size - 2; i++) {
                    for (int j = i + 2; j < size - 1; j++) {
                        Route tempRoute = newRoute.cloneRoute(newRoute);
                        tempRoute = newRoute.setRoute(getReversedList(tempRoute.getListClient(),i,j));
                        if (tempRoute.isFeasible(solution.instanceVRP.getCapacity())) {
                            ArrayList<String> action = new ArrayList<>();
                            Solution newNeighbor = new Solution();
                            newNeighbor = solution.cloneSolution();
                            newNeighbor.getRoutes().set(i1, tempRoute);
                            action.add("TwoOptSameRoute");
                            //Route modifié
                            action.add(Integer.toString(i1));
                            action.add(Integer.toString(i));
                            action.add(Integer.toString(j));
                            neighbors.add(new Pair(newNeighbor, action));
                        }
                    }
                }
            }

        if (!neighbors.isEmpty()) {
            return neighbors;
        } else {
            neighbors.add(new Pair(null, null));
            return neighbors;
        }
    }


    public ArrayList<Pair<Solution, ArrayList<String>>>  relocateInter(Solution solution){
        ArrayList<Pair <Solution, ArrayList<String>>> neighbors = new ArrayList<>();
        //Suppression du client dans la route d'origine
        Route newRouteOrigine = new Route();


        //Pour toutes les routes de la solution
        for(int i =0;i < solution.getRoutes().size()-1 ; i++){
            //On copie la liste de de client de la route
            Route route1 = solution.getRoutes().get(i).cloneRoute();
            ArrayList<Client> ListClientTemp1 = (ArrayList<Client>) new ArrayList<>(route1.getListClient()).clone();
            if(solution.getRoutes().get(i).getListClient().size()<3){
                break;
            }

            //Pour tous les clients de cette route
            for(int numClient = 1; numClient < ListClientTemp1.size()-1; numClient++){
               Client client = ListClientTemp1.get(numClient) ;

                // Pour toutes les routes de la solution, on teste où on peut insérer le client
                for (int j = i+1; j < solution.getRoutes().size(); j++) {
                    Route route2 = solution.getRoutes().get(j).cloneRoute();

                    if(solution.getRoutes().get(j).getListClient().size()<3){
                        break;
                    }

                    //On copie la liste de client de la nouvelle route
                    ArrayList<Client> ListClientTemp2 = (ArrayList<Client>) new ArrayList<>(route2.getListClient()).clone();

                    // On insère le client au début de la route
                    ListClientTemp2.add(1, client);
                    Route newRouteArrive = new Route();
                    newRouteArrive.setClients(ListClientTemp2);

                    // On essaie d'insérer le client à une place
                    if (relocateIntra(newRouteArrive) != null) {
                        //Ajout du client relogé dans la nouvelle route
                        newRouteArrive = relocateIntra(newRouteArrive);

                        newRouteOrigine = route1.cloneRoute();
                        ListClientTemp1.remove(client);
                        newRouteOrigine.setClients(ListClientTemp1);

                        //Insertion de la solution dans la liste
                        ArrayList<String> action = new ArrayList<>();
                        Solution newNeighbor = new Solution();
                        newNeighbor = solution.cloneSolution();
                        //route d'origine
                        newNeighbor.getRoutes().set(i, newRouteOrigine);
                        //route d'arrivée
                        newNeighbor.getRoutes().set(j, newRouteArrive);

                        int indexArrivee = newRouteArrive.getIndexOfClient(client);

                        action.add("Relocate");
                        action.add(Integer.toString(i));
                        action.add(Integer.toString(numClient));
                        action.add(Integer.toString(j));
                        action.add(Integer.toString(indexArrivee));
                        neighbors.add(new Pair(newNeighbor, action));


                    }
                }
            }
        }
        if (!neighbors.isEmpty()) {
            return neighbors;
        } else {
            neighbors.add(new Pair(null, null));
            return neighbors;
        }
    }






    /*
     * Route1 : Route de d'origine du client
     * Client à changer de route
     * capacité de l'instance VRP
     * */

//    public ArrayList<Route> relocateInter( ArrayList<Route>  routes, int capacity) {
//        Route newRouteArrive = new Route();
//
//        //On choisit au hasard une route origine
//        Random random = new Random();
//        int indexRouteOrigine = random.nextInt(routes.size());
//        Route selectedRoute = routes.get(indexRouteOrigine);
//
//        // On copie la liste de clients liée à la route
//        ArrayList<Client> listClientsOrigine = new ArrayList<>(selectedRoute.getListClient());
//        ArrayList<String> action = new ArrayList<>();
//
//        //On choisit un client random dans la route1
//        if(selectedRoute.getListClient().size() < 3) {
//            action.add(0,"Pas assez de clients dans les routes selectionnées");
//            return  null;
//        }
//
//        int randomIndex = random.nextInt(listClientsOrigine.size() - 2) + 1; // Génère un index aléatoire dans la plage valide
//        Client client = listClientsOrigine.get(randomIndex);
//        ArrayList<Pair<String, String>> clientsNewIndex = new ArrayList<>();
//
//
//        // Pour toutes les routes de la solution, on teste où on peut insérer le client
//        for (int i = 0; i < routes.size(); i++) {
//            //On saute la route d'origine
//            if (i == indexRouteOrigine) {
//                continue;
//            }
//            Route route = routes.get(i);
//            if (route.getTotalDemandRoute() + client.getDemand() > capacity ) {
//                return null;
//            } else {
//                //On copie la liste de client de la nouvelle route
//                ArrayList<Client> ListClientTemp = (ArrayList<Client>) new ArrayList<>(route.getListClient()).clone();
//
//                // On insère le client au début de la route
//                ListClientTemp.add(1, client);
//                newRouteArrive.setClients(ListClientTemp);
//
//                // On essaie d'insérer le client à une place
//                if (relocateIntra(newRouteArrive) != null) {
//                    newRouteArrive = relocateIntra(newRouteArrive);
//
//                    // On enlève le client de la première route
//                    listClientsOrigine.remove(client);
//                    selectedRoute.setClients(listClientsOrigine);
//
//                    // Remplacement des deux routes l'ArrayList 'routes'
//                    routes.set(i, newRouteArrive);
//                    return routes;
//                } else {
//                    return null;
//                }
//            }
//        }
//        return null;
//    }


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
                    routesPossibles.add(tempRoute);
                }
                //On enlève les client
                ListClientTemp.clear();
            }

        }

        if (!routesPossibles.isEmpty() ) {
            Random random = new Random();
            int randomIndex = random.nextInt(routesPossibles.size());
            newRoute= routesPossibles.get(randomIndex);
            return newRoute;
        } else {
            return null;
        }

    }

    /*
    * change de place un client d'une des routes choisie aléaoirement parmis celle d'une solution
    * */

    public ArrayList<Pair<Solution, ArrayList<String>>> relocateIntra(Solution solution){
        ArrayList<Pair <Solution, ArrayList<String>>> neighbors = new ArrayList<>();
        ArrayList<Pair<Client, Client>> clientPair = new ArrayList<>();
        ArrayList<Client> ListClientTemp = new ArrayList<>();

        for (int i1 =0; i1< solution.getRoutes().size(); i1++){
            // Pour tous les clients de cette route
            int size = solution.getRoutes().get(i1).getListClient().size();
            Route route = solution.getRoutes().get(i1);

            for (int i=1;i<size-1;i++){
                Client client = route.getListClient().get(i);
                for(int j=i+1; j < size-2;j++){
                    ListClientTemp.addAll(route.getListClient());
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
                    if (client.isFeasible(arrivalTime) && i!=j && tempRoute.isFeasible(solution.getInstanceVRP().getCapacity())){
                        ArrayList<String> action = new ArrayList<>();
                        Solution newNeighbor = new Solution();
                        newNeighbor = solution.cloneSolution();
                        newNeighbor.getRoutes().set(i1, tempRoute);
                        action.add("Relocate");
                        //Route modifié
                        action.add(Integer.toString(i1));
                        action.add(Integer.toString(i));
                        action.add(Integer.toString(i1));
                        action.add(Integer.toString(j));
                        neighbors.add(new Pair(newNeighbor, action));
                    }
                    //On enlève les client
                    ListClientTemp.clear();
                }

            }
        }

        if (!neighbors.isEmpty()) {
            return neighbors;
        } else {
            neighbors.add(new Pair(null, null));
            return  neighbors;
        }

    }


    /*
    * Méthode qui renvoie tous les échange entre deux clients possibles
    * */
    public ArrayList<Pair<Solution, ArrayList<String>>> exchange(Solution solution) {
        ArrayList<Pair<Client, Client>> clientPair = new ArrayList<>();
        ArrayList<Pair <Solution, ArrayList<String>>> neighbors = new ArrayList<>();

        // On génère toutes les paires de clients possibles
        for (int i = 1; i < solution.getInstanceVRP().getNb_client()-1; i++) {
            for(int j = 2 ; j < solution.getInstanceVRP().getNb_client() ; j++){
                clientPair.add(new Pair<>(solution.getInstanceVRP().getClientByIndex(i), solution.getInstanceVRP().getClientByIndex(j)));
            }
        }

        for (Pair pair : clientPair) {
            Client client1 = (Client) pair.getFirst();
            Client client2 = (Client) pair.getSecond();
            Route route1 = null;
            Route route2 = null;

            // On récupère l'index des routes dans la solution
            int indexRoute1 = 0;
            int indexRoute2 = 0;

            // On retrouve les routes de chaque client dans la solution
            for(int i = 0 ; i<solution.getRoutes().size(); i++) {
                Route route = solution.getRoutes().get(i);
                if (route.getListClient().contains(client1)) {
                    route1 = route.cloneRoute();
                    indexRoute1 = i;
                } if (route.getListClient().contains(client2)) {
                    route2 = route.cloneRoute();
                    indexRoute2 = i;
                }
            }

            if (route1 != null && route2 != null) {
                // On récupère l'index des clients dans les routes
                int indexClient1 = route1.getIndexOfClient(client1);
                int indexClient2 = route2.getIndexOfClient(client2);

                // Échanger les clients entre les routes
                if(route1.routesAreEquals(route2)) {
                    route1.getListClient().set(indexClient1, client2);
                    route1.getListClient().set(indexClient2, client1);
                    route2 = route1;
                } else {
                    route1.getListClient().set(indexClient1, client2);
                    route2.getListClient().set(indexClient2, client1);
                }

                if (route1.isFeasible(solution.getInstanceVRP().getNb_client()) && route2.isFeasible(solution.getInstanceVRP().getNb_client())) {
                    // On génère la nouvelle liste de route
                    ArrayList<Route> newRoutes = new ArrayList<>();
                    newRoutes = (ArrayList<Route>) solution.getRoutes().clone();

                    newRoutes.set(indexRoute1, route1);
                    newRoutes.set(indexRoute2, route2);

                    Solution newNeighbor = new Solution();
                    newNeighbor.setRoutes(newRoutes);

                    // On génère l'action correspondante
                    ArrayList<String> action = new ArrayList<>();
                    action.add("Exchange");
                    action.add(Integer.toString(indexRoute1));
                    action.add(Integer.toString(indexClient1));
                    action.add(Integer.toString(indexRoute2));
                    action.add(Integer.toString(indexClient2));

                    // On ajoute la nouvelle solution et l'action à la liste de voisins
                    neighbors.add(new Pair(newNeighbor, action));
                }
            } else {
                return null;
            }
        }
        if (!neighbors.isEmpty()) {
            return neighbors;
        } else {
            neighbors.add(new Pair(null, null));
            return neighbors;
        }
    }

    /*
     * Méthode qui renvoie tous les échanges entre deux parties de routes possibles
     * */
    public ArrayList<Pair<Solution, ArrayList<String>>> crossExchange(Solution solution){
        ArrayList<Pair <Solution, ArrayList<String>>> neighbors = new ArrayList<>();

        //Pour toutes les routes de solutions
        for(int i =0;i < solution.getRoutes().size()-1 ; i++){
            for(int j =i+1 ; j< solution.getRoutes().size(); j++){

                // Récupérer les deux routes sélectionnées de la liste temporaire
                Route route1 = solution.getRoutes().get(i).cloneRoute();
                Route route2 = solution.getRoutes().get(j).cloneRoute();

                // Si l'un des deux routes à moins de 2 client
                if (route1.getListClient().size() < 4 || route2.getListClient().size() < 4) {
                    break;
                }

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
                                //Vérifier si les routes sont réalisable
                                if (newRoute1.isFeasible(solution.getInstanceVRP().getCapacity()) && newRoute2.isFeasible(solution.getInstanceVRP().getCapacity())) {
                                    // Créer une nouvelle solution contenant les routes modifiées
                                    Solution newNeighbor = solution.cloneSolution();
                                    newNeighbor.getRoutes().set(i, newRoute1);
                                    newNeighbor.getRoutes().set(j, newRoute2);

                                    // On génère l'action correspondante
                                    ArrayList<String> action = new ArrayList<>();
                                    action.add("CrossExchange");
                                    action.add(Integer.toString(i));
                                    action.add(Integer.toString(startIndex1));
                                    action.add(Integer.toString(endIndex1));
                                    action.add(Integer.toString(j));
                                    action.add(Integer.toString(startIndex2));
                                    action.add(Integer.toString(endIndex2));

                                    // On ajoute la nouvelle solution et l'action à la liste de voisins
                                    neighbors.add(new Pair(newNeighbor, action));
                                }
                            }
                        }
                    }
                }
            }
        }
        if (!neighbors.isEmpty()) {
            return neighbors;
        } else {
            return null;
        }
    }

    public static <T> ArrayList<T> getReversedList(ArrayList<T> list, int startIndex, int endIndex) {
        ArrayList<T> reversedList = new ArrayList<>(list);
        while (startIndex < endIndex) {
            T temp = reversedList.get(startIndex);
            reversedList.set(startIndex, reversedList.get(endIndex));
            reversedList.set(endIndex, temp);
            startIndex++;
            endIndex--;
        }
        return reversedList;
    }

}
