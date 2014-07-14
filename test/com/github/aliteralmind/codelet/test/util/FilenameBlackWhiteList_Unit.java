package  com.github.aliteralmind.codelet.test.util;
   import  com.github.aliteralmind.codelet.util.BlackOrWhite;
   import  com.github.aliteralmind.codelet.util.FilenameBlackWhiteList;
   import  com.github.aliteralmind.codelet.util.FilenameBlacklist;
   import  com.github.aliteralmind.codelet.util.FilenameWhitelist;
   import  java.util.List;
   import  org.apache.commons.io.IOCase;
   import  org.junit.Test;
   import  static org.junit.Assert.*;
/**

java com.github.xbn.test.io.FilenameBlackWhiteList_Unit

 **/
public class FilenameBlackWhiteList_Unit  {
   public static final void main(String[] ignored)  {
      FilenameBlackWhiteList_Unit unit = new FilenameBlackWhiteList_Unit();
      unit.test_basicErrorChecking();
      unit.test_constructorParamsPassThrough();
      unit.test_basicUses();
      unit.test_packageClass();
      unit.test_newFromConfigStringVars();
   }
   @Test
   public void test_newFromConfigStringVars()  {
      FilenameBlackWhiteList fromConfig = FilenameBlackWhiteList.newFromConfigStringVars("off", "require", ",", "xbn.io.*,xbn.text.*", "xbn.text.line.*,xbn.io.IOUtil.java", null, null);
      fromConfig.equals(FilenameBlackWhiteList.newForAcceptAll(null));

      try  {
         fromConfig = FilenameBlackWhiteList.newFromConfigStringVars(null, "require", ",", "xbn.io.*,xbn.text.*", "xbn.text.line.*,xbn.io.IOUtil.java", null, null);
         assertFalse(true);
      }  catch(NullPointerException x)  {
         assertTrue(true);
      }
      try  {
         fromConfig = FilenameBlackWhiteList.newFromConfigStringVars("black", null, ",", "xbn.io.*,xbn.text.*", "xbn.text.line.*,xbn.io.IOUtil.java", null, null);
         assertFalse(true);
      }  catch(NullPointerException x)  {
         assertTrue(true);
      }
      try  {
         fromConfig = FilenameBlackWhiteList.newFromConfigStringVars("black", "require", null, "xbn.io.*,xbn.text.*", "xbn.text.line.*,xbn.io.IOUtil.java", null, null);
         assertFalse(true);
      }  catch(NullPointerException x)  {
         assertTrue(true);
      }
      try  {
         fromConfig = FilenameBlackWhiteList.newFromConfigStringVars("black", "require", "x", "xbn.io.*,xbn.text.*", "xbn.text.line.*,xbn.io.IOUtil.java", null, null);
         assertFalse(true);
      }  catch(IllegalArgumentException x)  {
         assertTrue(true);
      }
      try  {
         fromConfig = FilenameBlackWhiteList.newFromConfigStringVars("black", "require", "?", "xbn.io.*,xbn.text.*", "xbn.text.line.*,xbn.io.IOUtil.java", null, null);
         assertFalse(true);
      }  catch(IllegalArgumentException x)  {
         assertTrue(true);
      }
      try  {
         fromConfig = FilenameBlackWhiteList.newFromConfigStringVars("black", "require", "*", "xbn.io.*,xbn.text.*", "xbn.text.line.*,xbn.io.IOUtil.java", null, null);
         assertFalse(true);
      }  catch(IllegalArgumentException x)  {
         assertTrue(true);
      }
      try  {
         fromConfig = FilenameBlackWhiteList.newFromConfigStringVars("black", "require", "*", "xbn.io.*,xbn.text.*", "xbn.text.line.*,xbn.io.IOUtil.java", null, null);
         assertFalse(true);
      }  catch(IllegalArgumentException x)  {
         assertTrue(true);
      }
      try  {
         fromConfig = FilenameBlackWhiteList.newFromConfigStringVars("black", "require", "", null, "xbn.text.line.*,xbn.io.IOUtil.java", null, null);
         assertFalse(true);
      }  catch(IllegalArgumentException x)  {
         assertTrue(true);
      }
      try  {
         fromConfig = FilenameBlackWhiteList.newFromConfigStringVars("black", "require", ",", "xbn.io.*,xbn.text.*", null, null, null);
         assertFalse(true);
      }  catch(NullPointerException x)  {
         assertTrue(true);
      }

      fromConfig = FilenameBlackWhiteList.newFromConfigStringVars("black", "ignore", ",", "xbn.io.*,xbn.text.*", "xbn.text.line.*,xbn.io.IOUtil.java", null, null);
      assertTrue(fromConfig.doAccept("xbn.list.listify.Listify"));
      assertFalse(fromConfig.doAccept("xbn.text.UtilString.java"));
      assertTrue(fromConfig.doAccept("xbn.text.line.LineFilter.java"));
      assertFalse(fromConfig.doAccept("xbn.io.TextAppenter.java"));
      assertTrue(fromConfig.doAccept("xbn.io.IOUtil.java"));
   }
   @Test
   public void test_packageClass()  {
      FilenameBlacklist blackList = new FilenameBlacklist(IOCase.INSENSITIVE, (new String[]{"xbn.io.*", "xbn.text.*"}), (new String[]{"xbn.text.line.*", "xbn.io.IOUtil.java"}), null);
      assertTrue(blackList.doAccept("xbn.list.listify.Listify"));
      assertFalse(blackList.doAccept("xbn.text.UtilString.java"));
      assertTrue(blackList.doAccept("xbn.text.line.LineFilter.java"));
      assertFalse(blackList.doAccept("xbn.io.TextAppenter.java"));
      assertTrue(blackList.doAccept("xbn.io.IOUtil.java"));

      FilenameWhitelist whiteList = new FilenameWhitelist(IOCase.INSENSITIVE, (new String[]{"xbn.io.*", "xbn.text.*"}), (new String[]{"xbn.text.line.*", "xbn.io.IOUtil.java"}), null);
      assertFalse(whiteList.doAccept("xbn.list.listify.Listify"));
      assertTrue(whiteList.doAccept("xbn.text.UtilString.java"));
      assertFalse(whiteList.doAccept("xbn.text.line.LineFilter.java"));
      assertTrue(whiteList.doAccept("xbn.io.TextAppenter.java"));
      assertFalse(whiteList.doAccept("xbn.io.IOUtil.java"));
   }
   @Test
   public void test_basicUses()  {
      FilenameBlacklist blackList = new FilenameBlacklist(IOCase.INSENSITIVE, (new String[]{"*"}), (new String[]{"a"}), null);
      assertFalse(blackList.doAccept("abcde"));
      assertTrue(blackList.doAccept("a"));
      assertFalse(blackList.doAccept("abCDe"));
      assertTrue(blackList.doAccept("A"));

      blackList = new FilenameBlacklist(IOCase.SENSITIVE, (new String[]{"*"}), (new String[]{"a"}), null);
      assertFalse(blackList.doAccept("abcde"));
      assertTrue(blackList.doAccept("a"));
      assertFalse(blackList.doAccept("abCDe"));
      assertFalse(blackList.doAccept("A"));
   }
   @Test
   public void test_constructorParamsPassThrough()  {
      FilenameBlackWhiteList blackList = new FilenameBlackWhiteList(BlackOrWhite.BLACK, IOCase.SENSITIVE, (new String[]{"x", "y", "z"}), (new String[]{"a", "b", "c", "d"}), null);
         assertFalse(blackList.isWhitelist());
         assertEquals(IOCase.SENSITIVE, blackList.getCaseSensitivity());

         List<String> properList = blackList.getImmutableProperList();
         assertEquals(3, properList.size());
         assertEquals("x", properList.get(0));
         assertEquals("y", properList.get(1));
         assertEquals("z", properList.get(2));

         List<String> overrideList = blackList.getImmutableOverrideList();
         assertEquals(4, overrideList.size());
         assertEquals("a", overrideList.get(0));
         assertEquals("b", overrideList.get(1));
         assertEquals("c", overrideList.get(2));
         assertEquals("d", overrideList.get(3));

      FilenameBlackWhiteList blackList2 = new FilenameBlackWhiteList(blackList, BlackOrWhite.WHITE, IOCase.INSENSITIVE, null);
         assertTrue(blackList2.isWhitelist());
         assertEquals(IOCase.INSENSITIVE, blackList2.getCaseSensitivity());

         properList = blackList2.getImmutableProperList();
         assertEquals(3, properList.size());
         assertEquals("y", properList.get(1));

         overrideList = blackList2.getImmutableOverrideList();
         assertEquals(4, overrideList.size());
         assertEquals("c", overrideList.get(2));
   }
   @Test
   public void test_basicErrorChecking()  {
      FilenameBlackWhiteList bwlist = null;
      try  {
         bwlist = new FilenameBlackWhiteList(null, IOCase.INSENSITIVE, (new String[]{"x"}), (new String[]{"x"}), null);
         assertFalse(true);
      }  catch(NullPointerException x)  {
         assertTrue(true);
      }
      try  {
         bwlist = new FilenameBlackWhiteList(BlackOrWhite.BLACK, null, (new String[]{"x"}), (new String[]{"x"}), null);
         assertFalse(true);
      }  catch(NullPointerException x)  {
         assertTrue(true);
      }
      try  {
         bwlist = new FilenameBlackWhiteList(BlackOrWhite.BLACK, IOCase.INSENSITIVE, null, (new String[]{"x"}), null);
         assertFalse(true);
      }  catch(NullPointerException x)  {
         assertTrue(true);
      }
      try  {
         bwlist = new FilenameBlackWhiteList(BlackOrWhite.BLACK, IOCase.INSENSITIVE, (new String[]{"x"}), null, null);
         assertFalse(true);
      }  catch(NullPointerException x)  {
         assertTrue(true);
      }
      try  {
         bwlist.doAccept(null);
         assertFalse(true);
      }  catch(NullPointerException x)  {
         assertTrue(true);
      }
      try  {
         bwlist.isMatchedByProper(null);
         assertFalse(true);
      }  catch(NullPointerException x)  {
         assertTrue(true);
      }
      try  {
         bwlist.isMatchedByOverride(null);
         assertFalse(true);
      }  catch(NullPointerException x)  {
         assertTrue(true);
      }
      try  {
         bwlist.doAccept("");
         assertFalse(true);
      }  catch(NullPointerException x)  {
         assertTrue(true);
      }
      try  {
         bwlist.isMatchedByProper("");
         assertFalse(true);
      }  catch(NullPointerException x)  {
         assertTrue(true);
      }
      try  {
         bwlist.isMatchedByOverride("");
         assertFalse(true);
      }  catch(NullPointerException x)  {
         assertTrue(true);
      }

      FilenameBlackWhiteList bwlist2;
      try  {
         bwlist2 = new FilenameBlackWhiteList(null, BlackOrWhite.BLACK, IOCase.INSENSITIVE, null);
         assertFalse(true);
      }  catch(NullPointerException x)  {
         assertTrue(true);
      }
      try  {
         bwlist2 = new FilenameBlackWhiteList(bwlist, null, IOCase.INSENSITIVE, null);
         assertFalse(true);
      }  catch(NullPointerException x)  {
         assertTrue(true);
      }
      try  {
         bwlist2 = new FilenameBlackWhiteList(bwlist, BlackOrWhite.BLACK, null, null);
         assertFalse(true);
      }  catch(NullPointerException x)  {
         assertTrue(true);
      }
   }
}
