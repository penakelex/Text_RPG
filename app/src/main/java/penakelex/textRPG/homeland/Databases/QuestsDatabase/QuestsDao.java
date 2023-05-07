package penakelex.textRPG.homeland.Databases.QuestsDatabase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface QuestsDao {
    @Query("SELECT * FROM quests")
    List<Quest> getQuests();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void addQuest(Quest quest);

    @Query("UPDATE quests SET stage=:newStage WHERE id=:ID")
    void updateQuestStage(short newStage, int ID);

    @Query("DELETE FROM quests")
    void deleteAll();
}
