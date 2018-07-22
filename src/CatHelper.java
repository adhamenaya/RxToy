import java.util.Collections;
import java.util.List;

public class CatHelper {

    Api api;


    public String saveTheCutestCat( String query){

        // Steps:
        // 1- Get all cats
        // 2- Find the cutest cat
        // 3- Store the cutest cat
        List<Cat> cats = api.queryCats(query);

        Cat cat = findCutestCat(cats);

        String uri = api.store(cat);

        return uri;
    }

    public Cat findCutestCat(List<Cat> list){
        return Collections.max(list);
    }
}
