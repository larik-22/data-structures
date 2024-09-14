package nl.saxion.cds.experimenting;

public class RecursionRefresh {
    public static void main(String[] args) {
        new RecursionRefresh().run();
    }

    public void run(){
        for (int i = 1; i < 50; i++) {
            System.out.println(i + " --> " + collatz(i) + " steps");
        }
    }

    public int collatz(int n){
        if(n == 1) return 1;

        if (n % 2 == 0){
            return 1 + collatz(n / 2);
        } else {
            return 1 + collatz(n * 3 + 1);
        }
    }
}
