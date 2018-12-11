package io.zoran.api.domain;

/**
 * Represents a single dependency.
 */
abstract public class ResourceDependencyMetadata {
    protected final String parentIdentifier;

    protected ResourceDependencyMetadata(String parentIdentifier) {
        this.parentIdentifier = parentIdentifier;
    }

    abstract String getId();
    abstract String getName();
    abstract String getVersion();
    abstract String getDescription();
}
