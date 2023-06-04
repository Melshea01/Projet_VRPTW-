package Graphique;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;

import Logistique.Client;
import Logistique.Route;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.ViewerPipe;


public class Visualisation2 {


    private org.graphstream.graph.Graph graph;

    public Visualisation2(ArrayList<Route> routes, ArrayList<Client> Client){
        ArrayList<Client> copiedList = new ArrayList<>(Client);

        //Paramètre fenetre graph
        graph = new SingleGraph("VRPTW");
        graph.setAttribute("ui.stylesheet", "node { text-size: 20px; } edge { text-size: 20px; }");
        graph.setAttribute("ui.quality");
        graph.setAttribute("ui.antialias");
        graph.setStrict(false);

        // Ajouter le premier client (dépôt) en tant que nœud
        Client depot = copiedList.get(0);
        Node depotNode = graph.addNode(depot.getIdName());
        depotNode.setAttribute("xy", depot.getX(), depot.getY());
        depotNode.setAttribute("ui.label", depot.getIdName());
        copiedList.remove(depot);

        // Ajouter les autres clients en tant que noeuds et les routes en tant qu'arêtes
        int routeId = 0;

        for (Route route : routes) {
            copiedList = route.getListClient();

            // Ajouter l'arête entre le dépôt et le premier nœud de la route
            String LastNodeId = copiedList.get(1).getIdName();
            Node node1 = graph.addNode(LastNodeId);
            node1.setAttribute("xy", copiedList.get(1).getX(), copiedList.get(1).getY());
            node1.setAttribute("ui.label", copiedList.get(1).getIdName());
            String edgeId = "route_" + Integer.toString(routeId);
            graph.addEdge(edgeId, depotNode.getId(),LastNodeId);

            // Ajouter les autres clients en tant que nœuds et les arêtes entre les nœuds
            for (int i = 2; i < copiedList.size(); i++) {
                Client client = copiedList.get(i);
                Node node = graph.addNode(client.getIdName());
                node.setAttribute("xy", client.getX(), client.getY());
                node.setAttribute("ui.label", client.getIdName() );

                // Ajouter l'arête entre le dernier nœud et le nouveau nœud
                edgeId = "route_" + Integer.toString(routeId) + "_" + Integer.toString(i);
                graph.addEdge(edgeId, copiedList.get(i).getIdName(), LastNodeId);
                LastNodeId = copiedList.get(i).getIdName();

                //Retour du camion au dépot
                if(i==copiedList.size()-1){
                    edgeId = "route_" + Integer.toString(routeId)+ "_" + Integer.toString(i+1);
                    graph.addEdge(edgeId, LastNodeId, depotNode.getId());
                }
            }
            routeId++;
        }


        graph.setAttribute("ui.stylesheet", "node { size: 20px; text-size: 15; fill-color: red; text-color: black; } "
                + "edge { size: 1px; } "
                + "sprite.vehicle { shape: circle; size: 25px; fill-color: blue; }");

        Viewer viewer = graph.display();
        viewer.disableAutoLayout();

    }

    public void updateGraph(ArrayList<Route> routes) {

        for (int i = graph.getEdgeCount()-1; i >= 0; i--) {
            graph.removeEdge(i);
        }

        // Ajouter les arêtes représentant les chemins des véhicules
        int colorIndex = 0;
        for (Route route : routes) {
            ArrayList<Client> vehicleClients = route.getListClient();
            String color = "rgb(" + (colorIndex % 256) + ", " + (255 - colorIndex % 256) + ", " + (colorIndex % 128) + ")";
            for (int i = 0; i < vehicleClients.size() - 1; i++) {
                Node node1 = graph.getNode(vehicleClients.get(i).getIdName());
                Node node2 = graph.getNode(vehicleClients.get(i + 1).getIdName());
                Edge edge = graph.addEdge(node1.getId() + "-" + node2.getId(), node1, node2);
                if (edge != null) {
                    edge.setAttribute("vehicle");
                    edge.setAttribute("ui.style", "fill-color: " + color + "; size: 1px;");
                }
            }
            colorIndex += 100;
        }

    }
}
