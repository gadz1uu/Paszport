package com.example.paszport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

/**
 * Testy klasy PassportValidator. Działają na komputerze, bez telefonu.
 */
public class PassportValidatorTest {

    private final PassportValidator validator = new PassportValidator();

    @Test
    public void dataIsComplete_whenNameAndSurnameAreGiven() {
        assertTrue(validator.isDataComplete("Jan", "Nowak"));
    }

    @Test
    public void dataIsNotComplete_whenNameIsEmpty() {
        assertFalse(validator.isDataComplete("", "Nowak"));
    }

    @Test
    public void dataIsNotComplete_whenSurnameIsEmpty() {
        assertFalse(validator.isDataComplete("Jan", ""));
    }

    @Test
    public void dataIsNotComplete_whenOnlySpacesAreGiven() {
        assertFalse(validator.isDataComplete("   ", "Nowak"));
    }

    @Test
    public void dataIsNotComplete_whenNameIsNull() {
        assertFalse(validator.isDataComplete(null, "Nowak"));
    }

    @Test
    public void messageHasCorrectFormat() {
        String message = validator.buildMessage("Jan", "Nowak", "piwne");
        assertEquals("Jan Nowak kolor oczu piwne", message);
    }

    @Test
    public void photoFileNameIsBuiltFromNumber() {
        assertEquals("333-zdjecie.jpg", validator.getPhotoFileName("333"));
    }

    @Test
    public void fingerprintFileNameIsBuiltFromNumber() {
        assertEquals("333-odcisk.jpg", validator.getFingerprintFileName("333"));
    }
}
