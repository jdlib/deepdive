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


import java.io.IOException;
import java.io.InputStream;
import javax.annotation.CheckReturnValue;
import deepdive.actual.Actual;
import deepdive.impl.NotMustBeOff;


/**
 * An Actual implementation for InputStream objects.
 */
public class InputStreamActual<T extends InputStream,BACK,IMPL extends InputStreamActual<T,BACK,IMPL>> extends Actual<T,BACK,IMPL>  
{
	public InputStreamActual(T value, BACK back)
	{
		super(value, back);
	}


	/**
	 * Returns a ByteContent builder to access the binary content of the inputstream.
	 * @return the ByteContent
	 */
	@CheckReturnValue
	@NotMustBeOff
	public ByteContentBuilder<IMPL,IOException> read()
	{
		notMustBeOff();
		return new ByteContentBuilder<>(self(), true, this::value);
	}
}
