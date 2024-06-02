package vn.edu.fpt.SmartHealthC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.fpt.SmartHealthC.domain.entity.UserLesson;
import vn.edu.fpt.SmartHealthC.serivce.UserLessonService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user-lessons")
public class UserLessonController {
    @Autowired
    private UserLessonService userLessonService;

    @PostMapping
    public ResponseEntity<UserLesson> createUserLesson(@RequestBody UserLesson userLesson) {
        UserLesson createdUserLesson = userLessonService.createUserLesson(userLesson);
        return ResponseEntity.ok(createdUserLesson);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserLesson> getUserLessonById(@PathVariable Integer id) {
        Optional<UserLesson> userLesson = userLessonService.getUserLessonById(id);
        return userLesson.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<UserLesson>> getAllUserLessons() {
        List<UserLesson> userLessons = userLessonService.getAllUserLessons();
        return ResponseEntity.ok(userLessons);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserLesson> updateUserLesson(@PathVariable Integer id, @RequestBody UserLesson userLesson) {
        userLesson.setId(id);
        UserLesson updatedUserLesson = userLessonService.updateUserLesson(userLesson);
        return ResponseEntity.ok(updatedUserLesson);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserLesson(@PathVariable Integer id) {
        userLessonService.deleteUserLesson(id);
        return ResponseEntity.noContent().build();
    }

}