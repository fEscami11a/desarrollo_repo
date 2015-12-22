package mx.com.invex.msi.util;

import java.util.Comparator;

import mx.com.invex.msi.model.Compra;

public class ComparadorCompraMonto implements Comparator<Compra>{


	@Override
	public int compare(Compra o1, Compra o2) {
		
		return new Double (o1.getMonto()).compareTo(o2.getMonto());
	}

}
