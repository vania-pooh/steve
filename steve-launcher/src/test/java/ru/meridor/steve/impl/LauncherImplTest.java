package ru.meridor.steve.impl;

import org.junit.Before;
import org.junit.Test;
import ru.meridor.steve.Launcher;
import ru.meridor.steve.SteveException;

public class LauncherImplTest {

    private Launcher launcher;

    @Before
    public void beforeTest() throws SteveException {
        throw new UnsupportedOperationException();
    }

    @Test
    public void testLaunchJob() throws Exception {
        launcher.launch("test-job", "some-string", Integer.class);
    }

}
