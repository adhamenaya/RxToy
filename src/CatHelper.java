import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CatHelper {

    public interface CutestCatCallabck {

        void onError(Exception ex);

        void onCatSaved(String uri);
    }

    ApiWrapper apiWrapper;


    public void saveTheCutestCat(String query, Callback<String> callback) {

        // Steps:
        // 1- Get all cats
        // 2- Find the cutest cat
        // 3- Store the cutest cat

        apiWrapper.queryCats(query, new Callback<List<Cat>>() {
            @Override
            public void onResult(List<Cat> cats) {
                Cat cat = findCutestCat(cats);

                apiWrapper.store(cat, new Callback<String>() {
                    @Override
                    public void onResult(String uri) {
                        callback.onResult(uri);
                    }

                    @Override
                    public void onError(Exception ex) {
                        callback.onError(ex);
                    }
                });
            }

            @Override
            public void onError(Exception ex) {
                callback.onError(ex);
            }
        });
    }

    public Cat findCutestCat(List<Cat> list) {
        return Collections.max(list);
    }
}
