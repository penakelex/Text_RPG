package penakelex.textRPG.homeland.Databases.Tables.ReputationsDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ReputationDao {
    @Query("SELECT * FROM reputation")
    LiveData<List<ReputationItem>> getAllReputation();

    @Insert
    void insert(ReputationItem item);

    @Query("UPDATE reputation SET reputation=:newReputation WHERE id=:ID")
    void updateReputation(byte newReputation, short ID);

    @Query("SELECT * FROM reputation WHERE id=:ID")
    LiveData<ReputationItem> getItem(short ID);
}
