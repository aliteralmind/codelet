/*license*\
   Codelet: Copyright (C) 2014, Jeff Epstein (aliteralmind __DASH__ github __AT__ yahoo __DOT__ com)

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
	import  com.github.xbn.lang.reflect.ReflectRtxUtil;
	import  java.nio.file.NoSuchFileException;
	import  java.nio.file.AccessDeniedException;
/**
   <p>An {@code FQClassNameWithBaseDir} where the fully-qualified class name must represent an actually-existing class, as determined by <code>{@link java.lang.Class Class}.{@link java.lang.Class#forName(String) forName}(s)</code>.</p>

	@since  0.1.0
	@author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <a href="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</a>, <a href="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</a>
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