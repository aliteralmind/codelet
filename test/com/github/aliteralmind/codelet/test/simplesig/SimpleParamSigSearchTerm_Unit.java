package  com.github.aliteralmind.codelet.test.simplesig;
   import  com.github.aliteralmind.codelet.alter.NewLineFilterFor;
   import  com.github.aliteralmind.codelet.alter.NewJDLinkForWordOccuranceNum;
   import  com.github.aliteralmind.codelet.simplesig.MethodSigSearchTerm;
   import  com.github.xbn.lang.reflect.RTNoSuchMethodException;
   import  com.github.aliteralmind.codelet.simplesig.AllSimpleParamSignatures;
   import  com.github.aliteralmind.codelet.simplesig.SimpleParamSigSearchTerm;
   import  com.github.xbn.lang.reflect.Declared;
   import  org.junit.Test;
   import  static org.junit.Assert.*;

/**

java com.github.aliteralmind.codelet.test.simplesig.SimpleParamSigSearchTerm_Unit

 **/
public class SimpleParamSigSearchTerm_Unit  {
   public static final void main(String[] ignored)  {
      SimpleParamSigSearchTerm_Unit unit = new SimpleParamSigSearchTerm_Unit();
      unit.test_getOnlyMatch();
   }
   @Test
   public void test_getOnlyMatch()  {
      AllSimpleParamSignatures allInClass = new AllSimpleParamSignatures(
         SimpleParamSigSearchTerm_Unit.class, Declared.YES);

      try  {
         new MethodSigSearchTerm("nonExisting()", null, null).
            getOnlyMatch(allInClass);
         fail("non-existing matches");
      }  catch(RTNoSuchMethodException x)  {
         assertTrue(true);
      }

      new MethodSigSearchTerm("funcName(*, boolean)", null, null).
         getOnlyMatch(allInClass);

      //getOnlyMatch() should not crash...START
         new MethodSigSearchTerm("funcName()", null, null).
            getOnlyMatch(allInClass);

         new MethodSigSearchTerm("funcName(int)", null, null).
            getOnlyMatch(allInClass);

         new MethodSigSearchTerm("funcName(int, *)", null, null).
            getOnlyMatch(allInClass);

         new MethodSigSearchTerm("funcName(int, boolean, *)", null, null).
            getOnlyMatch(allInClass);

         new MethodSigSearchTerm("funcName(int, *, boolean)", null, null).
            getOnlyMatch(allInClass);

         new MethodSigSearchTerm("funcName(int, *, int, boolean)", null, null).
            getOnlyMatch(allInClass);

         new MethodSigSearchTerm("funcName(int, boolean, int, boolean)", null, null).
            getOnlyMatch(allInClass);

      allInClass = new AllSimpleParamSignatures(
         NewJDLinkForWordOccuranceNum.class, Declared.YES);

      new MethodSigSearchTerm("method(*, String, Appendable, *)", null, null).
         getOnlyMatch(allInClass);

      allInClass = new AllSimpleParamSignatures(
         NewLineFilterFor.class, Declared.YES);

      new MethodSigSearchTerm("lineRange(*, String)", null, null).
         getOnlyMatch(allInClass);

      new MethodSigSearchTerm("lineRange(int, *)", null, null).
         getOnlyMatch(allInClass);
//lineRange(CodeletInstance, String, int, boolean, String, int, boolean, String)
//lineRange(int, boolean, String, Appendable, Appendable, int, boolean, String, Appendable, Appendable, Appendable)
   }
   public static final void funcName()  {
   }
   public static final void funcName(int i)  {
   }
   public static final void funcName(int i, boolean b, int i2, boolean b2)  {
   }
}
