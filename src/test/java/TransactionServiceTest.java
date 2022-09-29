import org.bestbank.controller.dto.AccountResponse;
import org.bestbank.controller.dto.TransactionRequest;
import org.bestbank.repository.AccountRepository;
import org.bestbank.repository.ClientRepository;
import org.bestbank.repository.TransactionRepository;
import org.bestbank.repository.entity.Transaction;
import org.bestbank.service.AccountService;
import org.bestbank.service.TimeUtils;
import org.bestbank.service.TransactionService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.TestPropertySource;

import java.time.OffsetDateTime;

import static org.mockito.Mockito.*;

public class TransactionServiceTest {
    private TransactionService transactionService;
    private TransactionRepository repository;
    private AccountService accountService;
    private TimeUtils timeUtils;

    @BeforeEach
    public void setup(){
        repository = mock(TransactionRepository.class);
        accountService = mock(AccountService.class);
        timeUtils = mock(TimeUtils.class);
        transactionService = new TransactionService(repository, accountService,timeUtils);
    }

    @Test
    public void createTransaction_AllParamsOk_TransactionCreated(){
        //given
        TransactionRequest transactionRequest = new TransactionRequest(100, "PLN", 4L, 5L);

        when(accountService.findById(transactionRequest.getFrom_account_id())).thenReturn(new AccountResponse(4L, 100, "PLN", 10L));
        when(accountService.findById(transactionRequest.getTo_account_id())).thenReturn(new AccountResponse(5L, 100, "PLN", 10L));

        Transaction transaction = Transaction.builder()
                .amount(transactionRequest.getAmount())
                .currency(transactionRequest.getCurrency())
                .transaction_date(timeUtils.getTime())
                .from_account_id(transactionRequest.getFrom_account_id())
                .to_account_id(transactionRequest.getTo_account_id())
                .build();
        //when
        transactionService.createTransaction(transactionRequest);
        //then
        verify(repository).save(transaction);
    }

    @Test
    public void createTransaction_negativeAmount_throwIllegalArgumentException(){
        //given
        TransactionRequest transactionRequest = new TransactionRequest(-100, "PLN", 4L, 5L);
        //when/then
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> transactionService.createTransaction(transactionRequest)
        );
    }

    @Test
    public void createTransaction_emptyCurrency_throwIllegalArgumentException(){
        //given
        TransactionRequest transactionRequest = new TransactionRequest(100, "", 4L, 5L);
        //when/then
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> transactionService.createTransaction(transactionRequest)
        );
    }

    @Test
    public void createTransaction_nullCurrency_throwIllegalArgumentException(){
        //given
        TransactionRequest transactionRequest = new TransactionRequest(100, null, 4L, 5L);
        //when/then
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> transactionService.createTransaction(transactionRequest)
        );
    }

    @Test
    public void createTransaction_doesntExistFromAccountId_throwIllegalArgumentException(){
        //given
        TransactionRequest transactionRequest = new TransactionRequest(100, "PLN", 4L, 5L);
        when(accountService.findById(transactionRequest.getTo_account_id())).thenReturn(new AccountResponse(5L, 100, "PLN", 10L));
        //when/then
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> transactionService.createTransaction(transactionRequest)
        );
    }

    @Test
    public void createTransaction_doesntExistToAccountId_throwIllegalArgumentException(){
        //given
        TransactionRequest transactionRequest = new TransactionRequest(100, "PLN", 4L, 5L);
        when(accountService.findById(transactionRequest.getFrom_account_id())).thenReturn(new AccountResponse(4L, 100, "PLN", 10L));
        //when/then
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> transactionService.createTransaction(transactionRequest)
        );
    }
}
