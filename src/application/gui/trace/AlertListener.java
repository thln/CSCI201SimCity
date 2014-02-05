package application.gui.trace;

/**
 * An interface for being notified when an {@link Alert} occurs.
 * @author Keith DeRuiter
 * @modified Tam Henry Le Nguyen
 *
 */
public interface AlertListener {
        /**
         * Called when an alert occurs.
         * @param alert The alert that happened.
         */
        public void alertOccurred(Alert alert);
}
