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
package deepdive;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import deepdive.impl.ExpectBase;


abstract class Checkpoint implements ExpectBase
{
	static Checkpoint findSoftCheckpoint(Object candidate)
	{
		while (candidate instanceof Checkpoint)
		{
			Checkpoint cp = (Checkpoint)candidate;
			if (cp.softErrors_ != null)
				return cp;
			candidate = cp.backOrNull();
		}
		return null;
	}
	
	
	boolean postponed(AssertionError error)
	{
		Checkpoint softCp = findSoftCheckpoint(this);
		if (softCp != null)
		{
			softCp.softErrors_.add(error);
			return true;
		}
		else
			return false;
	}
	
	
	void startSoftMode()
	{
		if (softErrors_ == null)
		{
			Checkpoint softCp = findSoftCheckpoint(backOrNull());
			if (softCp == null)
				softErrors_ = new ArrayList<>();
		}
	}
	
	
	List<AssertionError> endSoftMode()
	{
		List<AssertionError> errors = softErrors_;
		softErrors_ = null;
		return errors != null ? errors : Collections.emptyList();
	}
	
	
	private List<AssertionError> softErrors_;
}
