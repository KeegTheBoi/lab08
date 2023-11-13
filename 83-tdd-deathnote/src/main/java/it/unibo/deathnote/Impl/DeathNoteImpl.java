package it.unibo.deathnote.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unibo.deathnote.api.DeathNote;

public class DeathNoteImpl implements DeathNote {

    private final static long TIME_LIMIT_FOR_CAUSES = 40;
    private final static long TIME_LIMIT_FOR_DETAILS = 6040;

    private final List<String> noteBook;
    private final Map<String, String> deathCauses;
    private final Map<String, String> deathDetails;
    private long time;
    private String currentDeathName;

    public String getCurrentDeathName() {
        return currentDeathName;
    }

    public List<String> getNoteBook() {
        return List.copyOf(noteBook);
    }

    public DeathNoteImpl(){
        noteBook = new ArrayList<>();
        deathCauses = new HashMap<>();
        deathDetails = new HashMap<>();
    }

    @Override
    public String getRule(int ruleNumber) {
        if(ruleNumber < 1 || ruleNumber >= RULES.size()){
            throw new IllegalArgumentException("arguments out of bounds");
        }
        return RULES.get(ruleNumber);
    }

    @Override
    public void writeName(String name) {
        if(name == null){
            throw new NullPointerException("Nome da uccidere non può essere vuoto");
        }
        if(!name.isEmpty()){
            this.noteBook.add(name);
            currentDeathName = name;
            this.deathCauses.putIfAbsent(name, "heart attack"); //default death cause
            this.deathDetails.putIfAbsent(name, ""); //set default death details
            this.calculateWritingTime();
        }

    }

    private void calculateWritingTime(){
        time = System.currentTimeMillis();
    }

    private long dimeDiff(){
        return System.currentTimeMillis() - time;
    }

    @Override
    public boolean writeDeathCause(String cause) {
        if(noteBook.isEmpty() || cause == null){
            throw new IllegalStateException("should write name first");
        }
        if(dimeDiff() < TIME_LIMIT_FOR_CAUSES){
            this.deathCauses.put(currentDeathName, cause);
            return true;
        }
        return false;
       
    }

    @Override
    public boolean writeDetails(String details) {
        if(noteBook.isEmpty() || details == null){
            throw new IllegalStateException("should write name first");
        }
        long diff = dimeDiff();
        if(diff < TIME_LIMIT_FOR_DETAILS){
            this.deathDetails.put(currentDeathName, details);
            return true;
        }
        return false;
        
    }

    @Override
    public String getDeathCause(String name) {
        if(!this.deathCauses.containsKey(name)){
            throw new IllegalArgumentException("couldn't find any death cause associated to th given name");
        }
        if(name != null){
            return this.deathCauses.get(name);
        }
        throw new NullPointerException("name cant be null or blank");
    }

    @Override
    public String getDeathDetails(String name) {
        if(!this.deathCauses.containsKey(name)){
            throw new IllegalArgumentException("couldn't find any death cause associated to th given name");
        }
        if(name != null ){
            return this.deathDetails.get(name);
        }
        throw new NullPointerException("name cant be null or blank");
    }

    @Override
    public boolean isNameWritten(String name) {
            return this.noteBook.contains(name);
        
    }
    
}
