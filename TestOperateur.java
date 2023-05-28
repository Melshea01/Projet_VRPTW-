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
        SolutionAleatoire solution = new SolutionAleatoire();
        Operateur o = new Operateur();
        int total_poid = 0;
        int nb_camion = 0;
        ArrayList<Transport> distance;

        //Choix du fichier
        //TODO : Correction Solution generate random consomme les données de Instance VRP d'ou des erreurs, corriger cette erreur
        InstanceVRP instanceVRP1 = Parsing.ParsingClientsFromFile("Data/datatestcross.vrp");
        InstanceVRP instanceVRP2 = Parsing.ParsingClientsFromFile("Data/datatestcross.vrp");

        //Instanciation de la solution 1
        Solution solution1 = solution.generateRandomSolution(instanceVRP1);
        Solution solution2 = new Solution();
        ArrayList<Route> routes = solution1.getRoutes();

        System.out.println(routes.size());

        Visualisation visu = new Visualisation(solution1.getRoutes(), instanceVRP2.getClients());

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

        /*
        * Test Opérateur Two opt Same Route
        * */

//        int i1 = 0;
//        Route routetest = solution1.getRoutes().get(0);
//        while (i1 < 5) {
//            System.out.println("route de base " + routetest.clients);
//            for (Client client : routetest.clients) {
//                System.out.println("Client " + client.getIdName() + " - temps de début: (" + client.getReadyTime() + ", temps de fin " + client.getDueTime() + ")");
//            }
//            if (o.twoOptSameRoute(routetest) != null) {
//                routetest = o.twoOptSameRoute(routetest);
//                solution2 = solution1.replaceRoute(routetest,0);
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
//
//            }
//            i1++;
//        }



        /*
         * Test Opérateur RelocateIntra
         * */

//        int i1 = 0;
//        Route routetest = solution1.getRoutes().get(0);
//        while (i1 < 5) {
//            System.out.println("route de base " + routetest.clients);
//            for (Client client : routetest.clients) {
//                System.out.println("Client " + client.getIdName() + " - temps de début: (" + client.getReadyTime() + ", temps de fin " + client.getDueTime() + ")");
//            }
//            if (o.relocateIntra23(routetest) != null) {
//                routetest = o.relocateIntra23(routetest);
//                solution2 = solution1.replaceRoute(routetest,0);
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
//
//            }
//            i1++;
//        }

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

        /*
         * Test Opérateur ExchangeIntra
         * */

//        ArrayList<Route> routetemp = new ArrayList<>();
//        int i1 = 0;
//        Route routetest = solution1.getRoutes().get(0);
//        solution2.setRoutes(solution1.getRoutes());
//        while (i1 < 5) {
//            System.out.println("route de base " + routetest.clients);
//            for (Client client : routetest.clients) {
//                System.out.println("Client " + client.getIdName() + " - temps de début: (" + client.getReadyTime() + ", temps de fin " + client.getDueTime() + ")");
//            }
//            routetemp = o.exchangeIntra(routes);
//            if (routetemp != null) {
//                solution2.setRoutes(routetemp);
//                ArrayList<Client> clients = routetemp.get(0).getListClient();
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

        /*
        * Opérateur Exchange Inter
        * */

//        ArrayList<Route> routetemp = new ArrayList<>();
//        int i1 = 0;
//        Route routetest = solution1.getRoutes().get(0);
//        solution2.setRoutes(solution1.getRoutes());
//        while (i1 < 5) {
//            System.out.println("route de base " + routetest.clients);
//            for (Client client : routetest.clients) {
//                System.out.println("Client " + client.getIdName() + " - temps de début: (" + client.getReadyTime() + ", temps de fin " + client.getDueTime() + ")");
//            }
//
//            routetemp = o.exchangeInter(routes);
//            if (routetemp != null) {
//                solution2.setRoutes(routetemp);
//                for(int i =0; i<solution2.getRoutes().size(); i++){
//                    ArrayList<Client> clients = routetemp.get(i).getListClient();
//                    System.out.println("route modifié :" + i );
//                    for (Client client : clients) {
//                        System.out.println("Client " + client.getIdName() + " - temps de début: (" + client.getReadyTime() + ", temps de fin " + client.getDueTime() + ")");
//                    }
//                }
//
//                //Visualisation
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

        /*
         * Opérateur CrossExchange
         * */

        ArrayList<Route> routetemp = new ArrayList<>();
        int i1 = 0;

        solution2.setRoutes(solution1.getRoutes());
        while (i1 < 10) {
            for(int i =0; i<solution2.getRoutes().size(); i++){
                ArrayList<Client> clients = solution2.getRoutes().get(i).getListClient();
                System.out.println("route base :" + i );
                for (Client client : clients) {
                    System.out.println("Client " + client.getIdName() + " - temps de début: (" + client.getReadyTime() + ", temps de fin " + client.getDueTime() + ")");
                }
            }
            routetemp = o.crossExchange(routes);
            if (routetemp != null) {
                solution2.setRoutes(routetemp);
                for(int i =0; i<solution2.getRoutes().size(); i++){
                    ArrayList<Client> clients = routetemp.get(i).getListClient();
                    System.out.println("route modifié :" + i );
                    for (Client client : clients) {
                        System.out.println("Client " + client.getIdName() + " - temps de début: (" + client.getReadyTime() + ", temps de fin " + client.getDueTime() + ")");
                    }
                }

                //Visualisation
                try {
                    visu.updateGraph(solution2.getRoutes());
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            else visu.updateGraph(solution2.getRoutes());
            i1++;
        }

        System.out.println("Finish");

        }
    }