package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.BidListRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class BidService {

    private final BidListRepository bidListRepository;

    public BidList create(BidList bid) {
        return bidListRepository.save(bid);
    }

    public List<BidList> findAll() {
        return bidListRepository.findAll();
    }
}
