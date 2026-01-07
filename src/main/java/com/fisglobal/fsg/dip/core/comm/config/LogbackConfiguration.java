package com.fisglobal.fsg.dip.core.comm.config;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import javax.inject.Inject;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;

@Component
public class LogbackConfiguration {

	@Value("${logback.xml.path}")
	private String logbackXMLPath;

	public void configureLogback() {
		LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

		loggerContext.reset();

		JoranConfigurator joranConfigurator = new JoranConfigurator();
		System.out.println("-----------"+logbackXMLPath);
		System.setProperty("LOG_DIR", logbackXMLPath + "/logs/");

		try (InputStream inputStream = new FileInputStream(logbackXMLPath + "logback.xml")) {
			joranConfigurator.setContext(loggerContext);
			joranConfigurator.doConfigure(inputStream);

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JoranException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
