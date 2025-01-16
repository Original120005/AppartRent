package com.rentalapp.repositories;

import com.rentalapp.models.PropertyImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PropertyImageRepository extends JpaRepository<PropertyImage,Long>
{
}
