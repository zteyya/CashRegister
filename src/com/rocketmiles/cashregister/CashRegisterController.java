package com.rocketmiles.cashregister;

import java.util.Scanner;

public class CashRegisterController extends Constants{

    private static String sUserInput;
    private static String[] saUserInput;

    private static CashRegisterView MyView = new CashRegisterView();
    private static CashRegisterModel MyModel = new CashRegisterModel();

    private static Scanner scInput = new Scanner(System.in);

    public CashRegisterController() {
        // This class will handle all input and command from the user
    }

    public static void main(String[] args) {

        MyView.showWelcomeMessage();
        String sCommand;

        do {
            /* Get input from user */
            sUserInput = scInput.nextLine();

            /* Remove spaces */
            saUserInput = sUserInput.trim().split("\\s+");
            sCommand = saUserInput[0].toLowerCase();

            switch(sCommand) {
            case SHOW:
                MyView.showCashDetails(MyModel.getTotalCashValue(), MyModel.iaMoney);
                break;

            case PUT:
                if (processAddCash(saUserInput)) {
                    MyView.showCashDetails(MyModel.getTotalCashValue(), MyModel.iaMoney);
                }
                break;

            case TAKE:
                if (processDeductCash(saUserInput)) {
                    MyView.showCashDetails(MyModel.getTotalCashValue(), MyModel.iaMoney);
                }
                break;

            case CHANGE:
                processChange(saUserInput);
                break;

            case EXIT:
                MyView.showExitMessage();
                break;

            default:
                MyView.showErrorMessage(INVALID_COMMAND);
                break;
            }

        } while (!sCommand.equals(EXIT));

    }


    /* This method put bills in each denomination: 
     * #$20 #$10 #$5 #$2 #$1
     */
    private static boolean processAddCash(String[] saUserInput) {

        int iNumBillsToAdd = 0;
        boolean zStatus = false;

        /* Check number of parameters */
        /* Expected format: put n1 n2 n3 n4 n5 */
        if (saUserInput.length != 6) {
            MyView.showErrorMessage(INVALID_PARAMETER);
            return zStatus;
        } else {
            /* Must accept only valid integer numbers as input */
            try {
                for (int i = 0; i < NUM_DENOMINATION_TYPE; i++)
                    iNumBillsToAdd = iNumBillsToAdd + Integer.parseUnsignedInt(saUserInput[i + 1]);
            } catch (NumberFormatException e) {
                MyView.showErrorMessage(INVALID_PARAMETER);
                return zStatus;
            }
        }

        /* Check if the cash register can contain the bills to be added */
        if (MyModel.isAllowedToAdd(iNumBillsToAdd)) {
            for (int i = 0; i < NUM_DENOMINATION_TYPE; i++) {
                MyModel.addBills(i, Integer.parseUnsignedInt(saUserInput[i + 1]));
            }
            MyModel.computeTotalCashValue();
            zStatus = true;
        } else {
            MyView.showErrorMessage(VAULT_FULL);
        }

        return zStatus;
    }

    
    /* This method take bills in each denomination: 
     * #$20 #$10 #$5 #$2 #$1
     */
    private static boolean processDeductCash(String[] saUserInput) {

        boolean zStatus = false;

        /* Check number of parameters */
        /* Expected format: take n1 n2 n3 n4 n5 */
        if (saUserInput.length != 6) {
            MyView.showErrorMessage(INVALID_PARAMETER);
            return zStatus;
        } else {
            /* Must accept only valid integer numbers as input */
            try {
                for (int i = 0; i < NUM_DENOMINATION_TYPE; i++) {
                    if (!MyModel.isAllowedtoDeduct(i, Integer.parseUnsignedInt(saUserInput[i + 1]))) {
                        MyView.showErrorMessage(INSUFFICIENT_FUNDS);
                        return zStatus;
                    }
                }
            } catch (NumberFormatException e) {
                MyView.showErrorMessage(INVALID_PARAMETER);
                return zStatus;
            }

            /* Actual processing */
            for (int i = 0; i < NUM_DENOMINATION_TYPE; i++) {
                MyModel.deductBills(i, Integer.parseUnsignedInt(saUserInput[i + 1]));
            }
            MyModel.computeTotalCashValue();
            zStatus = true;
        }

        return zStatus;
    }


    /* Show requested change in each denomination: 
     * #$20 #$10 #$5 #$2 #$1 
     */
    private static boolean processChange(String[] saUserInput) {

        boolean zStatus = false;
        int iaChange[] = new int[NUM_DENOMINATION_TYPE];

        /* Check number of parameters */
        /* Expected format: change n */
        if (saUserInput.length != 2) {
            MyView.showErrorMessage(INVALID_PARAMETER);
            return zStatus;
        } else {
            /* Must accept only valid integer numbers as input */
            try {
                iaChange = MyModel.doChange(Integer.parseUnsignedInt(saUserInput[1]));
            } catch (NumberFormatException e) {
                MyView.showErrorMessage(INVALID_PARAMETER);
                return zStatus;
            }
        }

        if (iaChange != null) {
            // This means, change can be performed
            MyView.showChange(iaChange);

            for (int i = 0; i < NUM_DENOMINATION_TYPE; i++) {
                MyModel.deductBills(i, iaChange[i]);
            }
            MyModel.computeTotalCashValue();
            zStatus = true;
        } else {
            // No change can be issued
            MyView.showErrorMessage(NO_CHANGE_AVAILABLE);
        }

        return zStatus;
    }

}
