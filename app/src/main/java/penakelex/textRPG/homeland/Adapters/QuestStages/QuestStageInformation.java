package penakelex.textRPG.homeland.Adapters.QuestStages;

import android.content.Context;

import penakelex.textRPG.homeland.R;

public class QuestStageInformation {
    private String stage;

    public QuestStageInformation(int ID, int stage, Context context) {
        switch (ID) {
            case 0:
                switch (stage) {
                    case 0:
                      this.stage = context.getResources().getString(R.string.you8);
                }
                break;
        }
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }
}