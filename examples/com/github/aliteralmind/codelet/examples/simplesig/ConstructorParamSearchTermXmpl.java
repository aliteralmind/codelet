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
package  com.github.aliteralmind.codelet.examples.simplesig;
   import  com.github.aliteralmind.codelet.simplesig.AllSimpleParamSignatures;
   import  com.github.aliteralmind.codelet.simplesig.ConstructorParamSearchTerm;
   import  com.github.aliteralmind.codelet.simplesig.ConstructorSimpleParamSig;
   import  com.github.aliteralmind.codelet.simplesig.CrashIfMoreThanOne;
   import  com.github.aliteralmind.codelet.simplesig.CrashIfZero;
   import  com.github.xbn.lang.reflect.Declared;
   import  java.util.List;
/**
   <P>Demonstration of {@link com.github.aliteralmind.codelet.simplesig.ConstructorParamSearchTerm}.</P>

   <P>{@code java com.github.aliteralmind.codelet.examples.simplesig.ConstructorParamSearchTermXmpl}</P>

   @author  Copyright (C) 2014, Jeff Epstein, dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
 **/

public class ConstructorParamSearchTermXmpl  {
   public static final String LINE_SEP = System.getProperty("line.separator", "\r\n");
   public static final void main(String[] ignored)  {

      AllSimpleParamSignatures allInClass = new AllSimpleParamSignatures(
         ConstructorParamSearchTermXmpl.class, Declared.NO);

      test(allInClass, "()");
      test(allInClass, "(*)");
      test(allInClass, "(boolean)");
   }
   private static void test(AllSimpleParamSignatures all_inClass, String param_searchTerm)  {
      ConstructorParamSearchTerm searchTerm = new ConstructorParamSearchTerm(param_searchTerm, null, null);

      System.out.println("Searching for: \"" + param_searchTerm + "\"");

      if(searchTerm.doesMatchAny(all_inClass.getConstructorList()))  {

         ConstructorSimpleParamSig first = searchTerm.getFirstMatch(
            all_inClass.getConstructorList(), CrashIfZero.NO, CrashIfMoreThanOne.NO);

         List<ConstructorSimpleParamSig> allMatches = searchTerm.getAllMatches(
            all_inClass.getConstructorList(), CrashIfZero.NO);

         System.out.println("   - First match: " + first);
         System.out.println("   - All matches:" + LINE_SEP +
            AllSimpleParamSignatures.toStringForAllListsInList("      - ", allMatches, LINE_SEP));

      }  else  {
         System.out.println("   Not found.");
      }

      System.out.println();
   }
   public ConstructorParamSearchTermXmpl(int i)  {
   }
   public ConstructorParamSearchTermXmpl(boolean b)  {
   }
}
