import org.bestbank.controller.dto.AccountRequest;
import org.bestbank.controller.dto.AccountResponse;
import org.bestbank.controller.dto.ClientRequest;
import org.bestbank.repository.AccountRepository;
import org.bestbank.repository.ClientRepository;
import org.bestbank.repository.entity.Account;
import org.bestbank.repository.entity.Client;
import org.bestbank.service.AccountService;
import org.bestbank.service.ClientService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.NoSuchElementException;

import static org.mockito.Mockito.*;

public class AccountServiceTest {
    private AccountService accountService;
    private AccountRepository repository;
    private ClientRepository clientRepository;

    @BeforeEach
    public void setup(){
        repository = mock(AccountRepository.class);
        clientRepository = mock(ClientRepository.class);
        accountService = new AccountService(repository, clientRepository);
    }

    @Test
    public void save_AllParamsOk_AccountSaved(){
        //given
        AccountRequest accountRequest = new AccountRequest(1000, "PLN", 4L);
        when(clientRepository.getOne(accountRequest.getUser_id())).thenReturn(new Client(4L,"Bartek", "bar@a.pl", Collections.singletonList(new Account(1000, "PLN"))));
        Account account = Account.builder()
                .balance(accountRequest.getBalance())
                .currency(accountRequest.getCurrency())
                .user_id(accountRequest.getUser_id())
                .build();
        //when
        accountService.save(accountRequest);
        //then
        verify(repository).save(account);
    }

    @Test
    public void save_nullAccount_throwIllegalArgumentException(){
        //given/when/then
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> accountService.save(null)
        );
    }

    @Test
    public void save_nullCurrency_throwIllegalArgumentException(){
        //given
        AccountRequest accountRequest = new AccountRequest(1000, null, 4L);
        //when/then
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> accountService.save(accountRequest)
        );
    }

    @Test
    public void save_emptyCurrency_throwIllegalArgumentException(){
        //given
        AccountRequest accountRequest = new AccountRequest(1000, "", 4L);
        //when/then
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> accountService.save(accountRequest)
        );
    }

    @Test
    public void save_negativeBalance_throwIllegalArgumentException(){
        //given
        AccountRequest accountRequest = new AccountRequest(-100, "PLN", 4L);
        //when/then
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> accountService.save(accountRequest)
        );
    }

    @Test
    public void save_doesntExistUserId_throwIllegalArgumentException(){
        //given
        AccountRequest accountRequest = new AccountRequest(100, "PLN", 4L);
        //when/then
        Assertions.assertThrows(NoSuchElementException.class,
                () -> accountService.save(accountRequest)
        );
    }

    @Test
    public void findById_AllParamsOk_foundAccount(){
        //given
        Account account = new Account(4L, 100, "PLN", 5L);
        when(repository.getOne(account.getId())).thenReturn(account);
        //when
        AccountResponse accountResponse = accountService.findById(account.getId());
        //then
        AccountResponse expectedAccount = new AccountResponse(4L,100, "PLN", 5L);

        Assertions.assertEquals(expectedAccount, accountResponse);
    }

    @Test
    public void findById_doesntExistAccount_throwIllegalArgumentException(){
        //given
        Account account = new Account(4L, 100, "PLN", 5L);
        //when/then
        Assertions.assertThrows(NoSuchElementException.class,
                () -> accountService.findById(account.getId())
        );
    }

    @Test
    public void transfer_AllParamsOk_foundsTransferred(){
        //given
        Account accountFrom = new Account(4L, 100, "PLN", 5L);
        Account accountTo = new Account(5L, 100, "PLN", 5L);
        double amount = 50;
        when(repository.getOne(accountFrom.getId())).thenReturn(accountFrom);
        when(repository.getOne(accountTo.getId())).thenReturn(accountTo);
        //when
        accountService.transfer(accountFrom.getId(), accountTo.getId(), amount);
        //then
        Account expectedAccountFrom = new Account(4L, 50, "PLN", 5L);
        Account expectedAccountTo = new Account(5L, 150, "PLN", 5L);

        verify(repository).save(expectedAccountFrom);
        verify(repository).save(expectedAccountTo);

    }

    @Test
    public void transfer_notEnoughFounds_throwIllegalArgumentException(){
        //given
        Account accountFrom = new Account(4L, 100, "PLN", 5L);
        Account accountTo = new Account(5L, 100, "PLN", 5L);
        double amount = 1000;
        when(repository.getOne(accountFrom.getId())).thenReturn(accountFrom);
        when(repository.getOne(accountTo.getId())).thenReturn(accountTo);
        //when
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> accountService.transfer(accountFrom.getId(), accountTo.getId(), amount)
        );
    }

    @Test
    public void transfer_negativeAmount_throwIllegalArgumentException(){
        //given
        Account accountFrom = new Account(4L, 100, "PLN", 5L);
        Account accountTo = new Account(5L, 100, "PLN", 5L);
        double amount = -100;
        when(repository.getOne(accountFrom.getId())).thenReturn(accountFrom);
        when(repository.getOne(accountTo.getId())).thenReturn(accountTo);
        //when
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> accountService.transfer(accountFrom.getId(), accountTo.getId(), amount)
        );
    }

    @Test
    public void transfer_toSameClient_throwIllegalArgumentException(){
        //given
        Account accountFrom = new Account(4L, 100, "PLN", 5L);
        Account accountTo = new Account(5L, 100, "PLN", 5L);
        double amount = 100;
        //when
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> accountService.transfer(accountFrom.getId(), accountFrom.getId(), amount)
        );
    }

    @Test
    public void transfer_doesntExistFromAccount_throwIllegalArgumentException(){
        //given
        Account accountFrom = new Account(4L, 100, "PLN", 5L);
        Account accountTo = new Account(5L, 100, "PLN", 5L);
        double amount = 100;
        when(repository.getOne(accountTo.getId())).thenReturn(accountTo);
        //when
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> accountService.transfer(accountFrom.getId(), accountTo.getId(), amount)
        );
    }

    @Test
    public void transfer_doesntExistToAccount_throwIllegalArgumentException(){
        //given
        Account accountFrom = new Account(4L, 100, "PLN", 5L);
        Account accountTo = new Account(5L, 100, "PLN", 5L);
        double amount = 100;
        when(repository.getOne(accountFrom.getId())).thenReturn(accountFrom);
        //when
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> accountService.transfer(accountFrom.getId(), accountTo.getId(), amount)
        );
    }
}
