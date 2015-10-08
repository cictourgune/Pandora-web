package org.tourgune.pandora.util;

import java.io.IOException;
import java.util.Properties;

public class Configuration {
	
	
	Properties properties = null;
	 
    public final static String CONFIG_FILE = "config.properties";
    
    public final static String IP = "IP";
    public final static String PORT = "PORT";
    public final static String ROOT = "ROOT";
    public final static String GCM_DOMAIN = "gcm.domain";
    public final static String GCM_CONTEXT = "gcm.context";
    public final static String QUEUE_DOMAIN = "queue.domain";
    public final static String certURL = "certURL";
    
    public final static String NAME = "pandora";
     
 
    private Configuration() {
        this.properties = new Properties();
        try {
            properties.load(Configuration.class.getClassLoader().getResourceAsStream(CONFIG_FILE));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }//Configuration
 
    /**
     * Implementando Singleton
     *
     * @return
     */
    public static Configuration getInstance() {
        return ConfigurationHolder.INSTANCE;
    }
 
    private static class ConfigurationHolder {
 
        private static final Configuration INSTANCE = new Configuration();
    }
 
    /**
     * Devuelve la propiedad de configuración solicitada
     *
     * @param key
     * @return
     */
    public String getProperty(String key) { 
    	return this.properties.getProperty(key);
    }
}
