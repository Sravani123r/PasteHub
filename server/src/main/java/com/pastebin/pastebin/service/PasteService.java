package com.pastebin.pastebin.service;

import com.pastebin.pastebin.entity.Paste;

public interface PasteService {

    Paste createPaste(String content, Integer ttlSeconds, Integer maxViews);

    Paste getPasteForView(String id, Long testNowMillis);
}
