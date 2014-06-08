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
package  com.github.aliteralmind.codelet.examples.util;
   import  com.github.aliteralmind.codelet.util.JavaDocUtil;
   import  java.lang.reflect.Method;
   import  java.lang.reflect.Constructor;
   import  com.github.xbn.lang.reflect.Declared;
   import  com.github.xbn.lang.reflect.ReflectRtxUtil;
/**
   <P>Demonstration of <CODE>{@link com.github.aliteralmind.codelet.util.JavaDocUtil}.{@link com.github.aliteralmind.codelet.util.JavaDocUtil#getUrlToConstructor(String, Constructor) getUrlToConstructor}</CODE> and {@link com.github.aliteralmind.codelet.util.JavaDocUtil#getUrlToMethod(String, Method) getUrlToMethod}</P>

   <P>{@code java com.github.xbn.examples.util.FunctionConstructorJavaDocLink}</P>

   @since  0.1.0
   @author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
 **/

public class FunctionConstructorJavaDocLink  {
   public static final void main(String[] ignored)  {
      testConstructor();
      testConstructor(String.class, int[].class, int[].class);
      testMethod(int.class);
      testMethod(String.class, int[].class);
   }

   private static final void testConstructor(Class<?>... param_types)  {
      Constructor c = ReflectRtxUtil.getConstructor(
         FunctionConstructorJavaDocLink.class, Declared.NO, null,
         (Class<?>[])param_types);
      System.out.println(JavaDocUtil.getUrlToConstructor("../", c));
   }

   private static final void testMethod(Class<?>... param_types)  {
      Method m = ReflectRtxUtil.getMethod(FunctionConstructorJavaDocLink.class,
         "doSomething", Declared.NO, null, (Class<?>[])param_types);
      System.out.println(JavaDocUtil.getUrlToMethod("../../", m));
   }

   public FunctionConstructorJavaDocLink()  {
   }
   public FunctionConstructorJavaDocLink(String s, int[] ai, int... ai2)  {
   }
   public void doSomething(int i)  {
   }
   public void doSomething(String s, int[] ai)  {
   }
}
