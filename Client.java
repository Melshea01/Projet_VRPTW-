import java.util.ArrayList;

public class Client {
    private String idName;
    private int x;
    private int y;
    private int readyTime;
    private int dueTime;
    private int demand;
    private int service;

    public Client(String idName, int x, int y, int readyTime, int dueTime, int demand, int service) {
        this.idName = idName;
        this.x = x;
        this.y = y;
        this.readyTime = readyTime;
        this.dueTime = dueTime;
        this.demand = demand;
        this.service = service;
    }

    public String getIdName() {
        return idName;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getReadyTime() {
        return readyTime;
    }

    public int getDueTime() {
        return dueTime;
    }

    public int getDemand() {
        return demand;
    }

    public int getService() {
        return service;
    }


    //Permet de calculer la distance total entre les clients et les dépots
    public static double calculateDistance(ArrayList<Integer> solution, double[][] distances, double[][] times, double[] demand, double capacity) {
        double totalDistance = 0;
        double[] arrivalTime = new double[distances.length];

        // Parcours de la solution
        for (int i = 1; i < solution.size(); i++) {
            int from = solution.get(i - 1);
            int to = solution.get(i);

            // Calcul de la distance entre deux clients
            double distance = distances[from][to];

            // Calcul du temps de service (temps d'attente inclus)
            double serviceTime = Math.max(arrivalTime[from] + times[from][to], times[from][to]);

            // Vérification de la faisabilité de la capacité
            if (i < solution.size() - 1) {
                int next = solution.get(i + 1);
                if (demand[to] > capacity || demand[to] + demand[next] > capacity) {
                    return Double.MAX_VALUE;
                }
            }

            // Mise à jour du temps d'arrivée
            arrivalTime[to] = serviceTime + demand[to];

            // Mise à jour de la distance totale
            totalDistance += distance;
        }

        return totalDistance;
    }


}
