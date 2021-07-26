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
package deepdive.actual.nio.file;


import java.io.IOException;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import javax.annotation.CheckReturnValue;
import deepdive.Context;
import deepdive.actual.Actual;
import deepdive.actual.io.FileActual;
import deepdive.actual.net.URIActual;
import deepdive.actual.util.IteratorActual;
import deepdive.impl.ActualChange;
import deepdive.impl.NotMustBeOff;


/**
 * An Actual implementation for {@link Path} objects.
 */
public class PathActual<BACK,IMPL extends PathActual<BACK,IMPL>> extends Actual<Path,BACK,IMPL>
{
	/**
	 * Creates a new URLActual.
	 * @param value the actual value
	 * @param back the owner object
	 */
	public PathActual(Path value, BACK back)
	{
		super(value, back);
	}

	
	/**
	 * Asserts that the path ends with expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL endsWith(Path expected)
	{
		return expectTo(value().endsWith(expected), "end with", expected);
	}
	
	
	/**
	 * Asserts that the path ends with expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL endsWith(String expected)
	{
		return expectTo(value().endsWith(expected), "end with", expected);
	}
	
	
	/**
	 * Returns a FileActual for the path turned into a file.
	 * @return the new actual
	 */
	public FileActual<IMPL,?> file()
	{
		return new FileActual<>(value().toFile(), self()).as("file");
	}

	
	/**
	 * Returns a Builder to select another path based on the actual path,
	 * and returns a new PathActual for that path. 
	 * @return the builder
	 */
	@CheckReturnValue
	public Nav<PathActual<IMPL,?>> get()
	{
		return new Nav<>(false);
	}
	
	
	/**
	 * Asserts that the path is absolute.
	 * @return this
	 */
	public IMPL isAbsolute()
	{
		expectTrue(value().isAbsolute(), "isAbsolute");
		return self();
	}
	
	
	public IteratorActual<Path,IMPL,?> iterator()
	{
		return new IteratorActual<>(value().iterator(), self()).as("iterator");
	}
	
	
	/**
	 * Asserts that the name count equals expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL nameCount(int expected)
	{
		expectEqual(expected, value().getNameCount(), "nameCount");
		return self();
	}

	
	/**
	 * Returns a Builder to select another path based on the actual path,
	 * {@link #setValue(Object) replaces} that path with the selected path
	 * and returns this.
	 * @return the builder
	 */
	@ActualChange
	@CheckReturnValue
	public Nav<IMPL> set()
	{
		return new Nav<>(true);
	}
	
	
	/**
	 * Asserts that the path starts with expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL startWith(Path expected)
	{
		return expectTo(value().startsWith(expected), "start with", expected);
	}
	
	
	/**
	 * Asserts that the path starts with expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL startWith(String expected)
	{
		return expectTo(value().startsWith(expected), "start with", expected);
	}
	
	
	/**
	 * Returns a URIActual for the path turned into a URI.
	 * @return the new actual
	 */
	public URIActual<IMPL,?> uri()
	{
		return new URIActual<>(value().toUri(), self()).as("uri");
	}
	
	
    /**
     * A Builder class to navigate to another path.
     */
    public class Nav<RESULT>
	{
    	@NotMustBeOff
		protected Nav(boolean set)
		{
			self();
			set_ = set;
		}
		
		
		@SuppressWarnings("unchecked")
		private RESULT create(Path newPath, CharSequence context)
		{
			IMPL impl = self(); 
			return set_ ? (RESULT)impl.setValue(newPath) : (RESULT)new PathActual<>(newPath, impl).as(context);
		}
		
		
		/**
		 * Navigates to the absolute path.
		 * @return the actual returned by the Nav builder
		 */
		public RESULT absolutePath()
		{
			return create(value().toAbsolutePath(), "absolutePath"); 
		}

		
		/**
		 * Navigates to the file name path.
		 * @return the actual returned by the Nav builder
		 */
		public RESULT fileName()
		{
			return create(value().getFileName(), "fileName");
		}

		
		/**
		 * Navigates to the normalized path.
		 * @return the actual returned by the Nav builder
		 */
		public RESULT normalize()
		{
			return create(value().normalize(), "normalize");
		}

		
		/**
		 * Navigates to the root path.
		 * @return the actual returned by the Nav builder
		 */
		public RESULT root()
		{
			return create(value().getRoot(), "root");
		}

		
		/**
		 * Navigates to the parent path.
		 * @return the actual returned by the Nav builder
		 */
		public RESULT parent()
		{
			return create(value().getParent(), "parent");
		}

		
		/**
		 * Navigates to the resolved path.
		 * @param other another path
		 * @return the actual returned by the Nav builder
		 */
		public RESULT resolve(Path other)
		{
			return create(value().resolve(other), Context.call("resolved", other));
		}

		
		/**
		 * Navigates to the resolved path.
		 * @param other another path string
		 * @return the actual returned by the Nav builder
		 */
		public RESULT resolve(String other)
		{
			return create(value().resolve(other), Context.call("resolved", other));
		}

		
		/**
		 * Navigates to the resolved sibling path.
		 * @param other another path
		 * @return the actual returned by the Nav builder
		 */
		public RESULT resolveSibling(Path other)
		{
			return create(value().resolveSibling(other), Context.call("resolveSibling", other));
		}

		
		/**
		 * Navigates to the resolved sibling path.
		 * @param other another path string
		 * @return the actual returned by the Nav builder
		 */
		public RESULT resolveSibling(String other)
		{
			return create(value().resolveSibling(other), Context.call("resolveSibling", other));
		}
		
		
		/**
		 * Navigates to the real path.
		 * @param options link options 
		 * @return the new actual
		 * @throws IOException if the operation fails
		 */
		public RESULT realPath(LinkOption... options) throws IOException
		{
			return create(value().toRealPath(options), Context.call("realPath", (Object[])options));
		}

		
		private final boolean set_;
	}
}
