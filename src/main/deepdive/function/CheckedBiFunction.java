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


import java.util.function.BiFunction;


/**
 * A {@link BiFunction} like interface which allows to throw any exception when invoked.
 * @param <T> the type of the first argument
 * @param <U> the type of the second argument
 * @param <R> the result type
 * @param <E> the type of the exception thrown by the Runnable
 */
@FunctionalInterface
public interface CheckedBiFunction<T,U,R,E extends Exception> 
{
    /**
     * Applies this function to the given arguments.
     * @param t the first function argument
     * @param u the second function argument
     * @return the function result
     * @throws E thrown by the function
     */
	R apply(T t, U u) throws E;
}
