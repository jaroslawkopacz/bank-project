import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;

public class ClientServiceTest {
    private List<Client> clients;
    private ClientService clientService;

    @BeforeEach
    public void setup(){
        clients = new ArrayList<>();
        clientService = new ClientService(new MemoryClientRepository(clients));
    }

    //Testing Method save
    @Test
    public void save_AllParamsOk_ClientSaved(){
        //given
        Client client = new Client("Bartek", "bar@a.pl", 1000);
        //when
        clientService.save(client);
        //then
        Client actualClient = clientService.findByEmail(client.getEmail());
        Client expectedClient = new Client("Bartek", "bar@a.pl", 1000);

        Assertions.assertEquals(expectedClient, actualClient);
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
        Client client = new Client(null, "bar@a.pl", 1000);
        //when/then
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> clientService.save(client)
        );
    }

    @Test
    public void save_nullEmailClient_throwIllegalArgumentException(){
        //given
        Client client = new Client("Bartek", null, 1000);
        //when/then
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> clientService.save(client)
        );
    }

    @Test
    public void save_negativeBalanceClient_throwIllegalArgumentException(){
        //given
        Client client = new Client("Bartek", "bar@a.pl", -1000);
        //when/then
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> clientService.save(client)
        );
    }

    //Testing Method findByEmail

    @Test
    public void findByEmail_AllParamsOk_foundClient(){
        //given
        Client client = new Client("Bartek", "bar@a.pl", 1000);
        clients.add(client);
        //when
        Client actualClient = clientService.findByEmail(client.getEmail());
        //then
        Client expectedClient = new Client("Bartek", "bar@a.pl", 1000);

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
        Client client = new Client("Bartek", "bar@a.pl", 1000);
        //when/then
        Assertions.assertThrows(NoSuchElementException.class,
                () -> clientService.findByEmail(client.getEmail())
        );
    }

    @Test
    public void findByEmail_UpperCaseEmail_foundClient(){
        //given
        String clientEmail = "BAR@A.PL";
        Client client = new Client("Bartek", "bar@a.pl", 1000);
        clients.add(client);
        //when
        Client actualClient = clientService.findByEmail(clientEmail);
        //then
        Client expectedClient = new Client("Bartek", "bar@a.pl", 1000);

        Assertions.assertEquals(expectedClient, actualClient);
    }


    //Testing Method transfer

    @Test
    public void transfer_AllParamsOk_foundsTransferred(){
        //given
        Client clientFrom = new Client("Bartek", "bar@a.pl", 1000);
        Client clientTo = new Client("Alek", "alek@a.pl", 500);
        double amount = 100;
        clients.add(clientFrom);
        clients.add(clientTo);
        //when
        clientService.transfer(clientFrom.getEmail(), clientTo.getEmail(), amount);
        //then
        Client actualClientFrom = clientService.findByEmail(clientFrom.getEmail());
        Client actualClientTo = clientService.findByEmail(clientTo.getEmail());
        Client expectedClientFrom = new Client("Bartek", "bar@a.pl", 900);
        Client expectedClientTo = new Client("Alek", "alek@a.pl", 600);

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions
                .assertThat(actualClientFrom)
                .isEqualTo(expectedClientFrom);
        softAssertions
                .assertThat(actualClientTo)
                .isEqualTo(expectedClientTo);
        softAssertions.assertAll();

    }

    @Test
    public void transfer_notEnoughFounds_throwIllegalArgumentException(){
        //given
        Client clientFrom = new Client("Bartek", "bar@a.pl", 100);
        Client clientTo = new Client("Alek", "alek@a.pl", 500);
        double amount = 1000;
        clients.add(clientFrom);
        clients.add(clientTo);
        //when
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> clientService.transfer(clientFrom.getEmail(), clientTo.getEmail(), amount)
        );
    }

    @Test
    public void transfer_negativeAmount_throwIllegalArgumentException(){
        //given
        Client clientFrom = new Client("Bartek", "bar@a.pl", 100);
        Client clientTo = new Client("Alek", "alek@a.pl", 500);
        double amount = -100;
        clients.add(clientFrom);
        clients.add(clientTo);
        //when
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> clientService.transfer(clientFrom.getEmail(), clientTo.getEmail(), amount)
        );
    }

    @Test
    public void transfer_toSameClient_throwIllegalArgumentException(){
        //given
        Client clientFrom = new Client("Bartek", "bar@a.pl", 200);
        double amount = 100;
        clients.add(clientFrom);
        //when
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> clientService.transfer(clientFrom.getEmail(), clientFrom.getEmail(), amount)
        );
    }

    @Test
    public void transfer_nullEmail_throwIllegalArgumentException(){
        //given
        Client clientFrom = new Client("Bartek", null, 200);
        Client clientTo = new Client("Alek", "alek@a.pl", 500);
        double amount = 100;
        clients.add(clientFrom);
        clients.add(clientTo);
        //when
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> clientService.transfer(clientFrom.getEmail(), clientTo.getEmail(), amount)
        );
    }

    @Test
    public void transfer_emptyEmail_throwIllegalArgumentException(){
        //given
        Client clientFrom = new Client("Bartek", "", 200);
        Client clientTo = new Client("Alek", "alek@a.pl", 500);
        double amount = 100;
        clients.add(clientFrom);
        clients.add(clientTo);
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
        Client clientFrom = new Client("Bartek", "bar@b.pl", 200);
        Client clientTo = new Client("Marek", "marek@m.pl", 500);
        double amount = 100;
        clients.add(clientFrom);
        clients.add(clientTo);
        //when
        clientService.transfer(bartekEmail, marekEmail, amount);
        //then
        Client expectedClientFrom = new Client("Bartek", "bar@b.pl", 100);
        Client expectedClientTo = new Client("Marek", "marek@m.pl", 600);
        Client actualClientFrom = clientService.findByEmail(clientFrom.getEmail());
        Client actualClientTo = clientService.findByEmail(clientTo.getEmail());

        SoftAssertions softAssertions = new SoftAssertions();
        softAssertions
                .assertThat(actualClientFrom)
                .isEqualTo(expectedClientFrom);
        softAssertions
                .assertThat(actualClientTo)
                .isEqualTo(expectedClientTo);
        softAssertions.assertAll();
    }

    @Test
    public void withdraw_AllParamsOk_foundsWithdrawn(){
        //given
        Client client = new Client("Bartek", "bar@b.pl", 200);
        clients.add(client);
        //when
        clientService.withdraw(client.getEmail(), 100);
        //then
        Client expectedClient = new Client("Bartek", "bar@b.pl", 100);
        Assertions.assertTrue(clients.contains(expectedClient));
    }

    @Test
    public void withdraw_negativeAmount_throwIllegalArgumentException(){
        //given
        Client client = new Client("Bartek", "bar@b.pl", 200);
        clients.add(client);
        //when/then
        Assertions.assertThrows(
                IllegalArgumentException.class, () ->
            clientService.withdraw(client.getEmail(), -100)
        );

    }

    @Test
    public void withdraw_zeroAmount_throwsIllegalArgumentException() {
        //given
        Client client = new Client("Bartek", "bar@b.pl", 200);
        clients.add(client);
        //when
        Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> clientService.withdraw(client.getEmail(), 0)
        );
    }

    @Test
    public void withdraw_amountBiggerThenBalance_throwsIllegalArgumentException() {
        //given
        Client client = new Client("Bartek", "bar@b.pl", 200);
        clients.add(client);
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
        Client client = new Client("Bartek", "bar@b.pl", 200);
        clients.add(client);
        //when
        clientService.withdraw(email, 50);
        //then
        Client expectedClient = new Client("Bartek", "bar@b.pl", 150);
        Client actualClient = clients.get(0);
        Assertions.assertEquals(expectedClient, actualClient);
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
