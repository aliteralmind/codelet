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
package  com.github.aliteralmind.codelet.type;
   import  java.nio.file.InvalidPathException;
   import  java.nio.file.Path;
   import  java.nio.file.Paths;
   import  com.github.xbn.io.PathMustBe;
   import  com.github.aliteralmind.codelet.CodeletFormatException;
   import  com.github.aliteralmind.codelet.CodeletInstance;
   import  com.github.aliteralmind.codelet.CodeletType;
   import  com.github.aliteralmind.codelet.CustomizationInstructions;
   import  com.github.aliteralmind.codelet.TagletOfTypeProcessor;
   import  com.github.aliteralmind.codelet.TagletTextUtil;
   import  com.github.xbn.io.PlainTextFileUtil;
   import  com.github.aliteralmind.codelet.simplesig.SimpleMethodSignature;
   import  java.nio.file.AccessDeniedException;
   import  java.nio.file.NoSuchFileException;
   import  java.util.Iterator;
   import  static com.github.aliteralmind.codelet.CodeletBaseConfig.*;
   import  static com.github.xbn.lang.XbnConstants.*;
/**
   <P>Reads a {@link com.github.aliteralmind.codelet.CodeletType#FILE_TEXT {@.file.textlet}} taglet and outputs its replacement text.</P>

   @since  0.1.0
   @author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
 **/
public class FileTextProcessor extends TagletOfTypeProcessor<FileTextTemplate>  {
   /**
      <P>Create a new instance from an {@code CodeletInstance}.</P>

      <P>Equal to <CODE>{@link com.github.aliteralmind.codelet.TagletOfTypeProcessor#TagletOfTypeProcessor(CodeletType, CodeletInstance) super}(CodeletType.FILE_TEXT, instance)</CODE>
    **/
   public FileTextProcessor(CodeletInstance instance) throws ClassNotFoundException, NoSuchMethodException, NoSuchFileException, AccessDeniedException  {
      super(CodeletType.FILE_TEXT, instance);

      if(getClassOrFilePortion().contains("("))  {
         throw  new CodeletFormatException(instance, "File-text taglets cannot contain command-line parameters");
      }

      Iterator<String> fileTextLineItr = getLineIteratorFromCodeletPath(instance);

      String strSig = getStringSigForFileText();
//		SimpleMethodSignature sig = getCustomizerSigFromString(strSig);

      CustomizationInstructions<FileTextTemplate> instructions =
         ((getCustomizerPortion() != null)
            ?  getCustomCustomizationInstructions(CodeletType.FILE_TEXT)
            :  newInstructionsForDefaults(
                  new CustomizationInstructions<FileTextTemplate>(instance, CodeletType.FILE_TEXT)));

      crashIfClassOrFileCannotUseCustomizer(instructions);

      String fileTextCustomized = instructions.getCustomizedBody(instance, fileTextLineItr);

      FileTextTemplate template = getTemplateFromInstructionsOverrideOrDefault(
         instructions);

      String finalOutput = template.fillBody(fileTextCustomized).getRendered(instance);
      setFullyProcessedOutput(finalOutput);
   }
      private final Iterator<String> getLineIteratorFromCodeletPath(CodeletInstance instance)  {

         boolean doDebug = isDebugOn(instance, "zzFileTextProcessor.obtainingfile");

         //See CodeletInstance.FILE_TEXT

         if(doDebug)  {
            debugln("Obtaining line iterator to {@.file.textlet} file (expected file-separator is '" + FILE_SEP + "')...");
            debugln("   - Raw file path from {@.file.textlet}:  " + getClassOrFilePortion());
            debugln("   - TagletTextUtil.getFilePath(instance): " + TagletTextUtil.getFilePath(instance));
         }

         Path path = Paths.get(TagletTextUtil.getFilePath(instance));

         PathMustBe pmb = new PathMustBe().existing().readable();
         String namePost = "TagletTextUtil.getFilePath(instance)]";

         //First assume absolute. This also works if the path is relative to
         //the directory in which javadoc.exe was invoked.

         if(pmb.isGood(path))  {
            if(doDebug)  {
               debugln("   SUCCESS: Path is either absolute, or relative to the directory in which javadoc.exe was invoked.");
            }

            return  PlainTextFileUtil.getLineIterator(path.toString(), "[path to file]");
         }

         //It's not absolute or relative to the invoking directory.
         //It must be relative to the enclosing file, or die.

         String parentPath = instance.getEnclosingFile().getParent();

         if(!parentPath.endsWith(FILE_SEP))  {
            parentPath += FILE_SEP;
         }

         if(doDebug)  {
            debugln("   Path to file is not absolute, and not relative to the javadoc.exe-invoking directory. It MUST be relative to the directory of its enclosing file:");
            debugln("    - File:   " + instance.getEnclosingFile());
            debugln("    - Parent: " + parentPath);
         }

         try  {
            path = Paths.get(parentPath, path.toString());
         }  catch(InvalidPathException ipx)  {
            throw  new InvalidPathException(parentPath + path.toString(), "{@.file.textlet} path is invalid. Not absolute, not relative to javadoc.exe invoking directory, and not relative to enclosing file");
         }

         return  PlainTextFileUtil.getLineIterator(path.toString(), "[path to {@.file.textlet} file]");
      }
   /**
      <P>Get the string signature for a {@code {@.file.textlet}} taglets only. Except where noted, this does not validate the taglet text or the returned signature.</P>

      <P>This follows the same steps as {@link com.github.aliteralmind.codelet.TagletOfTypeProcessor#getStringSignature() getStringSignature}, except<UL>
         <LI>The customizer portion of the taglet, when provided, must contain either an underscore ({@code '_'}) postfix or the processor's full function name.</LI>
         <LI>The {@linkplain com.github.aliteralmind.codelet.CodeletType#getDefaultLineProcNamePrefix() default function name prefix} is {@code "getFileTextConfig_"}</LI>
      </UL></P>

      @exception  CodeletFormatException  If no function name or underscore-postfix is provided.
    **/
   public String getStringSigForFileText()  {
      String sig = getCustomizerPortion();

      boolean doDebug = isDebugOn(getInstance(),
         "zzTagletOfTypeProcessor.getCustomizerSigFromString");

      if(sig == null)  {
         if(doDebug)  {
            debugln("   No Customizer");
         }
         return  null;
      }

      if(sig.equals("()"))  {
         throw  new CodeletFormatException(getInstance(), "Customizer portion for a " + CodeletType.FILE_TEXT.getName() + " taglet is equal to \"()\". The processor's function name or underscore-postfix must be specified.");
      }

      return  getSig2PrnsApnddForNameOrPostfix("", sig, doDebug);
   }
}
