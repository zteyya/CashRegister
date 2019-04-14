package com.rocketmiles.cashregister;

public class Constants {

    /* Application accepts $20, $10, $5, $2 and $1 bills. 
     * Later on, application may be updated to accept other denominations */
    static final int NUM_DENOMINATION_TYPE = 5;

    /* This is the maximum number of bills that the cash register can contain */
    static final int MAX_NUM_BILLS = 100; 

    /* Constant indices for denomination array list */
    protected final int TWENTY_DOLLARS = 0;
    protected final int TEN_DOLLARS = 1;
    protected final int FIVE_DOLLARS = 2;
    protected final int TWO_DOLLARS = 3;
    protected final int ONE_DOLLARS = 4;

    protected static final int BILL_COUNT_INDEX = 0;
    protected static final int DENOMINATION_INDEX = 1;

    /* Command Keywords */
    protected static final String SHOW = "show";
    protected static final String PUT = "put";
    protected static final String TAKE = "take";
    protected static final String CHANGE = "change";
    protected static final String EXIT = "quit";

    static final String MESSAGE_WELCOME = "RocketMiles Cash Register";
    static final String MESSAGE_EXIT = "Bye";
    static final String MESSAGE_ERROR_GENERIC = "Error encountered.";
    static final String MESSAGE_ERROR_INVALID_COMMAND = "Error encountered. Invalid command.";
    static final String MESSAGE_ERROR_INVALID_PARAMETER = "Error encountered. Invalid parameters.";
    static final String MESSAGE_ERROR_VAULT_FULL = "Limit will be reached. Cannot process transaction.";
    static final String MESSAGE_ERROR_INSUFFICIENT_FUNDS = "Funds not enough. Cannot process transaction.";
    static final String MESSAGE_ERROR_NO_CHANGE_AVAILABLE = "No change can be made with the amount.";

    /* Error Codes */
    protected static final int INVALID_PARAMETER = 1;
    protected static final int VAULT_FULL = 2;
    protected static final int INSUFFICIENT_FUNDS = 3;
    protected static final int INVALID_COMMAND = 4;
    protected static final int NO_CHANGE_AVAILABLE = 5; 
    
    public Constants() {
        // constructor
    }

}
