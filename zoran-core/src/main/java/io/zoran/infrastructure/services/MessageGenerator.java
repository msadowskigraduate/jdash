package io.zoran.infrastructure.services;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 14/12/2019.
 */
public class MessageGenerator {
    public static String processMessage(String message) {
        return new StringBuilder()
                .append("[")
                .append(DateTimeUtils.nowInIso())
                .append("] ")
                .append(message)
                .toString();
    }
}
