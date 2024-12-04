package com.datn.boarding_house_management_rental_website.services;
import com.datn.boarding_house_management_rental_website.entity.payload.request.BlogStoreRequest;
import com.datn.boarding_house_management_rental_website.entity.payload.response.BlogStoreResponse;
import com.datn.boarding_house_management_rental_website.entity.payload.response.MessageResponse;
import org.springframework.data.domain.Page;

public interface BlogStoreService {
    MessageResponse saveBlog(BlogStoreRequest storeRequest);

    Page<BlogStoreResponse> getPageOfBlog(Integer pageNo, Integer pageSize);
}
