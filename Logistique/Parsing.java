package Logistique;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Parsing {
    public static InstanceVRP ParsingClientsFromFile(String fileName) {
        ArrayList<Client> clients = new ArrayList<Client>();
        int capacity = 0;
        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);

            // read the client data
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();

                if (line.startsWith("MAX_QUANTITY")) {
                    String[] tokens = line.split(" ");
                    capacity = Integer.parseInt(tokens[1]);
                }

                if (line.startsWith("d")) {
                    String[] tokens = line.split(" ");
                    String id = tokens[0];
                    int x = Integer.parseInt(tokens[1]);
                    int y = Integer.parseInt(tokens[2]);
                    int readyTime = Integer.parseInt(tokens[3]);
                    int dueTime = Integer.parseInt(tokens[4]);
                    Client client = new Client(id, x, y, readyTime, dueTime, 0, 0);
                    clients.add(client);
                }

                if (line.startsWith("c")) {
                    String[] tokens = line.split(" ");
                    String id = tokens[0];
                    int x = Integer.parseInt(tokens[1]);
                    int y = Integer.parseInt(tokens[2]);
                    int readyTime = Integer.parseInt(tokens[3]);
                    int dueTime = Integer.parseInt(tokens[4]);
                    int demand = Integer.parseInt(tokens[5]);
                    int serviceTime = Integer.parseInt(tokens[6]);
                    Client client = new Client(id, x, y, readyTime, dueTime, demand, serviceTime);
                    clients.add(client);
                }

            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        InstanceVRP instanceVRP = new InstanceVRP(capacity, clients.size() - 1, clients);
        return instanceVRP;
    }
}
