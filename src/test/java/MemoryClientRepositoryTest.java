import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MemoryClientRepositoryTest {
    private MemoryClientRepository memoryClientRepository;
    private List<Client> clients;

    @BeforeEach
    public void setup(){
        clients = new ArrayList<>();
        memoryClientRepository = new MemoryClientRepository(clients);
    }

    @Test
    public void VerifyIfClientIsSavingToRepository(){
        //given
        Client client = new Client("Arek", "a@a.pl", 100);
        Client expectedClient = new Client("Arek", "a@a.pl", 100);
        //when
        memoryClientRepository.save(client);
        Client actualClient = clients.stream().findFirst().get();
        //then
        assertEquals(expectedClient, actualClient);
    }

}
