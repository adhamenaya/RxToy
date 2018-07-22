import java.util.List;

public interface Api {

     interface CatsQueryCallback{

        void onCatsListReceived(List<Cat> cats);

        void onQueryFailed(Exception ex);
    }

    interface CatStoreCallback{

         void onCatStored(String uri);

         void onStoreFailed(Exception ex);
    }

    List<Cat> queryCats(String query, CatsQueryCallback catsQueryCallback);

    String store(Cat cat, CatStoreCallback catStoreCallback);
}
