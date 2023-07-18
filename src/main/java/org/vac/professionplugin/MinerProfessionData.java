package org.vac.professionplugin;

public class MinerProfessionData
{
    public String materialName;
    public float xp;
    public boolean allowedLuminaritaElfica;
    public boolean allowedDuplicate;
    public String materialDuplicate;
    public double chanceLVL5;
    public double chanceLVL10;
    public double chanceLVL15;
    public double chanceLVL20;


    public MinerProfessionData(String materialName, float xp, boolean allowedLuminaritaElfica, boolean allowedDuplicate, String materialDuplicate, double chanceLVL5, double chanceLVL10, double chanceLVL15, double chanceLVL20)
    {
        this.materialName = materialName;
        this.xp = xp;
        this.allowedLuminaritaElfica = allowedLuminaritaElfica;
        this.allowedDuplicate = allowedDuplicate;
        this.materialDuplicate = materialDuplicate;
        this.chanceLVL5 = chanceLVL5;
        this.chanceLVL10 = chanceLVL10;
        this.chanceLVL15 = chanceLVL15;
        this.chanceLVL20 = chanceLVL20;
    }
}
