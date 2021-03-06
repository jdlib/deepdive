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
package deepdive.impl;


/**
 * Pair represents a pair of two values.
 */
public class Pair<V1,V2>
{
	public Pair(V1 v1, V2 v2)
	{
		this.v1 = v1;
		this.v2 = v2;
	}


	public final V1 v1;
	public final V2 v2;
}
