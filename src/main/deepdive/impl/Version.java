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
 * Holds the current DeepDive version.
 */
public class Version
{
	public static final String VALUE = "1.0";


	/**
	 * Prints the version number to System.out. If the first argument equals "-v", then a property like line
	 * "milo.version = (version)" is printed.
	 * @param args the command line args 
	 */
	public static void main(String[] args)
	{
		boolean verbose = (args.length > 0) && args[0].equals("-v");
		if (verbose)
			System.out.print("version = ");
		System.out.println(VALUE);
	}
}
