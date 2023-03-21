import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Parsing p = new Parsing();

        int total_poid = 0;
        int nb_camion = 0;
        InstanceVRP instanceVRP = p.ParsingClientsFromFile("Data/data101.vrp");

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


    }

}

