package penakelex.textRPG.homeland.Adapters.Capabilities;

import java.util.Objects;

public class CapabilitiesInformation {
    private String capabilityName;
    private int capabilityID;

    public CapabilitiesInformation(String talentName, int talentID) {
        this.capabilityName = talentName;
        this.capabilityID = talentID;
    }

    public String getCapabilityName() {
        return capabilityName;
    }

    public void setCapabilityName(String talentName) {
        this.capabilityName = talentName;
    }

    public int getCapabilityID() {
        return capabilityID;
    }

    public void setCapabilityID(int talentID) {
        this.capabilityID = talentID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CapabilitiesInformation that = (CapabilitiesInformation) o;
        return capabilityID == that.capabilityID && Objects.equals(capabilityName, that.capabilityName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(capabilityName, capabilityID);
    }
}
