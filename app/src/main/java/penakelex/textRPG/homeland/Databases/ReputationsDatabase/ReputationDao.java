package penakelex.textRPG.homeland.Databases.ReputationsDatabase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ReputationDao {
    @Query("SELECT * FROM reputation")
    List<ReputationItem> getAllReputation();

    @Insert
    void insert(ReputationItem item);

    @Query("DELETE FROM reputation")
    void deleteAll();

    @Query("UPDATE reputation SET reputation=:newReputation WHERE id=:ID")
    void updateReputation(byte newReputation, short ID);

    @Query("SELECT * FROM reputation WHERE id=:ID")
    ReputationItem getItem(short ID);
}
