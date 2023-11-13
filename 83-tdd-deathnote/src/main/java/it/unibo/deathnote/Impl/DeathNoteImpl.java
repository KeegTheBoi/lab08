package it.unibo.deathnote.Impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.unibo.deathnote.api.DeathNote;

public class DeathNoteImpl implements DeathNote {

    private List<String> noteBook;
    private Map<String, String> deathCauses;

    public List<String> getNoteBook() {
        return List.copyOf(noteBook);
    }

    public DeathNoteImpl(){
        noteBook = new ArrayList<>();
        deathCauses = new HashMap<>();
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
        }

    }

    @Override
    public boolean writeDeathCause(String cause) {
        if(noteBook.isEmpty()){
            throw new IllegalStateException("should write name first");
        }
        this.deathCauses.put(this.noteBook.get(noteBook.size() - 1), cause);
        return true;
    }

    @Override
    public boolean writeDetails(String details) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'writeDetails'");
    }

    @Override
    public String getDeathCause(String name) {
        if(name != null){
            return deathCauses.get(name);
        }
        throw new NullPointerException("name cant be null");
    }

    @Override
    public String getDeathDetails(String name) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getDeathDetails'");
    }

    @Override
    public boolean isNameWritten(String name) {
            return this.noteBook.contains(name);
        
    }
    
}
