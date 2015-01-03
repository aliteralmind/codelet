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
package  com.github.aliteralmind.codelet.simplesig;
   import  com.github.xbn.array.ArrayUtil;
   import  com.github.xbn.array.NullContainer;
   import  com.github.xbn.io.NewTextAppenterFor;
   import  com.github.xbn.io.TextAppenter;
   import  com.github.xbn.lang.CrashIfObject;
   import  com.github.xbn.lang.IllegalArgumentStateException;
   import  com.github.xbn.lang.reflect.InvokeMethodWithRtx;
   import  com.github.xbn.lang.reflect.ReflectRtxUtil;
   import  com.github.xbn.regexutil.RegexUtil;
   import  com.github.xbn.util.JavaRegexes;
   import  java.lang.reflect.Method;
   import  java.util.ArrayList;
   import  java.util.Arrays;
   import  java.util.List;
   import  java.util.Objects;
   import  java.util.regex.Matcher;
   import  java.util.regex.Pattern;
   import  static com.github.xbn.lang.XbnConstants.*;
/**
   <p>The pieces that represent a method, containing its return-type, containing-class, function-name, and a comma-delimited, and strictly-formatted string-list of its parameters--parameters which may only be of a primitive type or strings. This class is unrelated to {@link SimpleParamNameSignature}. Also contains utilities for creating this object from a &quot;string signature&quot; in the format:</p>

   <p><code>&quot;fully.qualified.package.ClassName#functionName(\&quot;all\&quot;, true, \&quot;parameters\&quot;, (byte)-112)&quot;</code></p>

   <p>There are two alternative formats, one in which the package name is not provided:</p>

   <p><code>&quot;ClassName#functionName(\&quot;all\&quot;, true, \&quot;parameters\&quot;, (byte)-112)&quot;</code></p>

   <p>and another in which only the function is provided:</p>

   <p><code>&quot;functionName(\&quot;all\&quot;, true, \&quot;parameters\&quot;, (byte)-112)&quot;</code></p>

   <p>In the two alternative signatures, default package or class values must be specified. If there are no parameters following the function name, it defaults to {@code "()"}.</p>

   <h3>Example: No defaults</h3>

   <p>A string signature where everything (the package, class, and function name) is provided.</p>

{@.codelet.and.out com.github.aliteralmind.codelet.examples.simplesig.SimpleMethodSigNoDefaults%eliminateCommentBlocksAndPackageDecl()}

   <h3>Example: Default classes</h3>

   <p>This demonstrates a string signature in which the (package and) class name is not specified. The <i>potential</i> classes, one in which the function <i>must</i> exist, are provided directly.</p>

{@.codelet.and.out com.github.aliteralmind.codelet.examples.simplesig.SimpleMethodSigWithClassDefaults%eliminateCommentBlocksAndPackageDecl()}

 * @since  0.1.0
 * @author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <a href="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</a>, <a href="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</a>
 **/
public class SimpleMethodSignature  {
   /**
      <p>Matches a validly formatted string-signature, as required by {@link #newFromStringAndDefaults(Class, Object, String, Class[], Appendable) newFromStringAndDefaults}. View the source-code for more documentation.</p>

      <p>Five named capture-groups:<ul>
         <li><b><code>package</code>:</b> The fully-qualified package-name, ending with a dot. When non-{@code null}, this is always followed by <code>className1</code>.</li>
         <li><b><code>className1</code>:</b> The name of the class that exists in <code>package</code>. When <code>package</code> is {@code null}, this is as well.</li>
         <li><b><code>className2</code>:</b> The class name existing in package <code>default_package</code> (parameter in {@code newFromStringAndDefaults}). <i>When non-{@code null}, <code>default_package</code> must be {@code null}. When both this and <code>className1</code> are {@code null}</i>, <code>default_classesInOrder</code> must be non-{@code null}.</li>
         <li><b><code>funcName</code>:</b> Always non-null.</li>
         <li><b><code>params</code>:</b> Always non-null, but may be the empty-string.</li>
      </ul>

    * @see  com.github.xbn.util.JavaRegexes#IDENTIFIER JavaRegexes#IDENTIFIER
    * @see  com.github.xbn.util.JavaRegexes#PACKAGE_NAME JavaRegexes#PACKAGE_NAME
    */
   public static final String STRING_SIGNATURE_REGEX = "^" +      //Line start
         "(?:(?:" +                                               //CrashIfZero or one of the following:
            "(?<package>" + JavaRegexes.PACKAGE_NAME + "\\.)" +   //  package-name followed by
                    //@since  0.1.2: FIXED: SimpleMethodSignature.STRING_SIGNATURE_REGEX incorrectly ommitted the literal dot between the package and class names.
               "(?<className1>" + JavaRegexes.IDENTIFIER + ")" +  //  class-name
         "|" +                                                    //OR
            "(?<className2>" + JavaRegexes.IDENTIFIER + "))#)?" + //  class-name (requires default_package)
         "(?<funcName>" + JavaRegexes.IDENTIFIER + ")" +          //Followed by function-name
         "\\((?<params>.*)\\)" +                                  //and all its parameters
      "$";                                                        //Line end

   private final Class<?>     returnTypeCls        ;
   private final Class<?>[]   containsFuncClsInOrdr;
   private final String       funcNm               ;
   private final String       paramStrList         ;
   private final TextAppenter dbgAptr              ;
   /**
   	<p>YYY</p>

    **/
   public SimpleMethodSignature(Class<?> return_typeCls, Class<?>[] containsFuncClasses_inOrder, String func_name, String param_strList, Appendable debugDest_ifNonNull)  {
      try  {
         if(containsFuncClasses_inOrder.length == 0)  {
            throw  new IllegalArgumentException("containsFuncClasses_inOrder has no elements.");
         }
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(containsFuncClasses_inOrder, "containsFuncClasses_inOrder", null, rx);
      }
      for(int i = 0; i < containsFuncClasses_inOrder.length; i++)  {
         if(containsFuncClasses_inOrder[i] == null)  {
            throw  new NullPointerException("containsFuncClasses_inOrder[" + i + "]");
         }
      }
      Objects.requireNonNull(return_typeCls, "return_typeCls");
      Objects.requireNonNull(func_name, "func_name");
      Objects.requireNonNull(param_strList, "param_strList");

      returnTypeCls = return_typeCls;
      containsFuncClsInOrdr = Arrays.copyOf(containsFuncClasses_inOrder, containsFuncClasses_inOrder.length);
      funcNm = func_name;
      paramStrList = param_strList;
      dbgAptr = NewTextAppenterFor.appendableSuppressIfNull(debugDest_ifNonNull);
   }
   public TextAppenter getDbgAptr()  {
      return  dbgAptr;
   }
   public Class<?> getReturnType()  {
      return  returnTypeCls;
   }
   public List<Object> getParamValueObjectList()  {
      return  SimpleMethodSignature.getObjectListFromCommaSepParamString(getParamStringList());
   }
   public Class<?> getMayContainFuncClass(int index)  {
      try  {
         return  containsFuncClsInOrdr[index];
      }  catch(ArrayIndexOutOfBoundsException abx)  {
         throw  new ArrayIndexOutOfBoundsException("index (" + index + ") is invalid given getMayContainFuncClassCount()=" + getMayContainFuncClassCount() + ".");
      }
   }

   /**
      <p>Execute the classes {@code main} function. This requires the {@code main} function to exist in {@link #getMayContainFuncClass(int) getMayContainFuncClass}{@code (0)}.</p>

    * @see  #invokeGetMainOutputNoExtraParams(String)
    */
   public void invokeMainNoExtraParams() throws NoSuchMethodException  {
      List<Object> paramValueList = getParamValueObjectList();
      Class<?>[] paramTypes = ReflectRtxUtil.getClassArrayFromObjects(paramValueList.toArray());

      Method mainFunc = getMethodFromParamTypes(paramTypes);

      new InvokeMethodWithRtx(mainFunc).
         sstatic().parameters(paramValueList.toArray()).invokeVoid();
   }
   /**
      <p>Execute the classes <a href="http://docs.oracle.com/javase/tutorial/getStarted/application/index.html#MAIN">{@code main} function</a> and get its console output. This requires the {@code main} function to exist in {@link #getMayContainFuncClass(int) getMayContainFuncClass}{@code (0)}.</p>

    * @see  #invokeMainNoExtraParams()
    */
   public String invokeGetMainOutputNoExtraParams(String app_descriptionNotClassName)  {
      List<Object> paramValueList = getParamValueObjectList();
//		Class<?>[] paramTypes = ReflectRtxUtil.getClassArrayFromObjects(paramValueList.toArray());

      String[] stringParams = ArrayUtil.getStringArrayOrNull(paramValueList.toArray(), NullContainer.BAD, "getParamValueObjectList()");
      return  InvokeMethodWithRtx.getApplicationOutput(getMayContainFuncClass(0),
         stringParams, app_descriptionNotClassName);
   }
/*
   public String[] getCmdLineParamsForPublicObjList()  {
      return  ArrayUtil.getStringArrayOrNull(paramValueObjList.toArray(), false, "paramValueObjList");
   }
 */
   public Method getMethod() throws NoSuchMethodException  {
      return  getMethodFromParamTypes(null);
   }
   public Method getMethodFromParamValueList(List<Object> paramValues_nullForSigDefault) throws NoSuchMethodException  {
      return  getMethodFromParamTypes(ReflectRtxUtil.getClassArrayFromObjects(paramValues_nullForSigDefault.toArray()));
   }
   /**
      <p>Get the method as specified in the {@code SimpleMethodSignature}, which must exist in one of the may-contain classes.</p>

    * @see  #getMayContainFuncClass(int)
    * @see  <code><a href="http://stackoverflow.com/questions/22876120/how-to-test-if-a-private-static-function-exists-in-a-class-without-having-to-ca">http://stackoverflow.com/questions/22876120/how-to-test-if-a-private-static-function-exists-in-a-class-without-having-to-ca</a></code>
    */
   public Method getMethodFromParamTypes(Class<?>[] paramClasses_nullForSigDefault) throws NoSuchMethodException  {
/*
      List<Class<?>> paramClassList = null;
      try  {
         paramClassList = ((paramClasses_nullForSigDefault != null)
         ?  paramClasses_nullForSigDefault
         :  getClassListFromObjects(getObjectListFromCommaSepParamString(getParamStringList())));
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(paramClasses_nullForSigDefault, "paramClasses_nullForSigDefault", null, rx);
      }
      Class<?>[] paramClasses = null;
      try  {
         paramClasses = paramClasses_nullForSigDefault.toArray(new Class<?>[paramClasses_nullForSigDefault.size()]);
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(paramClasses_nullForSigDefault, "paramClasses_nullForSigDefault", null, rx);
      }
 */
 		if(paramClasses_nullForSigDefault == null)  {
    		paramClasses_nullForSigDefault = ReflectRtxUtil.getClassArrayFromObjects(
            getObjectListFromCommaSepParamString(getParamStringList()).toArray());
      }

      if(getDbgAptr() != null)  {
         getDbgAptr().appentln("SimpleMethodSignature.getMethodFromParamTypes: paramClasses_nullForSigDefault: " + ReflectRtxUtil.getClassNames(null, paramClasses_nullForSigDefault, null, ", "));
      }

      Method mthd = null;
      for(int i = 0; i < getMayContainFuncClassCount(); i++)  {
         try  {
            if(getDbgAptr() != null)  {
               getDbgAptr().appentln("   Potential containing class " + (i+1) + " of " + getMayContainFuncClassCount() + ": " + getMayContainFuncClass(i).getName() + "...");
            }

            mthd = getMayContainFuncClass(i).getDeclaredMethod(getFunctionName(), paramClasses_nullForSigDefault);

            if(mthd.getReturnType() != getReturnType())  {
               throw  new NoSuchMethodException("Method found, but with unexpected return type. getReturnType()=" + getReturnType().getName() + ", method: " + mthd);
            }

            if(getDbgAptr() != null)  {
               getDbgAptr().appentln("   ...found");
            }

            return  mthd;
         }  catch(NoSuchMethodException nsmx)  {
            if(getDbgAptr() != null)  {
               getDbgAptr().appentln("   ...method not in class: " + nsmx);
            }
            //Using try-catch as the false condition is hopefully temporary.
            //See the stackoverflow question for an alternative.
         }
      }

      throw  new NoSuchMethodException("this=" + toString() + LINE_SEP + " - paramClasses_nullForSigDefault=" + ReflectRtxUtil.getClassNames(null, paramClasses_nullForSigDefault, null, ", "));
   }
   public int getMayContainFuncClassCount()  {
      return  containsFuncClsInOrdr.length;
   }
   public String getFunctionName()  {
      return  funcNm;
   }
   public String getParamStringList()  {
      return  paramStrList;
   }
   public String toString()  {
      return  appendToString(new StringBuilder()).toString();
   }
   public StringBuilder appendToString(StringBuilder to_appendTo)  {
      return  appendStringSig_ret_class_func_params(to_appendTo, true, true, true, true);
   }
   public String getStringSig_ret_class_func_params(boolean doInclude_returnType, boolean doInclude_class, boolean doInclude_funcName, boolean doInclude_params)  {
      return  appendStringSig_ret_class_func_params(new StringBuilder(), doInclude_returnType, doInclude_class, doInclude_funcName, doInclude_params).toString();
   }
   public StringBuilder appendStringSig_ret_class_func_params(StringBuilder to_appendTo, boolean doInclude_returnType, boolean doInclude_class, boolean doInclude_funcName, boolean doInclude_params)  {
      if(doInclude_returnType)  {
         to_appendTo.append(getReturnType().getName()).append(" ");
      }

      if(doInclude_class)  {
         if(containsFuncClsInOrdr.length == 1)  {
            to_appendTo.append(containsFuncClsInOrdr[0].getName());
         }  else  {
            to_appendTo.append("CLASS");
         }
      }

      if(doInclude_class  &&  doInclude_funcName)  {
            to_appendTo.append("#");
      }

      if(doInclude_funcName)  {
         to_appendTo.append(getFunctionName());
      }
      if(doInclude_params)  {
         to_appendTo.append("(").append(getParamStringList()).append(")");
      }

      if(doInclude_class  &&  containsFuncClsInOrdr.length > 1)  {
         to_appendTo.append(", where CLASS is one of: ").append(LINE_SEP);
         ReflectRtxUtil.appendClassNames(to_appendTo, " - ", containsFuncClsInOrdr, null, LINE_SEP);
      }
/*
      to_appendTo.append(LINE_SEP).append("paramClassList=[");

         ReflectRtxUtil.appendClassNames(to_appendTo,
            paramClassList.toArray(new Class<?>[paramClassList.size()]), ", ");

      to_appendTo.append("]").append(LINE_SEP).append("paramValueObjList=[");

         ReflectRtxUtil.appendClassNames(to_appendTo,
            ReflectRtxUtil.getClassArrayFromObjects(paramValueObjList.toArray()), ", ");

      to_appendTo.append("]");
 */
      return  to_appendTo;
   }
   /**
      <p>Splits a string-signature into its parts, for the classes <a href="http://docs.oracle.com/javase/tutorial/getStarted/application/index.html#MAIN">{@code main}</a> function (or any non-returning {@code void} function), and where the fully-qualified class is explicitely specified in the string (no defaults are used).</p>

    * @return  <code>{@link #newFromStringAndDefaults(Class, Object, String, Class[], Appendable) newFromStringAndDefaults}(Void.class, class_funcParamStringSignature, null, null, debugDest_ifNonNull)</code>
    */

   public static final SimpleMethodSignature getForMainFromStringSig(Object class_funcParamStringSignature, Appendable debugDest_ifNonNull) throws ClassNotFoundException, SimpleMethodSigFormatException  {
      return  newFromStringAndDefaults(Void.class, class_funcParamStringSignature, null, null, debugDest_ifNonNull);
   }
   /**
      <p>Splits a string-signature into its parts. An example string-signature is
      <br> &nbsp; &nbsp; {@code "ClassName#functionName(\"parameter\", \"list\", 1, (byte)-3, true)"}</p>

      <h3>Format requirements</h3>

      <p>{@code "functionName()"}</p>

      <p>A function with no parameters that exists in the default class. In all cases, when there are <b><u>no parameters</u></b>, the empty parentheses may be omitted: {@code "functionName"}.</p>

      <p>{@code "MyClass#functionName()"}</p>

      <p>A function that exists in {@code MyClass}, which is in the default package.</p>

      <p>{@code "com.something.me.MyClass#functionName()"}</p>

      <p>A function that exists in a specific class.</p>

      <p>{@code "com.something.me.MyClass#functionName(true, 1, \"hello\", ...)"}</p>

      <p>A function that exists in a specific class, with a particular set of {@linkplain #getParamValueObjectList() parameters}.</p>

    * @param  class_funcParamStringSignature  The string-signature. May not be null or empty, and must be formatted as specified above. Specifically, it must be matched by {@link #STRING_SIGNATURE_REGEX}.
    * @param  default_package  The default package to use when the string-signature <i>specifies a class-name but does not specify a package</i>. When the string-signature does not contain a package, and no default class is specified, this must be non-{@code null} and non-empty, must end with a dot ({@code '.'}), and must be the package in which the specified class <i>as specified in the string-signature</i> exists ({@code default_classesInOrder} must be {@code null}). <i>The class must exist in a package</i>.
    * @param  default_classesInOrder  The ordered set of classes to use when no class is specified in the string-signature. The function must exist in one of these classes. The search order is left-to-right (starting with element zero). When the class is specified in the string-signature, {@code default_classesInOrder} must be {@code null}. Otherwise, must be non-{@code null}, non-empty, and elements may not be {@code null}, and <i>should</i>  be unique. When non-{@code null}, {@code default_package} must be {@code null}.
    * @return  A non-{@code null} {@code SimpleMethodSigFormatException}.
    * @exception  ClassNotFoundException  If the class name, but not its package, is in the string-signature, and the class does not exist in the default package.
    * @exception  SimpleMethodSigFormatException  If {@code class_funcParamStringSignature} is invalidly-formatted.
    * @exception  IllegalArgumentStateException  If either<ul>
         <li>There is no package specified in the string-signature and {@code default_package} is {@code null}.</li>
         <li>There is no class name specified in the string-signature and {@code default_classesInOrder} is {@code null}.</li>
      </ul>
    * @see  #getForMainFromStringSig(Object, Appendable)
    */
   public static final SimpleMethodSignature newFromStringAndDefaults(Class<?> return_typeCls, Object class_funcParamStringSignature, String default_package, Class<?>[] default_classesInOrder, Appendable debugDest_ifNonNull) throws ClassNotFoundException  {
      TextAppenter dbgAptr = NewTextAppenterFor.appendableSuppressIfNull(debugDest_ifNonNull);
      if(dbgAptr != null)  {
         dbgAptr.appentln("SimpleMethodSignature.newFromStringAndDefaults:");
         dbgAptr.appentln(" - return_typeCls: " + return_typeCls.getName());
         dbgAptr.appentln(" - class_funcParamStringSignature: [" + class_funcParamStringSignature + "]");
         dbgAptr.appentln(" - default_package: [" + default_package + "]");
         dbgAptr.appent(" - default_classesInOrder:");
         if(default_classesInOrder != null)  {
            dbgAptr.appentln();
            for(int i = 0; i < default_classesInOrder.length; i++)  {
               dbgAptr.appentln("    - " + i + ": " + default_classesInOrder[i].getName());
            }
         }  else  {
            dbgAptr.appentln(" null");
         }
      }

      try  {
         if(!RegexUtil.resetGetMatcherCINullString(classFuncParamsMtchr, class_funcParamStringSignature.toString(), "class_funcParamStringSignature").matches())  {
            throw  new SimpleMethodSigFormatException(class_funcParamStringSignature, "Signature not matched by [" + classFuncParamsMtchr.pattern() + "]");
         }
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(class_funcParamStringSignature, "class_funcParamStringSignature", null, rx);
      }

      Class[] defaultClasses = null;

      //Element 3: Parameters
      String paramList = classFuncParamsMtchr.group("params");
      String funcName = classFuncParamsMtchr.group("funcName");

      String pkgNm = classFuncParamsMtchr.group("package");
      String clsNm = classFuncParamsMtchr.group("className1");
      if(clsNm == null)  {
         clsNm = classFuncParamsMtchr.group("className2");
      }

      if(clsNm == null)  {
         if(default_classesInOrder == null)  {
            throw  new IllegalArgumentStateException("The function's containing class is not in the string signature and there are no default classes. " + getParamDbg(class_funcParamStringSignature, default_package, default_classesInOrder));
         }

         defaultClasses = default_classesInOrder;

         if(default_package != null)  {
            throw  new IllegalArgumentStateException("Both default_package and default_classesInOrder are non-null. " + getParamDbg(class_funcParamStringSignature, default_package, default_classesInOrder));
         }

      }  else if(default_classesInOrder != null)  {
         throw  new IllegalArgumentStateException("The function's containing class provided in both string-signature and default_classesInOrder. " + getParamDbg(class_funcParamStringSignature, default_package, default_classesInOrder));
      }

      if(clsNm != null  &&  pkgNm == null)  {
         if(default_package == null  ||  default_package.length()  == 0)  {
            throw  new IllegalArgumentStateException("No package provided (or is the empty-string). " + getParamDbg(class_funcParamStringSignature, default_package, default_classesInOrder));
         }
         pkgNm = default_package;

      }  else if(pkgNm != null  &&  default_package != null)  {
         throw  new IllegalArgumentStateException("Package provided in both string-signature and default_package. (Package name found in sig: \"" + pkgNm + "\"). " + getParamDbg(class_funcParamStringSignature, default_package, default_classesInOrder));
      }

      if(pkgNm != null)  {

         try  {
            defaultClasses = new Class[] {Class.forName(pkgNm + clsNm)};
         }  catch(ClassNotFoundException cnfx)  {
            throw  new ClassNotFoundException("Attempting Class.forName(\"" + pkgNm + clsNm + "\"). " + getParamDbg(class_funcParamStringSignature, default_package, default_classesInOrder));
         }
      }

      SimpleMethodSignature sig = new SimpleMethodSignature(return_typeCls, defaultClasses, funcName, paramList, debugDest_ifNonNull);
      if(dbgAptr != null)  {
         dbgAptr.appentln("Returning SimpleMethodSignature: " + sig);
      }
      return  sig;
   }
      private static final String getParamDbg(Object class_funcParamStringSignature, String default_package, Class<?>[] default_classesInOrder)  {
         return  "class_funcParamStringSignature=\"" + class_funcParamStringSignature + "\", default_package=\"" + default_package + "\", default_classesInOrder=" + Arrays.toString(default_classesInOrder);
      }
      //Unused to-search strings, so matchers can be reset(s) instead of recreated
      private static final Matcher classFuncParamsMtchr = Pattern.compile(STRING_SIGNATURE_REGEX).matcher("");
   /**
      <p>Get the objects from the string-representation of a function's parameter list.</p>

    * @param  commaSep_varList  May not be {@code null}, each element must be separated by a <i>comma-space</i> ({@code ", "}), and each element must be formatted as required by {@link #getObjectFromString(String) getObjectFromString}{@code (s)}. Example value:
      <br> &nbsp; &nbsp; &nbsp; &nbsp; <code>&quot;\&quot;parameter\&quot;, \&quot;list\&quot;, 1, (byte)-3, true&quot;</code>
    * @return  A list of objects, where each element is the object represented by the corresponding element in {@code commaSep_varList}, in the same order as the exist in the string.
    * @see  <code><!-- GENERIC PARAMETERS FAIL IN @link --><a href="http://aliteralmind.com/docs/computer/programming/xbnjava/xbnjava-0.1.5/documentation/javadoc/com/github/xbn/lang/reflect/ReflectRtxUtil.html#getClassArrayFromObjects(O[])">getClassArrayFromObjects</a></code>
    * @see  #newFromStringAndDefaults(Class, Object, String, Class[], Appendable) newFromStringAndDefaults(cls,s,s,cls[])
    * @exception  IllegalArgumentException  If {@code commaSep_varList} is invalidly-formatted.
    */
   public static final List<Object> getObjectListFromCommaSepParamString(String commaSep_varList)  {
      if(commaSep_varList.length() == 0)  {
         //This used to be a static final EMPTY_ARRAY_LIST.
         //It was causing problems when being used in Codelet, because
         //JavaDoc is multi-threaded!
         return  new ArrayList<Object>(0);
      }
      String[] strVars = null;
      try  {
         strVars = commaSep_varList.split(", ");
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(commaSep_varList, "commaSep_varList", null, rx);
      }
      List<Object> objList = new ArrayList<Object>(strVars.length);
      for(int i = 0; i < strVars.length; i++)  {
         try  {
            objList.add(getObjectFromString(strVars[i]));
         }  catch(RuntimeException rx)  {
            throw  new IllegalArgumentException("Attempting to parse parameter element at index " + i + ": " + rx + ". Elements so far: " + Arrays.toString(objList.toArray()) + ". commaSep_varList=\"" + commaSep_varList + "\"");
         }
      }
      return  objList;
   }

   /**
      <p>Get a list of {@code Class}es, for each object in a list, as required when obtaining a method.</p>

    * @return  <code>{@link com.github.xbn.lang.reflect.ReflectRtxUtil ReflectRtxUtil}.{@link com.github.xbn.lang.reflect.ReflectRtxUtil#getClassListFromObjects(List) getClassListFromObjects}(objectList)</code>
   public static final List<Class<?>> getClassListFromObjects(List<?> objectList)  {
      return  ReflectRtxUtil.getClassListFromObjects(objectList);
   }
    */
   /**
   	<p>Get the object represented by a single string-representation of a single parameter. The only available types are the <a href="http://docs.oracle.com/javase/tutorial/java/nutsandbolts/datatypes.html">eight primitives</a> and non-{@code null} strings ({@code null} is not possible).</p>

   	@param  var_asString  May not be {@code null} or empty, and must conform to the following:

   	<TABLE ALIGN="center" WIDTH="100%" BORDER="1" CELLSPACING="0" CELLPADDING="4" BGCOLOR="#EEEEEE"><TR ALIGN="center" VALIGN="middle">
   		<TD><b><u>Type</u></b></TD>
   		<TD><b><u>Examples</u></b></TD>
   		<TD><b><u>Notes</u></b></TD>
   	</TR><TR>
   		<TD><b>{@link java.lang.Boolean Boolean}</b></TD>
   		<TD>{@code true} or {@code false}</TD>
   		<TD>&nbsp;</TD>
   	</TR><TR>
   		<TD><b>{@link java.lang.Character Character}</b></TD>
   		<TD>{@code 'x'}</TD>
   		<TD>Must start and end with a single quote, and contain exactly one character between it. For the single-quote, use three single quotes: {@code "'''"} (do not escape it).</TD>
   	</TR><TR>
   		<TD><b>{@link java.lang.Byte Byte}</b></TD>
   		<TD>{@code (byte)3}</TD>
   		<TD>Must contain the explicit cast and be an {@link java.lang.Byte#parseByte(String, int) appropriate value} for a {@code byte}. <i>The plus-sign, indicating a positive number, is not allowed for any numeric type.</i></TD>
   	</TR><TR>
   		<TD><b>{@link java.lang.Short Short}</b></TD>
   		<TD>{@code (short)-15}</TD>
   		<TD>Must contain the explicit cast and be an {@link java.lang.Short#parseShort(String, int) appropriate value} for a {@code short}</TD>
   	</TR><TR>
   		<TD><b>{@link java.lang.Integer Integer}</b></TD>
   		<TD>{@code -15}</TD>
   		<TD>Must be an {@link java.lang.Integer#parseInt(String, int) appropriate value} for an {@code int}</TD>
   	</TR><TR>
   		<TD><b>{@link java.lang.Long Long}</b></TD>
   		<TD>{@code 3300012L}</TD>
   		<TD>Must be followed by a capital {@code 'L'} and be an {@link java.lang.Long#parseLong(String, int) appropriate value} for an {@code long}</TD>
   	</TR><TR>
   		<TD><b>{@link java.lang.Float Float}</b></TD>
   		<TD>{@code 0.003f}</TD>
   		<TD>Must be followed by a lowercase {@code 'f'}, contain at least one digit on both sides of the decimal, and be an {@link java.lang.Float#valueOf(java.lang.String) appropriate value} for an {@code float}. <i>For both {@code float} and {@code double}, only digits (as matched by the regular expression {@code "\d"}) are allowed. Hex numbers, exponenents, and special values such as {@code NaN} or {@code Infinity} are not allowed.</i></TD>
   	</TR><TR>
   		<TD><b>{@link java.lang.Double Double}</b></TD>
   		<TD>{@code -3.8d}</TD>
   		<TD>Must be followed by a lowercase {@code 'd'}, contain at least one digit on both sides of the decimal, and be an {@link java.lang.Double#valueOf(java.lang.String) appropriate value} for an {@code double}</TD>
   	</TR><TR>
   		<TD><b>{@link java.lang.String String}</b></TD>
   		<TD>{@code "Hello there!"}</TD>
   		<TD>Must be non-{@code null}, surrounded by double-quotes, and may not contain a comma ({@code ','}), double-quote ({@code '"'}), or ampersand ({@code '&amp;'}). When these characters are needed, use their respective html entity codes: {@code "&amp;#44;"}, {@code "&amp;quot"}, and {@code "&amp;amp;"}.</TD>
   	</TR></TABLE>
    **/
   public static final Object getObjectFromString(String var_asString)  {
      //Remaining possibilites: BOOLEAN, CHAR, BYTE, SHORT, INT, LONG, FLOAT, DOUBLE, STRING
      try  {
         if(var_asString.charAt(0) == '(')  {
            if(var_asString.charAt(1) == 'b')  {
               return  getByteFromString(var_asString);
            }  else if(var_asString.charAt(1) == 's')  {
               return  getShortFromString(var_asString);
            }  else  {
               throw  new IllegalArgumentException("var_asString=\"" + var_asString + "\". Starts with '(', but is not properly formatted for a byte or short.");
            }
         }
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(var_asString, "var_asString", null, rx);
      }

      //Remaining possibilites: BOOLEAN, CHAR, INT, LONG, FLOAT, DOUBLE, STRING

      if(var_asString.charAt(0) == '\'')  {
         if(var_asString.length() != 3  ||  var_asString.charAt(2) != '\'')  {
            throw  new IllegalArgumentException("var_asString=\"" + var_asString + "\". Starts with a single-quote (\"'\"), but is not properly formatted for a character (for the literal single-quote, use \"'''\").");
         }
         return  new Character(var_asString.charAt(1));
      }

      //Remaining possibilites: BOOLEAN, INT, LONG, FLOAT, DOUBLE, STRING

      if(var_asString.equals("true")  ||  var_asString.equals("false"))  {
         return  new Boolean(var_asString);
      }

      //Remaining possibilites: INT, LONG, FLOAT, DOUBLE, STRING

      if(RegexUtil.resetGetMatcherCINullString(longIntMtchr, var_asString, "var_asString").matches())  {
         boolean isLong = (var_asString.charAt(var_asString.length() - 1) == 'L');
         try  {
            if(isLong)  {
               return  new Long(var_asString.substring(0, (var_asString.length() - 1)));
            }  else  {
               return  new Integer(var_asString);
            }
         }  catch(NumberFormatException nfx)  {
            throw  new IllegalArgumentException(getNumRangeErrMsg(var_asString, (isLong?"long":"int")));
         }
      }

      //Remaining possibilites: FLOAT, DOUBLE, STRING

      if(RegexUtil.resetGetMatcherCINullString(floatDoubleMtchr, var_asString, "var_asString").matches())  {
         boolean isFloat = (var_asString.charAt(var_asString.length() - 1) == 'f');
         try  {
            if(isFloat)  {
            return  new Float(var_asString.substring(0, (var_asString.length() - 1)));
         }  else  {
            return  new Double(var_asString.substring(0, (var_asString.length() - 1)));
            }
         }  catch(NumberFormatException nfx)  {
            throw  new IllegalArgumentException(getNumRangeErrMsg(var_asString, (isFloat?"float":"double")));
         }
      }

      //Remaining possibilites: STRING
      if(var_asString.charAt(0) == '"')  {
         if(var_asString.length() < 2  ||  var_asString.charAt(var_asString.length() - 1) != '"')  {
            throw  new IllegalArgumentException("var_asString=\"" + var_asString + "\". Starts with '\"', but is not properly formatted for a string.");
         }
         var_asString = var_asString.substring(1, (var_asString.length() - 1));
         if(var_asString.indexOf("\"") != -1  ||  var_asString.indexOf(",") != -1)  {
            //@since  0.1.2
            //||  var_asString.indexOf("&") != -1)  {

            throw  new IllegalArgumentException("var_asString=\"" + var_asString + "\" contains a literal comma (','), double-quote ('\"'), or ampersand ('&'). Escape it with \"&#44;\", \"&quot\", or \"&amp;\".");
         }
         return  var_asString.replace("&#44;", ",").replace("&quot;", "\"").replace("&amp;", "&");
      }

      throw  new IllegalArgumentException("var_asString=\"" + var_asString + "\". Unknown type.");
   }
      //Unused to-search strings, so matchers can be reset(s) instead of recreated
      private static final Matcher byteMtchr = Pattern.compile("^\\(byte\\)-?\\d+$").matcher("");
      private static final Matcher shortMtchr = Pattern.compile("^\\(short\\)-?\\d+$").matcher("");
      private static final Matcher longIntMtchr = Pattern.compile("^-?\\d+L?$").matcher("");
      private static final Matcher floatDoubleMtchr = Pattern.compile("^-?\\d+\\.\\d+[fd]$").matcher("");
   private static final Byte getByteFromString(String var_asString)  {
      if(!RegexUtil.resetGetMatcherCINullString(byteMtchr, var_asString, "var_asString").matches())  {
         throw  new IllegalArgumentException("var_asString=\"" + var_asString + "\". Starts with \"(b\", but is not properly formatted for a byte.");
      }
      try  {
         return  new Byte(Byte.parseByte(var_asString.substring(var_asString.indexOf(")") + 1)));
      }  catch(NumberFormatException nfx)  {
         throw  new IllegalArgumentException(getNumRangeErrMsg(var_asString, "byte"));
      }
   }
   private static final Short getShortFromString(String var_asString)  {
      if(!RegexUtil.resetGetMatcherCINullString(shortMtchr, var_asString, "var_asString").matches())  {
         throw  new IllegalArgumentException("var_asString=\"" + var_asString + "\". Starts with \"(s\", but is not properly formatted for a short.");
      }
      try  {
         return  new Short(Short.parseShort(var_asString.substring(var_asString.indexOf(")") + 1)));
      }  catch(NumberFormatException nfx)  {
         throw  new IllegalArgumentException(getNumRangeErrMsg(var_asString, "short"));
      }
   }
   private static final String getNumRangeErrMsg(String var_asString, String type)  {
      return  "var_asString=\"" + var_asString + "\". Appears to be a " + type + ", but may be out of range.";
   }
}
