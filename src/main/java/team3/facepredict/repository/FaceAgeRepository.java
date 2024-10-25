package team3.facepredict.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import team3.facepredict.entity.FaceAge;

@Repository
public interface FaceAgeRepository extends JpaRepository<FaceAge, Long> {
}