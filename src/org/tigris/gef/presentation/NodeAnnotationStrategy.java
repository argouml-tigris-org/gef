package org.tigris.gef.presentation;

import java.util.*;
import java.awt.*;
import org.tigris.gef.base.*;



public class NodeAnnotationStrategy extends AnnotationStrategy{
	
	public NodeAnnotationStrategy(){
	}

	public Point restoreAnnotationPosition(Fig annotation){
		int delta_x;
		float delta_y;
		Fig owner = annotation.getAnnotationOwner();
		// in this case: owner is a node
		if (! ( (owner instanceof FigEdge) || (owner instanceof FigLine) )){
			AnnotationProperties prop = (AnnotationProperties)annotations.get(annotation);
			delta_x = prop.getOffset();
			delta_y = prop.getRatio();
			int own_x = (int) owner.center().x;
			int own_y = (int) owner.center().y;
			int newX = (int) (own_x + delta_x);
			int newY = (int) (own_y + delta_y);
			// neue Position der Annotation einstellen
			return new Point( (int)newX- (annotation.getWidth()/2), (int)newY-(annotation.getHeight()/2) );
		}
		return new Point(1,1);
	}

	public void storeAnnotationPosition(Fig annotation){
		int delta_x; float delta_y;
		Fig owner = annotation.getAnnotationOwner();
		// in this case: owner is a node
		if (! ( (owner instanceof FigEdge) || (owner instanceof FigLine) )){
			Point anPos 	= annotation.center();
			Point ownerPos 	= owner.center();
			delta_x = anPos.x-ownerPos.x;
			delta_y = anPos.y-ownerPos.y;
			// store values
                        AnnotationProperties prop = (AnnotationProperties)annotations.get(annotation);
			prop.setOffset(delta_x ,prop.hasFixedOffset());
			prop.setRatio(delta_y,prop.hasFixedRatio());
		}
	}

    public void drawConnectingLine(Fig annotation){
        // if duration=0 -> do not draw the connecting line
        if (getAnnotationProperties(annotation).getLineVisibilityDuration() == 0) {
            return;
        }
    	Fig owner = annotation.getAnnotationOwner();
    	AnnotationProperties prop = (AnnotationProperties)annotations.get(annotation);
        FigLine line = prop.getConnectingLine();
    	line.setShape(annotation.center(), owner.center());
    	line.setLineColor(Color.red);
    	line.setFillColor(Color.red);
    	line.setDashed(true);
    	if (!(Globals.curEditor().getLayerManager().getContents(null).contains(line))) {
            Globals.curEditor().add(line);
        } 
    	Globals.curEditor().getLayerManager().bringToFront(annotation);
    	Globals.curEditor().getLayerManager().sendToBack(line);
    	line.damage();
    	annotation.damage();
    	// remove line automatically
    	AnnotationLineRemover.instance().removeLineIn( getAnnotationProperties(annotation).getLineVisibilityDuration(),annotation );
    }
		
	// move annotations to its new position
	public void translateAnnotations(Fig owner){
		java.util.Enumeration enum = annotations.keys();
		while (enum.hasMoreElements()){
			Fig annotation = (Fig)enum.nextElement();
			annotation.setLocation(restoreAnnotationPosition(annotation));
			drawConnectingLine(annotation);
			annotation.endTrans();
			annotation.damage();
		}
	}
	
} // end of class
