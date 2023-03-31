package com.damb.taskmanagment.converter;

import com.damb.taskmanagment.converter.exceptions.IncorrectStateToConvertException;
import com.damb.taskmanagment.domain.Status;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StatusConverterTest {

    private final static Status TEST_STATUS = Status.OPEN;
    private final static String INCORRECT_STATUS_NAME = "DONE";

    private StatusConverter converter;

    public StatusConverterTest() {
        this.converter = new StatusConverter();
    }

    @Test
    void convertStatusToDatabaseColumnTest() {
        String convertedStatusName = converter.convertToDatabaseColumn(TEST_STATUS);
        assertNotNull(convertedStatusName);
        assertEquals(TEST_STATUS.getName(), convertedStatusName);
    }

    @Test
    void convertNullStatusToDatabaseColumnTest() {
        String convertedStatusName = converter.convertToDatabaseColumn(null);
        assertNull(convertedStatusName);
    }

    @Test
    void convertStringToStatusInstance() {
        Status convertedStatus = converter.convertToEntityAttribute(TEST_STATUS.getName());
        assertNotNull(convertedStatus);
        assertEquals(TEST_STATUS, convertedStatus);
    }

    @Test
    void convertNullStringToStatusInstance() {
        Status convertedStatus = converter.convertToEntityAttribute(null);
        assertNull(convertedStatus);
    }

    @Test
    void convertIncorrectStringToStatusInstance() {
        assertThrows(IncorrectStateToConvertException.class, () -> converter.convertToEntityAttribute(INCORRECT_STATUS_NAME));
    }
}