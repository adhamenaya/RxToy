import java.util.List;

public class ApiWrapper {

    Api api;

    AsyncJob<List<Cat>> queryCats(String qurey) {
        return new AsyncJob<List<Cat>>() {
            @Override
            public void start(Callback<List<Cat>> callback) {
                api.queryCats(qurey, new Api.CatsQueryCallback() {
                    @Override
                    public void onCatsListReceived(List<Cat> cats) {
                        callback.onResult(cats);
                    }

                    @Override
                    public void onQueryFailed(Exception ex) {
                        callback.onError(ex);
                    }
                });
            }
        };
    }

    AsyncJob<String> store(Cat cat) {
        return new AsyncJob<String>() {
            @Override
            public void start(Callback<String> callback) {
                api.store(cat, new Api.CatStoreCallback() {
                    @Override
                    public void onCatStored(String uri) {
                        callback.onResult(uri);
                    }

                    @Override
                    public void onStoreFailed(Exception ex) {
                        callback.onError(ex);
                    }
                });
            }
        };
    }
}
