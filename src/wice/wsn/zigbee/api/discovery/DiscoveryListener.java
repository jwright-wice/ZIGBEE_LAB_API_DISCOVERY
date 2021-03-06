package wice.wsn.zigbee.api.discovery;

import com.digi.xbee.api.RemoteXBeeDevice;
import com.digi.xbee.api.listeners.IDiscoveryListener;

/**
 * Class to manage the XBee network discovery events.
 *
 * <p>
 * Acts as a discovery listener by implementing the {@code IDiscoveryListener}
 * interface, and is notified when new devices are found, an error occurs or the
 * discovery process finishes.
 * </p>
 *
 * @see IDiscoveryListener
 *
 */
public class DiscoveryListener implements IDiscoveryListener {
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.digi.xbee.api.listeners.IDiscoveryListener#deviceDiscovered(com.digi.
	 * xbee.api.RemoteXBeeDevice)
	 */

	private DiscoveryWorker discovery;

	public DiscoveryListener(DiscoveryWorker discovery) {
		this.discovery = discovery;
	}

	@Override
	public void deviceDiscovered(RemoteXBeeDevice discoveredDevice) {
		discovery.publishData(">> Device discovered: " + discoveredDevice.get64BitAddress());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.digi.xbee.api.listeners.IDiscoveryListener#discoveryError(java.lang.
	 * String)
	 */
	@Override
	public void discoveryError(String error) {
		discovery.publishData(">> There was an error discovering devices: " + error);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.digi.xbee.api.listeners.IDiscoveryListener#discoveryFinished(java.
	 * lang.String)
	 */
	@Override
	public void discoveryFinished(String error) {
		if (error == null) {
			discovery.publishData(">> Discovery process finished successfully.");
		} else {
			discovery.publishData(">> Discovery process finished due to the following error: " + error);
		}
		discovery.cancel(true);
	}
}
