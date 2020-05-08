package com.palominolabs.metrics.guice;

/*-
 * #%L
 * zheng-metrics
 * %%
 * Copyright (C) 2020 Zheng MingHai
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import static com.codahale.metrics.MetricRegistry.name;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.ExceptionMetered;
import com.codahale.metrics.annotation.Gauge;
import com.codahale.metrics.annotation.Metered;
import com.codahale.metrics.annotation.Timed;
import java.lang.reflect.Method;
import javax.annotation.Nonnull;

/**
 * Uses the name fields in the metric annotations, if present, or the method declaring class and
 * method name.
 */
public class DeclaringClassMetricNamer implements MetricNamer {

  static final String COUNTER_SUFFIX = "counter";
  static final String COUNTER_SUFFIX_MONOTONIC = "current";
  static final String GAUGE_SUFFIX = "gauge";
  static final String METERED_SUFFIX = "meter";
  static final String TIMED_SUFFIX = "timer";

  @Nonnull
  @Override
  public String getNameForCounted(@Nonnull Method method, @Nonnull Counted counted) {
    if (counted.absolute()) {
      return counted.name();
    }

    if (counted.name().isEmpty()) {
      if (counted.monotonic()) {
        return name(method.getDeclaringClass(), method.getName(), COUNTER_SUFFIX_MONOTONIC);
      } else {
        return name(method.getDeclaringClass(), method.getName(), COUNTER_SUFFIX);
      }
    }

    return name(method.getDeclaringClass(), counted.name());
  }

  @Nonnull
  @Override
  public String getNameForExceptionMetered(
      @Nonnull Method method, @Nonnull ExceptionMetered exceptionMetered) {
    if (exceptionMetered.absolute()) {
      return exceptionMetered.name();
    }

    if (exceptionMetered.name().isEmpty()) {
      return name(
          method.getDeclaringClass(), method.getName(), ExceptionMetered.DEFAULT_NAME_SUFFIX);
    }

    return name(method.getDeclaringClass(), exceptionMetered.name());
  }

  @Nonnull
  @Override
  public String getNameForGauge(
      @Nonnull Class<?> instanceClass, @Nonnull Method method, @Nonnull Gauge gauge) {
    if (gauge.absolute()) {
      return gauge.name();
    }

    if (gauge.name().isEmpty()) {
      return name(method.getDeclaringClass(), method.getName(), GAUGE_SUFFIX);
    }

    return name(method.getDeclaringClass(), gauge.name());
  }

  @Nonnull
  @Override
  public String getNameForMetered(@Nonnull Method method, @Nonnull Metered metered) {
    if (metered.absolute()) {
      return metered.name();
    }

    if (metered.name().isEmpty()) {
      return name(method.getDeclaringClass(), method.getName(), METERED_SUFFIX);
    }

    return name(method.getDeclaringClass(), metered.name());
  }

  @Nonnull
  @Override
  public String getNameForTimed(@Nonnull Method method, @Nonnull Timed timed) {
    if (timed.absolute()) {
      return timed.name();
    }

    if (timed.name().isEmpty()) {
      return name(method.getDeclaringClass(), method.getName(), TIMED_SUFFIX);
    }

    return name(method.getDeclaringClass(), timed.name());
  }
}
