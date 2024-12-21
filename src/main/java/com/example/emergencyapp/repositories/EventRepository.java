package com.example.emergencyapp.repositories;

import com.example.emergencyapp.models.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
}