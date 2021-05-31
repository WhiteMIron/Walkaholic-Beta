package com.example.demo.level;

import com.example.demo.level.model.entity.Level;
import org.springframework.data.jpa.repository.JpaRepository;

public interface  LevelRepository  extends JpaRepository<Level,Long> {
}
