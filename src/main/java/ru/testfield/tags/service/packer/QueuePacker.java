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

public class QueuePacker<T> implements Packer<T> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    protected static final int QUEUE_CAPACITY = 100000;

    private final Lock getPackLock = new ReentrantLock();

    protected final BlockingQueue<T> blockingQueue;

    public QueuePacker() {
        this.blockingQueue = new LinkedBlockingDeque<>(QUEUE_CAPACITY);
    }

    @Override
    public boolean addValue(T value) {
        if(!blockingQueue.offer(value)) {
            logger.error("Unable to accept value. Queue size: {}/{}",blockingQueue.size(), QUEUE_CAPACITY);
            return false;
        }else{
            return true;
        }
    }

    @Override
    public Optional<List<T>> getPack() {
        try {
            getPackLock.lock();;
            return getPackOfFixedSize(blockingQueue.size());
        } finally {
            getPackLock.unlock();
        }
    }

    @Override
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
        } else {
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