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
/**
 * Contains classes to write basic expectations, also known as assertions.
 * Depending on how you like to make assertion methods available in your test classes
 * you have these possibilities:
 * <ol>
 * <li>(statically) import methods from {@link deepdive.ExpectStatic} into the test class
 * <li>let the test class implement {@link deepdive.ExpectInterface}
 * <li>derive the test class from {@link deepdive.ExpectPublic}
 * <li>derive the test class from {@link deepdive.ExpectProtected}
 * </ol>
 */
package deepdive;