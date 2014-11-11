package ru.meridor.steve.impl;

import org.junit.Before;
import org.junit.Test;
import ru.meridor.steve.Launcher;
import ru.meridor.steve.Launchers;
import ru.meridor.steve.SteveException;

public class LauncherImplTest {

    private Launcher launcher;

    @Before
    @SuppressWarnings("unchecked")
    public void beforeTest() throws SteveException {
        launcher = Launchers.newLauncher(Mock.class);
    }

    @Test
    public void testLaunchJob() throws Exception {
        launcher.launch("test-job", "some-string", Integer.class);
    }

}
