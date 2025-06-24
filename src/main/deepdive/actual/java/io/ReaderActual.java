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
package deepdive.actual.java.io;


import java.io.Reader;
import javax.annotation.CheckReturnValue;
import deepdive.actual.Actual;
import deepdive.impl.NotMustBeOff;


/**
 * An Actual implementation for {@link Reader} objects.
 */
public class ReaderActual<T extends Reader,BACK,IMPL extends ReaderActual<T,BACK,IMPL>> extends Actual<T,BACK,IMPL>   
{
	public ReaderActual(T value, BACK back)
	{
		super(value, back);
	}


	/**
	 * Returns a CharContent builder to access the content of the Reader.
	 * @return the builder
	 */
	@CheckReturnValue
	@NotMustBeOff
	public CharContentBuilder<IMPL,RuntimeException> read()
	{
		notMustBeOff();
		return CharContentBuilder.of(self(), false, value());
	}
}
