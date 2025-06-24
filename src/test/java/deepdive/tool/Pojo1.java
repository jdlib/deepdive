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
package deepdive.tool;


import java.net.URL;


public class Pojo1
{
	public enum Type
	{
		A, B, C
	}
	
	
	// tested
	public URL url;
	private String name;
	private boolean hasFormat;
	
	
	// tested
	public boolean hasFormat()
	{
		return hasFormat;
	}

	// ignored
	public void setName(String name)
	{
	}

	// ignored
	public Pojo1 setName2(String name)
	{
		return this;
	}
	
	
	public String getName()
	{
		return name;
	}


	public String getQualifiedName(String prefix)
	{
		return prefix + name;
	}

	
	public boolean nameStartsWith(String s)
	{
		return name.startsWith(s);
	}
	
	
	public boolean nameEndwWith(String s1, String s2)
	{
		return true;
	}


	public Type getType()
	{
		return Type.A;
	}
}
