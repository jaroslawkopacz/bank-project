import org.bestbank.controller.dto.ClientRequest;
import org.bestbank.repository.ClientRepository;
import org.bestbank.repository.entity.Account;
import org.bestbank.repository.entity.Client;
import org.bestbank.service.ClientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.NoSuchElementException;

import static org.mockito.Mockito.*;

public class ClientServiceTest {
    private ClientService clientService;
    private ClientRepository repository;

    @BeforeEach
    public void setup(){
        repository = mock(ClientRepository.class);
        clientService = new ClientService(repository);
    }

//    UWAGA: Z klasy Client pousuwałem setBalance, getBalance i ręcznie stworzony przez nas konstruktor,
//    teraz Client tworzymy sobie w tym teście przez builder, a w innychy po prostu dodajemy Id Clienta :D
    @Test
    public void save_AllParamsOk_ClientSaved(){
        //given
        ClientRequest clientRequest = new ClientRequest("Bartek", "bar@a.pl");
        Client client = Client.builder()
                .email(clientRequest.getEmail())
                .name(clientRequest.getName())
                .build();
        //when
        clientService.save(clientRequest);
        //then
        verify(repository).save(client);
    }

    @Test
    public void save_nullClient_throwIllegalArgumentException(){
        //given/when/then
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> clientService.save(null)
        );
    }

    @Test
    public void save_nullNameClient_throwIllegalArgumentException(){
        //given
        ClientRequest clientRequest = new ClientRequest(null, "bar@a.pl");
        //when/then
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> clientService.save(clientRequest)
        );
    }

    @Test
    public void save_nullEmailClient_throwIllegalArgumentException(){
        //given
        ClientRequest clientRequest = new ClientRequest("Bartek", null);
        //when/then
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> clientService.save(clientRequest)
        );
    }

    @Test
    public void save_emptyNameClient_throwIllegalArgumentException(){
        //given
        ClientRequest clientRequest = new ClientRequest("", "bar@a.pl");
        //when/then
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> clientService.save(clientRequest)
        );
    }

    @Test
    public void save_emptyEmailClient_throwIllegalArgumentException(){
        //given
        ClientRequest clientRequest = new ClientRequest("Bartek", "");
        //when/then
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> clientService.save(clientRequest)
        );
    }

    @Test
    public void findByEmail_AllParamsOk_foundClient(){
        //given
        Client client = new Client(4L,"Bartek", "bar@a.pl", Collections.singletonList(new Account(1000, "PLN")));
        when(repository.findByEmail(client.getEmail())).thenReturn(client);
        //when
        Client actualClient = clientService.findByEmail(client.getEmail());
        //then
        Client expectedClient = new Client(4L,"Bartek", "bar@a.pl", Collections.singletonList(new Account(1000, "PLN")));

        Assertions.assertEquals(expectedClient, actualClient);
    }

    @Test
    public void findByEmail_nullEmail_throwIllegalArgumentException(){
        //given/when/then
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> clientService.findByEmail(null)
        );
    }

    @Test
    public void findByEmail_emptyEmail_throwIllegalArgumentException(){
        //given/when/then
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> clientService.findByEmail("")
        );
    }

    @Test
    public void findByEmail_doesntExistClient_throwIllegalArgumentException(){
        //given
        Client client = new Client(4L, "Bartek", "bar@a.pl", Collections.singletonList(new Account(200, "PLN")));
        //when/then
        Assertions.assertThrows(NoSuchElementException.class,
                () -> clientService.findByEmail(client.getEmail())
        );
    }

    @Test
    public void findByEmail_UpperCaseEmail_foundClient(){
        //given
        String clientEmail = "BAR@A.PL";
        Client client = new Client(4L, "Bartek", "bar@a.pl", Collections.singletonList(new Account(1000, "PLN")));
        when(repository.findByEmail(clientEmail.toLowerCase())).thenReturn(client);
        //when
        Client actualClient = clientService.findByEmail(clientEmail);
        //then
        Client expectedClient = new Client(4L,"Bartek", "bar@a.pl", Collections.singletonList(new Account(1000, "PLN")));

        Assertions.assertEquals(expectedClient, actualClient);
    }
}
