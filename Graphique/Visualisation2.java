package Graphique;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import Logistique.InstanceVRP;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.spriteManager.Sprite;
import org.graphstream.ui.spriteManager.SpriteManager;

import Logistique.Client;
import Logistique.Route;
import Logistique.Transport;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;

public class Visualisation2 {


    public static void show(ArrayList<Transport> transports, InstanceVRP VRP) {
        Graph graph = new SingleGraph("VRPTW");

        // Ajouter le premier client (dépôt) en tant que nœud
        Client depot = VRP.getClients().get(0);
        Node depotNode = graph.addNode(depot.getIdName());
        depotNode.setAttribute("xy", depot.getX(), depot.getY());
        depotNode.setAttribute("ui.label", depot.getIdName());

        // Ajouter les autres clients en tant que noeuds et les routes en tant qu'arêtes
        int routeId = 0;
        Map<String, String> routeColors = new HashMap<>();

        for (Transport transport : transports) {
            Route route = transport.getRoute();
            ArrayList<Client> clients = route.getRoute();


            // Ajouter l'arête entre le dépôt et le premier nœud de la route
            String LastNodeId = clients.get(1).getIdName();
            graph.addNode(LastNodeId);
            String edgeId = "route_" + Integer.toString(routeId);
            graph.addEdge(edgeId, depotNode.getId(),LastNodeId);

            // Ajouter les autres clients en tant que nœuds et les arêtes entre les nœuds
            for (int i = 2; i < clients.size(); i++) {
                Client client = clients.get(i);
                Node node = graph.addNode(client.getIdName());
                node.setAttribute("xy", client.getX(), client.getY());
                node.setAttribute("ui.label", client.getIdName());

                // Ajouter l'arête entre le dernier nœud et le nouveau nœud
                edgeId = "route_" + Integer.toString(routeId) + "_" + Integer.toString(i);
                graph.addEdge(edgeId, clients.get(i).getIdName(), LastNodeId);
                LastNodeId = clients.get(i).getIdName();

                //Retour du camion au dépot
                if(i==clients.size()-1){
                    edgeId = "route_" + Integer.toString(routeId)+ "_" + Integer.toString(i+1);
                    graph.addEdge(edgeId, LastNodeId, depotNode.getId());
                }
            }
            routeId++;
        }


        // Définir les styles d'affichage
        graph.setAttribute("ui.stylesheet", "node { size: 20px; text-size: 15; fill-color: red; text-color: black; } "
                + "edge { size: 1px; } "
                + "sprite.vehicle { shape: circle; size: 25px; fill-color: blue; }");


        //Vérification que tous les points ont bien été ajouté
        /*for (Node node : graph) {
            if (node.getId().equals("c100")) {
                System.out.println("Le client C100 a été ajouté avec succès au graphique.");
                break;
            }
        }*/

        // Lancement de la visualisation
        Viewer viewer = graph.display();
        View view = viewer.getDefaultView();
        view.getCamera().setViewPercent(0.5);
        view.getCamera().setAutoFitView(true);

    }

    //Attribuer une couleur
    public static String getRandomHexColor() {
        Random random = new Random();
        // Générer trois valeurs aléatoires pour les composantes RGB
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);
        // Convertir les valeurs en hexadécimal et les concaténer
        String hex = String.format("#%02x%02x%02x", r, g, b);
        return hex;
    }
}
