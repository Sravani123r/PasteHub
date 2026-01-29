import { Alert, Button, Container, MenuItem, TextField } from '@mui/material';
import { useState } from 'react';
import { createPaste } from '../api/axios';

export default function CreatePaste() {
  const [content, setContent] = useState('');
  const [expiration, setExpiration] = useState('');
  const [maxViews, setMaxViews] = useState('');
  const [link, setLink] = useState('');
  const [error, setError] = useState('');

  const handleSubmit = async () => {
    try {
      let ttlSeconds = null;
      let views = maxViews ? parseInt(maxViews, 10) : null;

      // 🔥 Expiration logic
      if (expiration === 'burn') {
        views = 1; // burn after read
        ttlSeconds = null;
      } else if (expiration) {
        ttlSeconds = parseInt(expiration, 10);
      }

      const payload = {
        content: content,
        ttl_seconds: ttlSeconds,
        max_views: views
      };

      const res = await createPaste(payload);
      setLink(res.data.url);
      setError('');
    } catch (e) {
      setError('Failed to create paste');
    }
  };

  return (
    <Container maxWidth="sm" sx={{ ml:40,mt:13, justifyContent: 'center', alignItems: 'center', }}>
      {/* Paste Content */}
      <TextField
        label="Paste Content"
        multiline
        rows={6}
        fullWidth
        value={content}
        onChange={(e) => setContent(e.target.value)}
        sx={{ mb: 2 }}
      />

      {/* Expiration Dropdown */}
      <TextField
        select
        label="Paste Expiration"
        fullWidth
        value={expiration}
        onChange={(e) => setExpiration(e.target.value)}
        sx={{ mb: 2 }}
      >
        <MenuItem value="">Never expire</MenuItem>
        <MenuItem value="burn">Burn after read</MenuItem>
        <MenuItem value="60">1 minute</MenuItem>
        <MenuItem value="120">2 minutes</MenuItem>
        <MenuItem value="300">5 minutes</MenuItem>
        <MenuItem value="600">10 minutes</MenuItem>
      </TextField>

      {/* Max Views (disabled if burn after read) */}
      <TextField
        label="Max Views"
        type="number"
        fullWidth
        value={maxViews}
        onChange={(e) => setMaxViews(e.target.value)}
        disabled={expiration === 'burn'}
        sx={{ mb: 2 }}
      />

      <Button variant="contained" fullWidth onClick={handleSubmit}>
        Create Paste
      </Button>

      {link && (
        <Alert severity="success" sx={{ mt: 2 }}>
          Link: {link}
        </Alert>
      )}

      {error && (
        <Alert severity="error" sx={{ mt: 2 }}>
          {error}
        </Alert>
      )}
    </Container>
  );
}
