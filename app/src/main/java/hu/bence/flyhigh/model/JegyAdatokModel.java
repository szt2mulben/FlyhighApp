package hu.bence.flyhigh.model;

import com.google.gson.annotations.SerializedName;

public class JegyAdatokModel {

    @SerializedName("jegy_id")
    private int jegyId;

    @SerializedName("honnan")
    private String honnan;

    @SerializedName("hova")
    private String hova;

    @SerializedName("ido")
    private String ido;   
    @SerializedName("ora")
    private int ora;

    @SerializedName("perc")
    private int perc;

    public int getJegyId() { return jegyId; }
    public String getHonnan() { return honnan; }
    public String getHova() { return hova; }
    public String getIdo() { return ido; }
    public int getOra() { return ora; }
    public int getPerc() { return perc; }
}
