package it.unibo.deathnote.Impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import it.unibo.deathnote.api.DeathNote;

public class DeathNoteImpl implements DeathNote {

    private final static long TIME_LIMIT_FOR_CAUSES = 40L;
    private final static long TIME_LIMIT_FOR_DETAILS = 6040L;
    private final static String DEFAULT_DEATH_CAUSE = "heart attack";

    private final Map<String, String> deathCauses;
    private final Map<String, String> deathDetails;
    private long time;
    private String currentDeathName;

    public String getCurrentDeathName() {
        return currentDeathName;
    }

    public DeathNoteImpl(){
        deathCauses = new HashMap<>();
        deathDetails = new HashMap<>();
    }

    @Override
    public String getRule(final int ruleNumber) {
        if(ruleNumber < 1 || ruleNumber > RULES.size()){
            throw new IllegalArgumentException("arguments out of bounds");
        }
        return RULES.get(ruleNumber - 1);
    }

    @Override
    public void writeName(final String name) {
        this.handleNullElements(name);
        if(!name.isEmpty()){
            currentDeathName = name;
            this.deathCauses.putIfAbsent(name, DeathNoteImpl.DEFAULT_DEATH_CAUSE); //default death cause
            this.deathDetails.putIfAbsent(name, ""); //set default death details
            this.startTimer();
        }

    }

    private void startTimer(){
        time = System.currentTimeMillis();
    }

    @Override
    public boolean writeDeathCause(final String cause) {
        this.handleNullElements(cause);
        this.checkDeathExistance();
        return this.onTime(deathCauses, cause, DeathNoteImpl.TIME_LIMIT_FOR_CAUSES);       
    }

    @Override
    public boolean writeDetails(final String details) {
        this.handleNullElements(details);
        this.checkDeathExistance();
        return this.onTime(this.deathDetails, details, DeathNoteImpl.TIME_LIMIT_FOR_DETAILS);        
    }

    @Override
    public String getDeathCause(final String name) {
        checkNameExistanceAndValidity(this.deathCauses, name);
        return this.deathCauses.get(name);       
    }

    
    @Override
    public String getDeathDetails(String name) {
        checkNameExistanceAndValidity(this.deathDetails, name);
        return this.deathDetails.get(name);       
    }

    @Override
    public boolean isNameWritten(String name) {
        return this.deathCauses.containsKey(name); //either deathCause and deathDetails      
    }

    private void checkNameExistanceAndValidity(final Map<String, String> mapperInfo, final String deathName){
        if(!mapperInfo.containsKey(deathName)){
            throw new IllegalArgumentException("couldn't find any death cause associated to th given name");
        }
        this.handleNullElements(deathName);
    }

    private void checkDeathExistance(){
        if(this.deathCauses.isEmpty()){
            throw new IllegalStateException("should write name first");
        }
    }

    private boolean onTime(final Map<String, String> mapInfos, final String info, final long limit){
        if((System.currentTimeMillis() - time) < limit){
            mapInfos.put(currentDeathName, info);
            return true;
        }
        return false;
    }

    private void handleNullElements(final String tester){
        if(Objects.isNull(tester)){
            throw new NullPointerException(tester + " can not be void");
        }
        
    }

    
}
