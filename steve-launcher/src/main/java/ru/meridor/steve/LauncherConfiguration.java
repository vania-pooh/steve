package ru.meridor.steve;

import com.google.inject.Module;

public interface LauncherConfiguration {

    Module[] getConfiguration();

}
