package org.vac.professionplugin.professions;

public class BlockDataProfession
{
    public String materialName;
    public boolean allowedMiner;
    public boolean allowedHunter;
    public boolean allowedC;
    public boolean allowedD;
    public boolean allowedE;
    public float xpBreak;
    public float xpPlace;
    public boolean allowedLuminaritaElfica;
    public boolean allowedDuplicate;
    public boolean allowedExtraExperience;
    public String materialDuplicate;
    public double chanceLVL5;
    public double chanceLVL10;
    public double chanceLVL15;
    public double chanceLVL20;

    public BlockDataProfession(String materialName, boolean allowedMiner, boolean allowedHunter, boolean allowedC, boolean allowedD, boolean allowedE, float xpBreak, float xpPlace, boolean allowedLuminaritaElfica, boolean allowedDuplicate, boolean allowedExtraExperience, String materialDuplicate, double chanceLVL5, double chanceLVL10, double chanceLVL15, double chanceLVL20)
    {
        this.materialName = materialName;
        this.allowedMiner = allowedMiner;
        this.allowedHunter = allowedHunter;
        this.allowedC = allowedC;
        this.allowedD = allowedD;
        this.allowedE = allowedE;
        this.xpBreak = xpBreak;
        this.xpPlace = xpPlace;
        this.allowedLuminaritaElfica = allowedLuminaritaElfica;
        this.allowedDuplicate = allowedDuplicate;
        this.allowedExtraExperience = allowedExtraExperience;
        this.materialDuplicate = materialDuplicate;
        this.chanceLVL5 = chanceLVL5;
        this.chanceLVL10 = chanceLVL10;
        this.chanceLVL15 = chanceLVL15;
        this.chanceLVL20 = chanceLVL20;
    }
}
