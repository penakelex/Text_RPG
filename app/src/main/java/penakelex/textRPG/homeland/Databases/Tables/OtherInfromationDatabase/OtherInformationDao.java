package penakelex.textRPG.homeland.Databases.Tables.OtherInfromationDatabase;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface OtherInformationDao {
    @Query("SELECT * FROM other_information")
    List<OtherInformationItem> getAllOtherInformation();

    @Query("SELECT * FROM other_information WHERE id=:ID")
    OtherInformationItem getOtherInformationItem(byte ID);

    @Insert
    void addItem(OtherInformationItem item);

    @Query("UPDATE other_information SET value=:newValue WHERE id=:ID")
    void updateInformation(int newValue, byte ID);
}
