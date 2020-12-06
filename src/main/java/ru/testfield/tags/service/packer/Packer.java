package ru.testfield.tags.service.packer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Packer<T> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected static final int QUEUE_CAPACITY = 100000;

    private final Lock getPackLock = new ReentrantLock();

    protected final BlockingQueue<T> blockingQueue;

    public Packer() {
        this.blockingQueue = new LinkedBlockingDeque<>(QUEUE_CAPACITY);
    }

    public boolean addValue(T value) {
        if(!blockingQueue.offer(value)) {
            logger.error("Unable to accept value. Queue size: {}/{}",blockingQueue.size(), QUEUE_CAPACITY);
            return false;
        }else{
            return true;
        }
    }

    /**
     * Method to pack and return all available values.
     *
     * @return Optional, containing list with all available values.
     */
    public Optional<List<T>> getPack() {
        try {
            getPackLock.lock();;
            return getPackOfFixedSize(blockingQueue.size());
        } finally {
            getPackLock.unlock();
        }
    }

    /**
     * Method to get pack of size maxNumber, or less if maxNumber is not available.
     *
     * @param maxNumber - maximum number of elements to return.
     * @return Optional, containing list with packed values, or empty if maxNumber not more then 0.
     */
    public Optional<List<T>> getPack(int maxNumber) {
        try {
            getPackLock.lock();
            return getPackOfFixedSize(maxNumber);
        } finally {
            getPackLock.unlock();
        }
    }

    private Optional<List<T>> getPackOfFixedSize(int fixedSize) {
        if (fixedSize > 0) {
            List<T> pack = new ArrayList<>(fixedSize);
            repackQueueToList(pack, fixedSize);
            return Optional.of(pack);
        }else{
            return Optional.empty();
        }
    }

    private void repackQueueToList(List<T> pack, int packSize) {
        T value;
        for (int i = 0; i<packSize; i++) {
            value = blockingQueue.poll();
            if(value!=null) {
                pack.add(value);
            }
        }
    }
}