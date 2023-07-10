package com.societegenerale.bank.commande_side.aggregates;

import com.societegenerale.bank.commande_side.commands.CreateAccountCommand;
import com.societegenerale.bank.commande_side.commands.CreditAccountCommand;
import com.societegenerale.bank.commande_side.commands.DebitAccountCommand;
import com.societegenerale.bank.core_api.enums.Currency;
import com.societegenerale.bank.core_api.enums.AccountStatus;
import com.societegenerale.bank.core_api.events.AccountActivatedEvent;
import com.societegenerale.bank.core_api.events.AccountCreatedEvent;
import com.societegenerale.bank.core_api.events.AccountCreditedEvent;
import com.societegenerale.bank.core_api.events.AccountDebitedEvent;
import com.societegenerale.bank.core_api.exceptions.InsufficientBalanceException;
import com.societegenerale.bank.core_api.exceptions.NegativeAmmountException;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import java.math.BigDecimal;
import java.util.Date;

@Aggregate
@NoArgsConstructor
@Slf4j
public class AccountAggregate {
    @AggregateIdentifier
    @Getter
    private String accountId;
    @Getter
    private BigDecimal balance;
    @Getter
    private Currency currency;
    @Getter
    private AccountStatus status;

    @CommandHandler
    public AccountAggregate(CreateAccountCommand createAccountCommand) {
        log.info("CreateAccountCommand Received");
        AggregateLifecycle.apply(new AccountCreatedEvent(
                createAccountCommand.getAggregateId(),
                new Date(),
                createAccountCommand.getBalance(),
                createAccountCommand.getCurrency(),
                AccountStatus.CREATED
        ));
    }

    @EventSourcingHandler
    public void on(AccountCreatedEvent accountCreatedEvent){
        log.info("AccountCreatedEvent Occured");
        this.accountId=accountCreatedEvent.getId();
        this.balance=accountCreatedEvent.getBalance();
        this.currency=accountCreatedEvent.getCurrency();
        this.status=accountCreatedEvent.getStatus();

        AggregateLifecycle.apply(new AccountActivatedEvent(
                accountCreatedEvent.getId(),
                new Date(),
                AccountStatus.ACTIVATED
        ));
    }

    @EventSourcingHandler
    public void on(AccountActivatedEvent accountActivatedEvent){
        log.info("AccountActivatedEvent Occured");
        this.status=accountActivatedEvent.getStatus();
    }


    //Debit account
    @CommandHandler
    public void handle(DebitAccountCommand debitAccountCommand){
        log.info("DebitAccountCommand Received");
        if(debitAccountCommand.getAmount().doubleValue() <= 0 ) {
            throw new NegativeAmmountException("Negative Ammount=>" + debitAccountCommand.getAmount());
        }
        else if(this.balance.subtract(debitAccountCommand.getAmount()).doubleValue() < 0){
            throw new InsufficientBalanceException("Insufficient Balance=>"+this.balance.doubleValue());
        }else {
            AggregateLifecycle.apply(new AccountDebitedEvent(
                    debitAccountCommand.getAggregateId(),
                    new Date(),
                    debitAccountCommand.getAmount()
            ));
        }
    }

    @EventSourcingHandler
    public void on(AccountDebitedEvent accountDebitedEvent){
        log.info("AccountDebitedEvent Occured");
        this.balance = this.balance.subtract(accountDebitedEvent.getAmount());
    }

    //Credit Account
    @CommandHandler
    public void handle(CreditAccountCommand creditAccountCommand){
        log.info("CreditAccountCommand Received");
        if(creditAccountCommand.getAmount().doubleValue() <= 0 ) {
            throw new NegativeAmmountException("Negative Ammount=>" + creditAccountCommand.getAmount());
        }else {
            AggregateLifecycle.apply(new AccountCreditedEvent(
                    creditAccountCommand.getAggregateId(),
                    new Date(),
                    creditAccountCommand.getAmount()
            ));
        }
    }

    @EventSourcingHandler
    public void on(AccountCreditedEvent accountCreditedEvent){
        log.info("AccountActivatedEvent Occured");
        this.balance = this.balance.add(accountCreditedEvent.getAmount());
    }
}
