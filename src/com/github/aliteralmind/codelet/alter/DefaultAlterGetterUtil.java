/*license*\
   Codelet

   Copyright (C) 2014, Jeff Epstein (aliteralmind __DASH__ github __AT__ yahoo __DOT__ com)

   This software is dual-licensed under the:
   - Lesser General Public License (LGPL) version 3.0 or, at your option, any later version;
   - Apache Software License (ASL) version 2.0.

   Either license may be applied at your discretion. More information may be found at
   - http://en.wikipedia.org/wiki/Multi-licensing.

   The text of both licenses is available in the root directory of this project, under the names "LICENSE_lgpl-3.0.txt" and "LICENSE_asl-2.0.txt". The latest copies may be downloaded at:
   - LGPL 3.0: https://www.gnu.org/licenses/lgpl-3.0.txt
   - ASL 2.0: http://www.apache.org/licenses/LICENSE-2.0.txt
\*license*/
package  com.github.aliteralmind.codelet.alter;
   import  com.github.aliteralmind.codelet.CodeletInstance;
   import  com.github.aliteralmind.codelet.CodeletType;
   import  com.github.xbn.linefilter.TextLineAlterer;
   import  com.github.xbn.lang.CrashIfObject;
   import  java.util.ArrayList;
   import  java.util.Arrays;
   import  java.util.Iterator;
   import  java.util.LinkedHashMap;
   import  java.util.List;
   import  java.util.Map;
   import  java.util.NoSuchElementException;
   import  static com.github.aliteralmind.codelet.CodeletBaseConfig.*;
/**
   <P>Utilities related to default alterers.</P>

   @see  com.github.aliteralmind.codelet.CodeletBaseConfig#DEFAULT_ALTERERS_CLASS_NAME
   @author  Copyright (C) 2014, Jeff Epstein, dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
 **/
public class DefaultAlterGetterUtil  {
   /**
      <P>Get a default alterer from its name.</P>

      @param  instance_forTypeOnly  May not be {@code null}.
      @param  map_keyName  Must be an existing key in the map.
      @return  <CODE>{@link #getMap(CodeletType) getMap}(instance_forTypeOnly.{@link CodeletInstance#getType() getType}()).{@link java.util.LinkedHashMap#get(Object) get}(map_keyName)</CODE>
      @exception  NoSuchElementException  If {@code map_keyName} is not a key in the map.
    **/
   public static final TextLineAlterer get(CodeletInstance instance_forTypeOnly, String map_keyName, Appendable debugDest_ifNonNull)  {
      TextLineAlterer alterer = null;
      try  {
         alterer = getMap(instance_forTypeOnly.getType()).get(map_keyName);
      }  catch(RuntimeException rx)  {
         throw  CrashIfObject.nullOrReturnCause(instance_forTypeOnly, "instance_forTypeOnly", null, rx);
      }
      if(alterer == null)  {
         throw  new NoSuchElementException("instance_forTypeOnly.getType()=" + instance_forTypeOnly.getType() + ", getDefaultAlterGetter():" + getDefaultAlterGetter().getClass().getName() + ", map_keyName=\"" + map_keyName + "\"");
      }
      try  {
         alterer = (TextLineAlterer)alterer.getObjectCopy();
         alterer.setDebug(debugDest_ifNonNull, (debugDest_ifNonNull != null));
      }  catch(RuntimeException rx)  {
         throw  new DefaultAlterGetterException("Attempting to duplicate the alterer and then set the debugging object. instance_forTypeOnly.getType()=" + instance_forTypeOnly.getType() + ", map_keyName=\"" + map_keyName + "\", debugDest_ifNonNull=" + debugDest_ifNonNull, rx);
      }
      return  alterer;
   }
   /**
      <P>Get the map of default alterers.</P>

      @param  needed_defaultAlterType  May not be {@code null}.
      @return  If {@code needed_defaultAlterType} is<UL>
         <LI>{@link CodeletType#SOURCE_CODE SOURCE_CODE}: <CODE>{@link com.github.aliteralmind.codelet.CodeletBaseConfig#getDefaultAlterGetter() getDefaultAlterGetter}()*.{@link DefaultAlterGetter#getForSourceCodelet() getForSourceCodelet}()</CODE></LI>
         <LI>{@link CodeletType#CONSOLE_OUT CONSOLE_OUT}: <CODE>getDefaultAlterGetter().{@link DefaultAlterGetter#getForCodeletDotOut() getForCodeletDotOut}()</CODE></LI>
         <LI>{@link CodeletType#FILE_TEXT FILE_TEXT}: <CODE>getDefaultAlterGetter().{@link DefaultAlterGetter#getForFileTextlet() getForFileTextlet}()</CODE></LI>
      </UL>
      @exception  IllegalArgumentException  If {@code needed_defaultAlterType} is {@link CodeletType#SOURCE_AND_OUT}. Use {@code SOURCE_CODE} or {@code CONSOLE_OUT} instead.
      @exception  DefaultAlterGetterException  If anything goes wrong when attempting to retrieve the map.
    **/
   public static final LinkedHashMap<String,TextLineAlterer> getMap(CodeletType needed_defaultAlterType)  {
      try  {
         switch(needed_defaultAlterType)  {
            case  SOURCE_CODE:
               return  getDefaultAlterGetter().getForSourceCodelet();
            case  CONSOLE_OUT:
               return  getDefaultAlterGetter().getForCodeletDotOut();
//				case  SOURCE_AND_OUT:
//					return  getDefaultAlterGetter().getForCodeletAndOut();
            case  FILE_TEXT:
               return  getDefaultAlterGetter().getForFileTextlet();
         }
         if(needed_defaultAlterType == CodeletType.SOURCE_AND_OUT)  {
            throw  new IllegalArgumentException("needed_defaultAlterType=SOURCE_AND_OUT. Use SOURCE_CODE or CONSOLE_OUT.");
         }
         throw  new DefaultAlterGetterException("needed_defaultAlterType (CodeletType." + needed_defaultAlterType + ") is an unknown value.");
      }  catch(RuntimeException rx)  {
         CrashIfObject.nnull(needed_defaultAlterType, "needed_defaultAlterType", null);
         throw  new DefaultAlterGetterException("Attempting to retrieve default alter map for needed_defaultAlterType=" + needed_defaultAlterType, rx);
      }
   }
   /**
      <P>Creates a new list with a initial capacity equal to the default-alterers map, plus a number.</P>

      @param  numToAdd_toDefaultMapSize  <I>Should</I> be greater than zero.
      @return  <CODE>(new {@link ArrayList#ArrayList(int) ArrayList}&lt;TextLineAlterer&gt;(
         <BR> &nbsp; &nbsp;
         numToAdd_toDefaultMapSize + {@link #getMap(CodeletType) getMap}(needed_defaultAlterType).size()))</CODE>
      @exception  IllegalArgumentException  If {@code numToAdd_toDefaultMapSize} results in an initial capacity that is negative.
      @exception  DefaultAlterGetterException  If anything goes wrong when attempting to retrieve the map or its size.
    **/
   public static final ArrayList<TextLineAlterer> newEmptyArrayListWithDefaultInitCapacityPlus(CodeletType needed_defaultAlterType, int numToAdd_toDefaultMapSize)  {
      try  {
         return  (new ArrayList<TextLineAlterer>(
            numToAdd_toDefaultMapSize + getMap(needed_defaultAlterType).size()));
      }  catch(IllegalArgumentException iax)  {
         throw  new IllegalArgumentException("numToAdd_toDefaultMapSize=" + numToAdd_toDefaultMapSize, iax);
      }
   }
   /**
      <P>Creates a new line-alter array with all defaults.</P>

      @return  <CODE>{@link #getAlterArrayWithDefaultAlterersAdded(CodeletType, List) getAlterArrayWithDefaultAlterersAdded}(needed_defaultAlterType,
      <BR> &nbsp; &nbsp; new {@link ArrayList#ArrayList(int) ArrayList}&lt;TextLineAlterer&gt;({@link #getMap(CodeletType) getMap}(needed_defaultAlterType).size()))</CODE>
    **/
   public static final TextLineAlterer[] getDefaultAlterArray(CodeletType needed_defaultAlterType)  {
      return  getAlterArrayWithDefaultAlterersAdded(needed_defaultAlterType,
         new ArrayList<TextLineAlterer>(getMap(needed_defaultAlterType).size()));
   }
   /**
      <P>Creates a new line-alter array with all default alterers placed after the provided alterer.</P>

      @return  <CODE>{@link #getAlterArrayWithDefaultAlterersAdded(CodeletType, List) getAlterArrayWithDefaultAlterersAdded}(needed_defaultAlterType,
      <BR> &nbsp; &nbsp; new {@link ArrayList#ArrayList(Collection) ArrayList}&lt;TextLineAlterer&gt;(
      <BR> &nbsp; &nbsp; &nbsp; &nbsp; {@link java.util.Arrays}.<!-- GENERIC PARAMETERS FAIL IN @link --><A HREF="http://docs.oracle.com/javase/7/docs/api/java/util/Arrays.html#asList(T)">asList</A>(new TextLineAlterer[]{the_onlyAlterer})))</CODE>
    **/
   public static final TextLineAlterer[] getAlterArrayWithDefaultAlterersAdded(CodeletType needed_defaultAlterType, TextLineAlterer the_onlyAlterer)  {
      return  getAlterArrayWithDefaultAlterersAdded(needed_defaultAlterType,
         new ArrayList<TextLineAlterer>(
            Arrays.asList(new TextLineAlterer[]{the_onlyAlterer})));
   }
   /**
      <P>Creates a new line-alter array with all default alterers placed after all provided alterers.</P>

      @param  needed_defaultAlterType  May not be {@code null}.
      @param  alter_list  May not be {@code null}.
      @exception  DefaultAlterGetterException  If anything goes wrong when attempting to retrieve the map, its {@linkplain java.util.Map#entrySet() entry} iterator, or an entry.
      @see  #getDefaultAlterArray(CodeletType) getDefaultAlterArray
      @see  #getAlterArrayWithDefaultAlterersAdded(CodeletType, TextLineAlterer) getAlterArrayWithDefaultAlterersAdded
    **/
   public static final TextLineAlterer[] getAlterArrayWithDefaultAlterersAdded(CodeletType needed_defaultAlterType, List<TextLineAlterer> alter_list)  {
      Iterator<Map.Entry<String,TextLineAlterer>> entryItr = null;
      try  {
         entryItr = getMap(needed_defaultAlterType).entrySet().iterator();
      }  catch(RuntimeException rx)  {
         throw  new DefaultAlterGetterException("Attempting getMap(needed_defaultAlterType).entrySet().iterator(). needed_defaultAlterType=" + needed_defaultAlterType, rx);
      }
      try  {
         while(entryItr.hasNext())  {
            try  {
               alter_list.add(entryItr.next().getValue());
            }  catch(RuntimeException rx)  {
               CrashIfObject.nnull(alter_list, "alter_list", null);
               throw  new DefaultAlterGetterException("Attempting alter_list.add([getMap(needed_defaultAlterType).entrySet().iterator()].next().getValue()) (\"next().getValue()\" failed). needed_defaultAlterType=" + needed_defaultAlterType, rx);
            }
         }
      }  catch(DefaultAlterGetterException dgx)  {
         throw  dgx;
      }  catch(RuntimeException rx)  {
         throw  new DefaultAlterGetterException("Attempting [getMap(needed_defaultAlterType).entrySet().iterator()].hasNext() (\"hasNext()\" failed). needed_defaultAlterType=" + needed_defaultAlterType, rx);
      }
      return  alter_list.toArray(new TextLineAlterer[alter_list.size()]);
   }
   private DefaultAlterGetterUtil()  {
      throw  new IllegalStateException("Do not instantiate.");
   }
}
