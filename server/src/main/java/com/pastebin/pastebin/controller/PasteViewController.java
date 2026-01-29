package com.pastebin.pastebin.controller;

import com.pastebin.pastebin.entity.Paste;
import com.pastebin.pastebin.service.PasteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class PasteViewController {

    private final PasteService pasteService;

    public PasteViewController(PasteService pasteService) {
        this.pasteService = pasteService;
    }

    @GetMapping("/p/{id}")
    public ResponseEntity<String> viewPaste(@PathVariable String id) {
        try {
            Paste paste = pasteService.getPasteForView(id, null);

            String html = """
                    <html>
                      <head>
                        <title>Paste</title>
                      </head>
                      <body>
                        <pre>%s</pre>
                      </body>
                    </html>
                    """.formatted(escapeHtml(paste.getContent()));

            return ResponseEntity.ok(html);

        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Paste not found or expired");
        }
    }

    private String escapeHtml(String input) {
        return input
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;");
    }
}
