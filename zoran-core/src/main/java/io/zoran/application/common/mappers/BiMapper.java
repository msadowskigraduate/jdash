package io.zoran.application.common.mappers;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 15.02.2019
 */
@FunctionalInterface
public interface BiMapper<T1, T2, retT> {
    retT map(T1 o, T2 m);
}
