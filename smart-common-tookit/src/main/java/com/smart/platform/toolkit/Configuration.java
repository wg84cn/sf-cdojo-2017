package com.smart.platform.toolkit;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {

	private final static Properties properties = new Properties();

	private static Configuration configuration = new Configuration();

	private Configuration() {
		configInit();
	}

	public static Configuration getInstance() {
		if (configuration == null) {
			configuration = new Configuration();
		}
		return configuration;
	}

	private void configInit() {
		InputStream configStream = null;
		try {
			configStream = Configuration.class.getResourceAsStream("/config.properties");
			properties.load(configStream);
		} catch (IOException e) {
			throw new RuntimeException("System config initial error:"
					+ e.toString());
		} finally {
			if (configStream == null) {
				return;
			}
			try {
				configStream.close();
			} catch (IOException e) {
				throw new RuntimeException("System config IO close error:"
						+ e.toString());
			}
		}
	}

	public String getConfigItemValue(String itemName) {
		return properties.getProperty(itemName);
	}

	public Integer getConfigItemInt(String itemName) {
		return Integer.parseInt(properties.getProperty(itemName));
	}
}
