package it.unibo.bank.impl;

import it.unibo.bank.api.AccountHolder;
import it.unibo.bank.api.BankAccount;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestStrictBankAccount {

    private final static int INITIAL_AMOUNT = 100;
    private final static int NEGATIVE_WITHDRAW = -100;

    // 1. Create a new AccountHolder and a StrictBankAccount for it each time tests are executed.
    private AccountHolder mRossi;
    private BankAccount bankAccount;

    @BeforeEach
    public void setUp() {
        mRossi = new AccountHolder("Mario", "Rossi", 1);
        bankAccount = new StrictBankAccount(mRossi, 0.0);
    }

    // 2. Test the initial state of the StrictBankAccount
    @Test
    public void testInitialization() {
        assertEquals(mRossi, bankAccount.getAccountHolder());
        assertEquals(0.0, bankAccount.getBalance());
        assertTrue(bankAccount.getTransactionsCount() == 0);
    }


    // 3. Perform a deposit of 100€, compute the management fees, and check that the balance is correctly reduced.
    @Test
    public void testManagementFees() {
        assertFalse(bankAccount.getTransactionsCount() > 0);
        bankAccount.deposit(mRossi.getUserID(), INITIAL_AMOUNT);
        final double expectedValue = bankAccount.getBalance() - (SimpleBankAccount.MANAGEMENT_FEE + bankAccount.getTransactionsCount() * StrictBankAccount.TRANSACTION_FEE);
        bankAccount.chargeManagementFees(mRossi.getUserID());       
        assertEquals(expectedValue, bankAccount.getBalance());
        assertTrue(bankAccount.getTransactionsCount() == 0);
   
    }

    // 4. Test the withdraw of a negative value
    @Test
    public void testNegativeWithdraw() {
    
        try {
            bankAccount.withdraw(mRossi.getUserID(), NEGATIVE_WITHDRAW);
            Assertions.fail();
        } catch (IllegalArgumentException e) {
            Assertions.assertEquals("Cannot withdraw a negative amount", e.getMessage());
        }
    }

    //5. Test withdrawing more money than it is in the account
    @Test
    public void testWithdrawingTooMuch() {
        try {
            bankAccount.withdraw(mRossi.getUserID(), bankAccount.getBalance() + INITIAL_AMOUNT);
            Assertions.fail();
        } catch (IllegalArgumentException e) {
            Assertions.assertEquals("Insufficient balance", e.getMessage());
        }
    }
}
