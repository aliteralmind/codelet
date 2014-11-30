package  com.github.aliteralmind.codelet.test.simplesig;
   import  java.util.List;
   import  org.junit.Test;
   import  static org.junit.Assert.*;
   import  com.github.aliteralmind.codelet.simplesig.SimpleMethodSignature;
/**

java com.github.aliteralmind.codelet.test.simplesig.SimpleMethodSignature_Unit

 **/
public class SimpleMethodSignature_Unit  {
   public static final void main(String[] ignored)  {
      SimpleMethodSignature_Unit unit = new SimpleMethodSignature_Unit();
      unit.test_1ParamErrors();
      unit.test_1ParamSuccess();
      unit.test_paramListSuccess();
      unit.test_parseStringSigErrors();
      unit.test_parseStringSigSuccess();
   }
   @Test
   public void test_parseStringSigErrors()  {
      try  {
         SimpleMethodSignature.newFromStringAndDefaults(null, null, null, null, null);
         assertTrue(false);
      }  catch(ClassNotFoundException x)  {
         assertNull(x);
      }  catch(NullPointerException x)  {
         //sig null
         assertNotNull(x);
      }
      try  {
         SimpleMethodSignature.newFromStringAndDefaults(String.class, "", null, null, null);
         assertTrue(false);
      }  catch(ClassNotFoundException x)  {
         assertNull(x);
      }  catch(IllegalArgumentException x)  {
         //invalid sig
         assertNotNull(x);
      }
      try  {
         SimpleMethodSignature.newFromStringAndDefaults(String.class, "functionName", null, null, null);
         assertTrue(false);
      }  catch(ClassNotFoundException x)  {
         assertNull(x);
      }  catch(IllegalArgumentException x)  {
         //invalid sig
         assertNotNull(x);
      }
      try  {
         SimpleMethodSignature.newFromStringAndDefaults(String.class, "functionName(", null, null, null);
         assertTrue(false);
      }  catch(ClassNotFoundException x)  {
         assertNull(x);
      }  catch(IllegalArgumentException x)  {
         //invalid sig
         assertNotNull(x);
      }
      try  {
         SimpleMethodSignature.newFromStringAndDefaults(String.class, "functionName()", null, null, null);
         assertTrue(false);
      }  catch(ClassNotFoundException x)  {
         assertNull(x);
      }  catch(IllegalArgumentException x)  {
         //no class provided
         assertNotNull(x);
      }
      try  {
         SimpleMethodSignature.newFromStringAndDefaults(String.class, "ClassName#functionName()", null, null, null);
         assertTrue(false);
      }  catch(ClassNotFoundException x)  {
         assertNull(x);
      }  catch(IllegalArgumentException x)  {
         //no package provided
         assertNotNull(x);
      }
      try  {
         SimpleMethodSignature.newFromStringAndDefaults(String.class, "ClassName#functionName()", "", null, null);
         assertTrue(false);
      }  catch(ClassNotFoundException x)  {
         assertNull(x);
      }  catch(IllegalArgumentException x)  {
         //empty package
         assertNotNull(x);
      }
      try  {
         SimpleMethodSignature.newFromStringAndDefaults(String.class, "ClassName#functionName()", ".", null, null);
         assertTrue(false);
      }  catch(ClassNotFoundException x)  {
         //invalid package
         assertNotNull(x);
      }  catch(IllegalArgumentException x)  {
         assertNull(x);
      }
      try  {
         SimpleMethodSignature.newFromStringAndDefaults(String.class, "ClassName#functionName()", "a", null, null);
         assertTrue(false);
      }  catch(ClassNotFoundException x)  {
         //invalid package
         assertNotNull(x);
      }
      try  {
         SimpleMethodSignature.newFromStringAndDefaults(String.class, "ClassName#functionName()", "a.", null, null);
         assertTrue(false);
      }  catch(ClassNotFoundException x)  {
         //invalid package
         assertNotNull(x);
      }

   }
   @Test
   public void test_parseStringSigSuccess()  {
      String sig = "functionName()";

      SimpleMethodSignature simpleSig = null;
      try  {
         simpleSig = SimpleMethodSignature.newFromStringAndDefaults(String.class, sig, null, new Class[]{SimpleMethodSignature_Unit.class}, null);
         assertTrue(true);
      }  catch(ClassNotFoundException x)  {
         assertNull(x);
      }
         assertEquals(SimpleMethodSignature_Unit.class, simpleSig.getMayContainFuncClass(0));
         assertEquals("functionName", simpleSig.getFunctionName());
         assertEquals("", simpleSig.getParamStringList());

      sig = "SimpleMethodSignature_Unit#functionName()";
      try  {
         simpleSig = SimpleMethodSignature.newFromStringAndDefaults(String.class, sig, "com.github.aliteralmind.codelet.test.simplesig.", null, null);
      }  catch(ClassNotFoundException x)  {
         assertNull(x);
      }
         assertEquals(SimpleMethodSignature_Unit.class, simpleSig.getMayContainFuncClass(0));
         assertEquals("functionName", simpleSig.getFunctionName());
         assertEquals("", simpleSig.getParamStringList());

      sig = "com.github.aliteralmind.codelet.test.simplesig.SimpleMethodSignature_Unit#functionName()";
      try  {
         simpleSig = SimpleMethodSignature.newFromStringAndDefaults(String.class, sig, null, null, null);
      }  catch(ClassNotFoundException x)  {
         assertNull(x);
      }
         assertEquals(SimpleMethodSignature_Unit.class, simpleSig.getMayContainFuncClass(0));
         assertEquals("functionName", simpleSig.getFunctionName());
         assertEquals("", simpleSig.getParamStringList());

      sig = "com.github.aliteralmind.codelet.test.simplesig.SimpleMethodSignature_Unit#functionName(true, 1, \"hello\", ...)";
      try  {
         simpleSig = SimpleMethodSignature.newFromStringAndDefaults(String.class, sig, null, null, null);
      }  catch(ClassNotFoundException x)  {
         assertNull(x);
      }
         assertEquals(SimpleMethodSignature_Unit.class, simpleSig.getMayContainFuncClass(0));
         assertEquals("functionName", simpleSig.getFunctionName());
         assertEquals("true, 1, \"hello\", ...", simpleSig.getParamStringList());
   }
   @Test
   public void test_paramListSuccess()  {
      List<Object> objList = SimpleMethodSignature.getObjectListFromCommaSepParamString("1, \"hello\", 3L, true");
      assertEquals(Integer.class, objList.get(0).getClass());
      assertEquals(String.class, objList.get(1).getClass());
      assertEquals(Long.class, objList.get(2).getClass());
      assertEquals(Boolean.class, objList.get(3).getClass());
   }
   @Test
   public void test_1ParamSuccess()  {
      assertEquals(Boolean.class, SimpleMethodSignature.getObjectFromString("true").getClass());
      assertEquals(Boolean.class, SimpleMethodSignature.getObjectFromString("false").getClass());
      assertEquals(Character.class, SimpleMethodSignature.getObjectFromString("' '").getClass());
      assertEquals(Character.class, SimpleMethodSignature.getObjectFromString("'''").getClass());
      assertEquals(Byte.class, SimpleMethodSignature.getObjectFromString("(byte)1").getClass());
      assertEquals(Short.class, SimpleMethodSignature.getObjectFromString("(short)1").getClass());
      assertEquals(Integer.class, SimpleMethodSignature.getObjectFromString("1").getClass());
      assertEquals(Long.class, SimpleMethodSignature.getObjectFromString("1L").getClass());
      assertEquals(Long.class,
         SimpleMethodSignature.getObjectFromString(new String(Integer.MAX_VALUE + "1L")).getClass());
      assertEquals(Float.class, SimpleMethodSignature.getObjectFromString("1.1f").getClass());
      assertEquals(Double.class, SimpleMethodSignature.getObjectFromString("1.1d").getClass());
      assertEquals(String.class, SimpleMethodSignature.getObjectFromString("\"hello\"").getClass());
      Object stringAsObj = SimpleMethodSignature.getObjectFromString("\"&quot;...&#44;...&amp;\"");
      assertEquals(String.class, stringAsObj.getClass());
      assertEquals("\"...,...&", stringAsObj);

      //Negative
         assertEquals(Byte.class, SimpleMethodSignature.getObjectFromString("(byte)-1").getClass());
         assertEquals(Short.class, SimpleMethodSignature.getObjectFromString("(short)-1").getClass());
         assertEquals(Integer.class, SimpleMethodSignature.getObjectFromString("-1").getClass());
         assertEquals(Long.class, SimpleMethodSignature.getObjectFromString("-1L").getClass());
         assertEquals(Long.class,
            SimpleMethodSignature.getObjectFromString("-" + new String(Integer.MAX_VALUE + "1L")).getClass());
         assertEquals(Float.class, SimpleMethodSignature.getObjectFromString("-1.1f").getClass());
         assertEquals(Double.class, SimpleMethodSignature.getObjectFromString("-1.1d").getClass());

   }
   @Test
   public void test_1ParamErrors()  {
      try  {
         SimpleMethodSignature.getObjectFromString(null);
         assertFalse(true);
      }  catch(NullPointerException x)  {
         assertNotNull(x);
      }
      try  {
         SimpleMethodSignature.getObjectFromString("truee");
         assertFalse(true);
      }  catch(IllegalArgumentException x)  {
         assertNotNull(x);
      }

      //(byte) (short)
         try  {
            SimpleMethodSignature.getObjectFromString("(b");
            assertFalse(true);
         }  catch(IllegalArgumentException x)  {
            assertNotNull(x);
         }
         try  {
            SimpleMethodSignature.getObjectFromString("(byte)x");
            assertFalse(true);
         }  catch(IllegalArgumentException x)  {
            assertNotNull(x);
         }
         try  {
            SimpleMethodSignature.getObjectFromString("(byte)" + Integer.MAX_VALUE);
            assertFalse(true);
         }  catch(IllegalArgumentException x)  {
            assertNotNull(x);
         }
         try  {
            SimpleMethodSignature.getObjectFromString("(short)" + Integer.MAX_VALUE);
            assertFalse(true);
         }  catch(IllegalArgumentException x)  {
            assertNotNull(x);
         }

      //int
         try  {
            SimpleMethodSignature.getObjectFromString(new String(Integer.MAX_VALUE + "" + 1));
            assertFalse(true);
         }  catch(IllegalArgumentException x)  {
            assertNotNull(x);
         }

      //long
         try  {
            SimpleMethodSignature.getObjectFromString(new String(Long.MAX_VALUE + "1L"));
            assertFalse(true);
         }  catch(IllegalArgumentException x)  {
            assertNotNull(x);
         }

      //char
         try  {
            SimpleMethodSignature.getObjectFromString("'");
            assertFalse(true);
         }  catch(IllegalArgumentException x)  {
            assertNotNull(x);
         }
         try  {
            SimpleMethodSignature.getObjectFromString("''");
            assertFalse(true);
         }  catch(IllegalArgumentException x)  {
            assertNotNull(x);
         }
         try  {
            SimpleMethodSignature.getObjectFromString("'  '");
            assertFalse(true);
         }  catch(IllegalArgumentException x)  {
            assertNotNull(x);
         }
         try  {
            SimpleMethodSignature.getObjectFromString("''x");
            assertFalse(true);
         }  catch(IllegalArgumentException x)  {
            assertNotNull(x);
         }

      //string
         try  {
            SimpleMethodSignature.getObjectFromString("\"");
            assertFalse(true);
         }  catch(IllegalArgumentException x)  {
            assertNotNull(x);
         }
         try  {
            SimpleMethodSignature.getObjectFromString("\"\"\"");
            assertFalse(true);
         }  catch(IllegalArgumentException x)  {
            assertNotNull(x);
         }
         try  {
            SimpleMethodSignature.getObjectFromString("\"\"\"");
            assertFalse(true);
         }  catch(IllegalArgumentException x)  {
            assertNotNull(x);
         }
         try  {
            SimpleMethodSignature.getObjectFromString("\",\"");
            assertFalse(true);
         }  catch(IllegalArgumentException x)  {
            assertNotNull(x);
         }
   }
}
