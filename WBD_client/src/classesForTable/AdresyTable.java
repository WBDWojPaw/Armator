package classesForTable;

public class AdresyTable {
	
	String KRAJ,MIASTO,ULICA, NR_LOKALU;
	Integer NR_ADRESU,NR_POCZTY;


	public String getKRAJ() {
		return KRAJ;
	}


	public void setKRAJ(String kRAJ) {
		KRAJ = kRAJ;
	}


	public String getMIASTO() {
		return MIASTO;
	}


	public void setMIASTO(String mIASTO) {
		MIASTO = mIASTO;
	}


	public String getULICA() {
		return ULICA;
	}


	public void setULICA(String uLICA) {
		ULICA = uLICA;
	}


	public Integer getNR_ADRESU() {
		return NR_ADRESU;
	}


	public void setNR_ADRESU(Integer nR_ADRESU) {
		NR_ADRESU = nR_ADRESU;
	}


	public String getNR_LOKALU() {
		return NR_LOKALU;
	}


	public void setNR_LOKALU(String nR_LOKALU) {
		NR_LOKALU = nR_LOKALU;
	}


	public Integer getNR_POCZTY() {
		return NR_POCZTY;
	}


	public void setNR_POCZTY(Integer nR_POCZTY) {
		NR_POCZTY = nR_POCZTY;
	}


	public AdresyTable(String[] strArray) {

		NR_ADRESU = Integer.parseInt(strArray[0]);
		KRAJ = strArray[1];
		MIASTO = strArray[2];
		ULICA = strArray[3];
		NR_LOKALU = strArray[4];
		NR_POCZTY = Integer.parseInt(strArray[5]);
	}
	

	
	
}
