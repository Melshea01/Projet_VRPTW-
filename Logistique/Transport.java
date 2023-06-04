package Logistique;

public class Transport {
    private int chargement;
    private int x_vehicule;
    private int y_vehicule;

    public Route route = new Route();
    public Route getRoute() {
        return route;
    }

    public Transport(){
        this.chargement = 0;
        this.x_vehicule =0;
        this.y_vehicule=0;
        new Route();
    }

    public int getChargement() {
        return chargement;
    }

    public int getX_vehicule() {
        return x_vehicule;
    }

    public int getY_vehicule() {
        return y_vehicule;
    }

    public void setX_vehicule(int x_vehicule) {
        this.x_vehicule = x_vehicule;
    }

    public void setY_vehicule(int y_vehicule) {
        this.y_vehicule = y_vehicule;
    }

    public void addChargement(int chargement) {
        this.chargement += chargement;
    }
}
