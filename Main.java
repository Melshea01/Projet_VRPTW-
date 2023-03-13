import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ArrayList<Client> clients = ParsingClientsFromFile("Data/data101.vrp");
        for(Client c : clients)
        {
            System.out.println (c.getIdName());
        }

    }

    public static ArrayList<Client> ParsingClientsFromFile(String fileName) {
        ArrayList<Client> clients = new ArrayList<Client>();

        try {
            File file = new File(fileName);
            Scanner scanner = new Scanner(file);


            // read the client data
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
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

        return clients;
    }
}

