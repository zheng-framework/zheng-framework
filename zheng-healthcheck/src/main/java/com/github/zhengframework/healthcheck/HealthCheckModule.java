package com.github.zhengframework.healthcheck;

/*-
 * #%L
 * zheng-healthcheck
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

import com.codahale.metrics.health.HealthCheckRegistry;
import com.codahale.metrics.health.jvm.ThreadDeadlockHealthCheck;
import com.github.zhengframework.healthcheck.datasource.DataSourceHealthCheck;
import com.github.zhengframework.healthcheck.sys.MemoryStatusHealthCheck;
import com.github.zhengframework.healthcheck.sys.SystemLoadHealthCheck;
import com.google.inject.AbstractModule;
import com.google.inject.multibindings.OptionalBinder;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = false)
public class HealthCheckModule extends AbstractModule {

  @Override
  protected void configure() {
    OptionalBinder.newOptionalBinder(binder(), HealthCheckRegistry.class)
        .setDefault().toInstance(new HealthCheckRegistry());
    bind(HealthCheckManagedService.class).asEagerSingleton();
    bind(SystemLoadHealthCheck.class);
    bind(ThreadDeadlockHealthCheck.class);
    bind(MemoryStatusHealthCheck.class);
    bind(DataSourceHealthCheck.class);
  }

}
