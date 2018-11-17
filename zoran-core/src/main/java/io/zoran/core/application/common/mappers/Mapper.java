package io.zoran.core.application.common.mappers;

/**
 * @author Michal Sadowski (michal.sadowski@roche.com) on 17.11.2018
 */
interface Mapper<T, retT> {
    retT map(T t);
}
