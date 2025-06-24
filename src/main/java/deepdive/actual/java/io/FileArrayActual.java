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


import java.io.File;
import deepdive.Context;
import deepdive.actual.java.lang.ArrayActual;


/**
 * An ArrayActual implementation for File arrays.
 */
public class FileArrayActual<BACK> extends ArrayActual<File,BACK,FileArrayActual<BACK>>
{
	public FileArrayActual(File[] actual, BACK back)
	{
		super(actual, back);
	}
	
	
	/**
	 * Returns a FileActual for the element at the given index.
	 * @return the new actual
	 */
	@Override public FileActual<FileArrayActual<BACK>,?> elem(int index)
	{
		return elem(index, FileActual::new).as(Context.indexed("elem", index));
	}
}
