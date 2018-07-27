import java.util.Collections;
import java.util.List;

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
        AsyncJob<Cat> cutestCatAsyncJob = catsListAsyncJob.map(new Func<List<Cat>, Cat>() {
            @Override
            public Cat call(List<Cat> cats) {
                return findCutestCat(cats);
            }
        });
        AsyncJob<String> storedUriAsyncJob = cutestCatAsyncJob.flatMap(new Func<Cat, AsyncJob<String>>() {
            @Override
            public AsyncJob<String> call(Cat cat) {
                return apiWrapper.store(cat);
            }
        });
        return storedUriAsyncJob;
    }

    private Cat findCutestCat(List<Cat> list) {
        return Collections.max(list);
    }
}
