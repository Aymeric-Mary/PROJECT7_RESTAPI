package com.nnk.springboot.services;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.mapper.BidListMapper;
import com.nnk.springboot.repositories.BidListRepository;
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
public class BidService {

    private final BidListRepository bidListRepository;
    private final BidListMapper bidListMapper;

    public BidList create(BidList bid) {
        return bidListRepository.save(bid);
    }

    public List<BidList> findAll() {
        return bidListRepository.findAll();
    }

    public Optional<BidList> findById(Integer id) {
        return bidListRepository.findById(id);
    }

    public BidList update(BidList existingBidList, BidList bidList) {
        bidListMapper.updateBidList(existingBidList, bidList);
        existingBidList.setRevisionDate(DateUtils.now());
        return bidListRepository.save(existingBidList);
    }

    public void deleteById(Integer id) {
        bidListRepository.deleteById(id);
    }
}
