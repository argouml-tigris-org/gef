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




// File: VectorSet.java
// Classes: VectorSet
// Original Author: jrobbins@ics.uci.edu
// $Id$

package org.tigris.gef.util;

import java.io.Serializable;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

/** An Ordered, non-duplicated collecton of objects (not exactly a
 *  mathemetical set because it is ordered).  Implemented with a
 *  Vector. */

public class VectorSet implements Serializable, Set  {
  ////////////////////////////////////////////////////////////////
  // constants
  public static final int TC_LIMIT = 50;

  ////////////////////////////////////////////////////////////////
  // instance variables
  private Vector vector;

  ////////////////////////////////////////////////////////////////
  // constructors
  
  public VectorSet() {
      vector = new Vector();
  }
  public VectorSet(int n) {
      vector = new Vector(n);
  }
  public VectorSet(Object o1) {
      vector = new Vector(); addElement(o1);
  }

  public void addElement(Object o) {
      if (!contains(o)) vector.addElement(o);
  }
  public void addAllElements(Collection v) {
    if (v == null) return;
    addAllElements(v.iterator());
  }
  public void addAllElements(Enumeration iter) {
    while (iter.hasMoreElements()) {
      addElement(iter.nextElement());
    }
  }
  public void addAllElements(Iterator iter) {
    while (iter.hasNext()) {
      addElement(iter.next());
    }
  }
  public void addAllElementsSuchThat(Enumeration iter, Predicate p) {
    if (p instanceof PredicateTrue) addAllElements(iter);
    else 
      while (iter.hasMoreElements()) {
	Object e = iter.nextElement();
	if (p.predicate(e)) addElement(e);
      }
  }  
  public void addAllElementsSuchThat(Iterator iter, Predicate p) {
    if (p instanceof PredicateTrue) addAllElements(iter);
    else
      while (iter.hasNext()) {
	Object e = iter.next();
	if (p.predicate(e)) addElement(e);
      }
  }
  public void addAllElements(VectorSet s) {
      addAllElements(s.elements());
  }
  
  public void addAllElementsSuchThat(VectorSet s, Predicate p) {
    addAllElementsSuchThat(s.elements(), p);
  }

  public boolean remove(Object o) {
      boolean result = contains(o);
      if (o != null) vector.removeElement(o);
      return result;
  }
  
  public void removeElement(Object o) {
      if (o != null) vector.removeElement(o);
  }
  
  public void removeAllElements() {
      vector.removeAllElements();
  }
  
  public boolean contains(Object o) {
    if (o != null) return vector.contains(o);
    return false;
  }
  
  public boolean containsSuchThat(Predicate p) {
    return findSuchThat(p) != null;
  }

  /** return the first element that causes p.predicate() to return
   * true. */
  public Object findSuchThat(Predicate p) {
    Enumeration elts = elements();
    while (elts.hasMoreElements()) {
      Object o = elts.nextElement();
      if (p.predicate(o)) return o;
    }
    return null;
  }

  public Enumeration elements() {
      return vector.elements();
  }

  public Object elementAt(int index) {
      return vector.elementAt(index);
  }

  public Vector asVector() {
      return vector;
  }

  public boolean equals(Object o) {
    if (!(o instanceof VectorSet)) return false;
    VectorSet set = (VectorSet) o;
    if (set.size() != size()) return false;
    Enumeration myEs = elements();
    while (myEs.hasMoreElements()) {
      Object obj = myEs.nextElement();
      if (!(set.contains(obj))) return false;
    }
    return true;
  }


  public Object firstElement() {
      return vector.firstElement();
  }

  public int size() {
      return vector.size();
  }
  
    public String toString() {
        String res = "Set{";
        Enumeration eles = elements();
        while (eles.hasMoreElements()) {
            res += eles.nextElement();
            if (eles.hasMoreElements()) res += ", ";
        }
        return res + "}";
    }

    /** Reply the Set of all objects that can be reached from the
     * receiving Set by taking steps defined by the given
     * ChildGenerator.  The result includes the elements of the original
     * Set. In order to avoid very deep searches which are often
     * programming mistakes, only paths of length TC_LIMIT or less are
     * considered. */
    public VectorSet transitiveClosure(ChildGenerator cg) {
        return transitiveClosure(cg, TC_LIMIT, PredicateTrue.theInstance());
    }

    /** Reply the Set of all objects that can be reached from the
     * receiving Set by taking steps defined by the given
     * ChildGenerator.  The result DOES NOT include the elements of the
     * original Set. In order to avoid very deep searches which are
     * often programming mistakes, only paths of length TC_LIMIT or less
     * are considered.*/
    public VectorSet reachable(ChildGenerator cg) {
        return reachable(cg, TC_LIMIT, PredicateTrue.theInstance());
    }

    /** Reply the Set of all objects that can be reached from the
     * receiving Set by taking steps defined by the given
     * ChildGenerator.  The result DOES NOT include the elements of the
     * original Set. In order to avoid very deep searches which are
     * often programming mistakes, only paths of given max length or
     * less are considered. Only paths consisting of elements which all
     * cause p.predicate() to return true are considered. */
    public VectorSet reachable(ChildGenerator cg, int max, Predicate p) {
        VectorSet kids = new VectorSet();
        Enumeration rootEnum = elements();
        while (rootEnum.hasMoreElements()) {
            Object r = rootEnum.nextElement();
            kids.addAllElementsSuchThat(cg.gen(r), p);
        }
        return kids.transitiveClosure(cg, max, p);
    }

    /** Reply the Set of all objects that can be reached from the
     * receiving Set by taking steps defined by the given
     * ChildGenerator.  The result includes the elements of the original
     * Set. In order to avoid very deep searches which are often
     * programming mistakes, only paths of given max length or less are
     * considered. Only paths consisting of elements which all cause
     * p.predicate() to return true are considered. */
    public VectorSet transitiveClosure(ChildGenerator cg, int max, Predicate p) {
        int iterCount = 0;
        int lastSize = -1;
        VectorSet touched = new VectorSet();
        VectorSet frontier;
        VectorSet recent = this;
    
        touched.addAllElements(this);
        while ((iterCount < max) && (touched.size() > lastSize)) {
            iterCount++;
            lastSize = touched.size();
            frontier = new VectorSet();
            Enumeration recentEnum = recent.elements();
            while (recentEnum.hasMoreElements()) {
            	Enumeration frontsEnum = cg.gen(recentEnum.nextElement());
            	frontier.addAllElementsSuchThat(frontsEnum, p);
            }
            touched.addAllElements(frontier);
            recent = frontier;
        }
        return touched;
    }
    
    public boolean isEmpty() {
        return vector.isEmpty();
    }
    
    public Iterator iterator() {
        return vector.iterator();
    }
    
    public Object[] toArray() {
        return vector.toArray();
    }
    
    public Object[] toArray(Object[] arg0) {
        return vector.toArray(arg0);
    }
    
    public boolean add(Object arg0) {
        boolean result = contains(arg0);
        addElement(arg0);
        return !result;
    }
   
    public boolean containsAll(Collection arg0) {
        return vector.containsAll(arg0);
    }
    

    public boolean addAll(Collection arg0) {
        boolean result = containsAll(arg0);
        addAllElements(arg0);
        return !result;
        
    }
    
    public boolean retainAll(Collection arg0) {
        Vector copy = (Vector) vector.clone();
        boolean result = false;
        for (Iterator iter=copy.iterator(); iter.hasNext();) {
            Object elem = iter.next();
            if (!arg0.contains(elem)) result = result || remove(elem);
        }
        return result;
    }
    
    public boolean removeAll(Collection arg0) {
        boolean result = false;
        for (Iterator iter = arg0.iterator(); iter.hasNext();) {
           result = result || remove(iter.next());
        }
        return result;
        
    }
    
    public void clear() {
        vector.clear();        
    }

} /* end class VectorSet */
