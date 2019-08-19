package io.zoran.domain.manifest;

/**
 * @author Michal Sadowski (sadochasee@gmail.com) on 08/12/2018.
 */
public enum ResourceType {
    CLASS("Class"),
    TEMPLATE("Template"),
    DEPENDENCY("Dependency"),
    PROJECT("Project"),
    MAVEN_PROJECT("maven-project"),
    GRADLE_PROJECT("gradle-project"),
    MAVEN_POM("maven-build"),
    GRADLE_CONFIG("gradle-build");

    private String value;

    ResourceType(String value) {
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }
}