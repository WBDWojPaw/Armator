package classesForTable;

public class ModeleLodziTable {
	Integer NR_MODELU, LICZBA_OSOB, DLUGOSC_KADLUBA;
	String NAZWA,TYP,TYP_PALIWA;
	
	public ModeleLodziTable(String[] strArray) {
		NR_MODELU = Integer.parseInt(strArray[0]);
		LICZBA_OSOB = Integer.parseInt(strArray[3]);
		DLUGOSC_KADLUBA = Integer.parseInt(strArray[2]);
		NAZWA = strArray[1];
		TYP = strArray[4];
		TYP_PALIWA = strArray[5];
	}

	public Integer getNR_MODELU() {
		return NR_MODELU;
	}

	public void setNR_MODELU(Integer nR_MODELU) {
		NR_MODELU = nR_MODELU;
	}

	public Integer getLICZBA_OSOB() {
		return LICZBA_OSOB;
	}

	public void setLICZBA_OSOB(Integer lICZBA_OSOB) {
		LICZBA_OSOB = lICZBA_OSOB;
	}

	public Integer getDLUGOSC_KADLUBA() {
		return DLUGOSC_KADLUBA;
	}

	public void setDLUGOSC_KADLUBA(Integer dLUGOSC_KADLUBA) {
		DLUGOSC_KADLUBA = dLUGOSC_KADLUBA;
	}

	public String getNAZWA() {
		return NAZWA;
	}

	public void setNAZWA(String nAZWA) {
		NAZWA = nAZWA;
	}

	public String getTYP() {
		return TYP;
	}

	public void setTYP(String tYP) {
		TYP = tYP;
	}

	public String getTYP_PALIWA() {
		return TYP_PALIWA;
	}

	public void setTYP_PALIWA(String tYP_PALIWA) {
		TYP_PALIWA = tYP_PALIWA;
	}
	
	
}
