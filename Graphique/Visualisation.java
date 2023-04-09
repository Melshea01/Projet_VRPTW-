package Graphique;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.JPanel;

import Logistique.Client;
import Logistique.Route;
import Logistique.Transport;

public class Visualisation extends JPanel {
    private ArrayList<Route> routes;
    private ArrayList<Transport> transports;

    static final int MULTIPLIER = 10;

    public Visualisation(ArrayList<Transport> transports) {
        this.routes = new ArrayList<Route>();
        for(int i=0; i<transports.size();i++){
            this.routes.add(transports.get(i).getRoute());
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        Graphics2D g2d = (Graphics2D) g;
        g2d.scale(5, 5); // agrandissement de 20 fois


        // Dessine chaque route en utilisant des couleurs différentes
        Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.PINK};


        for (int i = 0; i < routes.size(); i++) {
            Route route = routes.get(i);
            g.setColor(colors[i % colors.length]);

            // Dessine les clients visités
            for (int j = 0; j < route.getRoute().size() -1; j++) {
                Client client = route.getRoute().get(j);
                int x = (int) client.getX();
                int y = (int) client.getY();
                g.fillOval(x - 5, y - 5, 10, 10);
            }

            // Dessine les arcs entre les clients
            for (int j = 0; j < route.getRoute().size() -1; j++) {
                Client from = route.getRoute().get(j);
                Client to = route.getRoute().get(j+1);
                int x1 = (int) from.getX();
                int y1 = (int) from.getY();
                int x2 = (int) to.getX();
                int y2 = (int) to.getY();
                g.drawLine(x1, y1, x2, y2);
            }
        }
    }

    public static void show(ArrayList<Transport> transports) {
        Visualisation panel = new Visualisation(transports);
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setSize(500, 500);
        frame.setVisible(true);
    }
}
