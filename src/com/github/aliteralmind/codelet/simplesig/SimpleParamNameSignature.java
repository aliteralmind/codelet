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
package  com.github.aliteralmind.codelet.simplesig;
   import  com.github.xbn.lang.CrashIfObject;
   import  com.github.xbn.util.JavaRegexes;
   import  com.google.common.base.Joiner;
   import  java.lang.reflect.Member;
   import  java.util.ArrayList;
   import  java.util.Collections;
   import  java.util.List;
   import  java.util.Objects;
   import  java.util.regex.Matcher;
   import  java.util.regex.Pattern;
/**
   <p>A method or constructor signature, using only {@linkplain java.lang.Class#getSimpleName() simple} parameter-type names--this can be matched by a {@code SimpleParamSigSearchTerm}. This class is unrelated to {@link SimpleMethodSignature}.</p>

   @see  SimpleParamSigSearchTerm
   @since  0.1.0
   @author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <a href="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</a>, <a href="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</a>
 **/
public abstract class SimpleParamNameSignature implements Comparable<SimpleParamNameSignature>  {
   private static final Matcher IDENTIFIER_MTCHR = Pattern.compile(JavaRegexes.IDENTIFIER).matcher("");
   private final Member cnstrOrMthd;
   private final List<String> typeList;
   private final String noParens;
   private final String withParens;
   /**
      <p>Create a new instance from a constructor or method, and its parameter class types.</p>

      <p>Equal to
      <br/> &nbsp; &nbsp; <code>{@link #SimpleParamNameSignature(Member, List) this}(false, cnstr_method,
      <br/> &nbsp; &nbsp; SimpleParamNameSignature.{@link #getSimpleNameListFromClasses(Class[]) getSimpleNameListFromClasses}(param_types))</code></p>
    **/
   public SimpleParamNameSignature(Member cnstr_method, Class<?>[] param_types)  {
      this(false, cnstr_method,
         SimpleParamNameSignature.getSimpleNameListFromClasses(param_types));
   }
   /**
      <p>Create a new instance from a method or constructor, and an array of its parameter simple names.</p>

      <p>This sets<ol>
         <li>{@link #getParamNameList() getParamNameList}{@code ()} to an immutable string-list of all parameter type names.</li>
         <li>{@link #getWithParens() getWithParens}{@code ()} to a comma-delimited string of the each type's {@linkplain java.lang.Class#getSimpleName() simple names}, in the same order as in {@code types}, surrounded by parentheses.</li>
         <li>{@link #getNoParens() getNoParens}{@code ()} to the same comma-delimited list, with no paretheses.</li>
      </ol></p>

      @see  java.lang.Class#getSimpleName()
    **/
   public SimpleParamNameSignature(Member cnstr_method, List<String> type_list)  {
      this(false, cnstr_method, type_list);
      Objects.requireNonNull(cnstr_method, "cnstr_method");
      try  {
         for(int i = 0; i < type_list.size(); i++)  {
            try  {
               if(!IDENTIFIER_MTCHR.reset(type_list.get(i)).matches())  {
                  throw  new IllegalArgumentException("type_list.get(" + i + ") (\"" + type_list.get(i) + "\") is not a valid Java identifier.");
               }
            }  catch(RuntimeException rx)  {
               throw  CrashIfObject.nullOrReturnCause(type_list.get(i), "type_list.get(" + i + ")", null, rx);
            }
         }
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(type_list, "type_list", null, rx);
      }
   }
      private SimpleParamNameSignature(boolean ignored, Member cnstr_method, List<String> type_list)  {
         try  {
            //Does throw NPX if null:
            typeList = Collections.unmodifiableList(type_list);
         }  catch(RuntimeException rx)  {
            throw  CrashIfObject.nullOrReturnCause(type_list, "type_list", null, rx);
         }
         cnstrOrMthd = cnstr_method;
         noParens = Joiner.on(", ").join(typeList);
         withParens = "(" + noParens + ")";
      }
   /**
      <p>The method or constructor having these parameters.</p>

      @return  A non-{@code null} {@linkplain java.lang.reflect.Method method} or {@linkplain java.lang.reflect.Constructor constructor} object.
    **/
   public Member getMember()  {
      return  cnstrOrMthd;
   }
   /**
      <p>List of all parameter-type simple names.</p>

      @return  An immutable list of all parameter-types, in the same order as they exist in the consructor or function. If no parameters, this is an empty list.
      @see  #getWithParens()
      @see  #SimpleParamNameSignature(Member, List)
      @see  java.lang.Class#getSimpleName()
    **/
   public List<String> getParamNameList()  {
      return  typeList;
   }
   /**
      <p>Comma-delimited string of all parameter-type simple names, excluding the surrounding parentheses.</p>

      @return  A non-{@code null}, comma-delimited string of all simple type names, in the same order as declared, with a single space between each comma.
      @see  #getWithParens()
    **/
   public String getNoParens()  {
      return  noParens;
   }
   /**
      <p>Comma-delimited string of all parameter-type simple names, listed in the same order as declared, including the surrounding parentheses.</p>

      @return  A non-{@code null}, comma-delimited string of all simple type names, in the same order as declared, with a single space between each comma.
      @see  #getParamNameList()
      @see  #getNoParens()
      @see  #toString()
    **/
   public String getWithParens()  {
      return  withParens;
   }
   /**
      <p>If a method: its name, if a constructor: the empty string ({@code ""}).</p>
    **/
   public abstract String getMethodName();
   /**
      @return   <code>{@link #getMethodName() getMethodName}() + {@link #getWithParens() getWithParens}()</code>
    **/
   public String toString()  {
      return  getMethodName() + getWithParens();
   }
   /**
      @return  <code>{@link #getWithParens() getWithParens}().compareTo(to_compareTo.getWithParens())</code> &nbsp; &nbsp; <i>(This does not need to be overriden to include the constructor/function name. This is only for sorting parameter-lists within each constructor/method.)</i>
    **/
   public int compareTo(SimpleParamNameSignature to_compareTo)  {
      return  getWithParens().compareTo(to_compareTo.getWithParens());
   }
   /**
      <p>Create a list of all simple names from a class array.</p>

      @param  types  May not be {@code null}.
      @return  A non-null list having the same length as {@code types}.
      @see  #SimpleParamNameSignature(Member, Class[])
    **/
   public static final List<String> getSimpleNameListFromClasses(Class<?>[] types)  {
      List<String> typeList = null;
      try  {
         typeList = new ArrayList<String>(types.length);
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(types, "types", null, rx);
      }
      for(Class<?> type : types)  {
         typeList.add(type.getSimpleName());
      }
      return  typeList;
   }
}
