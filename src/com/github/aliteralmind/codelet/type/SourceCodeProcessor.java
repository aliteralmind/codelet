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
   import  com.github.aliteralmind.codelet.CodeletFormatException;
   import  com.github.aliteralmind.codelet.CodeletInstance;
   import  com.github.aliteralmind.codelet.CodeletType;
   import  com.github.aliteralmind.codelet.CustomizationInstructions;
   import  com.github.aliteralmind.codelet.TagletOfTypeProcessor;
   import  java.nio.file.AccessDeniedException;
   import  java.nio.file.NoSuchFileException;
   import  java.util.Iterator;
   import  static com.github.aliteralmind.codelet.CodeletBaseConfig.*;
/**
   <p>Reads a (source-code) {@link com.github.aliteralmind.codelet.CodeletType#SOURCE_CODE {@.codelet}} taglet and outputs its replacement text.</p>

 * @since  0.1.0
 * @author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <a href="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</a>, <a href="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</a>
 **/
public class SourceCodeProcessor extends TagletOfTypeProcessor<SourceCodeTemplate>  {
   /**
      <p>Create a new instance from an {@code CodeletInstance}.</p>

    * <p>Equal to <code>{@link com.github.aliteralmind.codelet.TagletOfTypeProcessor#TagletOfTypeProcessor(CodeletType, CodeletInstance) super}(CodeletType.SOURCE_CODE, instance)</code>
    */
   public SourceCodeProcessor(CodeletInstance instance) throws ClassNotFoundException, NoSuchMethodException, NoSuchFileException, AccessDeniedException  {
      super(CodeletType.SOURCE_CODE, instance);

      if(getClassOrFilePortion().contains("("))  {
         throw  new CodeletFormatException(instance, "Source code taglets cannot contain command-line parameters");
      }

      Iterator<String> srcLineItr = getSourceCodeLineIterator(instance);

      CustomizationInstructions<SourceCodeTemplate> instructions =
         getCustomizationInstructions(CodeletType.SOURCE_CODE);

      String customizedSourceCode = instructions.getCustomizedBody(
         instance, srcLineItr);

      boolean doDebug = isDebugOn(instance, "zzSourceCodeProcessor.progress");

      if(doDebug)  {
         debugln("   Getting template.");
      }

      SourceCodeTemplate template = getTemplateFromInstructionsOverrideOrDefault(
         instructions);

      if(doDebug)  {
         debugln("   Filling body text.");
      }

      String finalOutput = template.fillBody(customizedSourceCode).getRendered(instance);

      if(doDebug)  {
         debugln("   Setting fully-processed output.");
      }

      setFullyProcessedOutput(finalOutput);
   }
}
