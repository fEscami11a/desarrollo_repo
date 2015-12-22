package mx.com.invex.msi.model;

public class InfoEnviadaDTO {

	private Integer meses,numCompras;
	private Double monto;
	
	
	public InfoEnviadaDTO(int meses,int numCompras, double monto) {
		this.meses=meses;
		this.numCompras= numCompras;
		this.monto= monto;
	}
	
	public Integer getMeses() {
		return meses;
	}
	public void setMeses(Integer meses) {
		this.meses = meses;
	}
	public Integer getNumCompras() {
		return numCompras;
	}
	public void setNumCompras(Integer numCompras) {
		this.numCompras = numCompras;
	}
	public Double getMonto() {
		return monto;
	}
	public void setMonto(Double monto) {
		this.monto = monto;
	}
	
	
}
