package penakelex.textRPG.homeland.Databases.ReputationsDatabase;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

public class ReputationDatabaseHelper {

    public static void addNewOne(Context context, String name) {
        ReputationDatabase.getDatabase(context).reputationDao().insert(new ReputationItem(name));
    }
    public static ArrayList<String> getReputationsInformation(Context context) {
        ArrayList<String> arrayList = new ArrayList<>();
        List<ReputationItem> list = ReputationDatabase.getDatabase(context).reputationDao().getAllReputation();
        for (ReputationItem item : list) {
            arrayList.add(item.getName());
        }
        return (ArrayList<String>) arrayList.clone();
    }

    public static ReputationItem getItem(Context context, short ID) {
        return ReputationDatabase.getDatabase(context).reputationDao().getItem(ID);
    }
}
