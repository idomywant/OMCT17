package com.ifi.omct17.Classes.FBO;



public class HK20110000Req{

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
		public String HREQQ1;
		public String HREPQ1;
		public String HDRVQ1;
		public String HPVDQ1;
		public String HPVDQ2;
		public int HSYCVD;
		public String HTLID;
		public String HTXTID;
		public String HFMTID;
		public String HRETRN;
		public String HSLGNF;
		public String HSPSCK;
		public String HRTNCD;
		public String HSBTRF;
		public String HSFILL;
	}

	public class RequestHeader { 
		public int FuncId;
		public int TerminalId;
		public String AskSNO;
		public double AskTime;
	}

	public class DCHINF1 { 
		public String DRCCOD1;
		public String DRCCCY1;
		public double DRCAMT1;
	}

	public class DCHINF2 { 
		public String DRCCOD2;
		public String DRCCCY2;
		public String DRCAMT2;
	}

	public class DRINFO { 
		public String DRBANK;
		public double DRACCT;
		public String DRCCY;
		public double DRAMT;
		public DCHINF1 DCHINF1;
		public DCHINF2 DCHINF2;
	}

	public class CCHINF1 { 
		public String CRCCOD1;
		public String CRCCCY1;
		public double CRCAMT1;
	}

	public class CCHINF2 { 
		public String CRCCOD2;
		public String CRCCCY2;
		public String CRCAMT2;
	}

	public class CRINFO { 
		public String CRSER;
		public double CRACCT;
		public String CRCCY2;
		public double CRAMT2;
		public String FILLER2;
		public CCHINF1 CCHINF1;
		public CCHINF2 CCHINF2;
	}

	public class Content { 
		public int CUSTCOD;
		public int CUSTNAME;
		public String TXSER;
		public int TXTYPE;
		public String PRODCOD;
		public String RETRY;
		public int TXDATE;
		public int CRCNT;
		public String CRCCY;
		public double CRAMT;
		public String FLRMKS;
		public String PAYMENTDET1;
		public String PAYMENTDET2;
		public double PAYMENTDET3;
		public String PACKNO;
		public DRINFO DRINFO;
		public CRINFO CRINFO;
	}

	 class TxXML { 
		public RequestHeader RequestHeader;
		public Content Content;
	}

	public class TxBody { 
		public TxXML TxXML;
	}

//	public class Tx { 
		private FMPConnectionString FMPConnectionString;
		private TxHead TxHead;
		private TxBody TxBody;
//	}
}
