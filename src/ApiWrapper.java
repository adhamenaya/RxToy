import java.util.ArrayList;
import java.util.List;

public class ApiWrapper {

    public ApiWrapper() {
    }

    class ApiImpl implements Api {

        @Override
        public String store(Cat cat, CatStoreCallback catStoreCallback) {
            catStoreCallback.onCatStored("/cat/save/" + cat.name.replace(" ", "_"));
            return "/cat/save/" + cat.name.replace(" ", "_");
        }

        @Override
        public List<Cat> queryCats(String query, CatsQueryCallback catsQueryCallback) {
            List<Cat> cats = new ArrayList<Cat>();
            Cat c1 = new Cat(1, "cat 1");
            Cat c2 = new Cat(2, "cat 2");
            Cat c3 = new Cat(100, "cat 100");
            cats.add(c1);
            cats.add(c2);
            cats.add(c3);

            catsQueryCallback.onCatsListReceived(cats);
            if (cats.size() == 0) catsQueryCallback.onQueryFailed(new Exception("Cat not found!"));
            return cats;
        }
    }

    Api api = new ApiImpl();

    AsyncJob<List<Cat>> queryCats(String qurey) {
        return new AsyncJob<List<Cat>>() {
            @Override
            public void start(Callback<List<Cat>> callback) {
                api.queryCats(qurey, new Api.CatsQueryCallback() {
                    @Override
                    public void onCatsListReceived(List<Cat> cats) {
                        System.out.println(cats.size());
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
