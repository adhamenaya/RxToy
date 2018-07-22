public interface Callback<T> {

    void onResult(T t);
    void onError(Exception ex);
}
