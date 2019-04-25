package com.rocketmiles.cashregister;


public class CashRegisterModel extends Constants {


    int[][] iaMoney = new int[2][NUM_DENOMINATION_TYPE];

    private static int iTotalNumberOfBills;
    private static int iTotalCashValue; 

    public CashRegisterModel() {
        iTotalNumberOfBills = 0;
        iTotalCashValue = 0;

        initializeMoney();

    }

    private void initializeMoney() {
        iaMoney[DENOMINATION_INDEX][ONE_DOLLARS] = 1;
        iaMoney[DENOMINATION_INDEX][TWO_DOLLARS] = 2;
        iaMoney[DENOMINATION_INDEX][FIVE_DOLLARS] = 5;
        iaMoney[DENOMINATION_INDEX][TEN_DOLLARS] = 10;
        iaMoney[DENOMINATION_INDEX][TWENTY_DOLLARS] = 20;
    }

    protected void setBillCount(int iDenominationType, int iQuantity) {
        iaMoney[BILL_COUNT_INDEX][iDenominationType] = iQuantity;
    }

    protected void addBills(int iDenominationType, int iQuantity) {
        iaMoney[BILL_COUNT_INDEX][iDenominationType] += iQuantity;
    }

    protected void deductBills(int iDenominationType, int iQuantity) {
        iaMoney[BILL_COUNT_INDEX][iDenominationType] -= iQuantity;
    }

    protected int getBillCount(int iDenominationType) {
        return iaMoney[BILL_COUNT_INDEX][iDenominationType];
    }

    /* Use this method to get the number of bills the cashier holds */
    protected int getTotalNumberOfBills() {
        iTotalNumberOfBills = 0;

        for (int i = 0; i < NUM_DENOMINATION_TYPE; i++) {
            iTotalNumberOfBills = iTotalNumberOfBills + iaMoney[BILL_COUNT_INDEX][i];
        }

        return iTotalNumberOfBills;
    }

    protected boolean isAllowedToAdd(int iQuantity) {
        if ((iQuantity + getTotalNumberOfBills()) <= MAX_NUM_BILLS) {
            return true;
        }
        return false;
    }

    protected boolean isAllowedtoDeduct(int iDenominationType, int iQuantity) {
        if (iQuantity > iaMoney[BILL_COUNT_INDEX][iDenominationType]) {
            return false;
        }
        return true;
    }

    protected int [] doChange(int iValue) {
        boolean zFlag = false;
        int iValueTemp = iValue;
        int iaChangeSolution[] = new int[NUM_DENOMINATION_TYPE];
        int iOtherTemp = 0;
        int iGreatestDenominationReference = 0; // holds the index of the first non-zero value of iaChangeSolution
        int iIdx = 0;
        boolean zUpdateGDR = true;


        if (iValue == 0 || iValue > getTotalCashValue()) {
            return null;
        }

        while (iGreatestDenominationReference < NUM_DENOMINATION_TYPE - 1) {
            for ( ; iIdx < NUM_DENOMINATION_TYPE; iIdx++) {
                if (iValueTemp >= iaMoney[DENOMINATION_INDEX][iIdx]) {
                    for (int j = iaMoney[BILL_COUNT_INDEX][iIdx]; (j > 0); j--) {

                        if (zUpdateGDR) {
                            /* Update iGreatestDenominationReference to hold the index of the first non-zero value */
                            iGreatestDenominationReference = iIdx;
                            zUpdateGDR = false; /* Only need to be done once */
                        }

                        iValueTemp = iValueTemp - iaMoney[DENOMINATION_INDEX][iIdx];
                        iaChangeSolution[iIdx]++;
                        if (iValueTemp < iaMoney[DENOMINATION_INDEX][iIdx]) {
                            break;
                        }
                    }
                }
            }

            // Check if the solution is OK, otherwise, iterate
            iOtherTemp = 0;
            for (int i = 0; i < NUM_DENOMINATION_TYPE; i++) {
                iOtherTemp = iOtherTemp + iaChangeSolution[i] * iaMoney[DENOMINATION_INDEX][i];
            }
            if (iOtherTemp == iValue) {
                zFlag = true;
                break;
            } else {
                if (iaChangeSolution[iGreatestDenominationReference] != 0) {
                    iaChangeSolution[iGreatestDenominationReference]--;
                    iValueTemp += iaMoney[DENOMINATION_INDEX][iGreatestDenominationReference];
                } else {
                    // if (iaChangeSolution[iGreatestDenominationReference] == 0) {
                    // Move the reference to the next denomination
                    iGreatestDenominationReference++;
                }
                iIdx = iGreatestDenominationReference + 1;
            }
        }

        if (!zFlag) {
            return null;
        }

        return iaChangeSolution;
    }


    /* Computes the total value of the cash register */
    protected void computeTotalCashValue() {
        iTotalCashValue = 0;

        for (int i = 0; i < NUM_DENOMINATION_TYPE; i++) {
            iTotalCashValue = iTotalCashValue + iaMoney[BILL_COUNT_INDEX][i] * iaMoney[DENOMINATION_INDEX][i];
        }

    }

    /* Returns the total value of the cash register */
    protected int getTotalCashValue() {
        computeTotalCashValue();
        return iTotalCashValue;
    }

}
