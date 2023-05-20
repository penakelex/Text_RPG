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
                        this.stage = context.getResources().getString(R.string.quest_stage_register);
                        break;
                    case 1:
                        this.stage = context.getResources().getString(R.string.quest_completed);
                        break;
                }
                break;
            case 1:
                switch (stage) {
                    case 0:
                        this.stage = context.getResources().getString(R.string.quest_stage_take_a_tour);
                        break;
                    case 1:
                        this.stage = context.getResources().getString(R.string.quest_completed);
                        break;
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
