package com.sf.iguess.survey.config;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import com.smart.platform.toolkit.PropertyPlaceholderConfigurer;

@Configuration
public class ApplicationConfigurer {

	private static final Logger logger = LoggerFactory.getLogger(ApplicationConfigurer.class);

	public static final String SPRING_CONFIG_LOCATION = "spring.config.location";

	private ApplicationConfigurer(){}
	
	/**
	 * 自定义配置加载，方法定义为static的，保证优先加载
	 * @return
	 */
	@Bean
	public static PropertyPlaceholderConfigurer properties() {
		final PropertyPlaceholderConfigurer ppc = new PropertyPlaceholderConfigurer();
		ppc.setIgnoreResourceNotFound(true);
		final List<Resource> resourceList = new ArrayList<>();
		if (System.getProperty(SPRING_CONFIG_LOCATION) != null) {
			String configFilePath = System.getProperty(SPRING_CONFIG_LOCATION);
			String[] configFiles = configFilePath.split(",|;");

			for (String configFile : configFiles) {
				if (configFile.startsWith("file:")) {
					resourceList.add(new FileSystemResource(configFile));
				} else {
					resourceList.add(new ClassPathResource(configFile));
				}
			}
		} else {
			resourceList.add(new ClassPathResource("application.properties"));
			resourceList.add(new ClassPathResource("log4j.properties"));
		}
		ppc.setLocations(resourceList.toArray(new Resource[] {}));
		logger.info("start initial scanner configuration......");
		return ppc;
	}
}