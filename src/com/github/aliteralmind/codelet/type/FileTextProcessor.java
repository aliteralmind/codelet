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
   import  java.nio.file.AccessDeniedException;
   import  java.nio.file.NoSuchFileException;
   import  java.util.Iterator;
   import  static com.github.aliteralmind.codelet.CodeletBaseConfig.*;
   import  static com.github.xbn.lang.XbnConstants.*;
/**
   <p>Reads a {@link com.github.aliteralmind.codelet.CodeletType#FILE_TEXT {@.file.textlet}} taglet and outputs its replacement text.</p>

 * @since  0.1.0
 * @author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <a href="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</a>, <a href="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</a>
 **/
public class FileTextProcessor extends TagletOfTypeProcessor<FileTextTemplate>  {
   /**
      <p>Create a new instance from an {@code CodeletInstance}.</p>

    * <p>Equal to <code>{@link com.github.aliteralmind.codelet.TagletOfTypeProcessor#TagletOfTypeProcessor(CodeletType, CodeletInstance) super}(CodeletType.FILE_TEXT, instance)</code>
    */
   public FileTextProcessor(CodeletInstance instance) throws ClassNotFoundException, NoSuchMethodException, NoSuchFileException, AccessDeniedException  {
      super(CodeletType.FILE_TEXT, instance);

      if(getClassOrFilePortion().contains("("))  {
         throw  new CodeletFormatException(instance, "File-text taglets cannot contain command-line parameters");
      }

      Iterator<String> fileTextLineItr = getLineIteratorFromCodeletPath(instance);

//		String strSig = getStringSigForFileText();
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
            debugln("Obtaining line iterator to {@.file.textlet} file...");
            debugln("   - Raw file path from {@.file.textlet}:  " + getClassOrFilePortion());
            debugln("   - TagletTextUtil.getFilePath(instance): " + TagletTextUtil.getFilePath(instance));
         }

         String pathStr = getPathStrWithEnvVarPrefix(instance, doDebug);

         PathMustBe pmb = new PathMustBe().existing().readable();

         /*
            First assume absolute. This also works if the path is relative to the directory in which javadoc.exe was invoked (and the file separators happen to be correct for the OS).
          */
         Path path = Paths.get(pathStr);
         if(pmb.isGood(path))  {
            if(doDebug)  {
               debugln("   SUCCESS: Path is either absolute, or relative to the directory in which javadoc.exe was invoked (note: file-separators not yet changed).");
            }

            return  PlainTextFileUtil.getLineIterator(path.toString(), "[path to file]");
         }


         if(doDebug)  {
            debugln("   Replacing all '/' file separators with \"" + FILE_SEP + "\"...");
         }
         if(!FILE_SEP.equals("/"))  {
            if(doDebug)  {
               debugln("   This system's file separator is already '/'. Nothing to change.");
            }
         }  else  {
            pathStr.replace("/", FILE_SEP);
         }

         //It's not absolute.
         /*
            It's also not relative to the javadoc.exe-invoking directory (the current working directory: cwd) WITH THE ORIGINAL FILE-SEPARATORS. Let's try again now with the new file separators.
          */
         path = Paths.get(pathStr);
         if(pmb.isGood(path))  {
            if(doDebug)  {
               debugln("   SUCCESS: Path is relative to the directory in which javadoc.exe was invoked.");
            }

            return  PlainTextFileUtil.getLineIterator(path.toString(), "[path to file]");
         }

         //It's not relative to the cwd. It must be relative to the enclosing
         //file. If it's not, die.

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
      private final String getPathStrWithEnvVarPrefix(CodeletInstance instance, boolean do_debug)  {

         //See com.github.aliteralmind.codelet.CodeletType#FILE_TEXT

         String rawPath = TagletTextUtil.getFilePath(instance);

         if(!rawPath.startsWith("$<"))  {
            return  rawPath;
         }

         int idxCloseCurly = rawPath.indexOf('>');

         String envVarName = null;
         try  {
            envVarName = rawPath.substring("$<".length(), idxCloseCurly);
         }    catch(StringIndexOutOfBoundsException sbx)  {
            throw  new CodeletFormatException(instance, "File path begins \"$<\", but close sharp ('>') not found.", sbx);
         }
         String envVar = System.getenv(envVarName);
         String sysProp = System.getProperty(envVarName);

         boolean isEnvVar = (envVar != null  &&  envVar.length() > 0);
         boolean isSysProp = (sysProp != null  &&  sysProp.length() > 0);

         if(isEnvVar)  {
            if(isSysProp)  {
               throw  new CodeletFormatException(instance, "File path begins with \"$<" + envVarName + ">\". \"" + envVarName + "\" is both an environment variable and a system property. It must be one or the other." + LINE_SEP +
                  "System.getenv(\"" + envVarName + "\")=\"" + envVar + "\"" + LINE_SEP +
                  "System.getProperty(\"" + envVarName + "\")=\"" + sysProp + "\"");
            }

         }  else if(isSysProp)  {
            envVar = sysProp;
         }  else  {
            throw  new CodeletFormatException(instance, "File path begins with \"$<" + envVarName + ">\". The value of both System.getenv(\"" + envVarName + "\") (" +
               ((envVar == null) ? "null" : "\"\"") +
               ") and System.getProperty(\"" + envVarName + "\"), (" +
               ((sysProp == null) ? "null" : "\"\"") +
               ") are null/empty-string");
         }

         if(do_debug)  {
            debugln("   " +
               (isSysProp ? "System property" : "Environment variable") +
               " found: Name=" + envVarName + ", value=" + envVar + "");
            debugln("   - Raw file path from {@.file.textlet}:  " + getClassOrFilePortion());
            debugln("   - TagletTextUtil.getFilePath(instance): " + TagletTextUtil.getFilePath(instance));
         }

         return  envVar + rawPath.substring(idxCloseCurly + 1);
      }
   /**
      <p>Get the string signature for a {@code {@.file.textlet}} taglets only. Except where noted, this does not validate the taglet text or the returned signature.</p>

      <p>This follows the same steps as {@link com.github.aliteralmind.codelet.TagletOfTypeProcessor#getStringSignature() getStringSignature}, except<ul>
         <li>The customizer portion of the taglet, when provided, must contain either an underscore ({@code '_'}) postfix or the processor's full function name.</li>
         <li>The {@linkplain com.github.aliteralmind.codelet.CodeletType#getDefaultLineProcNamePrefix() default function name prefix} is {@code "getFileTextConfig_"}</li>
      </ul></p>

    * @exception  CodeletFormatException  If no function name or underscore-postfix is provided.
    */
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
