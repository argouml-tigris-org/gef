package org.tigris.gef.presentation;

import java.awt.Color;
import org.tigris.gef.base.*;

// each Anotation has one associated AnotationProperties object
public class AnotationProperties {

        private boolean fixedOffset = false;
        private boolean fixedRatio = false;
        private int offset = 10;
        private float ratio = (float)0.5;
	private int connectingLineVisibilityDuration = 300;
	private FigLine line = new FigLine(0,0,0,0);
	private Color lineColor = Color.red;
	private boolean deleteR = false;
	private boolean remote = false;
	
	
	public AnotationProperties(){
		this(false,5,false,(float)0.5);
	}	

        public AnotationProperties(boolean fixedOffset, int offset, boolean fixedRatio, float ratio){
                this.offset = offset;
                this.ratio = ratio;
                this.fixedOffset = fixedOffset;
                this.fixedRatio = fixedRatio;
		// connectingLine visible for 300 ms
        }

	public AnotationProperties(int offset, float ratio){
		this(false, offset, false, ratio);
	}

	public void setLineColor(Color c){
		lineColor = c;
	}
	
	public Color getLineColor(){
		return lineColor;
	}
	
	public void setLineVisibilityDuration(int millis){
		connectingLineVisibilityDuration = millis;
	}
	
	public int getLineVisibilityDuration(){
		return connectingLineVisibilityDuration;
	}

	public boolean hasFixedRatio(){
		return fixedRatio;
	}

	public boolean hasFixedOffset(){
		return fixedOffset;
	}

	public float getRatio(){ return ratio; }
	public void setRatio(float ratio, boolean fixedRatio){
		this.ratio = ratio;
		this.fixedRatio = fixedRatio;
	}

	public int getOffset(){ return offset; }
	public void setOffset(int offset, boolean fixedOffset){
		this.offset = offset;
		this.fixedOffset = fixedOffset;
	}

	public FigLine getConnectingLine(){
		return line;
	}
	
	// line is visible only if anotation is visible
	protected boolean lineIsVisible(Fig anotation){ 
		return anotationIsVisible(anotation);
	}
	
	protected boolean anotationIsVisible(Fig anotation){
		return true;
	}
	
	// removes the line from the active diagram
	public synchronized void removeLine(){
		if (Globals.curEditor().getLayerManager().getContents().contains(line))
				line.delete();
	}
		

} // end of class