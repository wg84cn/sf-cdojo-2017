/**
 * Project Name:iatp-basic-common
 * File Name:PropertyPlaceholderConfigurer.java
 * Package Name:com.smart.platform.toolkit
 * Date:2017年2月27日下午8:21:32
 * Copyright (c) 2017, xutao9@sf-express.com All Rights Reserved.
 *
*/

package com.smart.platform.toolkit;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.Set;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PlaceholderConfigurerSupport;
import org.springframework.core.Constants;
import org.springframework.util.PropertyPlaceholderHelper;
import org.springframework.util.PropertyPlaceholderHelper.PlaceholderResolver;
import org.springframework.util.StringValueResolver;

import com.smart.platform.encrypt.SystemConfigEncrypt;

/**
 * ClassName:PropertyPlaceholderConfigurer <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason:	 TODO ADD REASON. <br/>
 * Date:     2017年2月27日 下午8:21:32 <br/>
 * @author   01135912
 * @version  
 * @since    JDK 1.6
 * @see 	 
 */
/**
 * ClassName: PropertyPlaceholderConfigurer <br/>
 * Function: TODO ADD FUNCTION. <br/>
 * Reason: TODO ADD REASON(可选). <br/>
 * date: 2017年2月27日 下午8:21:32 <br/>
 *
 * @author 01135912
 * @version
 * @since JDK 1.7
 */
public class PropertyPlaceholderConfigurer extends PlaceholderConfigurerSupport
{
    
    private static final String ENCRYPT_KEY_START = "enc:";
    
    private static final String SYS_KEY_COMPONENT = "system.key.component";
    
    private static Map<Object, Object> SYSTEM_CONFIG_MAP = null;
    
    /** Never check system properties. */
    public static final int SYSTEM_PROPERTIES_MODE_NEVER = 0;
    
    /**
     * Check system properties if not resolvable in the specified properties. This is the default.
     */
    public static final int SYSTEM_PROPERTIES_MODE_FALLBACK = 1;
    
    /**
     * Check system properties first, before trying the specified properties. This allows system properties to override
     * any other property source.
     */
    public static final int SYSTEM_PROPERTIES_MODE_OVERRIDE = 2;
    
    private static final Constants constants = new Constants(PropertyPlaceholderConfigurer.class);
    
    private int systemPropertiesMode = SYSTEM_PROPERTIES_MODE_FALLBACK;
    
    private boolean searchSystemEnvironment = true;
    
    public void setSystemPropertiesModeName(String constantName)
        throws IllegalArgumentException
    {
        this.systemPropertiesMode = constants.asNumber(constantName).intValue();
    }
    
    public void setSystemPropertiesMode(int systemPropertiesMode)
    {
        this.systemPropertiesMode = systemPropertiesMode;
    }
    
    public void setSearchSystemEnvironment(boolean searchSystemEnvironment)
    {
        this.searchSystemEnvironment = searchSystemEnvironment;
    }
    
    protected String resolvePlaceholder(String placeholder, Properties props, int systemPropertiesMode)
    {
        String propVal = null;
        if (systemPropertiesMode == SYSTEM_PROPERTIES_MODE_OVERRIDE)
        {
            propVal = resolveSystemProperty(placeholder);
        }
        if (propVal == null)
        {
            propVal = resolvePlaceholder(placeholder, props);
        }
        if (propVal == null && systemPropertiesMode == SYSTEM_PROPERTIES_MODE_FALLBACK)
        {
            propVal = resolveSystemProperty(placeholder);
        }
        return propVal;
    }
    
    protected String resolvePlaceholder(String placeholder, Properties props)
    {
        return props.getProperty(placeholder);
    }
    
    protected String resolveSystemProperty(String key)
    {
        try
        {
            String value = System.getProperty(key);
            if (value == null && this.searchSystemEnvironment)
            {
                value = System.getenv(key);
            }
            return value;
        }
        catch (Throwable ex)
        {
            if (logger.isDebugEnabled())
            {
                logger.debug("Could not access system property '" + key + "': " + ex);
            }
            return null;
        }
    }
    
    @Override
    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess, Properties props)
        throws BeansException
    {
        Set<Entry<Object, Object>> entrySet = props.entrySet();
        for (Entry<Object, Object> entry : entrySet)
        {
            String propValue = (String)entry.getValue();
            if (propValue.startsWith(ENCRYPT_KEY_START))
            {
                String fileComponent = (String)props.get(SYS_KEY_COMPONENT);
                String decodeValue =
                    SystemConfigEncrypt.decryptConfig(fileComponent, propValue.substring(ENCRYPT_KEY_START.length()));
                props.put(entry.getKey(), decodeValue);
            }
        }
        SYSTEM_CONFIG_MAP = props;
        StringValueResolver valueResolver = new PlaceholderResolvingStringValueResolver(props);
        this.doProcessProperties(beanFactoryToProcess, valueResolver);
    }
    
    private class PlaceholderResolvingStringValueResolver implements StringValueResolver
    {
        
        private final PropertyPlaceholderHelper helper;
        
        private final PlaceholderResolver resolver;
        
        public PlaceholderResolvingStringValueResolver(Properties props)
        {
            this.helper = new PropertyPlaceholderHelper(placeholderPrefix, placeholderSuffix, valueSeparator,
                ignoreUnresolvablePlaceholders);
            this.resolver = new PropertyPlaceholderConfigurerResolver(props);
        }
        
        @Override
        public String resolveStringValue(String strVal)
            throws BeansException
        {
            String value = this.helper.replacePlaceholders(strVal, this.resolver);
            return (value.equals(nullValue) ? null : value);
        }
    }
    
    private class PropertyPlaceholderConfigurerResolver implements PlaceholderResolver
    {
        private final Properties props;
        
        private PropertyPlaceholderConfigurerResolver(Properties props)
        {
            this.props = props;
        }
        
        @Override
        public String resolvePlaceholder(String placeholderName)
        {
            return PropertyPlaceholderConfigurer.this.resolvePlaceholder(placeholderName, props, systemPropertiesMode);
        }
    }
    
    public static String getConfigValue(String configName)
    {
        if (SYSTEM_CONFIG_MAP == null || SYSTEM_CONFIG_MAP.isEmpty())
        {
            return null;
        }
        Object configObject = SYSTEM_CONFIG_MAP.get(configName);
        return (configObject == null) ? null : (String)configObject;
    }
}
