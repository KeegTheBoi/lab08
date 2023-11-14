package it.unibo.deathnote;

import org.junit.jupiter.api.Test;

import it.unibo.deathnote.Impl.DeathNoteImpl;
import it.unibo.deathnote.api.DeathNote;

import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;


class TestDeathNote {
    private DeathNote note;
    private final static String EMPTY_STRING = "";
    private final String humanName = "Mary Jane";
    private final String anotherHuman = "Jamal";
     /**
    * Configuration step: this is performed BEFORE each test.
    */
   @BeforeEach
   public void setUp() {
        note = new DeathNoteImpl();
   }

    /**
     * check that the exceptions are thrown correctly, that their type is the expected one, and that the message is not null, empty, or blank.
     */
    @Test
    public void testExceptionAndMessages() {
        try {
            note.getRule(0);
            note.getRule(-4);
            fail();
        } catch (IllegalArgumentException e) {
            //check that the exceptions are thrown correctly, that their type is the expected one, and that the message is not null, empty, or blank.
            assertEquals("arguments out of bounds", e.getMessage());
            assertNotNull(e.getMessage());
            assertFalse(e.getMessage().isEmpty());
            assertFalse(e.getMessage().isBlank());
        }
    }

    
    /**
     *  No rule is empty or null in the DeathNote rules
     */
    @Test
    public void testRules(){
        //for all the valid rules, check that none is null or blank    
        for (int i = 2; i <= DeathNote.RULES.size(); i++) {
            String resString = note.getRule(i);
            assertNotNull(resString);
            assertFalse(resString.isEmpty());
            assertFalse(resString.isBlank());
        } 
       
    }

    /**
     *  The human whose name is written in the DeathNote will eventually die.
     */
    @Test
    public void testWiteDeathNote(){
        //verify that the human has not been written in the notebook yet
        assertFalse(note.isNameWritten(humanName));
        //write the human in the notebook
        note.writeName(humanName);
        //verify that the human has been written in the notebook
        assertTrue(note.isNameWritten(humanName));
        //verify that another human has not been written in the notebook
        assertFalse(note.isNameWritten(anotherHuman));
        //verify that the empty string has not been written in the notebook
        assertFalse(note.isNameWritten(EMPTY_STRING));

    }

    /**
     *  Only if the cause of death is written within the next 40 milliseconds of writing the person's name, it will happen.
     * @throws InterruptedException
     */
    @Test
    public void testCauseOfDeath() throws InterruptedException{
        //check that writing a cause of death before writing a name throws the correct exception
        try {
            note.writeDeathCause("any death cause");
            fail();
        } catch (IllegalStateException e) {
            assertEquals("should write name first", e.getMessage());
        }
        finally{
            //write the name of a human in the notebook
            note.writeName(humanName);
            //verify that the cause of death is a heart attack
            note.writeDeathCause("hearth attack");
            //write the name of another human in the notebook
            note.writeName(anotherHuman);
            //set the cause of death to "karting accident"
            note.writeDeathCause("karting accident");
            //verify that the cause of death has been set correctly 
            assertTrue(note.writeDeathCause("karting accident"));
            assertEquals("karting accident", note.getDeathCause(anotherHuman));
            Thread.sleep(100);
            note.writeDeathCause("different cause");
            assertEquals("karting accident", note.getDeathCause(anotherHuman));

        }
    }

    /**
     *  Only if the cause of death is written within the next 6 seconds and 40 milliseconds of writing the death's details, it will happen.
     * @throws InterruptedException
     */
    @Test
    public void testDetails() throws InterruptedException{
       
        try {
             //check that writing the death details before writing a name throws the correct exception
            note.writeDetails("any death detail");
            fail();
        } catch (IllegalStateException e) {
            assertEquals("should write name first", e.getMessage());
        }
        finally{
               //write the name of a human in the notebook
               note.writeName(humanName);
               //verify that the details of the death are currently empty
               assertTrue(note.getDeathDetails(humanName).isEmpty());
               //set the details of the death to "ran for too long"
               boolean res = note.writeDetails("ran for too long");
               //verify that death details have been set correctly (returned true, and the details are indeed "ran for too long")
               assertTrue(res);
               assertEquals("ran for too long", note.getDeathDetails(humanName));
               // write the name of another human in the notebook
               note.writeName(anotherHuman);
               //sleep for 6100ms
               Thread.sleep(6100);
               //try to change the details
               //verify that the details have not been changed
               note.writeDetails("other details");
               assertEquals("", note.getDeathDetails(anotherHuman));

        }

    }



}