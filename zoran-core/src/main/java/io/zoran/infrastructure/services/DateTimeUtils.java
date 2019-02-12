package io.zoran.infrastructure.services;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 12.02.2019
 */
@UtilityClass
public class DateTimeUtils {
    public static String iso(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ISO_DATE_TIME);
    }
}
