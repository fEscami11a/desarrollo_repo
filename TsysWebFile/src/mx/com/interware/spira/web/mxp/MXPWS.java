package mx.com.interware.spira.web.mxp;

import mx.com.invex.exception.InvexServiceException;

public class MXPWS implements MXP{
	
	private MXP mxp;
	public MXPWS() {
		mxp = new MXPImpl();
	}

	public String updateMXP(MXPParam param) throws InvexServiceException {
		return mxp.updateMXP(param);
	}

}
