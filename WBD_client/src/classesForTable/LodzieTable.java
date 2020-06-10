package classesForTable;

public class LodzieTable {
	Integer NR_LODZI,NR_BIURA,NR_MODELU,NR_PLACOWKI;
	String NAZWA,NR_REJESTRACYJNY;
	
	public LodzieTable(String[] strArray) {
		NR_LODZI = Integer.parseInt(strArray[0]);
		NR_REJESTRACYJNY = strArray[2];
		NR_BIURA = Integer.parseInt(strArray[3]);
		NR_MODELU = Integer.parseInt(strArray[4]);
		NR_PLACOWKI = Integer.parseInt(strArray[5]);
		NAZWA = strArray[1];
	}

	public Integer getNR_LODZI() {
		return NR_LODZI;
	}

	public void setNR_LODZI(Integer nR_LODZI) {
		NR_LODZI = nR_LODZI;
	}

	public String getNR_REJESTRACYJNY() {
		return NR_REJESTRACYJNY;
	}

	public void setNR_REJESTRACYJNY(String nR_REJESTRACYJNY) {
		NR_REJESTRACYJNY = nR_REJESTRACYJNY;
	}

	public Integer getNR_BIURA() {
		return NR_BIURA;
	}

	public void setNR_BIURA(Integer nR_BIURA) {
		NR_BIURA = nR_BIURA;
	}

	public Integer getNR_MODELU() {
		return NR_MODELU;
	}

	public void setNR_MODELU(Integer nR_MODELU) {
		NR_MODELU = nR_MODELU;
	}

	public Integer getNR_PLACOWKI() {
		return NR_PLACOWKI;
	}

	public void setNR_PLACOWKI(Integer nR_PLACOWKI) {
		NR_PLACOWKI = nR_PLACOWKI;
	}

	public String getNAZWA() {
		return NAZWA;
	}

	public void setNAZWA(String nAZWA) {
		NAZWA = nAZWA;
	}
	
	

}
