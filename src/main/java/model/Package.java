package model;

import org.immutables.value.Value;

import java.util.List;

@Value.Immutable
public interface Package {

    String packageName();

    List<ClassName> classes();

}
