package javamailapi;

import java.util.List;

//Model class which contains email details like hostname, portnumber etc.
public class EmailDetails {
	
	private String hostName;
	
	private String portNumber;
	
	private String subject;
	
	private String fromAddress;
	
	private List<String> toAddresses;
	
	public EmailDetails(String hostName, String portNumber, String subject, String fromAddress, List<String> toAddresses) {
		super();
		this.hostName = hostName;
		this.portNumber = portNumber;
		this.subject = subject;
		this.fromAddress = fromAddress;
		this.toAddresses = toAddresses;
	}

	/**
	 * @return the hostName
	 */
	public String getHostName() {
		return hostName;
	}

	/**
	 * @return the port
	 */
	public String getPortNumber() {
		return portNumber;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @return the fromAddress
	 */
	public String getFromAddress() {
		return fromAddress;
	}

	/**
	 * @return the toAddresses
	 */
	public List<String> getToAddresses() {
		return toAddresses;
	}

}
