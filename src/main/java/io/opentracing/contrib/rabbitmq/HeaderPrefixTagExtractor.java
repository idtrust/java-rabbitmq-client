/*
 * Copyright 2017-2020 The OpenTracing Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package io.opentracing.contrib.rabbitmq;

import com.rabbitmq.client.AMQP.BasicProperties;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/** Extracts TAGs for headers starting with a prefix */
public class HeaderPrefixTagExtractor implements TagExtractor {

  private String prefix;

  public HeaderPrefixTagExtractor(String prefix) {
    this.prefix = prefix;
  }

  @Override
  public Map<String, String> extractTags(BasicProperties props) {
    Map<String, Object> headers = props.getHeaders();
    if (headers != null) {
      HashMap<String, String> result = new HashMap<>(10);
      headers.keySet().stream()
          .filter(s -> s.startsWith(prefix))
          .forEach(k -> result.put(k, Objects.toString(headers.get(k))));
      return result;
    } else return Collections.emptyMap();
  }
}
