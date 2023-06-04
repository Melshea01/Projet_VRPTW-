package Graphique;

import java.util.ArrayList;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.view.Viewer;


import Logistique.Client;
import Logistique.Route;


public class Visualisation {

    private org.graphstream.graph.Graph graph;

    public  Visualisation(ArrayList<Route> routes, ArrayList<Client> Client) {
        ArrayList<Client> copiedList = new ArrayList<>(Client);
        Client depot = copiedList.get(0);

        //Paramètre fenetre graph
        graph = new SingleGraph("VRPTW");
        graph.setAttribute("ui.stylesheet", "node { text-size: 20px; } edge { text-size: 20px; }");
        graph.setAttribute("ui.quality");
        graph.setAttribute("ui.antialias");
        graph.setStrict(false);


        // Ajouter les autres clients en tant que noeuds et les routes en tant qu'arêtes
        int routeId = 0;

        for (Route route : routes) {
            copiedList = route.getListClient();

            // Ajouter le noeud du dépôt
            Node depotNode = graph.addNode(depot.getIdName());
            depotNode.setAttribute("ui.label",  depot.getIdName());
            depotNode.setAttribute("xy", depot.getX(), depot.getY());


            // Ajouter l'arête entre le dépôt et le premier nœud de la route
            String LastNodeId = copiedList.get(1).getIdName();
            Node node1 = graph.addNode(LastNodeId);
            node1.setAttribute("xy", copiedList.get(1).getX(), copiedList.get(1).getY());
            node1.setAttribute("ui.label", copiedList.get(1).getIdName());
            String edgeId = "route_" + Integer.toString(routeId);
            graph.addEdge(edgeId, depotNode.getId(), LastNodeId);

            // Ajouter les noeuds des clients
            for (Client client : route.getListClient()) {
                Node clientNode = graph.addNode(client.getIdName());
                clientNode.setAttribute("ui.label", "    " + client.getIdName());
                clientNode.setAttribute("xy", client.getX(), client.getY());

            }

        }

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
