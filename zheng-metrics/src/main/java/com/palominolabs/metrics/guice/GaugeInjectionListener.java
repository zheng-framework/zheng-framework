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

import com.codahale.metrics.Gauge;
import com.codahale.metrics.MetricRegistry;
import com.google.inject.spi.InjectionListener;
import java.lang.reflect.Method;

/**
 * An injection listener which creates a gauge for the declaring class with the given name (or the
 * method's name, if none was provided) which returns the value returned by the annotated method.
 */
public class GaugeInjectionListener<I> implements InjectionListener<I> {

  private final MetricRegistry metricRegistry;
  private final String metricName;
  private final Method method;

  public GaugeInjectionListener(MetricRegistry metricRegistry, String metricName, Method method) {
    this.metricRegistry = metricRegistry;
    this.metricName = metricName;
    this.method = method;
  }

  @Override
  public void afterInjection(final I i) {
    metricRegistry.register(
        metricName,
        (Gauge<Object>)
            () -> {
              try {
                return method.invoke(i);
              } catch (Exception e) {
                return new RuntimeException(e);
              }
            });
  }
}
