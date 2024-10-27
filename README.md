# Getting Started

## Usage
```bash
$ docker compose up -d --force-recreate --build
```

## Spring REST API with CURL
- Upload file with prediction-result
```bash
curl -F "file=@<file_path/file_name>" -F "prediction_result=<prediction_result>" http://localhost:3003/uploadfile
```
```java
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
```
- Get all data
```bash
curl -X GET http://localhost:3003/all
```
```java
@GetMapping("/all")
public ResponseEntity<List<FaceAge>> getAllFaceAgeRecords() {
    List<FaceAge> allRecords = faceAgeRepository.findAll();
    return ResponseEntity.ok(allRecords);
}
```
- Update data info
```bash
curl -X PUT -d "answer=<answer>" -d "num=<id>" http://localhost:3003/update
```
```java
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
```
- Delete data
```bash
curl -X DELETE -d num=1 http://localhost:3003/delete
```
```java
@DeleteMapping("/delete")
public ResponseEntity<String> deleteFaceAge(@RequestParam Long num) {
    if (faceAgeRepository.existsById(num)) {
        faceAgeRepository.deleteById(num);
        return ResponseEntity.ok("Deleted successfully");
    } else {
        return ResponseEntity.status(404).body("Record not found");
    }
}
```

### Reference Documentation
For further reference, please consider the following sections:

* [Official Gradle documentation](https://docs.gradle.org)
* [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/3.3.4/gradle-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.3.4/gradle-plugin/packaging-oci-image.html)
* [Spring Boot DevTools](https://docs.spring.io/spring-boot/docs/3.3.4/reference/htmlsingle/index.html#using.devtools)
* [Thymeleaf](https://docs.spring.io/spring-boot/docs/3.3.4/reference/htmlsingle/index.html#web.servlet.spring-mvc.template-engines)

### Guides
The following guides illustrate how to use some features concretely:

* [Handling Form Submission](https://spring.io/guides/gs/handling-form-submission/)

### Additional Links
These additional references should also help you:

* [Gradle Build Scans – insights for your project's build](https://scans.gradle.com#gradle)

