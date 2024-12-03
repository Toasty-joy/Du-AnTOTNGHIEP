package org.example.duan.service;

import org.example.duan.entity.ProductEntity;
import org.example.duan.entity.SizeEntity;
import org.example.duan.entity.SizesProductsEntity;
import org.example.duan.repository.ProductRepository;
import org.example.duan.repository.SizeRepository;
import org.example.duan.repository.SizesProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SizeService {

    @Autowired
    private SizeRepository sizesRepository;
    @Autowired
    private SizesProductsRepository sizesProductsRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private SizeRepository sizeRepository;

    // Phương thức lấy các kích cỡ theo productId
    public List<SizeEntity> getSizesByProductId(int productId) {
        return sizesRepository.findByProductId(productId);
    }
    // Lấy tất cả kích thước
    public List<SizeEntity> getAllSizes() {
        return sizesRepository.findAll(); // Gọi phương thức findAll() từ repository
    }
    // Thêm kích thước mới cho sản phẩm
    public SizeEntity createSize(SizeEntity sizeEntity) {
        return sizesRepository.save(sizeEntity);  // Lưu kích thước mới
    }

    // Cập nhật kích thước cho sản phẩm
    public SizeEntity updateSize(SizeEntity sizeEntity) {
        return sizesRepository.save(sizeEntity);  // Cập nhật kích thước
    }

    // Xóa kích thước của sản phẩm
    public void deleteSize(int sizeId) {
        sizesRepository.deleteById(sizeId);  // Xóa kích thước theo ID
    }
    public SizeEntity getSizeById(int id) {
        return sizesRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Size with ID " + id + " not found"));
    }
    // Thêm kích thước vào sản phẩm
    // Thêm kích thước vào sản phẩm
    public void addSizeToProduct(int productId, int sizeId) {
        ProductEntity product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("Sản phẩm không tồn tại"));

        SizeEntity size = sizeRepository.findById(sizeId)
                .orElseThrow(() -> new IllegalArgumentException("Kích thước không tồn tại"));

        // Kiểm tra xem sản phẩm đã có kích thước này chưa
        SizesProductsEntity existing = sizesProductsRepository.findByProductIdAndSizeId(productId, sizeId).orElse(null);
        if (existing != null) {
            throw new IllegalArgumentException("Sản phẩm đã có kích thước này");
        }

        // Thêm kích thước vào sản phẩm
        SizesProductsEntity sizesProductsEntity = new SizesProductsEntity();
        sizesProductsEntity.setProduct(product);
        sizesProductsEntity.setSize(size);
        sizesProductsRepository.save(sizesProductsEntity);
    }

    // Xóa kích thước khỏi sản phẩm
    public void removeSizeFromProduct(int productId, int sizeId) {
        SizesProductsEntity sizesProductsEntity = sizesProductsRepository
                .findByProductIdAndSizeId(productId, sizeId)
                .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy kích thước cho sản phẩm này"));

        sizesProductsRepository.delete(sizesProductsEntity);
    }




}

