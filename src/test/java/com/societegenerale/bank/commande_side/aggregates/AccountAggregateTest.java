package com.societegenerale.bank.commande_side.aggregates;

import com.societegenerale.bank.commande_side.commands.CreateAccountCommand;
import com.societegenerale.bank.commande_side.commands.CreditAccountCommand;
import com.societegenerale.bank.commande_side.commands.DebitAccountCommand;
import com.societegenerale.bank.core_api.enums.AccountStatus;
import com.societegenerale.bank.core_api.enums.Currency;
import com.societegenerale.bank.core_api.events.AccountActivatedEvent;
import com.societegenerale.bank.core_api.events.AccountCreatedEvent;
import com.societegenerale.bank.core_api.events.AccountCreditedEvent;
import com.societegenerale.bank.core_api.events.AccountDebitedEvent;
import com.societegenerale.bank.core_api.exceptions.InsufficientBalanceException;
import com.societegenerale.bank.core_api.exceptions.NegativeAmmountException;
import org.assertj.core.api.Assertions;
import org.axonframework.test.aggregate.AggregateTestFixture;
import org.axonframework.test.aggregate.FixtureConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import java.math.BigDecimal;
import java.util.Date;


class AccountAggregateTest {

    private FixtureConfiguration<AccountAggregate> fixture;

    @BeforeEach
    void setUp() {
        fixture = new AggregateTestFixture<>(AccountAggregate.class);
    }

    @Test
    void itShouldOnCreateAccountCommand() {
        Date date = new Date();
        String id = "1";
        fixture
                .given()
                .when(new CreateAccountCommand(id,new BigDecimal(100),Currency.USD))
                .expectEvents(new AccountCreatedEvent(id,date,new BigDecimal(100),Currency.USD,AccountStatus.CREATED),
                        new AccountActivatedEvent(id,date,AccountStatus.ACTIVATED));
    }

    @Test
    void itShouldOnCreditAccountCommand() {
        Date date = new Date();
        String id = "1";
        fixture
                .given(new AccountCreatedEvent(id,date,new BigDecimal(3000), Currency.EUR, AccountStatus.CREATED),
                        new AccountActivatedEvent(id,date,AccountStatus.ACTIVATED))
                .when(new CreditAccountCommand(id,new BigDecimal(100)))
                .expectEvents(new AccountCreditedEvent(id,date,new BigDecimal(100)))
                .expectState(state ->
                        {
                            Assertions.assertThat(state.getAccountId()).isEqualTo(id);
                            Assertions.assertThat(state.getBalance()).isEqualTo(new BigDecimal(3100));
                            Assertions.assertThat(state.getCurrency()).isEqualTo(Currency.EUR);
                            Assertions.assertThat(state.getStatus()).isEqualTo(AccountStatus.ACTIVATED);
                        }
                );
    }

    @Test
    void itShouldThrowExeptionOnCreditAccountCommand() {
        Date date = new Date();
        String id = "2";
        fixture
                .given(new AccountCreatedEvent(id,date,new BigDecimal(3000), Currency.EUR, AccountStatus.CREATED),
                        new AccountActivatedEvent(id,date,AccountStatus.ACTIVATED))
                .when(new CreditAccountCommand(id,new BigDecimal(-100)))
                .expectException(NegativeAmmountException.class);
    }


    @Test
    void itShouldOnDebitAccountCommand() {
        Date date = new Date();
        String id = "3";
        fixture
                .given(new AccountCreatedEvent(id,date,new BigDecimal(1000), Currency.MAD, AccountStatus.CREATED),
                        new AccountActivatedEvent(id,date,AccountStatus.ACTIVATED))
                .when(new DebitAccountCommand(id,new BigDecimal(200)))
                .expectEvents(new AccountDebitedEvent(id,date,new BigDecimal(200)))
                .expectState(state ->
                        {
                            Assertions.assertThat(state.getAccountId()).isEqualTo(id);
                            Assertions.assertThat(state.getBalance()).isEqualTo(new BigDecimal(800));
                            Assertions.assertThat(state.getCurrency()).isEqualTo(Currency.MAD);
                            Assertions.assertThat(state.getStatus()).isEqualTo(AccountStatus.ACTIVATED);
                        }
                );;
    }

    @Test
    void itShouldThrowExeptionOnDebitAccountCommand() {
        Date date = new Date();
        String id = "4";
        fixture
                .given(new AccountCreatedEvent(id,date,new BigDecimal(3000), Currency.EUR, AccountStatus.CREATED),
                        new AccountActivatedEvent(id,date,AccountStatus.ACTIVATED))
                .when(new DebitAccountCommand(id,new BigDecimal(-100)))
                .expectException(NegativeAmmountException.class);

        fixture
                .given(new AccountCreatedEvent(id,date,new BigDecimal(3000), Currency.EUR, AccountStatus.CREATED),
                        new AccountActivatedEvent(id,date,AccountStatus.ACTIVATED))
                .when(new DebitAccountCommand(id,new BigDecimal(10000)))
                .expectException(InsufficientBalanceException.class);
    }






}