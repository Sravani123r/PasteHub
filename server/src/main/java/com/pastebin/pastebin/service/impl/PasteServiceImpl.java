package com.pastebin.pastebin.service.impl;

import com.pastebin.pastebin.entity.Paste;
import com.pastebin.pastebin.repository.PasteRepository;
import com.pastebin.pastebin.service.PasteService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class PasteServiceImpl implements PasteService {

    private final PasteRepository pasteRepository;

    @Value("${TEST_MODE:false}")
    private boolean testMode;

    public PasteServiceImpl(PasteRepository pasteRepository) {
        this.pasteRepository = pasteRepository;
    }

    @Override
    public Paste createPaste(String content, Integer ttlSeconds, Integer maxViews) {

        Instant expiresAt = null;
        if (ttlSeconds != null) {
            expiresAt = Instant.now().plusSeconds(ttlSeconds);
        }

        Paste paste = Paste.builder()
                .id(generateId())
                .content(content)
                .expiresAt(expiresAt)
                .maxViews(maxViews)
                .build();

        return pasteRepository.save(paste);
    }

    @Override
    @Transactional
    public Paste getPasteForView(String id, Long testNowMillis) {

        Paste paste = pasteRepository.findByIdForUpdate(id)
                .orElseThrow(() -> new RuntimeException("Paste not found"));

        Instant now = (testMode && testNowMillis != null)
                ? Instant.ofEpochMilli(testNowMillis)
                : Instant.now();

        if (paste.getExpiresAt() != null && paste.getExpiresAt().isBefore(now)) {
            throw new RuntimeException("Paste expired");
        }

        if (paste.getMaxViews() != null &&
                paste.getViewCount() >= paste.getMaxViews()) {
            throw new RuntimeException("View limit exceeded");
        }

        paste.setViewCount(paste.getViewCount() + 1);
        return paste;
    }

    private String generateId() {
        return UUID.randomUUID().toString().replace("-", "").substring(0, 10);
    }
}
