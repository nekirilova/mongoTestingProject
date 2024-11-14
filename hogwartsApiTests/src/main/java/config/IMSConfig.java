package config;

import org.aeonbits.owner.Config;

@Config.LoadPolicy(Config.LoadType.MERGE)
@Config.Sources({"system:env", "classpath:${ENVIRONMENT}.config.properties"})
public interface IMSConfig extends Config {
    @Key("ConnectionString")
    String ConnectionString();

    @Config.Key("Database")
    String Database();

    @Config.Key("BaseUrl")
    String BaseUrl();
}
