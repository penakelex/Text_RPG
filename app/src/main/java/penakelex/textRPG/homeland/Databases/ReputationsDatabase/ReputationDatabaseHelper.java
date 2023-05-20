package penakelex.textRPG.homeland.Databases.ReputationsDatabase;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import penakelex.textRPG.homeland.Adapters.Reputations.ReputationInformation;

public class ReputationDatabaseHelper {

    public static void addNewOne(Context context, String name) {
        ReputationDatabase.getDatabase(context).reputationDao().insert(new ReputationItem(name));
    }
    public static ArrayList<ReputationInformation> getReputationsInformation(Context context) {
        ArrayList<ReputationInformation> arrayList = new ArrayList<>();
        List<ReputationItem> list = ReputationDatabase.getDatabase(context).reputationDao().getAllReputation();
        for (ReputationItem item : list) {
            arrayList.add(new ReputationInformation(item.getName()));
        }
        return (ArrayList<ReputationInformation>) arrayList.clone();
    }

    public static ReputationItem getItem(Context context, short ID) {
        return ReputationDatabase.getDatabase(context).reputationDao().getItem(ID);
    }
}
