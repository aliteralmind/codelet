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
   import  com.github.xbn.lang.reflect.RTNoSuchMethodException;
   import  com.github.xbn.lang.reflect.Declared;
   import  com.github.xbn.lang.CrashIfObject;
   import  com.github.xbn.list.ImmutableValues;
   import  com.github.xbn.list.MapUtil;
   import  com.github.xbn.list.SortListValues;
   import  java.lang.reflect.Constructor;
   import  java.lang.reflect.Method;
   import  java.util.ArrayList;
   import  java.util.Collections;
   import  java.util.List;
   import  java.util.Map;
   import  java.util.TreeMap;
   import  static com.github.xbn.lang.XbnConstants.*;
/**
   <P>All constructors and methods in a class.</P>

   @since  0.1.0
   @author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
 **/
public class AllSimpleParamSignatures  {
   private final Class<?> containing;
   private final List<ConstructorSimpleParamSig>  cnstrList;
   private final Map<String,List<MethodSimpleParamSig>> methodMap;
   /**
      <P>Create a new instance from a class.</P>

      <P>Sets<OL>
         <LI>{@link #getConstructorList() getConstructorList}{@code ()} to an {@linkplain java.util.Collections#unmodifiableList(List) immutable} version of
         <BR> &nbsp; &nbsp; <CODE>AllSimpleParamSignatures.{@link AllSimpleParamSignatures#newConstructorList(Class, Declared, Sorted) newConstructorList}(containing_cls, declared, {@link Sorted Sorted}.{@link Sorted#YES YES})</CODE></LI>
         <LI>{@link #getMethodMap() getMethodMap}{@code ()} to an {@linkplain java.util.Collections#unmodifiableMap(Map) immutable} version of
         <BR> &nbsp; &nbsp; <CODE>AllSimpleParamSignatures.{@link #newMethodMap(Class, Declared, SortListValues, ImmutableValues) newMethodMap}(containing_cls, declared, {@link com.github.xbn.list.SortListValues}.{@link com.github.xbn.list.SortListValues#ORIGINAL ORIGINAL}, {@link com.github.xbn.list.ImmutableValues ImmutableValues}.{@link com.github.xbn.list.ImmutableValues#YES YES})</CODE></LI>
      </OL></P>

      @param  containing_cls  The class containing the methods. May not be {@code null} and <I>should</I> be the class whose methods are in {@code map}. Get with {@link #getContainingClass() getContainingClass}{@code ()}. Get with {@link #getMethodMap() getMethodMap}{@code ()}.
    **/
   public AllSimpleParamSignatures(Class<?> containing_cls, Declared declared)  {
      containing = containing_cls;

      List<ConstructorSimpleParamSig> cnstrList2 = AllSimpleParamSignatures.
         newConstructorList(containing_cls, declared, Sorted.YES);
      Map<String,List<MethodSimpleParamSig>> methodMap2 = AllSimpleParamSignatures.
         newMethodMap(containing_cls, declared, SortListValues.ORIGINAL, ImmutableValues.YES);

      //Both throw NPX if null (doesn't apply, just FYI)
      cnstrList = Collections.<ConstructorSimpleParamSig>unmodifiableList(cnstrList2);
      methodMap = Collections.<String,List<MethodSimpleParamSig>>unmodifiableMap(methodMap2);
   }
   /**
      <P>All constructors.</P>

      @return  A non-{@code null}, immutable list of all constructors in the {@link #getContainingClass() class}.
      @see  #getMethodMap()
      @see  #AllSimpleParamSignatures(Class, Declared)
    **/
   public List<ConstructorSimpleParamSig> getConstructorList()  {
      return  cnstrList;
   }
   /**
      <P>All methods.</P>

      @return  A non-{@code null}, immutable map of all methods in the {@link #getContainingClass() class}.
      @see  #getConstructorList()
      @see  #AllSimpleParamSignatures(Class, Declared)
      @see  #getMethodListForNameCrashIfNone(String)
    **/
   public Map<String,List<MethodSimpleParamSig>> getMethodMap()  {
      return  methodMap;
   }
   /**
      <P>Get the methods with a name, or crash if no methods have that name.</P>

      @return  <CODE>{@link #getMethodMap() getMethodMap}().{@link java.util.Map#get(Object) get}(name)</CODE>
      @exception  RTNoSuchMethodException  If no methods have the name {@code name}.
    **/
   public List<MethodSimpleParamSig> getMethodListForNameCrashIfNone(String name)  {
      List<MethodSimpleParamSig> match = getMethodMap().get(name);
      if(match == null)  {
         throw  new RTNoSuchMethodException("name=\"" + name + "\"");
      }
      return  match;
   }
   /**
      <P>The class containing the constructors and methods.</P>

      @see  #AllSimpleParamSignatures(Class, Declared)
    **/
   public Class<?> getContainingClass()  {
      return  containing;
   }
   /**
      @return  <CODE>{@link #appendToString(StringBuilder) appendToString}(new StringBuilder()).toString()</CODE>
    **/
   public String toString()  {
      return  appendToString(new StringBuilder()).toString();
   }
   /**
      <P>A summary of all constructors and functions.</P>

      @param  to_appendTo May not be {@code null}.
      @see  #toString()
    **/
   public StringBuilder appendToString(StringBuilder to_appendTo)  {
      try  {
         to_appendTo.append(getContainingClass().getName()).append(": ").append("constructors=" + getConstructorList().size()).append(", ");
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(to_appendTo, "to_appendTo", null, rx);
      }

      int i = 0;
      int sizeMinus1 = getMethodMap().size() - 1;
      for (Map.Entry<String,List<MethodSimpleParamSig>> entry : methodMap.entrySet())  {
         to_appendTo.append(entry.getKey()).append("=").append(entry.getValue().size());
         if(i < sizeMinus1)  {
            to_appendTo.append(", ");
         }
      }

      return  to_appendTo;
   }
   /**
      @return  <CODE>{@link #appendFullToString(StringBuilder) appendFullToString}(new StringBuilder()).toString()</CODE>
    **/
   public String fullToString()  {
      return  appendFullToString(new StringBuilder()).toString();
   }
   /**
      <P>A full listing of all constructors and functions.</P>

      @param  to_appendTo May not be {@code null}.
      @see  #fullToString()
    **/
   public StringBuilder appendFullToString(StringBuilder to_appendTo)  {
      try  {
         to_appendTo.append(getContainingClass().getName()).append(":").append(LINE_SEP).append("Constructors (").append(getConstructorList().size()).append("):").append(LINE_SEP);
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(to_appendTo, "to_appendTo", null, rx);
      }

      AllSimpleParamSignatures.appendToStringForAllListsInList(to_appendTo, " - ", getConstructorList(), LINE_SEP);

      to_appendTo.append(LINE_SEP).append("All methods:").append(LINE_SEP);

      int i = 0;
      int sizeMinus1 = getMethodMap().size() - 1;
      for (Map.Entry<String,List<MethodSimpleParamSig>> entry : methodMap.entrySet())  {
         List<MethodSimpleParamSig> value = entry.getValue();
         if(value.size() == 1)  {
            to_appendTo.append(" - ").append(value.get(0)).append(LINE_SEP);

         }  else  {
            to_appendTo.append(" - ").append(entry.getKey()).
               append(" (").append(value.size()).append(")").append(LINE_SEP);
            AllSimpleParamSignatures.appendToStringForAllListsInList(to_appendTo, "    - ", value, LINE_SEP);
            to_appendTo.append(LINE_SEP);
         }
      }
      return  to_appendTo;
   }
   /**
      <P>For displaying all parameter-lists in a list.</P>

      @return  <CODE>{@link #appendToStringForAllListsInList(StringBuilder, String, List, String) appendToStringForAllListsInArray}((new StringBuilder()), prefix, param_listList, between).toString()</CODE>
    **/
   public static final String toStringForAllListsInList(String prefix, List<? extends SimpleParamNameSignature> param_listList, String between)  {
      return  appendToStringForAllListsInList((new StringBuilder()), prefix, param_listList, between).toString();
   }
   /**
      <P>For displaying all parameter-lists in a list.</P>

      @return  <CODE>{@link #appendToStringForAllListsInArray(StringBuilder, String, SimpleParamNameSignature[], String) appendToStringForAllListsInArray}(new StringBuilder(), prefix, methods, between).toString()</CODE>
    **/
   public static final StringBuilder appendToStringForAllListsInList(StringBuilder to_appendTo, String prefix, List<? extends SimpleParamNameSignature> param_listList, String between)  {
      try  {
         return  appendToStringForAllListsInArray(to_appendTo, prefix,
            param_listList.toArray(new SimpleParamNameSignature[param_listList.size()]),
            between);
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(param_listList, "param_listList", null, rx);
      }
   }
   /**
      <P>For displaying all parameter-lists in an array.</P>

      @return  <CODE>{@link #appendToStringForAllListsInArray(StringBuilder, String, SimpleParamNameSignature[], String) appendToStringForAllListsInArray}(new StringBuilder(), prefix, methods, between).toString()</CODE>
    **/
   public static final String toStringForAllListsInArray(String prefix, SimpleParamNameSignature[] param_lists, String between)  {
      return  appendToStringForAllListsInArray(new StringBuilder(), prefix, param_lists, between).toString();
   }
   /**
      <P>For displaying all parameter-lists in an array.</P>

      @param  to_appendTo  May not be {@code null}.
      @param  prefix  What to print before each method. Setting this to {@code null} is the same as setting it to the empty-string ({@code ""}).
      @param  param_lists  May not be {@code null}, and no element may be {@code null}.
      @param  between  What to print between each method. <I>Should</I> not be {@code null} or empty.
    **/
   public static final StringBuilder appendToStringForAllListsInArray(StringBuilder to_appendTo, String prefix, SimpleParamNameSignature[] param_lists, String between)  {
      int sizeMinus1 = -1;
      try  {
         sizeMinus1 = param_lists.length - 1;
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(param_lists, "param_lists", null, rx);
      }

      if(prefix == null)  {
         prefix = "";
      }

      int i = 0;
      try  {
         for(; i < param_lists.length; i++)  {
            try  {
               to_appendTo.append(prefix).append(param_lists[i].toString());
            }  catch(RuntimeException rx)  {
               throw  CrashIfObject.nullOrReturnCause(to_appendTo, "to_appendTo", null, rx);
            }
            if(i < sizeMinus1)  {
               to_appendTo.append(between);
            }
         }
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(param_lists[i], "param_lists[i]", null, rx);
      }
      return  to_appendTo;
   }
   /**
      <P>Create a new list of all constructors in a class.</P>

      @param  containing_cls  May not be {@code null}.
      @param  declared  If {@link com.github.xbn.lang.reflect.Declared Declared}.{@link com.github.xbn.lang.reflect.Declared#YES YES}, then {@linkplain java.lang.Class#getDeclaredMethods() declared} methods are retrieved. If {@link com.github.xbn.lang.reflect.Declared#NO NO}, {@linkplain java.lang.Class#getMethods() non-declared}.
      @param  sort  If {@link com.github.xbn.list.SortListValues#ORIGINAL ORIGINAL} or {@link com.github.xbn.list.SortListValues#DUPLICATE DUPLICATE}, then the returned list is sorted.
    **/
   public static final List<ConstructorSimpleParamSig> newConstructorList(Class<?> containing_cls, Declared declared, Sorted sort)  {
      Constructor[] cnstrs = null;
      try  {
         cnstrs = (declared.isYes() ? containing_cls.getDeclaredConstructors()
            :  containing_cls.getConstructors());
      }  catch(RuntimeException rx)  {
         CrashIfObject.nnull(containing_cls, "containing_cls", null);
         throw  CrashIfObject.nullOrReturnCause(declared, "declared", null, rx);
      }
      List<ConstructorSimpleParamSig> list = new ArrayList<ConstructorSimpleParamSig>(cnstrs.length);
      for(Constructor cnstr : cnstrs)  {
         list.add(new ConstructorSimpleParamSig(cnstr));
      }

      try  {
         if(sort.isYes())  {
            Collections.<ConstructorSimpleParamSig>sort(list);
         }

         return  list;
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(sort, "sort", null, rx);
      }
   }
   /**
      <P>Create a new map of all methods in a class.</P>

      @param  containing_cls  May not be {@code null}.
      @param  declared  If {@link com.github.xbn.lang.reflect.Declared Declared}.{@link com.github.xbn.lang.reflect.Declared#YES YES}, then {@linkplain java.lang.Class#getDeclaredMethods() declared} methods are retrieved. If {@link com.github.xbn.lang.reflect.Declared#NO NO}, {@linkplain java.lang.Class#getMethods() non-declared}.
      @return  A non-null map containing all methods. This ends by returning
      <BR> &nbsp; &nbsp; <CODE>{@link com.github.xbn.list.MapUtil MapUtil}.&lt;MethodSimpleParamSig,String&gt;{@link com.github.xbn.list.MapUtil#getWithModifiedListValues(Map, SortListValues, ImmutableValues) getWithModifiedListValues}(<I>[the-map]</I>, sort_lists, immutable_lists)</CODE>
    **/
   public static final Map<String,List<MethodSimpleParamSig>> newMethodMap(Class<?> containing_cls, Declared declared, SortListValues sort_lists, ImmutableValues immutable_lists)  {
      Method[] methods = null;
      try  {
         methods = (declared.isYes() ? containing_cls.getDeclaredMethods()
            :  containing_cls.getMethods());
      }  catch(RuntimeException rx)  {
         CrashIfObject.nnull(containing_cls, "containing_cls", null);
         throw  CrashIfObject.nullOrReturnCause(declared, "declared", null, rx);
      }
      Map<String,List<MethodSimpleParamSig>> map = new TreeMap<String,List<MethodSimpleParamSig>>();
      for(Method m : methods)  {
         String name = m.getName();
         if(!map.containsKey(name))  {
            List<MethodSimpleParamSig> list = new ArrayList<MethodSimpleParamSig>(3);
            list.add(new MethodSimpleParamSig(m));
            map.put(name, list);
         }  else  {
            map.get(name).add(new MethodSimpleParamSig(m));
         }
      }

      return  MapUtil.<MethodSimpleParamSig,String>getWithModifiedListValues(map, sort_lists, immutable_lists);
   }
}
