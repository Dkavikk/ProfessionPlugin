package org.vac.professionplugin;

public class HunterProfessionData
{
    public String entityName;
    public float xp;
    public boolean allowedExtraExperience;
    public boolean allowedCooked;
    public String materialOriginal;
    public String materialCooked;

    public HunterProfessionData(String entityName, float xp, boolean allowedExtraExperience, boolean allowedCooked, String materialOriginal, String materialCooked)
    {
        this.entityName = entityName;
        this.xp = xp;
        this.allowedExtraExperience = allowedExtraExperience;
        this.allowedCooked = allowedCooked;
        this.materialOriginal = materialOriginal;
        this.materialCooked = materialCooked;
    }
}