package hu.bence.flyhigh.model;

import com.google.gson.annotations.SerializedName;

public class JegyModel {

    @SerializedName("jegy_id")
    private int jegyId;

    @SerializedName("megrendelo_nev")
    private String megrendeloNev;

    @SerializedName("Indulas_hely")
    private String indulasHely;

    @SerializedName("erkezes_hely")
    private String erkezesHely;

    @SerializedName("indulasido")
    private String indulasIdo;

    @SerializedName("erkezesido")
    private String erkezesIdo;

    @SerializedName("utazok")
    private Integer utazok;

    @SerializedName("osztaly")
    private String osztaly;

    @SerializedName("ar")
    private Integer ar;

    @SerializedName("SorSzek")
    private String sorSzek;

    public int getJegyId() { return jegyId; }
    public String getMegrendeloNev() { return megrendeloNev; }
    public String getIndulasHely() { return indulasHely; }
    public String getErkezesHely() { return erkezesHely; }
    public String getIndulasIdo() { return indulasIdo; }
    public String getErkezesIdo() { return erkezesIdo; }
    public Integer getUtazok() { return utazok; }
    public String getOsztaly() { return osztaly; }
    public Integer getAr() { return ar; }
    public String getSorSzek() { return sorSzek; }
}
