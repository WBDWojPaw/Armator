package classesForTable;

public class PlacowkiTable {
	Integer NR_PLACOWKI,NR_BIURA,NR_ADRESU;
	String DATA_ZALOZENIA;
	
	public PlacowkiTable(String[] strArray)
	{
		NR_PLACOWKI = Integer.parseInt(strArray[0]);
		NR_BIURA = Integer.parseInt(strArray[2]);
		NR_ADRESU = Integer.parseInt(strArray[3]);
		DATA_ZALOZENIA = strArray[1];
	}

	public Integer getNR_PLACOWKI() {
		return NR_PLACOWKI;
	}

	public void setNR_PLACOWKI(Integer nR_PLACOWKI) {
		NR_PLACOWKI = nR_PLACOWKI;
	}

	public Integer getNR_BIURA() {
		return NR_BIURA;
	}

	public void setNR_BIURA(Integer nR_BIURA) {
		NR_BIURA = nR_BIURA;
	}

	public Integer getNR_ADRESU() {
		return NR_ADRESU;
	}

	public void setNR_ADRESU(Integer nR_ADRESU) {
		NR_ADRESU = nR_ADRESU;
	}

	public String getDATA_ZALOZENIA() {
		return DATA_ZALOZENIA;
	}

	public void setDATA_ZALOZENIA(String dATA_ZALOZENIA) {
		DATA_ZALOZENIA = dATA_ZALOZENIA;
	}
	
	
}
