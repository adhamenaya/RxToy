public abstract class AsyncJob<T> {

    public abstract void start(Callback<T> callback);

    public <R> AsyncJob<R> map(Func<T, R> func) {

        final AsyncJob<T> source = this;

        return new AsyncJob<R>() {
            @Override
            public void start(Callback<R> callback) {
                source.start(new Callback<T>() {
                    @Override
                    public void onResult(T t) {
                        R mapped = func.call(t);
                        callback.onResult(mapped);
                    }

                    @Override
                    public void onError(Exception ex) {
                        callback.onError(ex);
                    }
                });
            }
        };
    }

    public <R> AsyncJob<R> flatMap(Func<T, AsyncJob<R>> func) {
        final AsyncJob<T> source = this;

        return new AsyncJob<R>() {
            @Override
            public void start(Callback<R> callback) {
                source.start(new Callback<T>() {
                    @Override
                    public void onResult(T t) {
                        AsyncJob<R> mapped = func.call(t);

                        mapped.start(new Callback<R>() {
                            @Override
                            public void onResult(R r) {
                                callback.onResult(r);
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
    }
}
