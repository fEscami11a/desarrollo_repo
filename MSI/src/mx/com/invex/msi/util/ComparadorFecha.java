package mx.com.invex.msi.util;

import java.util.Comparator;

import mx.com.invex.msi.model.Compra;

public class ComparadorFecha implements Comparator<Compra>{


	@Override
	public int compare(Compra o1, Compra o2) {
		
		return o1.getFechaCompra().compareTo(o2.getFechaCompra());
	}

}
