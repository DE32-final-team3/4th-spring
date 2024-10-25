package team3.facepredict.entity;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Entity
@Table(name = "face_age")
@Getter
@Setter
public class FaceAge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long num;

    @Column(name = "origin_name", nullable = false)
    private String originName;

    @Column(name = "file_path", nullable = false)
    private String filePath;

    @Column(name = "request_time", nullable = false)  
    private String requestTime;  

    @Column(name = "prediction_result")  
    private String predictionResult;

    @Column(name = "prediction_time")  
    private String predictionTime;  

    @Column(name = "answer")  
    private String answer;

    @Column(name = "comments")  
    private String comments;
}
