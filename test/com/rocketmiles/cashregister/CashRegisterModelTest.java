package com.rocketmiles.cashregister;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


/* This is a unit test for CashRegisterModel */
public class CashRegisterModelTest extends Constants {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSetBillCount() {
        CashRegisterModel model = new CashRegisterModel();

        for (int i = 0; i < NUM_DENOMINATION_TYPE; i++) {
            model.setBillCount(i, i + 1);
        }

        for (int i = 0; i < NUM_DENOMINATION_TYPE; i++) {
            assertEquals(i + 1, model.iaMoney[BILL_COUNT_INDEX][i]);
        }
    }

    @Test
    public void testAddBills() {
        CashRegisterModel model = new CashRegisterModel();

        // add bills 1 2 3 4 5
        for (int i = 0; i < NUM_DENOMINATION_TYPE; i++) {
            model.addBills(i, i + 1);
        }

        // add bills 1 2 3 4 5
        for (int i = 0; i < NUM_DENOMINATION_TYPE; i++) {
            model.addBills(i, i + 1);
        }

        // verify that the bills are added
        for (int i = 0; i < NUM_DENOMINATION_TYPE; i++) {

            assertEquals((i + 1) * 2, model.iaMoney[BILL_COUNT_INDEX][i]);
        }
    }

    @Test
    public void testDeductBills() {
        CashRegisterModel model = new CashRegisterModel();

        // add bills 1 2 3 4 5
        for (int i = 0; i < NUM_DENOMINATION_TYPE; i++) {
            model.addBills(i, i + 1);
        }

        // deduct bills 1 2 3 4 5
        for (int i = 0; i < NUM_DENOMINATION_TYPE; i++) {
            model.deductBills(i, i + 1);
        }

        // verify that the bills are deducted
        for (int i = 0; i < NUM_DENOMINATION_TYPE; i++) {
            assertEquals(0, model.iaMoney[BILL_COUNT_INDEX][i]);
        }
    }

    @Test
    public void testGetBillCount() {
        CashRegisterModel model = new CashRegisterModel();

        // add bills 1 2 3 4 5
        for (int i = 0; i < NUM_DENOMINATION_TYPE; i++) {
            model.addBills(i, i + 1);
        }

        // get bill count per denomination
        for (int i = 0; i < NUM_DENOMINATION_TYPE; i++) {
            assertEquals(i + 1, model.getBillCount(i));
        }

        // deduct bills 1 2 3 4 5
        for (int i = 0; i < NUM_DENOMINATION_TYPE; i++) {
            model.deductBills(i, i + 1);
        }

        // get bill count per denomination
        for (int i = 0; i < NUM_DENOMINATION_TYPE; i++) {
            assertEquals(0, model.getBillCount(i));
        }
    }

    @Test
    public void testGetTotalNumberOfBills() {
        CashRegisterModel model = new CashRegisterModel();
        int iTotalBills = 0;

        // add bills 1 2 3 4 5
        for (int i = 0; i < NUM_DENOMINATION_TYPE; i++) {
            model.addBills(i, i + 1);
            iTotalBills = iTotalBills + i + 1;
            assertEquals(iTotalBills, model.getTotalNumberOfBills());
        }

        // deduct bills 1 2 3 4 5
        for (int i = 0; i < NUM_DENOMINATION_TYPE; i++) {
            model.deductBills(i, i + 1);
            iTotalBills = iTotalBills - (i + 1);
            assertEquals(iTotalBills, model.getTotalNumberOfBills());
        }
    }

    @Test
    public void testIsAllowedToAdd() {
        CashRegisterModel model = new CashRegisterModel();
        int iQuantity = 0;

        /* Test when total number of bills equal to empty */
        // set total number of bills to add equal to 0 (min)
        assertEquals(true, model.isAllowedToAdd(0));

        // set total number of bills to add equal to 1 (min + 1)
        assertEquals(true, model.isAllowedToAdd(1));

        // set total number of bills to add equal to 50 (mid)
        assertEquals(true, model.isAllowedToAdd(MAX_NUM_BILLS / 2));

        // set total number of bills to add equal to 100 (max)
        assertEquals(true, model.isAllowedToAdd(MAX_NUM_BILLS));

        /* Test when total number of bills equal to half */
        //model.iTotalNumberOfBills = MAX_NUM_BILLS / 2;

        iQuantity = MAX_NUM_BILLS / NUM_DENOMINATION_TYPE / 2;
        for (int i = 0; i < NUM_DENOMINATION_TYPE; i++) {
            model.addBills(i, iQuantity);
        }

        assertEquals(MAX_NUM_BILLS / 2, model.getTotalNumberOfBills());

        // set total number of bills to add equal to 0 (min)
        assertEquals(true, model.isAllowedToAdd(0));

        // set total number of bills to add equal to 1 (min + 1)
        assertEquals(true, model.isAllowedToAdd(1));

        // set total number of bills to add equal to 50 (mid)
        assertEquals(true, model.isAllowedToAdd(MAX_NUM_BILLS / 2));

        // set total number of bills to add equal to 100 (max)
        assertEquals(false, model.isAllowedToAdd(MAX_NUM_BILLS));

        /* Test when total number of bills equal to full */
        //model.iTotalNumberOfBills = MAX_NUM_BILLS;
        for (int i = 0; i < NUM_DENOMINATION_TYPE; i++) {
            model.addBills(i, iQuantity);
        }

        assertEquals(MAX_NUM_BILLS, model.getTotalNumberOfBills());

        // set total number of bills to add equal to 0 (min)
        assertEquals(true, model.isAllowedToAdd(0));

        // set total number of bills to add equal to 1 (min + 1)
        assertEquals(false, model.isAllowedToAdd(1));

        // set total number of bills to add equal to 50 (mid)
        assertEquals(false, model.isAllowedToAdd(MAX_NUM_BILLS / 2));

        // set total number of bills to add equal to 100 (max)
        assertEquals(false, model.isAllowedToAdd(MAX_NUM_BILLS));

    }

    @Test
    public void testIsAllowedtoDeduct() {
        CashRegisterModel model = new CashRegisterModel();

        // add bills 1 2 3 4 5
        for (int i = 0; i < NUM_DENOMINATION_TYPE; i++) {
            model.addBills(i, i + 1);
        }

        for (int i = 0; i < NUM_DENOMINATION_TYPE; i++) {
            assertEquals(true, model.isAllowedtoDeduct(i, i + 1));
        }

        // deduct bills 1 2 3 4 5
        for (int i = 0; i < NUM_DENOMINATION_TYPE; i++) {
            model.deductBills(i, i + 1);
        }

        for (int i = 0; i < NUM_DENOMINATION_TYPE; i++) {
            assertEquals(false, model.isAllowedtoDeduct(i, i + 1));
        }

    }

    @Test
    public void testDoChange() {

        CashRegisterModel model = new CashRegisterModel();
        int [] iaActualChange = new int[NUM_DENOMINATION_TYPE];
        int [] iaExpectedChange = new int[NUM_DENOMINATION_TYPE];

        // set cash registry: $43 1 0 3 4 0
        model.setBillCount(TWENTY_DOLLARS, 1);
        model.setBillCount(TEN_DOLLARS, 0);
        model.setBillCount(FIVE_DOLLARS, 3);
        model.setBillCount(TWO_DOLLARS, 4);
        model.setBillCount(ONE_DOLLARS, 0);

        // ask for change
        iaActualChange = model.doChange(11);

        iaExpectedChange[TWENTY_DOLLARS] = 0;
        iaExpectedChange[TEN_DOLLARS] = 0;
        iaExpectedChange[FIVE_DOLLARS] = 1;
        iaExpectedChange[TWO_DOLLARS] = 3;
        iaExpectedChange[ONE_DOLLARS] = 0;

        assertArrayEquals(iaExpectedChange, iaActualChange);

        // deduct bills 1 2 3 4 5
        for (int i = 0; i < NUM_DENOMINATION_TYPE; i++) {
            model.deductBills(i, iaExpectedChange[i]);
        }

        // ask for change
        iaActualChange = model.doChange(14);
        assertNull(iaActualChange);

        // set cash registry: $43 1 0 3 4 0
        model.setBillCount(TWENTY_DOLLARS, 1);
        model.setBillCount(TEN_DOLLARS, 0);
        model.setBillCount(FIVE_DOLLARS, 3);
        model.setBillCount(TWO_DOLLARS, 4);
        model.setBillCount(ONE_DOLLARS, 0);

        // ask for change: whole cash register value
        iaExpectedChange[TWENTY_DOLLARS] = model.iaMoney[BILL_COUNT_INDEX][TWENTY_DOLLARS];
        iaExpectedChange[TEN_DOLLARS] = model.iaMoney[BILL_COUNT_INDEX][TEN_DOLLARS];
        iaExpectedChange[FIVE_DOLLARS] = model.iaMoney[BILL_COUNT_INDEX][FIVE_DOLLARS];
        iaExpectedChange[TWO_DOLLARS] = model.iaMoney[BILL_COUNT_INDEX][TWO_DOLLARS];
        iaExpectedChange[ONE_DOLLARS] = model.iaMoney[BILL_COUNT_INDEX][ONE_DOLLARS];
        iaActualChange = model.doChange(model.getTotalCashValue());

        assertArrayEquals(iaExpectedChange, iaActualChange);

        // deduct bills
        for (int i = 0; i < NUM_DENOMINATION_TYPE; i++) {
            model.deductBills(i, iaExpectedChange[i]);
        }

        // verify that the bills are deducted
        for (int i = 0; i < NUM_DENOMINATION_TYPE; i++) {
            assertEquals(0, model.iaMoney[BILL_COUNT_INDEX][i]);
        }

        // set cash registry: $331 6 11 1 48 0
        model.setBillCount(TWENTY_DOLLARS, 6);
        model.setBillCount(TEN_DOLLARS, 11);
        model.setBillCount(FIVE_DOLLARS, 1);
        model.setBillCount(TWO_DOLLARS, 48);
        model.setBillCount(ONE_DOLLARS, 0);

        // ask for change
        iaActualChange = model.doChange(101);

        iaExpectedChange[TWENTY_DOLLARS] = 0;
        iaExpectedChange[TEN_DOLLARS] = 9;
        iaExpectedChange[FIVE_DOLLARS] = 1;
        iaExpectedChange[TWO_DOLLARS] = 3;
        iaExpectedChange[ONE_DOLLARS] = 0;

        assertArrayEquals(iaExpectedChange, iaActualChange);

        // set cash registry: $221 10 1 1 3 0
        model.setBillCount(TWENTY_DOLLARS, 10);
        model.setBillCount(TEN_DOLLARS, 1);
        model.setBillCount(FIVE_DOLLARS, 1);
        model.setBillCount(TWO_DOLLARS, 3);
        model.setBillCount(ONE_DOLLARS, 0);

        // ask for change
        iaActualChange = model.doChange(11);

        iaExpectedChange[TWENTY_DOLLARS] = 0;
        iaExpectedChange[TEN_DOLLARS] = 0;
        iaExpectedChange[FIVE_DOLLARS] = 1;
        iaExpectedChange[TWO_DOLLARS] = 3;
        iaExpectedChange[ONE_DOLLARS] = 0;

        assertArrayEquals(iaExpectedChange, iaActualChange);

        // set cash registry: $301 6 8 1 48 0
        model.setBillCount(TWENTY_DOLLARS, 6);
        model.setBillCount(TEN_DOLLARS, 8);
        model.setBillCount(FIVE_DOLLARS, 1);
        model.setBillCount(TWO_DOLLARS, 48);
        model.setBillCount(ONE_DOLLARS, 0);

        // ask for change
        iaActualChange = model.doChange(101);

        iaExpectedChange[TWENTY_DOLLARS] = 0;
        iaExpectedChange[TEN_DOLLARS] = 8;
        iaExpectedChange[FIVE_DOLLARS] = 1;
        iaExpectedChange[TWO_DOLLARS] = 8;
        iaExpectedChange[ONE_DOLLARS] = 0;

        assertArrayEquals(iaExpectedChange, iaActualChange);

    }

    @Test
    public void testGetTotalCashValue() {
        CashRegisterModel model = new CashRegisterModel();

        model.setBillCount(TWENTY_DOLLARS, 0);
        model.setBillCount(TEN_DOLLARS, 0);
        model.setBillCount(FIVE_DOLLARS, 0);
        model.setBillCount(TWO_DOLLARS, 0);
        model.setBillCount(ONE_DOLLARS, 0);

        assertEquals(0, model.getTotalCashValue());

        model.setBillCount(TWENTY_DOLLARS, 10);
        model.setBillCount(TEN_DOLLARS, 20);
        model.setBillCount(FIVE_DOLLARS, 30);
        model.setBillCount(TWO_DOLLARS, 40);
        model.setBillCount(ONE_DOLLARS, 50);

        assertEquals(680, model.getTotalCashValue());

    }

}
