package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CurvePointService {

    private final CurvePointRepository curvePointRepository;

    public CurvePoint create(CurvePoint curvePoint) {
        return curvePointRepository.save(curvePoint);
    }

    public List<CurvePoint> findAll() {
        return curvePointRepository.findAll();
    }
}
