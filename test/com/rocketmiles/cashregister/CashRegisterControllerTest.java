package com.rocketmiles.cashregister;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class CashRegisterControllerTest extends Constants{

    CashRegisterController cashregister;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
        //cashregister = CashRegisterController.class.newInstance();
    }

    @After
    public void tearDown() throws Exception {
        cashregister = null;
    }

    @Test
    public void testPut() {

        String[] input1 = {"put", "1", "2", "3", "4", "5"};
        String[] input2 = {"put", "1", "2", "3", "4"}; // invalid number of parameters
        String[] input3 = {"put", "-1", "2", "3", "4", "5"};
        boolean zFlag = false;

        // Create a stream to hold the output
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream psNew = new PrintStream(baos);

        PrintStream psOld = System.out;
        // Tell Java to use your special stream
        System.setOut(psNew);

        try {
            //Class<?> privateClass = cashregister.getClass();
            Class<?> privateClass = Class.forName(CashRegisterController.class.getName());
            Constructor<?> privateConstructor = privateClass.getConstructor(String.class);
            Object object = privateConstructor.newInstance(new Object[] {null});

            cashregister = (CashRegisterController) object;

            Method privateMethodAccessor;
            
            privateMethodAccessor = privateClass.getDeclaredMethod("processAddCash", Array.newInstance(String.class, input1.length).getClass());
            privateMethodAccessor.setAccessible(true);

            zFlag = (boolean) privateMethodAccessor.invoke(cashregister, (Object) input3);
            assertEquals(false, zFlag);
            /* Verify console display */
            assertEquals(MESSAGE_ERROR_INVALID_PARAMETER, (baos.toString()).replaceAll("(\\r\\n|\\n|\\r)", ""));

            // Reset
            baos.reset();
            System.out.flush();

            zFlag = (boolean) privateMethodAccessor.invoke(cashregister, (Object) input2);
            assertEquals(false, zFlag);
            /* Verify console display */
            assertEquals(MESSAGE_ERROR_INVALID_PARAMETER, (baos.toString()).replaceAll("(\\r\\n|\\n|\\r)", ""));
            // Reset
            baos.reset();
            System.out.flush();

            zFlag = (boolean) privateMethodAccessor.invoke(cashregister, (Object) input1);
            assertEquals(true, zFlag);

            // Reset
            baos.reset();
            System.out.flush();
            System.setOut(psOld);

        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | ClassNotFoundException | InstantiationException e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testTake() {

        String[] input1 = {"take", "1", "2", "3", "4", "5"};
        String[] input2 = {"take", "1", "2", "3", "4"}; // invalid number of parameters
        String[] input3 = {"put", "1", "2", "3", "4", "5"};
        boolean zFlag = false;

        // Create a stream to hold the output
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream psNew = new PrintStream(baos);

        PrintStream psOld = System.out;
        // Tell Java to use your special stream
        System.setOut(psNew);

        try {
            //Class<?> privateClass = cashregister.getClass();
            Class<?> privateClass = Class.forName(CashRegisterController.class.getName());
            Constructor<?> privateConstructor = privateClass.getConstructor(String.class);
            Object object = privateConstructor.newInstance(new Object[] {null});

            cashregister = (CashRegisterController) object;

            Method privateMethodAccessor;

            privateMethodAccessor = privateClass.getDeclaredMethod("processDeductCash", Array.newInstance(String.class, input1.length).getClass());
            privateMethodAccessor.setAccessible(true);

            /* Test to take insufficient funds */
            zFlag = (boolean) privateMethodAccessor.invoke(cashregister, (Object) input1);
            assertEquals(false, zFlag);
            /* Verify console display */
            assertEquals(MESSAGE_ERROR_INSUFFICIENT_FUNDS, (baos.toString()).replaceAll("(\\r\\n|\\n|\\r)", ""));

            // Reset
            baos.reset();
            System.out.flush();

            /* Invalid character in input, same number of parameters */
            input1[1] = "-3";

            zFlag = (boolean) privateMethodAccessor.invoke(cashregister, (Object) input1);
            assertEquals(false, zFlag);
            /* Verify console display */
            assertEquals(MESSAGE_ERROR_INVALID_PARAMETER, (baos.toString()).replaceAll("(\\r\\n|\\n|\\r)", ""));
            // Reset
            input1[1] = "1";
            baos.reset();
            System.out.flush();

            /* Invalid number of parameters */
            zFlag = (boolean) privateMethodAccessor.invoke(cashregister, (Object) input2);
            assertEquals(false, zFlag);
            /* Verify console display */
            assertEquals(MESSAGE_ERROR_INVALID_PARAMETER, (baos.toString()).replaceAll("(\\r\\n|\\n|\\r)", ""));
            // Reset
            baos.reset();
            System.out.flush();

            /* Add cash */
            privateMethodAccessor = privateClass.getDeclaredMethod("processAddCash", Array.newInstance(String.class, input3.length).getClass());
            privateMethodAccessor.setAccessible(true);

            zFlag = (boolean) privateMethodAccessor.invoke(cashregister, (Object) input3);
            assertEquals(true, zFlag);
            // Reset
            baos.reset();
            System.out.flush();

            /* Take cash */
            privateMethodAccessor = privateClass.getDeclaredMethod("processDeductCash", Array.newInstance(String.class, input1.length).getClass());
            privateMethodAccessor.setAccessible(true);
            zFlag = (boolean) privateMethodAccessor.invoke(cashregister, (Object) input1);
            assertEquals(true, zFlag);

            // Reset
            baos.reset();
            System.out.flush();
            System.setOut(psOld);

        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | ClassNotFoundException | InstantiationException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testChange() {
        String[] input1 = {"change", "11"};
        String[] input2 = {"change", "14"};
        String[] input3 = {"put", "1", "0", "3", "4", "0"}; // set cash registry: $43 1 0 3 4 0
        String[] input4 = {"change", "7", "8", "9"}; // invalid number of parameters
        String[] input5 = {"change", "-7"};

        boolean zFlag = false;

        // Create a stream to hold the output
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PrintStream psNew = new PrintStream(baos);

        PrintStream psOld = System.out;
        // Tell Java to use your special stream
        System.setOut(psNew);

        try {
            //Class<?> privateClass = cashregister.getClass();
            Class<?> privateClass = Class.forName(CashRegisterController.class.getName());
            Constructor<?> privateConstructor = privateClass.getConstructor(String.class);
            Object object = privateConstructor.newInstance(new Object[] {null});

            cashregister = (CashRegisterController) object;

            Method privateMethodAccessor;

            privateMethodAccessor = privateClass.getDeclaredMethod("processChange", Array.newInstance(String.class, input1.length).getClass());
            privateMethodAccessor.setAccessible(true);

            /* Test for invalid parameters */
            zFlag = (boolean) privateMethodAccessor.invoke(cashregister, (Object) input4);
            assertEquals(false, zFlag);
            /* Verify console display */
            assertEquals(MESSAGE_ERROR_INVALID_PARAMETER, (baos.toString()).replaceAll("(\\r\\n|\\n|\\r)", ""));
            // Reset
            baos.reset();
            System.out.flush();

            zFlag = (boolean) privateMethodAccessor.invoke(cashregister, (Object) input5);
            assertEquals(false, zFlag);
            /* Verify console display */
            assertEquals(MESSAGE_ERROR_INVALID_PARAMETER, (baos.toString()).replaceAll("(\\r\\n|\\n|\\r)", ""));
            // Reset
            baos.reset();
            System.out.flush();

            /* Add cash */
            privateMethodAccessor = privateClass.getDeclaredMethod("processAddCash", Array.newInstance(String.class, input3.length).getClass());
            privateMethodAccessor.setAccessible(true);

            zFlag = (boolean) privateMethodAccessor.invoke(cashregister, (Object) input3);
            assertEquals(true, zFlag);
            // Reset
            baos.reset();
            System.out.flush();

            /* Test for change - success */
            privateMethodAccessor = privateClass.getDeclaredMethod("processChange", Array.newInstance(String.class, input1.length).getClass());
            privateMethodAccessor.setAccessible(true);
            zFlag = (boolean) privateMethodAccessor.invoke(cashregister, (Object) input1);
            assertEquals(true, zFlag);
            /* Verify console display */
            assertEquals("0 0 1 3 0", (baos.toString()).replaceAll("(\\r\\n|\\n|\\r)", ""));
            // Reset
            baos.reset();
            System.out.flush();

            /* Test for change - No change can be issued */
            zFlag = (boolean) privateMethodAccessor.invoke(cashregister, (Object) input2);
            assertEquals(false, zFlag);
            /* Verify console display */
            assertEquals(MESSAGE_ERROR_NO_CHANGE_AVAILABLE, (baos.toString()).replaceAll("(\\r\\n|\\n|\\r)", ""));

            // Reset
            baos.reset();
            System.out.flush();
            System.setOut(psOld);

        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | ClassNotFoundException | InstantiationException e) {
            e.printStackTrace();
        }

    }


}
