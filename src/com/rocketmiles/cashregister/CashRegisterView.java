package com.rocketmiles.cashregister;


public class CashRegisterView extends Constants {

    public CashRegisterView() {
        // TODO Auto-generated constructor stub
    }

    protected void showCashierValue() {
        System.out.println(MESSAGE_WELCOME);
    }


    protected void showWelcomeMessage() {
        System.out.println("===========================");
        System.out.println(MESSAGE_WELCOME);
        System.out.println("===========================");
        System.out.println();
        System.out.println("Ready");
    }
    
    protected void showErrorMessage() {
        System.out.println(MESSAGE_ERROR_GENERIC);
    }
    
    protected void showErrorMessage(int iError) {
        switch (iError) {
        case INVALID_PARAMETER:
            System.out.println(MESSAGE_ERROR_INVALID_PARAMETER);
            break;
        case VAULT_FULL:
            System.out.println(MESSAGE_ERROR_VAULT_FULL);
            break;
        case INSUFFICIENT_FUNDS:
            System.out.println(MESSAGE_ERROR_INSUFFICIENT_FUNDS);
            break;
        case INVALID_COMMAND:
            System.out.println(MESSAGE_ERROR_INVALID_COMMAND);
            break;
        case NO_CHANGE_AVAILABLE:
            System.out.println(MESSAGE_ERROR_NO_CHANGE_AVAILABLE);
            break;
        default:
            System.out.println(MESSAGE_ERROR_GENERIC);
            break;
        }

    }

    /* This method displays a detailed view of the cash register */
    protected void showCashDetails(int totalCashValue, int[][] iaMoney) {
        String sData = "$" + totalCashValue;

        for (int i=0; i < NUM_DENOMINATION_TYPE; i++) {
            sData = sData + " " + iaMoney[BILL_COUNT_INDEX][i];
        }

        System.out.println(sData);
    }
    
    /* This method displays the number of bills per denomination for change*/
    protected void showChange(int[] iaChange) {

        for (int i=0; i < iaChange.length; i++) {
            System.out.print(iaChange[i]);
            if (i != iaChange.length - 1) {
                System.out.print(" ");
            } else {
                System.out.println();
            }
        }
    }

    protected void showExitMessage() {
        System.out.println(MESSAGE_EXIT);
    }

    
}
