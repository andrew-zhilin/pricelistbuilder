package com.gmail.dev.zhilin.pricelistbuilder.services;

import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import com.gmail.dev.zhilin.pricelistbuilder.models.Item;
import com.gmail.dev.zhilin.pricelistbuilder.parsers.ObmenPRSParser;
import com.gmail.dev.zhilin.pricelistbuilder.services.updaters.AdvancedPriceListUpdater;
import com.gmail.dev.zhilin.pricelistbuilder.services.updaters.SimplePriceListUpdater;

@Service
public class UpdateService {

    @Autowired
    private ObmenPRSParser parser;
    @Autowired
    private AdvancedPriceListUpdater advancedPriceListUpdater;
    @Autowired
    private SimplePriceListUpdater simplePriceListUpdater;
    private static ReentrantLock lock = new ReentrantLock();

    public boolean isActive() {
        return lock.isLocked();
    }

    @Scheduled(cron = "0 * */2 * * *") // every two hours
    public void update() {
        if (lock.tryLock()) {
            List<Item> items = parser.parse();

            advancedPriceListUpdater.update(items);
            simplePriceListUpdater.update(items);
            lock.unlock();
        } else {
            do {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (lock.isLocked());
        }
    }

}
