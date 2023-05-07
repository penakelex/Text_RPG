package penakelex.textRPG.homeland.Adapters.Talents;

import java.util.Objects;

public class TalentInformation {
    private String talentName;
    private int talentID;

    public TalentInformation(String talentName, int talentID) {
        this.talentName = talentName;
        this.talentID = talentID;
    }

    public String getTalentName() {
        return talentName;
    }

    public void setTalentName(String talentName) {
        this.talentName = talentName;
    }

    public int getTalentID() {
        return talentID;
    }

    public void setTalentID(int talentID) {
        this.talentID = talentID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TalentInformation that = (TalentInformation) o;
        return talentID == that.talentID && Objects.equals(talentName, that.talentName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(talentName, talentID);
    }
}
