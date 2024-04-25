package com.ifi.omct17.Classes.FBO;

public class HK20110000Rsp {
	public class TxHead { 
		public String HMSGID;
		public int HERRID;
		public int HSYDAY;
		public int HSYTIME;
		public String HWSID;
		public int HSTANO;
		public int HDTLEN;
		public Object HREQQ1;
		public Object HREPQ1;
		public String HDRVQ1;
		public Object HPVDQ1;
		public Object HPVDQ2;
		public int HSYCVD;
		public Object HTLID;
		public String HTXTID;
		public Object HFMTID;
		public String HRETRN;
		public Object HSLGNF;
		public Object HSPSCK;
		public Object HRTNCD;
		public Object HSBTRF;
		public Object HFILL;
	}

	public class ResponseHeader { 
		public int FuncId;
		public int ErrorCode;
		public Object ErrorMsg;
		public int TerminalId;
		public String AskSNO;
	}

	public class DCHINF1 { 
		public Object DRCCOD1;
		public Object DRCCCY1;
		public Object DRCAMT1;
	}

	public class DCHINF2 { 
		public Object DRCCOD2;
		public Object DRCCCY2;
		public Object DRCAMT2;
	}

	public class DRINFO { 
		public int DERRCOD;
		public Object DERRMSG;
		public String DUPDSER;
		public Object DRBANK;
		public double DRACCT;
		public String DRCCY;
		public int DRAMT;
		public DCHINF1 DCHINF1;
		public DCHINF2 DCHINF2;
	}

	public class CCHINF1 { 
		public Object CRCCOD1;
		public Object CRCCCY1;
		public int CRCAMT1;
	}

	public class CCHINF2 { 
		public Object CRCCOD2;
		public Object CRCCCY2;
		public Object CRCAMT2;
	}

	public class CRINFO { 
		public int CERRCOD;
		public Object CERRMSG;
		public String CUPDSER;
		public Object CRSER;
		public Object CRACCT2;
		public String CRCCY2;
		public int CRAMT2;
		public CCHINF1 CCHINF1;
		public CCHINF2 CCHINF2;
	}

	public class Content { 
		public int CUSTCOD;
		public String TXSER;
		public int TXTYPE;
		public Object RETRY;
		public Object CRCNT;
		public Object CRCCY;
		public String PRODCOD;
		public double CRAMT;
		public String FLRMKS;
		public Object PACKNO;
		public Object SUCCNT;
		public DRINFO DRINFO;
		public CRINFO CRINFO;
	}

	public class TxXML { 
		public ResponseHeader ResponseHeader;
		public Content Content;
	}

	public class TxBody { 
		public TxXML TxXML;
	}

	public class Tx { 
		public TxHead TxHead;
		public TxBody TxBody;
	}


}
