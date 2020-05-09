package com.github.zhengframework.remoteconfig;

/*-
 * #%L
 * zheng-remote-config
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

public class RemoteConfigException extends Exception {

  private static final long serialVersionUID = 2804131287746588562L;
  private final RemoteConfigResponse<?> response;

  public RemoteConfigException(RemoteConfigResponse<?> response) {
    this.response = response;
  }

  public RemoteConfigResponse<?> getResponse() {
    return response;
  }
}
