package com.sprenkels.photoSafe;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.cfg4j.provider.ConfigurationProvider;
import org.cfg4j.provider.ConfigurationProviderBuilder;
import org.cfg4j.source.ConfigurationSource;
import org.cfg4j.source.context.environment.Environment;
import org.cfg4j.source.context.environment.ImmutableEnvironment;
import org.cfg4j.source.context.filesprovider.ConfigFilesProvider;
import org.cfg4j.source.files.FilesConfigurationSource;
import org.cfg4j.source.reload.ReloadStrategy;
import org.cfg4j.source.reload.strategy.PeriodicalReloadStrategy;

public class MainPhotoSafe {
	
	final static Logger log = Logger.getLogger(MainPhotoSafe.class);

	public static void main(String[] args) {
		
		// Specify which files to load. Configuration from both files will be merged.
		ConfigFilesProvider configFilesProvider = () -> Arrays.asList(Paths.get("application.properties"));

		// Use local files as configuration store
		ConfigurationSource source = new FilesConfigurationSource(configFilesProvider);

		// (optional) Select path to use
		Environment environment = new ImmutableEnvironment(".");

		// (optional) Reload configuration every 5 seconds
		ReloadStrategy reloadStrategy = new PeriodicalReloadStrategy(5, TimeUnit.SECONDS);

		// Create provider
		ConfigurationProvider config = new ConfigurationProviderBuilder()
		    .withConfigurationSource(source)
		    .withEnvironment(environment)
		    .withReloadStrategy(reloadStrategy)
		    .build();

		Properties conf = config.allConfigurationAsProperties();
		log.debug(conf.getProperty("storePath", "."));
	
		
//		PhotoSafe ps = new PhotoSafe("I:\\FotoVideoMASTER\\master\\2004");
		PhotoSafe ps = new PhotoSafe(conf.getProperty("storePath", "."));
		log.debug("showing Contents  ");
		ps.showContents();
		log.debug("checking " + conf.getProperty("checkAndAddDiff"));
		ps.checkDiff(conf.getProperty("checkAndAddDiff"));
		// ps.checkDiff("../additions");
		// ps.showContents();
		log.debug("Finished.");
		System.exit(0);
	}
}
