package org.alexa.dao;

import org.alexa.entity.RawData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RawDataDAO extends JpaRepository<RawData, Long> {
}
