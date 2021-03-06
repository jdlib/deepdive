/*
 * Copyright (c) 2021 jdlib, https://github.com/jdlib
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package deepdive.function;


import java.util.function.Supplier;


/**
 * A {@link Supplier} like interface which allows to throw any exception when invoked.
 * @param <T> the return type
 * @param <E> the exception type
 */
@FunctionalInterface
public interface CheckedSupplier<T,E extends Exception>
{
    /**
     * Gets a result.
     * @return a result
     * @throws E thrown by the supplier
     */
    T get() throws E;
}
