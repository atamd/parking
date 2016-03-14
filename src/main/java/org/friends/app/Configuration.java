package org.friends.app;

import spark.utils.StringUtils;

public class Configuration {

	public final static String PORT = "PORT";
	public final static String DEPLOY_MODE = "PARKING_MODE";
	public final static String MAIL_ENABLE = "MAIL_ENABLE";
	

	public static String getMailServiceLogin() {
		return System.getenv("SENDGRID_USERNAME");
	}

	public static String getMailServicePassword() {
		return System.getenv("SENDGRID_PASSWORD");
	}

	public static Integer getPort() {
		String port = System.getenv(PORT);
		if (port == null)
			port = System.getProperty(PORT);
		if (port == null)
			throw new RuntimeException("Port not defined");
		return Integer.valueOf(port);
	}

	public static boolean development() {
		return System.getProperty(DEPLOY_MODE) != null;
	}
	
	/**
	 * Get configuration, first in environment, then in property.
	 * @param propertyName
	 */
	public static String get(String propertyName, String defaultValue) {
		String value = System.getenv(propertyName);
		if (!StringUtils.isEmpty(value))
			return value;
		return System.getProperty(propertyName, defaultValue);
	}
}
