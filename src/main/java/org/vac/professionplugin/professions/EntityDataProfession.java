package org.vac.professionplugin.professions;

public class EntityDataProfession
{
    public String entityName;
    public boolean allowedMiner;
    public boolean allowedHunter;
    public boolean allowedC;
    public boolean allowedD;
    public boolean allowedE;
    public float xpKill;
    public float xpBreed;
    public boolean allowedExtraExperience;
    public boolean allowedCooked;
    public String materialOriginal;
    public String materialCooked;

    public EntityDataProfession(String entityName, boolean allowedMiner, boolean allowedHunter, boolean allowedC, boolean allowedD, boolean allowedE, float xpKill, float xpBreed, boolean allowedExtraExperience, boolean allowedCooked, String materialOriginal, String materialCooked)
    {
        this.entityName = entityName;
        this.allowedMiner = allowedMiner;
        this.allowedHunter = allowedHunter;
        this.allowedC = allowedC;
        this.allowedD = allowedD;
        this.allowedE = allowedE;
        this.xpKill = xpKill;
        this.xpBreed = xpBreed;
        this.allowedExtraExperience = allowedExtraExperience;
        this.allowedCooked = allowedCooked;
        this.materialOriginal = materialOriginal;
        this.materialCooked = materialCooked;
    }
}
