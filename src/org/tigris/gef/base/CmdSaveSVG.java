// Copyright (c) 1996-99 The Regents of the University of California. All
// Rights Reserved. Permission to use, copy, modify, and distribute this
// software and its documentation without fee, and without a written
// agreement is hereby granted, provided that the above copyright notice
// and this paragraph appear in all copies.  This software program and
// documentation are copyrighted by The Regents of the University of
// California. The software program and documentation are supplied "AS
// IS", without any accompanying services from The Regents. The Regents
// does not warrant that the operation of the program will be
// uninterrupted or error-free. The end-user understands that the program
// was developed for research purposes and is advised not to rely
// exclusively on the program for any reason.  IN NO EVENT SHALL THE
// UNIVERSITY OF CALIFORNIA BE LIABLE TO ANY PARTY FOR DIRECT, INDIRECT,
// SPECIAL, INCIDENTAL, OR CONSEQUENTIAL DAMAGES, INCLUDING LOST PROFITS,
// ARISING OUT OF THE USE OF THIS SOFTWARE AND ITS DOCUMENTATION, EVEN IF
// THE UNIVERSITY OF CALIFORNIA HAS BEEN ADVISED OF THE POSSIBILITY OF
// SUCH DAMAGE. THE UNIVERSITY OF CALIFORNIA SPECIFICALLY DISCLAIMS ANY
// WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF
// MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE. THE SOFTWARE
// PROVIDED HEREUNDER IS ON AN "AS IS" BASIS, AND THE UNIVERSITY OF
// CALIFORNIA HAS NO OBLIGATIONS TO PROVIDE MAINTENANCE, SUPPORT,
// UPDATES, ENHANCEMENTS, OR MODIFICATIONS.

// File: CmdSaveSVG.java
// Classes: CmdSaveSVG

package org.tigris.gef.base;

import java.util.*;
import java.io.*;
import java.awt.Rectangle;

import org.tigris.gef.persistence.*;


public class CmdSaveSVG extends CmdSaveGraphics {

  public CmdSaveSVG() {
    super("Save Scalable Vector Graphics...", NO_ICON);
  }

  protected void saveGraphics(OutputStream s, Editor ce,
			      Rectangle drawingArea)
                 throws IOException {
	  System.out.println("Writing Scalable Vector Graphics...");
	  SVGWriter writer = null;
	  try {
	      writer = new SVGWriter(s, drawingArea);
	  } catch (Exception e) {
	      System.out.println("Whatever this exception may be..." +e);
	      e.printStackTrace();
	  }
	  if (writer != null) {   
	      ce.print(writer);
	      writer.dispose();
	      System.out.println("Wrote Scalable Vector Graphics...");
	  }
  }

} /* end class CmdSaveSVG */
