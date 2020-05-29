package com.pecc.example.metricsspike;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

import io.micrometer.core.instrument.Meter.Type;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.stackdriver.StackdriverConfig;
import io.micrometer.stackdriver.StackdriverMeterRegistry;
import io.micrometer.stackdriver.StackdriverNamingConvention;

@SpringBootApplication
public class MetricsSpikeApplication {

	public static void main(String[] args) {
		SpringApplication.run(MetricsSpikeApplication.class, args);
	}

	@Bean
	@ConditionalOnProperty(name = "management.metrics.export.stackdriver.enabled", havingValue = "true")
	public MeterRegistry stackDriverMeterRegistry(
			@Value("${management.metrics.export.stackdriver.project-id}") String projectId,
			@Value("${hostname:${host:${user:'unknown'}}}") String host,
			@Value("${spring.application.name}") String applicationName) {

		StackdriverConfig stackdriverConfig = new StackdriverConfig() {
			@Override
			public String projectId() {
				return projectId;
			}

			@Override
			public String get(String key) {
				return null;
			}
		};

		StackdriverMeterRegistry registry = StackdriverMeterRegistry.builder(stackdriverConfig).build();
		registry.config().commonTags("application", applicationName);
		registry.config().commonTags("host", host);
		registry.config().namingConvention(new StackdriverNamingConvention() {
			@Override
			public String name(String name, Type type, String baseUnit) {
				return "springmetrics/" + super.name(name, type, baseUnit);
			}
		});
		return registry;
	}


}
