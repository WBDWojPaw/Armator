package classesForTable;

public class AtrakcjeTable {
	String NAZWA,OPIS;
	Integer NR_ATRAKCJI, NR_ADRESU;
	public String getNAZWA() {
		return NAZWA;
	}
	public void setNAZWA(String nAZWA) {
		NAZWA = nAZWA;
	}
	public String getOPIS() {
		return OPIS;
	}
	public void setOPIS(String oPIS) {
		OPIS = oPIS;
	}
	public Integer getNR_ATRAKCJI() {
		return NR_ATRAKCJI;
	}
	public void setNR_ATRAKCJI(Integer nR_ATRAKCJI) {
		NR_ATRAKCJI = nR_ATRAKCJI;
	}
	public Integer getNR_ADRESU() {
		return NR_ADRESU;
	}
	public void setNR_ADRESU(Integer nR_ADRESU) {
		NR_ADRESU = nR_ADRESU;
	}
	
	public AtrakcjeTable(String[] strArray) {
		NR_ATRAKCJI = Integer.parseInt(strArray[0]);
		NAZWA = strArray[1];
		OPIS = strArray[2];
		NR_ADRESU = Integer.parseInt(strArray[3]);;
	}
	
	
}
