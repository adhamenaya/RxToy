import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class CatHelper {

    public interface CutestCatCallabck{

        void onError(Exception ex);

        void onCatSaved(String uri);
    }

    Api api;


    public void saveTheCutestCat( String query, CutestCatCallabck cutestCatCallabck){

        // Steps:
        // 1- Get all cats
        // 2- Find the cutest cat
        // 3- Store the cutest cat

            List<Cat> cats = api.queryCats(query, new Api.CatsQueryCallback() {
                @Override
                public void onCatsListReceived(List<Cat> cats) {
                    Cat cat = findCutestCat(cats);

                    String uri = api.store(cat, new Api.CatStoreCallback() {
                        @Override
                        public void onCatStored(String uri) {
                            cutestCatCallabck.onCatSaved(uri);
                        }

                        @Override
                        public void onStoreFailed(Exception ex) {
                            cutestCatCallabck.onError(ex);
                        }
                    });

                }

                @Override
                public void onQueryFailed(Exception ex) {
                    cutestCatCallabck.onError(ex);
                }
            });
    }

    public Cat findCutestCat(List<Cat> list){
        return Collections.max(list);
    }
}
