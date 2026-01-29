package com.pastebin.pastebin.controller;

import com.pastebin.pastebin.entity.Paste;
import com.pastebin.pastebin.service.PasteService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/pastes")
public class PasteController {

    private final PasteService pasteService;

    @Value("${app.base-url}")
    private String baseUrl;

    public PasteController(PasteService pasteService) {
        this.pasteService = pasteService;
    }

    // ================= CREATE PASTE =================
    @PostMapping
    public ResponseEntity<?> createPaste(@RequestBody Map<String, Object> request) {

        String content = (String) request.get("content");

        Integer ttlSeconds = parseInteger(request.get("ttl_seconds"));
        Integer maxViews   = parseInteger(request.get("max_views"));

        if (content == null || content.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(error("Content must not be empty"));
        }

        if (ttlSeconds != null && ttlSeconds < 1) {
            return ResponseEntity.badRequest().body(error("ttl_seconds must be >= 1"));
        }

        if (maxViews != null && maxViews < 1) {
            return ResponseEntity.badRequest().body(error("max_views must be >= 1"));
        }

        Paste paste = pasteService.createPaste(content, ttlSeconds, maxViews);

        Map<String, Object> response = new HashMap<>();
        response.put("id", paste.getId());
        response.put("url", baseUrl + "/p/" + paste.getId());

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ================= FETCH PASTE (API) =================
    @GetMapping("/{id}")
    public ResponseEntity<?> getPaste(
            @PathVariable String id,
            @RequestHeader(value = "x-test-now-ms", required = false) Long testNowMillis
    ) {
        try {
            Paste paste = pasteService.getPasteForView(id, testNowMillis);

            Map<String, Object> response = new HashMap<>();
            response.put("content", paste.getContent());
            response.put("remaining_views",
                    paste.getMaxViews() == null
                            ? null
                            : paste.getMaxViews() - paste.getViewCount());
            response.put("expires_at", paste.getExpiresAt());

            return ResponseEntity.ok(response);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(error("Paste not found or unavailable"));
        }
    }

    // ================= HELPER METHODS =================

    private Integer parseInteger(Object value) {
        if (value == null) return null;

        if (value instanceof Number) {
            return ((Number) value).intValue();
        }

        if (value instanceof String) {
            String s = ((String) value).trim();
            if (s.isEmpty()) return null;
            return Integer.parseInt(s);
        }

        return null;
    }

    private Map<String, String> error(String message) {
        Map<String, String> map = new HashMap<>();
        map.put("error", message);
        return map;
    }
}
