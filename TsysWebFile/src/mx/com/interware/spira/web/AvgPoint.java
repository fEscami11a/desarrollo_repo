/*
 * Created on May 17, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package mx.com.interware.spira.web;

import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author vbaez
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class AvgPoint {
	private Date date;
	private List delta;
	
	
	private int number;
	private double avg;
			
	public AvgPoint(Date now) {
		date=now;
		delta=new LinkedList();
	}
	public List getDelta() {
		return delta;
	}
		
	public void closeHour() {
		number=delta.size();
		avg=0;
		for (Iterator iter = delta.iterator(); iter.hasNext();) {
			Long value = (Long) iter.next();
			avg+=value.longValue();
		}
		avg=avg/number;
		delta=null;
	}
		
	/**
	 * @return
	 */
	public double getAvg() {
		return avg;
	}

	/**
	 * @return
	 */
	public String getWhen() {
		return AbstractTSYSMessage.avgFormater.format(date);
	}

	/**
	 * @return
	 */
	public int getNumber() {
		return number;
	}

}
