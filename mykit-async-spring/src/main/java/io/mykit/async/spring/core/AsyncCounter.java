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
package io.mykit.async.spring.core;

/**
 * @author liuyazhuang
 * @date 2018/9/9 21:29
 * @description 异步调用计数器
 * @version 1.0.0
 */
public class AsyncCounter {

    private static ThreadLocal<Integer> counterThreadMap = new ThreadLocal<Integer>();

    public static int intValue() {
        Integer counter = counterThreadMap.get();
        if (counter == null) {
            counter = 0;
        }
        return counter;
    }

    public static Integer get() {
        return counterThreadMap.get();
    }

    public static void set(Integer counter) {
        if (counter == null) {
            counter = 0;
        }
        counterThreadMap.set(counter);
    }

    public static void release() {
        counterThreadMap.remove();
    }
}
