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



}
