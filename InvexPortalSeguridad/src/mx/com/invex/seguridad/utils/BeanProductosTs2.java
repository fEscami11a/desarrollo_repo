package mx.com.invex.seguridad.utils;

public class BeanProductosTs2 {
	private String prod; 
	private String bin; 
	private String tpc;
	private String cpc;
	
	
	public BeanProductosTs2(String prod_1, String bin_1, String tpc_1,
			String cpc_1) {
		prod=prod_1;
		bin=bin_1;
		tpc=tpc_1;
		cpc=cpc_1;
	}


	public String getProd() {
		return prod;
	}


	public void setProd(String prod) {
		this.prod = prod;
	}


	public String getBin() {
		return bin;
	}


	public void setBin(String bin) {
		this.bin = bin;
	}


	public String getTpc() {
		return tpc;
	}


	public void setTpc(String tpc) {
		this.tpc = tpc;
	}


	public String getCpc() {
		return cpc;
	}


	public void setCpc(String cpc) {
		this.cpc = cpc;
	}

}
