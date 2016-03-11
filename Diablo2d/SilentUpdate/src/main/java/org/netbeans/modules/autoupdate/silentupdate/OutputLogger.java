package org.netbeans.modules.autoupdate.silentupdate;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.openide.util.NbBundle;
import org.openide.windows.IOProvider;
import org.openide.windows.InputOutput;

/**
 *
 */
public final class OutputLogger {

    // to log all messages for whole package
    private static final Logger LOGGER = Logger.getLogger(OutputLogger.class.getPackage().getName());
    private static Grain grain;
    private static final String OUTPUT_LOGGER_NAME = "Updating Application"; // NOI18N
    private static InputOutput io;

    private enum Grain {

        VERBOSE,
        WINDOW,
        LOG,
        SILENT
    }
    private static final Grain DEFAULT = Grain.LOG;

    private static Grain grain() {
        if (grain == null) {
            String s = NbBundle.getBundle("org.netbeans.modules.autoupdate.silentupdate.resources.Bundle").getString("OutputLogger.Grain");
            grain = DEFAULT;
            try {
                grain = Grain.valueOf(s);
            } catch (IllegalArgumentException x) {
                LOGGER.info(s);
            }
        }

        return grain;
    }

    public static void log(String msg, Throwable... x) {
        log(Level.INFO, msg, x);
    }

    @SuppressWarnings("fallthrough")
    public static void log(Level l, String msg, Throwable... x) {
        switch (grain()) {
            case VERBOSE:
                if (x == null) {
                    LOGGER.log(Level.INFO, msg);
                } else {
                    LOGGER.log(Level.INFO, msg);
                    for (Throwable t : x) {
                        LOGGER.log(Level.INFO, msg, t);
                    }
                }
                break;
            case WINDOW:
                try {
                    InputOutput output = getIO();
                    if (x == null) {
                        output.getOut().println(msg);
                        output.getOut().close();
                    } else {
                        output.getErr().println(msg);
                        for (Throwable t : x) {
                            output.getErr().println(t);
                        }
                        output.getErr().close();
                    }
                } catch (Exception ex) {
                    LOGGER.log(Level.INFO, ex.getLocalizedMessage(), ex);
                }
            // use LOGGER for these message too
            case LOG:
                if (LOGGER.isLoggable(l)) {
                    if (x == null) {
                        LOGGER.log(l, msg);
                    } else {
                        LOGGER.log(l, msg);
                        for (Throwable t : x) {
                            LOGGER.log(l, msg, t);
                        }
                    }
                }
                break;
            case SILENT:
                if (x == null) {
                    LOGGER.log(Level.FINEST, msg);
                } else {
                    LOGGER.log(Level.FINEST, msg);
                    for (Throwable t : x) {
                        LOGGER.log(Level.FINEST, msg, t);
                    }
                }
                break;
            default:
                assert false : "Unkown grain " + grain();
        }
    }

    private static synchronized InputOutput getIO() {
        if (io == null) {
            io = IOProvider.getDefault().getIO(OUTPUT_LOGGER_NAME, true);
        }
        return io;
    }

}
