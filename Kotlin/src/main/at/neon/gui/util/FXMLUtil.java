package at.neon.gui.util;

import at.neon.gui.main.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.DialogPane;
import javafx.stage.Popup;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.logging.Logger;

public class FXMLUtil {
    private static Logger LOGGER = Logger.getLogger(FXMLUtil.class.getName());
    private static Class<?> mainCSS = Main.class;

    public static void load(Object caller, Class<?> klass) {
        URL fxmlFile = klass.getResource(klass.getSimpleName() + ".fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlFile);
        if (caller != null) {
            fxmlLoader.setController(caller);
        }
        if (caller != null) {
            fxmlLoader.setRoot(caller);
        }

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        if (DialogPane.class.isAssignableFrom(klass)) {
            loadCSS(mainCSS, caller);
        } else if (Popup.class.isAssignableFrom(klass)) {
            for (Node rootNode : ((Popup) caller).getContent()) {
                loadCSS(mainCSS, rootNode);
            }
        }

        loadCSS(klass, caller);
    }

    public static boolean shouldinit(Object caller, Class<?> klass) {
        return caller.getClass() == klass;
    }

    private static void loadCSS(Class<?> klass, Object caller) {
        try {
            Optional.of(klass.getResource(klass.getSimpleName() + ".css")).ifPresent(value -> {
                if (caller instanceof Parent) {
                    ((Parent) caller).getStylesheets().add(value.toExternalForm());
                }
            });
        } catch (NullPointerException e) {
            if (FXMLUtil.class.desiredAssertionStatus()) {
                LOGGER.warning("No CSS defined for " + klass.getName());
            }
        }
    }
}
