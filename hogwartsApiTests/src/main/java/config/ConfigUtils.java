package config;

import org.aeonbits.owner.ConfigFactory;

public abstract class ConfigUtils {
    public final static IMSConfig cfg = ConfigFactory.create(IMSConfig.class);
}
