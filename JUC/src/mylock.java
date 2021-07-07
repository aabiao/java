import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class mylock implements Lock {
    //独占锁  不可重入锁
    class Mysyn extends AbstractQueuedSynchronizer{
        @Override
        protected boolean tryAcquire(int arg) {

            if (compareAndSetState(0,1)){
                setExclusiveOwnerThread(Thread.currentThread());
                return true;
            }
            return false;
        }
//-------------
        @Override
        protected boolean tryRelease(int arg) {
            setExclusiveOwnerThread(null);
            setState(0);
            return true;
        }

        @Override
        protected boolean isHeldExclusively() {
            return getState()==1;
        }

        public  Condition newCondition(){
            return new ConditionObject();
        }
    }
    private Mysyn syn=new Mysyn();
    @Override
    public void lock() {
        syn.acquire(1);//不成功会进入阻塞状态
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {
        syn.acquireInterruptibly(1);
    }

    @Override
    public boolean tryLock() {
        return syn.tryAcquire(1);
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {

        return syn.tryAcquireNanos(1,unit.toNanos(time));
    }



    @Override
    public void unlock() {
        syn.release(1);
    }

    @Override
    public Condition newCondition() {
        return syn.newCondition();
    }
}
