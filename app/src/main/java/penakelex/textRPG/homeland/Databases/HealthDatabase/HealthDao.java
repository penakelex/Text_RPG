package penakelex.textRPG.homeland.Databases.HealthDatabase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface HealthDao {
    @Query("SELECT * FROM health_status")
    List<HealthItem> getAllHealthStatuses();

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(HealthItem item);

    @Query("UPDATE health_status SET value=:newValue WHERE id=:ID")
    void updateValue(short newValue, byte ID);
}
