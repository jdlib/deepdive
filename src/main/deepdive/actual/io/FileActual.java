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
package deepdive.actual.io;


import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import javax.annotation.CheckReturnValue;
import deepdive.actual.Actual;
import deepdive.actual.lang.LongActual;
import deepdive.actual.lang.StringActual;
import deepdive.actual.lang.StringArrayActual;
import deepdive.impl.ActualChange;
import deepdive.impl.NotMustBeOff;


/**
 * An Actual implementation for {@link File} objects.
 */
public class FileActual<BACK,IMPL extends FileActual<BACK,IMPL>> extends Actual<File,BACK,IMPL>
{
	/**
	 * Creates a new FileActual.
	 * @param value the value
	 * @param back the owner
	 */
	public FileActual(File value, BACK back)
	{
		super(value, back);
	}
	

	/**
	 * Asserts that one can execute the file.
	 * @return this
	 */
	public IMPL canExecute()
	{
		expectTrue(value().canExecute(), "canExecute");
		return self();
	}
	

	/**
	 * Asserts that one can read the file.
	 * @return this
	 */
	public IMPL canRead()
	{
		expectTrue(value().canRead(), "canRead");
		return self();
	}
	
	
	/**
	 * Asserts that one can write to the file.
	 * @return this
	 */
	public IMPL canWrite()
	{
		expectTrue(value().canWrite(), "canWrite");
		return self();
	}
	
	
	/**
	 * Asserts that the File exists.
	 * @return this
	 */
	public IMPL exists()
	{
		expectTrue(value().exists(), "exists");
		return self();
	}
	
	
	/**
	 * Asserts that the file has the given extension
	 * The file extension is the part of the file name after the last '.' or 
	 * or null if the name does not contain a '.'
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL extension(String expected)
	{
		expectEqual(expected, getExtension(value()), "extension");
		return self();
	}

	
	/**
	 * Returns a StringActual for the file extension.
	 * The file extension is the part of the file name after the last '.' or 
	 * or null if the name does not contain a '.'
	 * @return the new actual
	 */
	public StringActual<IMPL,?> extension()
	{
		return new StringActual<>(getExtension(value()), self()).as("extension");
	}
	
	
	/**
	 * Returns a Builder to select another file based on the actual file,
	 * and returns a new FileActual for that file. 
	 * @return the builder
	 */
	@CheckReturnValue
	public Nav<FileActual<IMPL,?>> get()
	{
		return new Nav<>(false);
	}

	
	/**
	 * Asserts that the File is absolute.
	 * @return this
	 */
	public IMPL isAbsolute()
	{
		expectTrue(value().isAbsolute(), "isAbsolute");
		return self();
	}
	
	
	/**
	 * Asserts that the File is a directory.
	 * @return this
	 */
	public IMPL isDirectory()
	{
		expectTrue(value().isDirectory(), "isDirectory");
		return self();
	}
	
	
	/**
	 * Asserts that the File is a file.
	 * @return this
	 */
	public IMPL isFile()
	{
		expectTrue(value().isFile(), "isFile");
		return self();
	}
	
	
	/**
	 * Asserts that the file is hidden.
	 * @return this
	 */
	public IMPL isHidden()
	{
		expectTrue(value().isHidden(), "isHidden");
		return self();
	}
	
	
	/**
	 * Returns a LongActual for the last modified value.
	 * @return the new actual
	 */
	public LongActual<IMPL,?> lastModified()
	{
		return new LongActual<>(value().lastModified(), self()).as("lastModified");
	}

	
	/**
	 * Asserts that the file length equals the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL length(long expected)
	{
		expectEqual(expected, value().length());
		return self();
	}

	
	/**
	 * Returns a LongActual for the file length.
	 * @return the new actual
	 */
	public LongActual<IMPL,?> length()
	{
		return new LongActual<>(value().length(), self()).as("length");
	}

	
	/**
	 * Returns a StringArrayActual for the file names listed by this file.
	 * @return the new actual
	 */
	public StringArrayActual<IMPL,?> list()
	{
		return list(null);
	}

	
	/**
	 * Returns a StringArrayActual for the file names listed by this file.
	 * @param filter optional: a filter for the listed files
	 * @return the new actual
	 */
	public StringArrayActual<IMPL,?> list(FilenameFilter filter)
	{
		String[] names = filter != null ? value().list(filter) : value().list(); 
		return new StringArrayActual<>(names, self()).as("list");
	}

	
	/**
	 * Returns a FileArrayActual for the files listed by this file.
	 * @return the new actual
	 */
	public FileArrayActual<IMPL> listFiles()
	{
		return listFiles(null);
	}

	
	/**
	 * Returns a FileArrayActual for the files listed by this file.
	 * @param filter optional: a filter for the listed files
	 * @return the new actual
	 */
	public FileArrayActual<IMPL> listFiles(FilenameFilter filter)
	{
		File[] files = filter != null ? value().listFiles(filter) : value().listFiles(); 
		return new FileArrayActual<>(files, self()).as("listFiles");
	}

	
	/**
	 * Asserts that the file name equals the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL name(String expected)
	{
		expectEqual(expected, value().getName());
		return self();
	}

	
	/**
	 * Returns a StringActual for the file name.
	 * @return the new actual
	 */
	public StringActual<IMPL,?> name()
	{
		return new StringActual<>(value().getName(), self()).as("name");
	}
	
	
	/**
	 * Returns a StringActual for the parent path.
	 * @return the new actual
	 */
	public StringActual<IMPL,?> parent()
	{
		return new StringActual<>(value().getParent(), self()).as("parent");
	}

	
	/**
	 * Asserts that the file path equals the expected value.
	 * @param expected the expected value
	 * @return this
	 */
	public IMPL path(String expected)
	{
		expectEqual(expected, value().getPath(), "path");
		return self();
	}

	
	/**
	 * Returns a StringActual for the file path.
	 * @return the new actual
	 */
	public StringActual<IMPL,?> path()
	{
		return new StringActual<>(value().getPath(), self()).as("path");
	}
	
	
	/**
	 * Returns a ByteContentBuilder builder to access the content of the file.
	 * @return the builder
	 */
	@CheckReturnValue
	@NotMustBeOff
	public ByteContentBuilder<IMPL,IOException> read()
	{
		notMustBeOff();
		return ByteContentBuilder.of(self(), value());
	}
	
	
	/**
	 * Returns a Builder to select another file based on the actual file,
	 * {@link #setValue(Object) replaces} that file with the selected file
	 * and returns this.
	 * @return the builder
	 */
	@CheckReturnValue
	@ActualChange
	public Nav<IMPL> set()
	{
		return new Nav<>(true);
	}
	
	
	/**
	 * Returns a LongActual for the files total space..
	 * @return the new actual
	 */
	public LongActual<IMPL,?> totalSpace()
	{
		return new LongActual<>(value().getTotalSpace(), self()).as("totalSpace");
	}


    /**
     * A Builder class to navigate to another file.
     */
    public class Nav<RESULT>
	{
    	@NotMustBeOff
		protected Nav(boolean set)
		{
    		notMustBeOff();
			set_ = set;
		}
		
		
		@SuppressWarnings("unchecked")
		private RESULT create(File file, CharSequence context)
		{
			IMPL impl = self(); 
			return set_ ? (RESULT)impl.setValue(file) : (RESULT)new FileActual<>(file, impl).as(context);
		}

		
		/**
		 * Navigates to the child file.
		 * @param name the name of the child file
		 * @return the FileActual returned by the Nav builder
		 */
		public RESULT childFile(String name)
		{
			return create(new File(value(), name), "childFile"); 
		}
		
		
		/**
		 * Navigates to the absolute file.
		 * @return the FileActual returned by the Nav builder
		 */
		public RESULT absoluteFile()
		{
			return create(value().getAbsoluteFile(), "absoluteFile"); 
		}


		/**
		 * Navigates to the canonical file.
		 * @return the FileActual returned by the Nav builder
		 * @throws IOException if the canonical file cannot be retrieved
		 */
		public RESULT canonicalFile() throws IOException
		{
			return create(value().getCanonicalFile(), "canonicalFile"); 
		}

	
		/**
		 * Navigates to the parent file.
		 * @return the FileActual returned by the Nav builder
		 */
		public RESULT parentFile()
		{
			return create(value().getParentFile(), "parentFile"); 
		}
		
		
		private final boolean set_;
	}


	public static String getExtension(File file)
	{
		String extension = null;
		if (file != null)
		{
			String name = file.getName();
			int n = name.lastIndexOf('.');
			if (n >= 0)
				extension = name.substring(n + 1);
		}
		return extension;
	}
}
