/*license*\
   Codelet: http://codelet.aliteralmind.com

   Copyright (c) 2014, Jeff Epstein (aliteralmind __DASH__ github __AT__ yahoo __DOT__ com)

   This software is dual-licensed under the:
   - Lesser General Public License (LGPL) version 3.0 or, at your option, any later version;
   - Apache Software License (ASL) version 2.0.

   Either license may be applied at your discretion. More information may be found at
   - http://en.wikipedia.org/wiki/Multi-licensing.

   The text of both licenses is available in the root directory of this project, under the names "LICENSE_lgpl-3.0.txt" and "LICENSE_asl-2.0.txt". The latest copies may be downloaded at:
   - LGPL 3.0: https://www.gnu.org/licenses/lgpl-3.0.txt
   - ASL 2.0: http://www.apache.org/licenses/LICENSE-2.0.txt
\*license*/
package  com.github.aliteralmind.codelet.linefilter;
   import  com.github.xbn.analyze.alter.ValueAlterer;
   import  com.github.xbn.analyze.alter.AlterValueWhen;
   import  com.github.xbn.analyze.validate.ValueValidator;
/**
   <P>An {@code AlterValueWhen} for {@code LineObject}s.</P>

   @author  Copyright (C) 2014, Jeff Epstein, dual-licensed under the LGPL (version 3.0 or later) or the ASL (version 2.0). See source code for details. <A HREF="http://codelet.aliteralmind.com">{@code http://codelet.aliteralmind.com}</A>, <A HREF="https://github.com/aliteralmind/codelet">{@code https://github.com/aliteralmind/codelet}</A>
 **/
public class AlterLineWhen<O,L extends LineObject<O>> extends AlterValueWhen<L,O>  {
   public AlterLineWhen()  {
      super();
   }
   public AlterLineWhen(ValueAlterer<L,O> alter_forValid)  {
      super(alter_forValid);
   }
   public AlterLineWhen(ValueValidator<L> condition, ValueAlterer<L,O> alter_forValid)  {
      super(condition, alter_forValid);
   }
   public AlterLineWhen(ValueValidator<L> condition, ValueAlterer<L,O> alter_forValid, ValueAlterer<L,O> alter_forInvalid)  {
      super(condition, alter_forValid, alter_forInvalid);
   }
   public AlterLineWhen(AlterLineWhen<O,L> to_copy)  {
      super(to_copy);
   }
}
