package org.meridor.steve;

import java.util.List;

public interface ClassesProvider {

    List<Class> provide() throws SteveException;

}
