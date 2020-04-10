package com.github.zhengframework.healthchecks;

import com.codahale.metrics.health.HealthCheck;
import com.github.zhengframework.service.ServiceRegistry;
import com.google.common.base.Strings;
import com.google.common.util.concurrent.AbstractScheduledService;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

/**
 * This service periodically runs health checks at a configured interval and reports any health
 * issues via slf4j
 */
@SuppressWarnings("UnstableApiUsage")
@Singleton
@Slf4j
public class HealthChecksService extends AbstractScheduledService {

  private final HealthChecks healthChecks;
  private final HealthChecksConfig config;

  @Inject
  public HealthChecksService(ServiceRegistry services, HealthChecksConfig config,
      HealthChecks healthChecks) {
    this.config = config;
    this.healthChecks = healthChecks;

    // No need to start it up if we're not configured
    if (config.getInterval() != null) {
      services.add(this);
    }
  }

  @Override
  protected void runOneIteration() throws Exception {

    for (Map.Entry<String, HealthCheck.Result> entry : healthChecks.run().entrySet()) {
      if (entry.getValue().isHealthy()) {
        log.trace("{} : OK {}", entry.getKey(), Strings.nullToEmpty(entry.getValue().getMessage()));
      } else {
        log.warn("{} : FAIL - {}", entry.getKey(),
            Strings.nullToEmpty(entry.getValue().getMessage()), entry.getValue().getError());
      }
    }
  }

  @Override
  protected Scheduler scheduler() {
    return Scheduler.newFixedRateSchedule(0, config.getInterval().getQuantity(),
        config.getInterval().getUnit());
  }
}
