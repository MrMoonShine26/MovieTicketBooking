package com.xyz.repo;

import com.xyz.model.BookingResponse;
import org.springframework.data.repository.CrudRepository;

public interface BookingRepo extends CrudRepository<BookingResponse, String> {
}
