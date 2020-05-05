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

import com.codahale.metrics.Counter;
import com.codahale.metrics.annotation.Counted;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

public class CountedInterceptor implements MethodInterceptor {

  private final Counter counter;
  private final boolean decrementAfterMethod;

  public CountedInterceptor(Counter counter, Counted annotation) {
    this.counter = counter;
    decrementAfterMethod = !annotation.monotonic();
  }

  @Override
  public Object invoke(MethodInvocation invocation) throws Throwable {
    counter.inc();
    try {
      return invocation.proceed();
    } finally {
      if (decrementAfterMethod) {
        counter.dec();
      }
    }
  }
}
