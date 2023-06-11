package penakelex.textRPG.homeland.Adapters.QuestStages;

import android.content.Context;

import penakelex.textRPG.homeland.R;

public class QuestStageInformation {
    private String stage;

    public QuestStageInformation(int ID, int stage, Context context) {
        switch (ID) {
            case 1:
                switch (stage) {
                    case 0 ->
                            this.stage = context.getResources().getString(R.string.quest_stage_register);
                    case 1 ->
                            this.stage = context.getResources().getString(R.string.quest_completed);
                }
                break;
            case 2:
                switch (stage) {
                    case 0 ->
                            this.stage = context.getResources().getString(R.string.quest_stage_take_a_tour);
                    case 1 ->
                            this.stage = context.getResources().getString(R.string.quest_completed);
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
