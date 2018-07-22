import java.util.List;

public interface Api {

    List<Cat> queryCats(String query);

    String store(Cat cat);
}
