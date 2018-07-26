import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CatHelper {

    public interface CutestCatCallabck {

        void onError(Exception ex);

        void onCatSaved(String uri);
    }

    ApiWrapper apiWrapper;


    public AsyncJob<String> saveTheCutestCat(String query) {
        return new AsyncJob<String>() {
            @Override
            public void start(Callback<String> callback) {
                apiWrapper.queryCats(query).start(new Callback<List<Cat>>() {
                    @Override
                    public void onResult(List<Cat> cats) {
                        Cat cat = findCutestCat(cats);
                        apiWrapper.store(cat).start(new Callback<String>() {
                            @Override
                            public void onResult(String s) {
                                callback.onResult(s);
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
        };
    }

    public Cat findCutestCat(List<Cat> list) {
        return Collections.max(list);
    }
}
