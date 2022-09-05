import org.bestbank.Account;
import org.bestbank.Client;
import org.bestbank.ClientRepository;
import org.bestbank.ClientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.mockito.Mockito.*;

public class ClientServiceTest {
    private ClientService clientService;
    private ClientRepository repository;

    @BeforeEach
    public void setup(){
        repository = mock(ClientRepository.class);
        clientService = new ClientService(repository);
    }

//    //Testing Method save
    @Test
    public void save_AllParamsOk_ClientSaved(){
        //given
        Client client = new Client("Bartek", "bar@a.pl", Collections.singletonList(new Account(1000, "PLN")));
        //when
        clientService.save(client);
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
        Client client = new Client(null, "bar@a.pl", Collections.singletonList(new Account(1000, "PLN")));
        //when/then
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> clientService.save(client)
        );
    }

    @Test
    public void save_nullEmailClient_throwIllegalArgumentException(){
        //given
        Client client = new Client("Bartek", null, Collections.singletonList(new Account(1000, "PLN")));
        //when/then
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> clientService.save(client)
        );
    }

    @Test
    public void save_negativeBalanceClient_throwIllegalArgumentException(){
        //given
        Client client = new Client("Bartek", "bar@a.pl", Collections.singletonList(new Account(-200, "PLN")));
        //when/then
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> clientService.save(client)
        );
    }
//
//    //Testing Method findByEmail

    @Test
    public void findByEmail_AllParamsOk_foundClient(){
        //given
        Client client = new Client("Bartek", "bar@a.pl", Collections.singletonList(new Account(1000, "PLN")));
        when(repository.findByEmail(client.getEmail())).thenReturn(client);
        //when
        Client actualClient = clientService.findByEmail(client.getEmail());
        //then
        Client expectedClient = new Client("Bartek", "bar@a.pl", Collections.singletonList(new Account(1000, "PLN")));

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
        Client client = new Client("Bartek", "bar@a.pl", Collections.singletonList(new Account(200, "PLN")));
        //when/then
        Assertions.assertThrows(NoSuchElementException.class,
                () -> clientService.findByEmail(client.getEmail())
        );
    }

    @Test
    public void findByEmail_UpperCaseEmail_foundClient(){
        //given
        String clientEmail = "BAR@A.PL";
        Client client = new Client("Bartek", "bar@a.pl", Collections.singletonList(new Account(1000, "PLN")));
        when(repository.findByEmail(clientEmail.toLowerCase())).thenReturn(client);
        //when
        Client actualClient = clientService.findByEmail(clientEmail);
        //then
        Client expectedClient = new Client("Bartek", "bar@a.pl", Collections.singletonList(new Account(1000, "PLN")));

        Assertions.assertEquals(expectedClient, actualClient);
    }


    //Testing Method transfer

    @Test
    public void transfer_AllParamsOk_foundsTransferred(){
        //given
        Client clientFrom = new Client("Bartek", "bar@a.pl", Collections.singletonList(new Account(1000, "PLN")));
        Client clientTo = new Client("Alek", "alek@a.pl", Collections.singletonList(new Account(500, "PLN")));
        double amount = 100;
        when(repository.findByEmail(clientFrom.getEmail())).thenReturn(clientFrom);
        when(repository.findByEmail(clientTo.getEmail())).thenReturn(clientTo);
        //when
        clientService.transfer(clientFrom.getEmail(), clientTo.getEmail(), amount);
        //then
        Client expectedClientFrom = new Client("Bartek", "bar@a.pl", Collections.singletonList(new Account(900, "PLN")));
        Client expectedClientTo = new Client("Alek", "alek@a.pl", Collections.singletonList(new Account(600, "PLN")));

        verify(repository).save(expectedClientFrom);
        verify(repository).save(expectedClientTo);

    }

    @Test
    public void transfer_notEnoughFounds_throwIllegalArgumentException(){
        //given
        Client clientFrom = new Client("Bartek", "bar@a.pl", Collections.singletonList(new Account(100, "PLN")));
        Client clientTo = new Client("Alek", "alek@a.pl", Collections.singletonList(new Account(500, "PLN")));
        double amount = 1000;
        when(repository.findByEmail(clientFrom.getEmail())).thenReturn(clientFrom);
        when(repository.findByEmail(clientTo.getEmail())).thenReturn(clientTo);
        //when
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> clientService.transfer(clientFrom.getEmail(), clientTo.getEmail(), amount)
        );
    }

    @Test
    public void transfer_negativeAmount_throwIllegalArgumentException(){
        //given
        Client clientFrom = new Client("Bartek", "bar@a.pl",  Collections.singletonList(new Account(100, "PLN")));
        Client clientTo = new Client("Alek", "alek@a.pl",  Collections.singletonList(new Account(500, "PLN")));
        double amount = -100;
        when(repository.findByEmail(clientFrom.getEmail())).thenReturn(clientFrom);
        when(repository.findByEmail(clientTo.getEmail())).thenReturn(clientTo);
        //when
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> clientService.transfer(clientFrom.getEmail(), clientTo.getEmail(), amount)
        );
    }

    @Test
    public void transfer_toSameClient_throwIllegalArgumentException(){
        //given
        Client clientFrom = new Client("Bartek", "bar@a.pl",  Collections.singletonList(new Account(200, "PLN")));
        double amount = 100;
        //when
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> clientService.transfer(clientFrom.getEmail(), clientFrom.getEmail(), amount)
        );
    }

    @Test
    public void transfer_nullEmail_throwIllegalArgumentException(){
        //given
        Client clientFrom = new Client("Bartek", null, Collections.singletonList(new Account(200, "PLN")));
        Client clientTo = new Client("Alek", "alek@a.pl", Collections.singletonList(new Account(500, "PLN")));
        double amount = 100;
        //when
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> clientService.transfer(clientFrom.getEmail(), clientTo.getEmail(), amount)
        );
    }

    @Test
    public void transfer_emptyEmail_throwIllegalArgumentException(){
        //given
        Client clientFrom = new Client("Bartek", "", Collections.singletonList(new Account(200, "PLN")));
        Client clientTo = new Client("Alek", "alek@a.pl", Collections.singletonList(new Account(500, "PLN")));
        double amount = 100;
        //when
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> clientService.transfer(clientFrom.getEmail(), clientTo.getEmail(), amount)
        );
    }

    @Test
    public void transfer_upperCaseEmails_foundsTransferred(){
        //given
        String bartekEmail = "BAR@B.PL";
        String marekEmail = "MAREK@M.PL";
        Client clientFrom = new Client("Bartek", "bar@b.pl", Collections.singletonList(new Account(200, "PLN")));
        Client clientTo = new Client("Marek", "marek@m.pl", Collections.singletonList(new Account(500, "PLN")));
        double amount = 100;
        when(repository.findByEmail(clientFrom.getEmail())).thenReturn(clientFrom);
        when(repository.findByEmail(clientTo.getEmail())).thenReturn(clientTo);
        //when
        clientService.transfer(bartekEmail, marekEmail, amount);
        //then
        Client expectedClientFrom = new Client("Bartek", "bar@b.pl", Collections.singletonList(new Account(100, "PLN")));
        Client expectedClientTo = new Client("Marek", "marek@m.pl", Collections.singletonList(new Account(600, "PLN")));

        verify(repository).save(expectedClientFrom);
        verify(repository).save(expectedClientTo);

    }

    @Test
    public void withdraw_AllParamsOk_foundsWithdrawn(){
        //given
        Client client = new Client("Bartek", "bar@b.pl", Collections.singletonList(new Account(200, "PLN")));
        when(repository.findByEmail(client.getEmail())).thenReturn(client);
        //when
        clientService.withdraw(client.getEmail(), 100);
        //then
        Client expectedClient = new Client("Bartek", "bar@b.pl", Collections.singletonList(new Account(100, "PLN")));

        verify(repository).save(expectedClient);
    }

    @Test
    public void withdraw_negativeAmount_throwIllegalArgumentException(){
        //given
        Client client = new Client("Bartek", "bar@b.pl", Collections.singletonList(new Account(200, "PLN")));
        //when/then
        Assertions.assertThrows(
                IllegalArgumentException.class, () ->
            clientService.withdraw(client.getEmail(), -100)
        );

    }

    @Test
    public void withdraw_zeroAmount_throwsIllegalArgumentException() {
        //given
        Client client = new Client("Bartek", "bar@b.pl", Collections.singletonList(new Account(200, "PLN")));
        //when
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> clientService.withdraw(client.getEmail(), 0)
        );
    }

    @Test
    public void withdraw_amountBiggerThenBalance_throwsIllegalArgumentException() {
        //given
        Client client = new Client("Bartek", "bar@b.pl", Collections.singletonList(new Account(200, "PLN")));
        when(repository.findByEmail(client.getEmail())).thenReturn(client);
        //when
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> clientService.withdraw(client.getEmail(), 1000)
        );
    }

    @Test
    public void withdraw_incorrectEmail_throwsNoSuchElementException() {
        //given
        String email = "incorrect_email@a.pl";
        when(repository.findByEmail(email)).thenThrow(new NoSuchElementException());
        //when/then
        Assertions.assertThrows(
                NoSuchElementException.class,
                () -> clientService.withdraw(email, 1000)
        );
    }


    @Test
    public void withdraw_upperCaseEmail_balanceChangedCorrectly() {
        //given
        String email = "BAR@B.PL";
        Client client = new Client("Bartek", "bar@b.pl", Collections.singletonList(new Account(200, "PLN")));
        when(repository.findByEmail(client.getEmail())).thenReturn(client);
        //when
        clientService.withdraw(email, 50);
        //then
        Client expectedClient = new Client("Bartek", "bar@b.pl", Collections.singletonList(new Account(150, "PLN")));

        verify(repository).save(expectedClient);
    }

    @Test
    public void withdraw_nullEmail_throwsIllegalArgumentException() {
        //given/when/then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> clientService.withdraw(null, 1000)
        );
    }

    @Test
    public void withdraw_emptyEmail_throwsIllegalArgumentException() {
        //given/when/then
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> clientService.withdraw("", 1000)
        );
    }



//    Zasady TDD
//
//	1. Nie możesz napisać kodu dopóki nie zrobisz testu który failuje
//	2. Nie możesz napisać kolejnych testów, dopóki test Ci failuje
//	3. Nie możesz pisać więcej kodu produkcyjnego niż ten który jest Ci potrzebny, żeby test przeszedł

//    3 FAZY: RED, GREEN, REFACTOR

//   Zalety TDD
//	- Podstawową zaletą TDD jest szybkość wychwytywania błędów.
//	- Do kolejnych zalet możemy zaliczyć bardziej przemyślany kod.
//  - Duża wystarczająca ilość testów, nasz kod jest bardzo dobrze otestowany



}
