package com.damb.taskmanagment.converter;

import com.damb.taskmanagment.converter.exceptions.IncorrectStateToConvertException;
import com.damb.taskmanagment.domain.Status;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.Arrays;

@Converter(autoApply = true)
public class StatusConverter implements AttributeConverter<Status, String> {
    @Override
    public String convertToDatabaseColumn(Status status) {
        if (status == null) return null;
        return status.getName();
    }

    @Override
    public Status convertToEntityAttribute(String statusName) {
        if (statusName == null) return null;
        return Arrays.stream(Status.values())
                .filter(status -> status.getName().equals(statusName))
                .findAny()
                .orElseThrow(() -> new IncorrectStateToConvertException(String.format("status name %s does not exist", statusName)));
    }
}
