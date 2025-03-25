package seedu.address.commons.core;

import java.awt.Point;
import java.io.Serializable;
import java.util.Objects;

import seedu.address.commons.util.ToStringBuilder;
import seedu.address.model.NavigationMode;

/**
 * A Serializable class that contains the GUI settings. Guarantees: immutable.
 */
public class GuiSettings implements Serializable {

    private static final double DEFAULT_HEIGHT = 600;
    private static final double DEFAULT_WIDTH = 740;
    private static final NavigationMode DEFAULT_NAVIGATION_MODE = NavigationMode.STUDENT;

    private final double windowWidth;
    private final double windowHeight;
    private final Point windowCoordinates;
    private final NavigationMode navigationMode;

    /**
     * Constructs a {@code GuiSettings} with the default height, width, position,
     * and navigation mode.
     */
    public GuiSettings() {
        windowWidth = DEFAULT_WIDTH;
        windowHeight = DEFAULT_HEIGHT;
        windowCoordinates = null; // null represent no coordinates
        navigationMode = DEFAULT_NAVIGATION_MODE;
    }

    /**
     * Constructs a {@code GuiSettings} with the specified height, width, position,
     * and navigation mode.
     */
    public GuiSettings(double windowWidth, double windowHeight, int xPosition, int yPosition,
                    NavigationMode navigationMode) {
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        windowCoordinates = new Point(xPosition, yPosition);
        this.navigationMode = navigationMode;
    }

    public double getWindowWidth() {
        return windowWidth;
    }

    public double getWindowHeight() {
        return windowHeight;
    }

    public Point getWindowCoordinates() {
        return windowCoordinates != null ? new Point(windowCoordinates) : null;
    }

    public NavigationMode getNavigationMode() {
        return navigationMode;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof GuiSettings)) {
            return false;
        }

        GuiSettings otherGuiSettings = (GuiSettings) other;
        return windowWidth == otherGuiSettings.windowWidth && windowHeight == otherGuiSettings.windowHeight
                        && Objects.equals(windowCoordinates, otherGuiSettings.windowCoordinates)
                        && navigationMode == otherGuiSettings.navigationMode;
    }

    @Override
    public int hashCode() {
        return Objects.hash(windowWidth, windowHeight, windowCoordinates, navigationMode);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this).add("windowWidth", windowWidth).add("windowHeight", windowHeight)
                        .add("windowCoordinates", windowCoordinates).add("navigationMode", navigationMode).toString();
    }
}
