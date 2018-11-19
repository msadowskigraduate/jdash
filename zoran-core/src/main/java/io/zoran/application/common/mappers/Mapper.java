package io.zoran.application.common.mappers;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 17.11.2018
 */
public interface Mapper<T, retT> {
    retT map(T t);
}
