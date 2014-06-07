/*license*\
   Codelet

   Copyright (c) 2014, Jeff Epstein (aliteralmind __DASH__ github __AT__ yahoo __DOT__ com)

   This software is dual-licensed under the:
   - Lesser General Public License (LGPL) version 3.0 or, at your option, any later version;
   - Apache Software License (ASL) version 2.0.

   Either license may be applied at your discretion. More information may be found at
   - http://en.wikipedia.org/wiki/Multi-licensing.

   The text of both licenses is available in the root directory of this project, under the names "LICENSE_lgpl-3.0.txt" and "LICENSE_asl-2.0.txt". The latest copies may be downloaded at:
   - LGPL 3.0: https://www.gnu.org/licenses/lgpl-3.0.txt
   - ASL 2.0: http://www.apache.org/licenses/LICENSE-2.0.txt
\*license*/
package  com.github.aliteralmind.codelet.util;
   import  com.github.xbn.util.JavaUtil;
   import  com.github.xbn.io.PathMustBe;
   import  com.github.xbn.io.Existence;
   import  com.github.xbn.io.NewPrintWriterToFile;
   import  com.github.xbn.io.PlainTextFileUtil;
   import  com.github.xbn.io.Readable;
   import  com.github.xbn.io.Writable;
   import  com.github.xbn.lang.Copyable;
   import  com.github.xbn.lang.CrashIfObject;
   import  java.io.PrintWriter;
   import  java.nio.file.AccessDeniedException;
   import  java.nio.file.Files;
   import  java.nio.file.LinkOption;
   import  java.nio.file.NoSuchFileException;
   import  java.nio.file.Path;
   import  java.nio.file.Paths;
   import  java.util.Iterator;
/**
   <P>Given a base directory and fully-qualified class name, get a {@link java.nio.file.Path}, {@link org.apache.commons.io.LineIterator}, or {@link java.io.PrintWriter} to its source code, class file, or JavaDoc html file. The fully-qualified class name may or may not represent an actually-existing class (as determined by {@code Class.forName(s)}).</P>

   @author  Copyright (C) 2014, Jeff Epstein, dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
 **/
public class FQClassNameWithBaseDir implements Copyable  {
   private final String       baseDir   ;
   private final String       fqClsNm   ;
   private final Path         javaPath  ;
   private final String       dotPlusXtn;
   public FQClassNameWithBaseDir(String base_dir, String fq_className, String dot_plusExtension)   {
      try  {
         if(!dot_plusExtension.startsWith("."))  {
            throw  new IllegalArgumentException("dot_plusExtension (" + dot_plusExtension + ") does not start with a dot.");
         }
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(dot_plusExtension, "dot_plusExtension", null, rx);
      }
      String path = JavaUtil.getPathForJavaClass(base_dir, fq_className, dot_plusExtension);

      //Set parameters into fields here, so toString() can be used in error messages.
      baseDir = base_dir;
      fqClsNm = fq_className;
      dotPlusXtn = dot_plusExtension;
      javaPath = Paths.get(path);
   }
   /**
      <P>Create a new instance as a duplicate of another.</P>

      @param  to_copy  May not be <CODE>null</CODE>.
      @see  #getObjectCopy()
    **/
   public FQClassNameWithBaseDir(FQClassNameWithBaseDir to_copy)  {
      this(to_copy, null, null, null);
   }
   /**
      <P>Create a new instance as a duplicate of another.</P>

      @param  to_copy  May not be <CODE>null</CODE>.
      @see  #getObjectCopy()
    **/
   public FQClassNameWithBaseDir(FQClassNameWithBaseDir to_copy, String baseDir_override, String fqClsName_override, String dotXtnsn_override)  {
      this(((baseDir_override == null) ? (new ToCopyHandler(to_copy)).getBaseDirectory() : baseDir_override),
         ((fqClsName_override == null) ? (new ToCopyHandler(to_copy)).getClassName() : fqClsName_override),
         ((dotXtnsn_override == null) ? (new ToCopyHandler(to_copy)).getDotPlusExtension() : dotXtnsn_override));
   }
   public FQClassNameWithBaseDir getThisOrCrashIfBad(Readable readable_is, Writable writable_is) throws NoSuchFileException, AccessDeniedException  {
      getThisOrCrashIfBad(Existence.REQUIRED, readable_is, writable_is);
      return  this;
   }
   public FQClassNameWithBaseDir getThisOrCrashIfBad(Existence existence_is, Readable readable_is, Writable writable_is) throws NoSuchFileException, AccessDeniedException  {
      new PathMustBe().
         existing(existence_is).readable(readable_is).writable(writable_is).
         crashIfBad(getPath(), "getPath()");
      return  this;
   }

   public Iterator<String> newLineIterator()  {
      try  {
         return  PlainTextFileUtil.getLineIterator(getPath().toFile(), "getPath().toFile()");
      }  catch(RuntimeException rx)  {
         throw  new RuntimeException("Attempting PlainTextFileUtil.getLineIterator(getPath().toFile(), ...), this=[" + this + "]", rx);
      }
   }
   public String getFileText()  {
      try  {
         return  PlainTextFileUtil.getText(getPath().toFile(), "getPath().toFile()");
      }  catch(RuntimeException rx)  {
         throw  new RuntimeException("Attempting PlainTextFileUtil.getText(getPath().toFile(), ...), this=[" + this + "]", rx);
      }
   }
   public StringBuilder appendText(StringBuilder to_appendTo)  {
      try  {
         return  PlainTextFileUtil.appendText(to_appendTo, getPath().toFile(), "getPath().toFile()");
      }  catch(RuntimeException rx)  {
         throw  new RuntimeException("Attempting PlainTextFileUtil.appendText(to_appendTo, getPath().toFile(), ...), this=[" + this + "]", rx);
      }
   }
   public PrintWriter newFileWriter()  {
      return  new NewPrintWriterToFile().overwrite().autoFlush().
            build(getPath().toString());
   }
   public String getBaseDirectory()  {
      return  baseDir;
   }
   public String getClassName()  {
      return  fqClsNm;
   }
   public Path getPath()  {
      return  javaPath;
   }
   public String getDotPlusExtension()  {
      return  dotPlusXtn;
   }
   public String toString()  {
      return  "base=" + getBaseDirectory() + ", class=" + getClassName() + ", dot-extension=" + getDotPlusExtension() + ", absolute=" + getPath().toAbsolutePath();
   }
   /**
      <P>Get a duplicate of this <CODE>FQClassNameWithBaseDir</CODE>.</P>

      @return  <CODE>(new <A HREF="#FQClassNameWithBaseDir(FQClassNameWithBaseDir)">FQClassNameWithBaseDir</A>(this))</CODE>
    **/
   public FQClassNameWithBaseDir getObjectCopy()  {
      return  (new FQClassNameWithBaseDir(this));
   }
   public static final Iterator<String> newLineIteratorForExistingReadable(String base_dir, String fq_className, String dot_plusExtension) throws NoSuchFileException, AccessDeniedException  {
      return  new FQClassNameWithBaseDir(
            base_dir, fq_className, dot_plusExtension).
         getThisOrCrashIfBad(Existence.REQUIRED, Readable.REQUIRED, Writable.OPTIONAL).
         newLineIterator();
   }
   public static final PrintWriter newFileWriterForWriteable(String base_dir, String fq_className, String dot_plusExtension) throws NoSuchFileException, AccessDeniedException  {
      return  new FQClassNameWithBaseDir(
            base_dir, fq_className, dot_plusExtension).
         getThisOrCrashIfBad(Readable.OPTIONAL, Writable.REQUIRED).
         newFileWriter();
   }
}
class ToCopyHandler  {
   private final FQClassNameWithBaseDir toCopy;
   ToCopyHandler(FQClassNameWithBaseDir to_copy)  {
      toCopy = to_copy;
   }
   public String getBaseDirectory()  {
      try  {
         return  toCopy.getBaseDirectory();
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(toCopy, "to_copy", null, rx);
      }
   }
   public String getClassName()  {
      try  {
         return  toCopy.getClassName();
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(toCopy, "to_copy", null, rx);
      }
   }
   public String getDotPlusExtension()  {
      try  {
         return  toCopy.getDotPlusExtension();
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(toCopy, "to_copy", null, rx);
      }
   }
}
