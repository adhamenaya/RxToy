import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CatHelper {

    public interface CutestCatCallabck {

        void onError(Exception ex);

        void onCatSaved(String uri);
    }

    private ApiWrapper apiWrapper;

    public CatHelper() {
        apiWrapper = new ApiWrapper();
    }


    public AsyncJob<String> saveTheCutestCat(String query) {

        AsyncJob<List<Cat>> catsListAsyncJob = apiWrapper.queryCats(query);

        AsyncJob<Cat> cutestCatAsyncJob = new AsyncJob<Cat>() {
            @Override
            public void start(Callback<Cat> callback) {
                catsListAsyncJob.start(new Callback<List<Cat>>() {
                    @Override
                    public void onResult(List<Cat> cats) {
                        Cat c = findCutestCat(cats);
                        if (c == null)
                            callback.onError(new Exception("Cat not found!"));
                        else
                            callback.onResult(c);
                    }

                    @Override
                    public void onError(Exception ex) {
                        callback.onError(ex);
                    }
                });
            }
        };

        AsyncJob<String> storedUriAsyncJob = new AsyncJob<String>() {
            @Override
            public void start(Callback<String> callback) {
                cutestCatAsyncJob.start(new Callback<Cat>() {
                    @Override
                    public void onResult(Cat cat) {
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
        return storedUriAsyncJob;
    }

    private Cat findCutestCat(List<Cat> list) {
        return Collections.max(list);
    }
}
