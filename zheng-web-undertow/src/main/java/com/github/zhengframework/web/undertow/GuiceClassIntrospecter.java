package com.github.zhengframework.web.undertow;

/*-
 * #%L
 * zheng-web-undertow
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
import com.google.inject.Injector;
import io.undertow.servlet.api.ClassIntrospecter;
import io.undertow.servlet.api.InstanceFactory;
import io.undertow.servlet.util.ImmediateInstanceFactory;

public class GuiceClassIntrospecter implements ClassIntrospecter {

  private final Injector injector;

  @Inject
  public GuiceClassIntrospecter(Injector injector) {
    this.injector = injector;
  }

  @Override
  public <T> InstanceFactory<T> createInstanceFactory(Class<T> clazz) {
    return new ImmediateInstanceFactory<>(injector.getInstance(clazz));
  }
}
