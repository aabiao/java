public class test_auc {
    public static void main(String[] args) {
        mylock lock=new mylock();
        new Thread(()->{
            lock.lock();
            System.out.println("t1 locking....");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("t1 unlocking....");
            lock.unlock();
        }).start();
        new Thread(()->{
            lock.lock();
            System.out.println("t2 locking....");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("t2 unlocking....");
            lock.unlock();
        }).start();
    }
}
