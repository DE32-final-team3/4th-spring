package team3.facepredict.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import team3.facepredict.entity.FaceAge;
import team3.facepredict.repository.FaceAgeRepository;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping
public class FileUploadController {

    @Autowired
    private FaceAgeRepository faceAgeRepository;

    @PostMapping("/uploadfile")
    public String handleFileUpload(
            @RequestParam("file") MultipartFile file,
            @RequestParam("prediction_result") String predictionResult)
            throws IOException {

        String originName = file.getOriginalFilename();

        String uploadDir = "/home/ubuntu/images";
        File directory = new File(uploadDir);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String uniqueFileName = UUID.randomUUID().toString() + ".jpg";
        String filePath = uploadDir + File.separator + uniqueFileName;
        File dest = new File(filePath);
        file.transferTo(dest);

        FaceAge faceAge = new FaceAge();
        faceAge.setOriginName(originName);
        faceAge.setFilePath(filePath);

        String currentTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        faceAge.setRequestTime(currentTime);
        faceAge.setPredictionResult(predictionResult);

        faceAgeRepository.save(faceAge);

        return "File uploaded successfully: " + originName;
    }

    @GetMapping("/all")
    public ResponseEntity<List<FaceAge>> getAllFaceAgeRecords() {
        List<FaceAge> allRecords = faceAgeRepository.findAll();
        return ResponseEntity.ok(allRecords);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateFaceAge(@RequestParam String answer, @RequestParam Long num) {
        Optional<FaceAge> optionalFaceAge = faceAgeRepository.findById(num);
        if (optionalFaceAge.isPresent()) {
            FaceAge faceAge = optionalFaceAge.get();
            faceAge.setAnswer(answer);
            faceAgeRepository.save(faceAge);
            return ResponseEntity.ok("Updated successfully");
        } else {
            return ResponseEntity.status(404).body("Record not found");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteFaceAge(@RequestParam Long num) {
        if (faceAgeRepository.existsById(num)) {
            faceAgeRepository.deleteById(num);
            return ResponseEntity.ok("Deleted successfully");
        } else {
            return ResponseEntity.status(404).body("Record not found");
        }
    }

}
