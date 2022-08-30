import java.util.List;
import java.util.Set;

public class MemoryClientRepository implements ClientRepository {
    List<Client> clients;

    public MemoryClientRepository(List<Client> clients) {
        this.clients = clients;
    }

    @Override
    public void save(Client client) {
        clients.add(client);
    }

    @Override
    public Client findByEmail(String email) {
        return clients
                .stream()
                .filter(client -> client.getEmail().equals(email))
                .findFirst()
                .get();
    }
}
