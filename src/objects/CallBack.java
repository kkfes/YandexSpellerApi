package objects;

import java.util.ArrayList;
import java.util.List;

public class CallBack {
    private List<SpellResult> spellResults = new ArrayList<>();

    public List<SpellResult> getSpellResults() {
        return spellResults;
    }

    public void setSpellResults(List<SpellResult> spellResults) {
        this.spellResults = spellResults;
    }

    @Override
    public String toString() {
        return "objects.CallBack{" +
                "spellResults=" + spellResults +
                '}';
    }
}
