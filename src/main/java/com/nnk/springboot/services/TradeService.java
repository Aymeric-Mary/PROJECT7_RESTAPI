package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.mapper.TradeMapper;
import com.nnk.springboot.repositories.TradeRepository;
import com.nnk.springboot.utils.DateUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TradeService {

    private final TradeRepository tradeRepository;
    private final TradeMapper tradeMapper;

    public Trade create(Trade trade) {
        return tradeRepository.save(trade);
    }

    public List<Trade> findAll() {
        return tradeRepository.findAll();
    }

    public Optional<Trade> findById(Integer id) {
        return tradeRepository.findById(id);
    }

    public Trade update(Trade existingTrade, Trade trade) {
        tradeMapper.updateTrade(existingTrade, trade);
        existingTrade.setRevisionDate(Timestamp.from(DateUtils.now()));
        return tradeRepository.save(existingTrade);
    }

    public void deleteById(Integer id) {
        tradeRepository.deleteById(id);
    }
}
