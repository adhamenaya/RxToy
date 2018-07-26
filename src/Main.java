public class Main {

    public static void main(String[] args) {
        CatHelper helper = new CatHelper();

        helper.saveTheCutestCat("cat1").start(new Callback<String>() {
            @Override
            public void onResult(String s) {
                System.out.println("Cat saved under name: " + s);
            }

            @Override
            public void onError(Exception ex) {
                System.out.print(ex.getMessage());
            }
        });
    }
}
