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


package uci.uml.Behavioral_Elements.State_Machines;

import java.util.*;
import java.beans.*;

import uci.uml.Behavioral_Elements.Common_Behavior.Signal;
import uci.uml.Foundation.Data_Types.*;

public class SignalEvent extends Event {
  public Signal _signal;

  public SignalEvent() { }
  public SignalEvent(Name name, Signal s) {
    super(name);
    try { setSignal(s); }
    catch (PropertyVetoException pve) { }
  }
  public SignalEvent(String nameStr) { super(new Name(nameStr)); }

  public Signal getSignal() { return _signal; }
  public void setSignal(Signal x) throws PropertyVetoException {
    fireVetoableChange("signal", _signal, x);
    _signal = x;
  }

  static final long serialVersionUID = -3139229055741844228L;
}