package io.zoran.core.domain;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 16.11.2018
 */
public interface Extension {
    Extension getInstance();
    boolean isDefined();
    boolean isActive();
    String getOwnerId();
}
