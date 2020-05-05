package com.github.zhengframework.job;

/*-
 * #%L
 * zheng-job
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

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;

@Singleton
public class SchedulerProvider implements Provider<Scheduler> {

  private final GuiceJobFactory guiceJobFactory;

  private final SchedulerFactory schedulerFactory;
  private Scheduler scheduler;

  @Inject
  public SchedulerProvider(GuiceJobFactory guiceJobFactory,
      SchedulerFactory schedulerFactory) {
    this.guiceJobFactory = guiceJobFactory;

    this.schedulerFactory = schedulerFactory;
  }

  @Override
  public Scheduler get() {
    if (scheduler == null) {
      try {
        scheduler = schedulerFactory.getScheduler();
        scheduler.setJobFactory(guiceJobFactory);
      } catch (SchedulerException e) {
        throw new RuntimeException(e);
      }
    }
    return scheduler;
  }
}
