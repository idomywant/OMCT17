package com.ifi.omct17.Classes.FBO;

public class HK20110000Req {
	public class FMPConnectionString { 
		public String SPName;
		public String LoginID;
		public String Password;
		public String TxnId;
	}

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
		public Object HDRVQ1;
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
		public Object HSFILL;
	}

	public class RequestHeader { 
		public int FuncId;
		public int TerminalId;
		public String AskSNO;
		public double AskTime;
	}

	public class DCHINF1 { 
		public Object DRCCOD1;
		public Object DRCCCY1;
		public double DRCAMT1;
	}

	public class DCHINF2 { 
		public Object DRCCOD2;
		public Object DRCCCY2;
		public Object DRCAMT2;
	}

	public class DRINFO { 
		public Object DRBANK;
		public double DRACCT;
		public String DRCCY;
		public double DRAMT;
		public DCHINF1 DCHINF1;
		public DCHINF2 DCHINF2;
	}

	public class CCHINF1 { 
		public Object CRCCOD1;
		public Object CRCCCY1;
		public double CRCAMT1;
	}

	public class CCHINF2 { 
		public Object CRCCOD2;
		public Object CRCCCY2;
		public Object CRCAMT2;
	}

	public class CRINFO { 
		public String CRSER;
		public double CRACCT;
		public String CRCCY2;
		public double CRAMT2;
		public Object FILLER2;
		public CCHINF1 CCHINF1;
		public CCHINF2 CCHINF2;
	}

	public class Content { 
		public int CUSTCOD;
		public int CUSTNAME;
		public String TXSER;
		public int TXTYPE;
		public String PRODCOD;
		public Object RETRY;
		public int TXDATE;
		public int CRCNT;
		public String CRCCY;
		public double CRAMT;
		public String FLRMKS;
		public String PAYMENTDET1;
		public String PAYMENTDET2;
		public double PAYMENTDET3;
		public Object PACKNO;
		public DRINFO DRINFO;
		public CRINFO CRINFO;
	}

	public class TxXML { 
		public RequestHeader RequestHeader;
		public Content Content;
	}

	public class TxBody { 
		public TxXML TxXML;
	}

	public class Tx { 
		public FMPConnectionString FMPConnectionString;
		public TxHead TxHead;
		public TxBody TxBody;
	}
}
