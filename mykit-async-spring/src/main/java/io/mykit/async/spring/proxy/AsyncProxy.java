/**
 * Copyright 2018-2118 the original author or authors.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.mykit.async.spring.proxy;

/**
 * @author liuyazhuang
 * @version 1.0.0
 * @date 2018/9/9 21:58
 * @description 异步代理接口
 */
public interface AsyncProxy {

    Object buildProxy(Object target, boolean all);

    Object buildProxy(Object target, long timeout, boolean all);
}
