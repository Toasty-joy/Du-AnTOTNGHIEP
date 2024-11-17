package org.example.duan.service;

import org.example.duan.entity.SizeEntity;
import org.example.duan.repository.SizeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SizeService {

    @Autowired
    private SizeRepository sizesRepository;

    // Phương thức lấy các kích cỡ theo productId
    public List<SizeEntity> getSizesByProductId(int productId) {
        return sizesRepository.findByProductId(productId);
    }
}
