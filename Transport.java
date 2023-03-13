import java.util.ArrayList;
import java.util.List;

public class Transport {
    int id_vehicule;
    int chargement;
    private List<Integer> clientlivre;

    public Transport(){
        clientlivre = new ArrayList<Integer>();
    }
    public void livrerClient(int idClient) {
        clientlivre.add(idClient);
    }
    public List<Integer> getClientlivre() {
        return clientlivre;
    }
}
