/*license*\
   Codelet

   XBN-Java is a collection of generically-useful non-GUI programming utilities, featuring shareable self-returning method chains, regular expression convenience classes, TextLineFilter, highly-configurable output for lists, and automated insertion of example code into documentation.

   Copyright (C) 2014, Jeff Epstein

   This library is free software; you can redistribute it and/or modify it under the terms of the GNU Lesser General Public License as published by the Free Software Foundation; either version 2.1 of the License, or (at your option) any later version.

   This library is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more details.

   You should have received a copy of the GNU Lesser General Public License along with this library; if not, write to the Free Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
\*license*/
package  com.github.aliteralmind.codelet.util;
   import  com.github.xbn.lang.reflect.ReflectRtxUtil;
   import  java.nio.file.NoSuchFileException;
   import  java.nio.file.AccessDeniedException;
/**
   <P>An {@code FQClassNameWithBaseDir} where the fully-qualified class name must represent an actually-existing class, as determined by <CODE>{@link java.lang.Class Class}.{@link java.lang.Class#forName(String) forName}(s)</CODE>.</P>

   @author  Copyright (C) 2014, Jeff Epstein, dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
 **/
public class JavaClassWithBaseDir extends FQClassNameWithBaseDir  {
   private final Class<?> javaCls;
   public JavaClassWithBaseDir(String base_dir, String fq_className, String dot_plusExtension, String fqClsNm_varName) throws NoSuchFileException, AccessDeniedException  {
      super(base_dir, fq_className, dot_plusExtension);
      javaCls = ReflectRtxUtil.getClassForName(fq_className, fqClsNm_varName);
   }
   public JavaClassWithBaseDir(JavaClassWithBaseDir to_copy) throws NoSuchFileException, AccessDeniedException  {
      super(to_copy);
      javaCls = ReflectRtxUtil.getClassForName(getClassName(), "getClassName()");
   }
   public JavaClassWithBaseDir(JavaClassWithBaseDir to_copy, String baseDir_override, String fqClsName_override, String dotXtnsn_override) throws NoSuchFileException, AccessDeniedException  {
      super(to_copy, baseDir_override, fqClsName_override, dotXtnsn_override);
      javaCls = ReflectRtxUtil.getClassForName(getClassName(), "getClassName()");
   }
   public JavaClassWithBaseDir(String base_dir, Class<?> java_cls, String dot_plusExtension) throws NoSuchFileException, AccessDeniedException  {
      super(base_dir,
         ((java_cls == null) ? null : java_cls.getName()),
         dot_plusExtension);
      javaCls = java_cls;
   }
   public JavaClassWithBaseDir(JavaClassWithBaseDir to_copy, String baseDir_override, Class<?> classForName_override, String dotXtnsn_override) throws NoSuchFileException, AccessDeniedException  {
      super(to_copy, baseDir_override,
         ((classForName_override == null) ? null : classForName_override.getName()),
         dotXtnsn_override);
      javaCls = ReflectRtxUtil.getClassForName(getClassName(), "getClassName()");
   }
   public Class<?> getJavaClass()  {
      return  javaCls;
   }
}
