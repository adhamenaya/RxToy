import java.util.List;

public class ApiWrapper {

    Api api;

    void queryCats(String qurey, Callback<List<Cat>> callback) {
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

    void store(Cat cat, Callback<String> callback) {
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
}
