package io.zoran.infrastructure.services;

import lombok.experimental.UtilityClass;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 12.02.2019
 */
@UtilityClass
public class DateTimeUtils {

    //The contract here is that when LastRun is null, then it means that no runs have been scheduled.
    public static String iso(LocalDateTime dateTime) {
        if(dateTime == null) {
            return null;
        }
        return dateTime.format(DateTimeFormatter.ISO_DATE_TIME);
    }

    public static String nowInIso() {
        return iso(LocalDateTime.now());
    }
}
