package hu.bence.flyhigh.model;

import com.google.gson.annotations.SerializedName;

public class GepAdatokModel {

    @SerializedName("Id")
    private int id;

    @SerializedName("Gepneve")
    private String gepneve;

    @SerializedName("Foglaltturista")     
    private int foglaltTurista;

    @SerializedName("Foglalt1oszt")       

    @SerializedName("Elsoosztulohelyek")
    private int elsoOsztUlohelyek;

    @SerializedName("Turistaulohelyek")
    private int turistaUlohelyek;

    public int getId() {
        return id;
    }

    public String getGepneve() {
        return gepneve;
    }

    public int getFoglaltTurista() {
        return foglaltTurista;
    }

    public int getFoglaltElsoOsztaly() {
        return foglaltElsoOsztaly;
    }

    public int getElsoOsztUlohelyek() {
        return elsoOsztUlohelyek;
    }

    public int getTuristaUlohelyek() {
        return turistaUlohelyek;
    }
}
