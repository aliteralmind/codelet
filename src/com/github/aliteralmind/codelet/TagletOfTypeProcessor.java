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
package  com.github.aliteralmind.codelet;
	import  com.github.xbn.text.StringWithNullDefault;
	import  com.github.xbn.io.PlainTextFileUtil;
	import  org.apache.commons.io.FilenameUtils;
	import  com.github.xbn.lang.CrashIfObject;
	import  com.github.xbn.text.CrashIfString;
	import  com.github.xbn.lang.reflect.InvokeMethodWithRtx;
	import  com.github.xbn.lang.reflect.ReflectRtxUtil;
	import  com.github.aliteralmind.codelet.simplesig.SimpleMethodSignature;
	import  java.lang.reflect.Method;
	import  java.nio.file.AccessDeniedException;
	import  java.nio.file.NoSuchFileException;
	import  java.util.Iterator;
	import  java.util.List;
	import  java.util.Objects;
	import  static com.github.aliteralmind.codelet.CodeletBaseConfig.*;
	import  static com.github.xbn.lang.XbnConstants.*;
/**
	<P>Base class for type-specific Codelet processors. Called by {@link com.github.aliteralmind.codelet.TagletProcessor}.</P>

	@since  0.1.0
	@author  Copyright (C) 2014, Jeff Epstein ({@code aliteralmind __DASH__ github __AT__ yahoo __DOT__ com}), dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
 **/
public class TagletOfTypeProcessor<T extends CodeletTemplateBase>  {
	private final CodeletInstance     instance  ;
	private final String              objTagTxt ;
	private final String              procTagTxt;
	private String fullyProcessedOutput;
	/**
		<P>The name of the class in each package that <I>uses</I> codelets, that is the &quot;fallback&quot; location for IXC-customizers--Equal to {@code "zCodeletCustomizers"}. This class is only applicable for customizers that are<UL>
			<LI>Not in {@link com.github.aliteralmind.codelet.BasicCustomizers BasicCustomizers},</LI>
			<LI>Not in the taglet's {@linkplain CodeletInstance#getEnclosingPackage() enclosing class}, and</LI>
			<LI>Not explicitely specified in the taglet's text (after the customizer {@linkplain CodeletInstance#CUSTOMIZER_PREFIX_CHAR prefix character}).</LI>
		</UL></P>
	 **/
	public static final String DEFAULT_CUSTOMIZER_CLASS_NAME = "zCodeletCustomizers";
	/**
		<P>Create a new instance.</P>

		<P>Steps:<OL>
			<LI>If a Codelet customizer is<UL>
				<LI>specified (meaning its {@linkplain CodeletInstance#CUSTOMIZER_PREFIX_CHAR prefix character} is found anywhere in {@code tag_text}), this sets<OL>
					<LI>{@link #getClassOrFilePortion() getClassOrFilePortion}{@code ()} to the text preceding the prefix char.</LI>
					<LI>{@link #getCustomizerPortion() getCustomizerPortion}{@code ()} to the text following it.</LI>
				</OL></LI>
				<LI>Not specified, this sets<OL>
					<LI>{@link #getClassOrFilePortion() getClassOrFilePortion}{@code ()} to <CODE>instance.{@link com.github.aliteralmind.codelet.CodeletInstance#getText() getText}()</CODE>.</LI>
					<LI>{@link #getCustomizerPortion() getCustomizerPortion}{@code ()} to {@code null}.</LI>
				</OL></LI>
			</UL></LI>
		</OL></P>
		<P></P>

		@param  sub_classType  The taglet type of the sub-class. May not be {@code null}. Discarded after construction.
		@param  instance  May not be {@code null}, and its {@linkplain CodeletInstance#getType() type} must be the same as {@code sub_classType}. Get with {@link #getInstance() getInstance}{@code ()}.
		@exception  NoSuchMethodException  If the customizer function does not exist, either in the specified or default classes, or with the required attributes, such as missing parameters, an unexpected return value, or if it's non-static.
		@exception  NoSuchFileException  If the source-code or plain-text file does not exist.
		@exception  AccessDeniedException  If the file exists, but cannot be read.
	 **/
	public TagletOfTypeProcessor(CodeletType sub_classType, CodeletInstance instance)  {
		if(sub_classType != instance.getType())  {
			throw  new IllegalStateException("sub_classType is " + sub_classType + ", instance.getType()=" + instance.getType() + "");
		}
		Objects.requireNonNull(instance, "instance");

		this.instance = instance;

		String[] rawObjectProcStrs = TagletTextUtil.getTagletTextSplitOnLineProcDelim(instance);

		objTagTxt = rawObjectProcStrs[0];
		procTagTxt = ((rawObjectProcStrs.length == 1) ? null
			:  rawObjectProcStrs[1]);

		if(isDebugOn(instance, "zzTagletOfTypeProcessor.classcustomizersplit"))  {
			debugln("   Example-class/file-text portion: \"" + objTagTxt + "\"");
			debugln("   Customizer portion: " + StringWithNullDefault.get("\"", procTagTxt, "\"", null));
		}

		fullyProcessedOutput = null;
	}
	/**
		<P>The current taglet instance.</P>

		@see  #TagletOfTypeProcessor(CodeletType, CodeletInstance)
	 **/
	public CodeletInstance getInstance()  {
		return  instance;
	}
	/**
		<P>The example code's fully-qualified class name (plus any command-line parameters), or the path to the plain-text file. This is the portion of the taglet's text that follows the {@linkplain CodeletType#getName() name} and precedes the optional customizer {@linkplain CodeletInstance#CUSTOMIZER_PREFIX_CHAR prefix character}. If no customizer is specified, this is equal to <CODE>{@link #getInstance() getInstance}().{@link com.github.aliteralmind.codelet.CodeletInstance#getText() getText}()</CODE></P>

		@see  #TagletOfTypeProcessor(CodeletType, CodeletInstance)
	 **/
	public String getClassOrFilePortion()  {
		return  objTagTxt;
	}
	/**
		<P>If the class or file is not allowed to use the customizer, crash. Otherwise, do nothing.</P>

		@exception  IllegalStateException  If
		<BR> &nbsp; &nbsp; <CODE>{@link org.apache.commons.io.FilenameUtils}.{@link org.apache.commons.io.FilenameUtils#wildcardMatch(String, String) wildcardMatch}({@link #getClassOrFilePortion() getClassOrFilePortion}(), instructions.{@link CustomizationInstructions#getClassNameOrFilePathRestricter() getClassNameOrFilePathRestricter})</CODE>
		<BR>is {@code false}.
	 **/
	protected void crashIfClassOrFileCannotUseCustomizer(CustomizationInstructions<T> instructions)  {
		if(isDebugOn(instance, "zzSourceCodeProcessor.progress"))  {
			debugln("Verifying class/file name (\"" + getClassOrFilePortion() + "\") against instructions.getClassNameOrFilePathRestricter() (\"" + instructions.getClassNameOrFilePathRestricter() + "\")");
		}
		if(!FilenameUtils.wildcardMatch(getClassOrFilePortion(), instructions.getClassNameOrFilePathRestricter()))  {
			throw  new IllegalStateException("getClassOrFilePortion()=\"" + getClassOrFilePortion() + "\", instructions.getClassNameOrFilePathRestricter()=\"" + instructions.getClassNameOrFilePathRestricter() + "\"");
		}
	}

	/**
		<P>The optional customizer part of a codelet, as found after its prefix character. If no customizer is specified, this is {@code null}.</P>

		@see  #TagletOfTypeProcessor(CodeletType, CodeletInstance)
		@see  com.github.aliteralmind.codelet.CodeletInstance#CUSTOMIZER_PREFIX_CHAR CodeletInstance#CUSTOMIZER_PREFIX_CHAR
	 **/
	public String getCustomizerPortion()  {
		return  procTagTxt;
	}
	public String getFullyCustomizedOutput()  {
		return  fullyProcessedOutput;
	}
	protected void setFullyProcessedOutput(String fully_processed)  {
		CrashIfString.nullEmpty(fully_processed, "fully_processed", null);
		fullyProcessedOutput = fully_processed;
	}
/*
	public T getTemplateFromInstructionsOverrideOrDefault(T tmpl_fromCustomInstructions, Appendable debugDest_ifNonNull)  {
		T t = null;
		if(tmpl_fromCustomInstructions != null)  {
			t = tmpl_fromCustomInstructions;
			t.setDebug(debugDest_ifNonNull, (debugDest_ifNonNull != null));
		}  else  {
			t = TemplateOverrides.INSTANCE.<T>get(getInstance(), debugDest_ifNonNull);
		}
		return  t;
	}
 */
	public T getTemplateFromInstructionsOverrideOrDefault(CustomizationInstructions<T> instructions)  {
		T t = null;
		if(instructions.getTemplate() != null)  {
			t = instructions.getTemplate();
			t.setDebug(instructions.getDefaultTemplateDebug(),
				(instructions.getDefaultTemplateDebug() != null));
		}  else  {
			t = TemplateOverrides.<T>get(getInstance(),
				instructions.getDefaultTemplateDebug());
		}
		return  t;
	}
	/**
		<P>Get the string-signature for executing the example code.</P>

		@return  {@code clsNm + "#main(" + params + ")"}
		<BR>Where {@code clsNm} is
		<BR> &nbsp; &nbsp; <CODE>{@link com.github.aliteralmind.codelet.TagletTextUtil TagletTextUtil}.{@link com.github.aliteralmind.codelet.TagletTextUtil#getExampleClassFQName(CodeletInstance) getExampleClassFQName}(instance)</CODE>
		<BR>and {@code params} is
		<BR> &nbsp; &nbsp; <CODE>{@link com.github.aliteralmind.codelet.TagletTextUtil TagletTextUtil}.{@link com.github.aliteralmind.codelet.TagletTextUtil#getExampleCommandLineParams(CodeletInstance) getExampleCommandLineParams}(instance)</CODE>
	 **/
	public String getExampleCodeStringSig()  {
		String clsNm = TagletTextUtil.getExampleClassFQName(getInstance());
		String params = TagletTextUtil.getExampleCommandLineParams(getInstance());

		String strSig = clsNm + "#main(" + params + ")";

		if(isDebugOn(getInstance(),
				"zzTagletOfTypeProcessor.getCustomizerSigFromString"))  {
			debugln("   Example code string sig: " + strSig);
		}

		return  strSig;
	}
	public CustomizationInstructions<T> getCustomCustomizationInstructions(CodeletType needed_defaultAlterType) throws ClassNotFoundException, NoSuchMethodException, SecurityException  {
		String strSig = getStringSignature();
		SimpleMethodSignature sig = getCustomizerSigFromString(strSig);
		return  getInstructionsFromSig(sig, needed_defaultAlterType);
	}
	/**
		<P>Invoke the customizer and get its return value, as required by the taglet-processor--For {@code {@.codelet.and.out}} taglets only.</P>

		<P>This {@linkplain com.github.aliteralmind.codelet.simplesig.SimpleMethodSignature#getMethodFromParamValueList(List) obtains} and then {@linkplain java.lang.reflect.Method#invoke(Object, Object...) invokes} the customizer signature, and returns its return value, which must be a {@code CustomizationInstructions} of the type required by the processor.</P>

		@param  customizer_sig  May not be {@code null}, and the function in represents must conform to the requirements.
		@exception  IllegalArgumentException  If
		<BR> &nbsp; &nbsp; <CODE><I>[the-instructions-returned-by-the-customizer]</I>.{@link CustomizationInstructions#getNeededAlterArrayType() getNeededAlterArrayType}()</CODE>
		<BR>is different than <CODE>needed_defaultAlterType</CODE>. See
		<BR> &nbsp; &nbsp; <CODE>{@link CustomizationInstructions#CustomizationInstructions(CodeletInstance, CodeletType)}</CODE>
	 **/
	public CustomizationInstructions<T> getInstructionsFromSig(SimpleMethodSignature customizer_sig, CodeletType needed_defaultAlterType) throws ClassNotFoundException, NoSuchMethodException, SecurityException  {

		List<Object> paramValueList = null;
		try  {
			//Must add the non-extra parameters to the object list. Since they're
			//not "simple" types (primitives or strings), they can't added to the string-signature.
			paramValueList = customizer_sig.getParamValueObjectList();
			paramValueList.add(0, getInstance());
			paramValueList.add(1, needed_defaultAlterType);
		}  catch(RuntimeException rx)  {
			throw  CrashIfObject.nullOrReturnCause(customizer_sig, "customizer_sig", null, rx);
		}

		Method customizer = null;
		try  {
			customizer = customizer_sig.getMethodFromParamValueList(paramValueList);
		}  catch(NoSuchMethodException nsmx)  {
			throw  new NoSuchMethodException("Customizer function not found (is its containing class compiled?):" + LINE_SEP +
				" - class.function: " + customizer_sig.getStringSig_ret_class_func_params(false, true, true, true) + LINE_SEP +
				" - Taglet: " + getInstance() +
				LINE_SEP + " - Original error: <<" + nsmx + ">>");
		}

		boolean doDebug = isDebugOn(getInstance(),
			"zzTagletOfTypeProcessor.getCustomizerSigFromString");
		if(doDebug)  {
			debugln("      Customizer method obtained...");
		}

		customizer.setAccessible(true);

		if(doDebug)  {
			debugln("      ...made accessible...");
		}

/*
		//The below call to sstatic() makes this redundant
		if(!Modifier.isStatic(customizer.getModifiers()))  {
			throw  new NoSuchMethodException("Method found, but not static: " + customizer_sig);
		}
 */

		Object o = new InvokeMethodWithRtx(customizer).
			sstatic().parameters(paramValueList.toArray()).
			invokeGetReturnValueWXtraInfo("Attempting to execute the *static* Codelet customizer.");


		if(doDebug)  {
			debugln("      ...invoked...");
		}

		try  {
			@SuppressWarnings("unchecked")
			CustomizationInstructions<T> instructions = (CustomizationInstructions<T>)o;

			if(doDebug)  {
				debugln("      ...return-value cast properly to CustomizationInstructions<T> (done)");
			}

			if(instructions.getNeededAlterArrayType() != needed_defaultAlterType)  {
				throw  new IllegalArgumentException("The returned CustomizationInstructions' getNeededAlterArrayType() (" + instructions.getNeededAlterArrayType() + ") is different than needed_defaultAlterType (" + needed_defaultAlterType + ").");
			}

			return  instructions;
		}  catch(ClassCastException ccx)  {
			throw  new IllegalStateException("Customizer invoked properly, but its return value cannot be cast to a C (CustomizationInstructions): return-type=" + o.getClass().getName() + ", value=[" + o + "]");
		}
	}
	/**
		<P>Given the customizer's fully-expanded string-signature, get its simple-signature.</P>

		<P>Steps:<OL>
			<LI>If there is no customizer: This <I><B>returns</B></I> {@code null}.</LI>
			<LI>If the fully-qualified class-name, in which the customizer function exists, is<UL>
				<LI>not specified (before the {@code '#'}): The following classes are set as its defaults, one in which the customizer function must exist (although that's verified externally--not by this function). They are search in order:<OL>
				<LI>{@link com.github.aliteralmind.codelet.BasicCustomizers BasicCustomizers}</LI>
				<LI>The taglet's {@linkplain CodeletInstance#getEnclosingPackage() enclosing class}</LI>
				<LI>And <I>if it exists:</I> The {@linkplain CodeletInstance#getEnclosingPackage() enclosing package}'s {@linkplain #DEFAULT_CUSTOMIZER_CLASS_NAME default processor class}.</LI>
			</OL></LI>
			<LI><I><B>Returns</B></I> a new {@link com.github.aliteralmind.codelet.simplesig.SimpleMethodSignature#newFromStringAndDefaults(Class, Object, String, Class[], Appendable) simple signature}</LI>
		</OL></P>

		@param  customizer_strSig  The expanded string-signature. If {@code null}, no customizer is specified.
		@exception  ClassNotFoundException  If the class name, but not its package, is in the string-signature, and the class does not exist in the enclosing package.
		@see  #getStringSignature()
	 **/
	public SimpleMethodSignature getCustomizerSigFromString(String customizer_strSig) throws ClassNotFoundException  {
		if(customizer_strSig == null)  {
			return  null;
		}
/*
		//The line processor's class is explicitly specified.
		Class<?> lineProcCls = null;
		String lineProcClsNm = customizer_strSig.substring(0, hashIdx);
		try  {
			lineProcCls = Class.forName(lineProcClsNm);
		}  catch(ClassNotFoundException cnfx)  {
			throw  new CodeletFormatException(getInstance(), "Unknown line processor class: \"" + lineProcClsNm + "\".");
		}
 */

 		boolean doDebug = isDebugOn(getInstance(),
			"zzTagletOfTypeProcessor.getCustomizerSigFromString");

		Class<?>[] allPossibleClasses = null;
		int openParenIdx = customizer_strSig.indexOf("(");
		int hashIdx = customizer_strSig.indexOf("#");
		if(hashIdx != -1  &&  hashIdx < openParenIdx)  {
			if(doDebug)  {
				debugln("   Customizer-function's containing class specified in taglet: [" + customizer_strSig.substring(0, hashIdx) + "]");
			}
		}  else  {

			if(doDebug)  {
				debugln("   Customizer-function's containing class not specified. Searching for potential containing classes...");
			}

			Class<?> defaultCustomizerClsForPkg = null;
			Class<?> containingClass = null;

			if(getInstance().getEnclosingPackage().length() == 0)  {
				if(doDebug)  {
					debugln("      Enclosing file (" + getInstance().getEnclosingFullyQualifiedName() + ") has no package. The default customizer class for packages (" + DEFAULT_CUSTOMIZER_CLASS_NAME + ") does not (cannot) exist for this taglet. Classes not in a package (if this is indeed a class) cannot hold customizer functions.");
				}
			}  else  {
				String defaultCstmzrClsNmForPkg = getInstance().getEnclosingPackage() + "." + DEFAULT_CUSTOMIZER_CLASS_NAME;
				if(doDebug)  {
					debugln("      Testing package-default customizer class for existence: \"" + defaultCstmzrClsNmForPkg + "\"");
				}

				defaultCustomizerClsForPkg = ReflectRtxUtil.getClassIfExistsOrNull(defaultCstmzrClsNmForPkg);

				if(doDebug)  {
					debugln("      Exists? " + (defaultCustomizerClsForPkg != null));
				}

				containingClass = getInstance().getEnclosingClass();

				if(doDebug)  {
					debugln("      Enclosing file, " + getInstance().getEnclosingFullyQualifiedName() + ", is " + ((containingClass == null)
						?  "not a class (or is a class, but is not yet compiled)"
						:  "a class") + ".");
				}
			}


			int arraySize = 1 +
				((containingClass == null) ? 0 : 1) +
				((defaultCustomizerClsForPkg == null) ? 0 : 1);

			allPossibleClasses = new Class[arraySize];

			//Definitely one
			allPossibleClasses[0] = BasicCustomizers.class;

			//Possibly two
			if(containingClass != null)  {
				allPossibleClasses[1] = containingClass;

				//Possibly three
				if(defaultCustomizerClsForPkg != null)  {
					allPossibleClasses[2] = defaultCustomizerClsForPkg;
				}

			 //ELSE: Possibly two
			}  else if(defaultCustomizerClsForPkg != null)  {
				allPossibleClasses[1] = defaultCustomizerClsForPkg;
			}

			if(doDebug)  {
				debugln("      ...Done. All potential containing classes:");
				for(int i = 0; i < allPossibleClasses.length; i++)  {
					debugln("    - " + i + ": " + allPossibleClasses[i].getName());
				}
			}
		}

//		String pkg = getInstance().getEnclosingPackage();
//		pkg = ((pkg == "") ? null : pkg);
		SimpleMethodSignature sig = SimpleMethodSignature.newFromStringAndDefaults(
			CustomizationInstructions.class, customizer_strSig, null, allPossibleClasses,
			getDebugApblIfOn(getInstance(),
			"zzTagletOfTypeProcessor.getCustomizerSigFromString"));

		if(doDebug)  {
			debugln("   Customizer: " + sig);
		}

		return  sig;
	}
	/**
		<P>Expands a potentially-shortened customizer signature (shortcut) to its full string-signature, for {@link com.github.aliteralmind.codelet.CodeletType#SOURCE_CODE {@.codelet}} and {@link com.github.aliteralmind.codelet.CodeletType#CONSOLE_OUT {@.codelet.out}} taglets only. This does not validate the taglet text or the returned signature.</P>

		<P>Steps:<OL>
			<LI>If no {@linkplain TagletOfTypeProcessor#getCustomizerPortion() customizer} is in the taglet, this <I><B>returns</B></I> {@code null}.</LI>
			<LI>If it is equal to {@code "()"}, this <I><B>returns</B></I> the default Codelet customizer function name<OL>
				<LI>The taglet-type's {@linkplain CodeletType#getDefaultLineProcNamePrefix() default prefix}</LI>
				<LI>The example classes {@linkplain java.lang.Class#getSimpleName() simple name} (the text after the final dot: {@code '.'} in the example code's {@linkplain TagletTextUtil#getExampleClassFQName(CodeletInstance) fully-qualified class name}).</LI>
				<LI>{@code "()"}</LI>
			</OL>Example: {@code "getSourceConfigAGreatExample()"}</LI>
			<LI>If the line-proc starts with an underscore ({@code '_'}), it is the function name's postfix, which is appended after the classes simple name. This <I><B>returns</B></I> (for example)
			<BR> &nbsp; &nbsp; {@code "getSourceConfigAGreatExample_DoSpecialStuff()"}</LI>
			<LI>This <I><B>returns</B></I> the line-proc portion of the taglet's text unchanged.</LI>
		</OL><I>In all cases, if there are no paremeters specified, empty parentheses are appended to the signature: {@code "()"}</I></P>
	 **/
	public String getStringSignature()  {
		String sig = getCustomizerPortion();
//		String xmplFqName = TagletTextUtil.getExampleClassFQName(getInstance());

		boolean doDebug = isDebugOn(getInstance(),
			"zzTagletOfTypeProcessor.getCustomizerSigFromString");

		if(sig == null)  {

			if(doDebug)  {
				debugln("   No Customizer");
			}

			return  null;
		}

		if(sig.equals("()"))  {
			String strSig = getInstance().getType().getDefaultLineProcNamePrefix() +
				TagletTextUtil.getExampleSimpleClassName(getInstance()) +
				sig;  //sig equals "()"

			if(doDebug)  {
				debugln("   Customizer string-sig (using \":()\" shortcut): " + strSig);
			}

			return  strSig;
		}

		String fileOrClsSimpleName = ((getInstance().getType().isFileText())
			?  TagletTextUtil.getFileNameWithExtension(getInstance())
			:  TagletTextUtil.getExampleSimpleClassName(getInstance()));

		String strSig = getSig2PrnsApnddForNameOrPostfix(fileOrClsSimpleName + "_", sig,  doDebug);

		if(doDebug)  {
			debugln("   Customizer string-sig: " + strSig);
		}

		return  strSig;
	}
	protected String getSig2PrnsApnddForNameOrPostfix(String betweenPre_andPostInclUndrScr, String sig, boolean do_debug)  {
		if(!sig.endsWith(")"))  {
			throw  new CodeletFormatException(getInstance(), "No parentheses in customizer portion [last character not a ')' ].");
		}
		if(sig.charAt(0) == '_')  {
			//No class specified.

			if(do_debug)  {
				debugln("   Customizer portion (\"" + sig + "\") starts with '_' postfix. Expanding:");
			}

			try  {
				sig = getInstance().getType().getDefaultLineProcNamePrefix() + betweenPre_andPostInclUndrScr + sig.substring(1);
			}  catch(StringIndexOutOfBoundsException sbx)  {
				throw  new CodeletFormatException(getInstance(), "Customizer portion is *equal* to the underscore. Function-name postfix must follow the underscore.", sbx);
			}

			if(do_debug)  {
				debugln("   Expanded: \"" + sig + "\"");
			}
			return  sig;
		}

		int idxHashUnder = sig.indexOf("#_");
		if(idxHashUnder == -1)  {
			return  sig;
		}

		//Class specified, but only func-name postfix specified

		try  {
			return  sig.substring(0, idxHashUnder) + "#" + getInstance().getType().getDefaultLineProcNamePrefix() + betweenPre_andPostInclUndrScr + sig.substring(idxHashUnder + 2);
		}  catch(StringIndexOutOfBoundsException sbx)  {
			throw  new CodeletFormatException(getInstance(), "Customizer portion *ends with* '#_'. Function-name postfix must follow the underscore.", sbx);
		}
	}
/*
	public CustomizationInstructions<T> newInstructionsForDefaults(CodeletInstance instance)  {
		return  newInstructionsForDefaults(new CustomizationInstructions<T>(instance));
	}
 */
	public CustomizationInstructions<T> newInstructionsForDefaults(CustomizationInstructions<T> instructions)  {
		String prefix = "zzTagletOfTypeProcessor.newInstructionsForDefaults.";
		return  instructions.defaults(
				getDebugApblIfOn(getInstance(), prefix + "filter.alllines"), null,
				getDebugApblIfOn(getInstance(), prefix + "allalterer"),
				getDebugApblIfOn(getInstance(), prefix + "templateparseandfill")).
			build();
	}
	public Iterator<String> getSourceCodeLineIterator(CodeletInstance instance)  {
		if(isDebugOn(instance, "zzSourceCodeProcessor.progress"))  {
			debugln("Obtaining line-iterator to source code.");
		}

		return  PlainTextFileUtil.getLineIterator(TagletTextUtil.getJavaSourceFilePath(instance), "TagletTextUtil.getJavaSourceFilePath(instance)");
	}
	public CustomizationInstructions<T> getCustomizationInstructions(CodeletType needed_defaultAlterType) throws ClassNotFoundException, NoSuchMethodException, NoSuchFileException, AccessDeniedException  {
		if(isDebugOn(getInstance(), "zzSourceCodeProcessor.progress"))  {
			debugln("   Obtaining instructions for customizer portion: " +
				StringWithNullDefault.get("\"", getCustomizerPortion(), "\"",
					null));
		}

		CustomizationInstructions<T> instructions =
			((getCustomizerPortion() != null)
				?  getCustomCustomizationInstructions(needed_defaultAlterType)
				:  newInstructionsForDefaults(new CustomizationInstructions<T>(instance, needed_defaultAlterType)));

		crashIfClassOrFileCannotUseCustomizer(instructions);

		return  instructions;
	}
}
