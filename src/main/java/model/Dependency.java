package model;

import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
public interface Dependency {

    String groupId();

    String artifactId();

    String version();

    List<Package> packages();

}
