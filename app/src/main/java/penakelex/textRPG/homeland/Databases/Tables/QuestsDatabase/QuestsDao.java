package penakelex.textRPG.homeland.Databases.Tables.QuestsDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface QuestsDao {
    @Query("SELECT * FROM quests")
    LiveData<List<QuestItem>> getQuests();

    @Insert
    void addQuest(QuestItem quest);

    @Query("UPDATE quests SET stage=:newStage WHERE id=:ID")
    void updateQuestStage(short newStage, int ID);
}
