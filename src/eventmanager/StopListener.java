package eventmanager;

/**
 * @author clemens
 *
 * Diese Interface wird von Threads implementiert, die durch eine Message gestoppt werden kšnnen sollen.
 *
 * History
 * 		11.6.2010	-cm		-Inital Version
 */
public interface StopListener extends ListenerInterface {
	public void stopExecution();
}
