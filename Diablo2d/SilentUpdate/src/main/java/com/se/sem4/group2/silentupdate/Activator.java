package com.se.sem4.group2.silentupdate;

/**
 *
 */
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import org.openide.modules.ModuleInstall;

/**
 * Manages a module's lifecycle. Remember that an installer is optional and
 * often not needed at all.
 */
public class Activator extends ModuleInstall {

    private final ScheduledExecutorService exector = Executors.newScheduledThreadPool(1);

    @Override
    public void restored() {
        exector.scheduleAtFixedRate(doCheck, 5000, 5000, TimeUnit.MILLISECONDS);
        System.out.println("Running check!");
    }

    private static final Runnable doCheck = new Runnable() {
        @Override
        public void run() {
            if (UpdateHandler.timeToCheck()) {
                UpdateHandler.checkAndHandleUpdates();
            }
        }

    };

}