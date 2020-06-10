package classesForTable;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CzarteryTable {
	Integer NR_CZARTERU,CENA,NR_KLIENTA,NR_BIURA;
	Date DATA_OD,DATA_DO;
	public Integer getNR_CZARTERU() {
		return NR_CZARTERU;
	}
	public void setNR_CZARTERU(Integer nR_CZARTERU) {
		NR_CZARTERU = nR_CZARTERU;
	}
	public Integer getCENA() {
		return CENA;
	}
	public void setCENA(Integer cENA) {
		CENA = cENA;
	}
	public Integer getNR_KLIENTA() {
		return NR_KLIENTA;
	}
	public void setNR_KLIENTA(Integer nR_KLIENTA) {
		NR_KLIENTA = nR_KLIENTA;
	}
	public Integer getNR_BIURA() {
		return NR_BIURA;
	}
	public void setNR_BIURA(Integer nR_BIURA) {
		NR_BIURA = nR_BIURA;
	}
	public Date getDATA_OD() {
		return DATA_OD;
	}
	public void setDATA_OD(Date dATA_OD) {
		DATA_OD = dATA_OD;
	}
	public Date getDATA_DO() {
		return DATA_DO;
	}
	public void setDATA_DO(Date dATA_DO) {
		DATA_DO = dATA_DO;
	}
	public CzarteryTable(String[] strArray) {
		NR_CZARTERU = Integer.parseInt(strArray[0]);
		CENA = Integer.parseInt(strArray[3]);
		NR_KLIENTA = Integer.parseInt(strArray[4]);
		NR_BIURA = Integer.parseInt(strArray[5]);
		try {
			DATA_OD = new SimpleDateFormat("dd/MM/yyyy").parse(strArray[1]);
			DATA_DO = new SimpleDateFormat("dd/MM/yyyy").parse(strArray[2]);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}
