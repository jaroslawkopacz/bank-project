//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//public class MemoryClientRepositoryTest {
//    private org.bestbank.MemoryClientRepository memoryClientRepository;
//    private List<org.bestbank.Client> clients;
//
//    @BeforeEach
//    public void setup(){
//        clients = new ArrayList<>();
//        memoryClientRepository = new org.bestbank.MemoryClientRepository(clients);
//    }
//
//    @Test
//    public void VerifyIfClientIsSavingToRepository(){
//        //given
//        org.bestbank.Client client = new org.bestbank.Client("Arek", "a@a.pl", 100);
//        org.bestbank.Client expectedClient = new org.bestbank.Client("Arek", "a@a.pl", 100);
//        //when
//        memoryClientRepository.save(client);
//        org.bestbank.Client actualClient = clients.stream().findFirst().get();
//        //then
//        assertEquals(expectedClient, actualClient);
//    }
//
//}
