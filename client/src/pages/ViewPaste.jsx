import { Container, Typography } from '@mui/material';
import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { getPaste } from '../api/axios';

export default function ViewPaste() {
  const { id } = useParams();
  const [content, setContent] = useState('');

  useEffect(() => {
    getPaste(id)
      .then((res) => setContent(res.data.content))
      .catch(() => setContent('Paste expired or not found'));
  }, [id]);

  return (
    <Container sx={{ mt: 4 }}>
      <Typography component="pre">{content}</Typography>
    </Container>
  );
}
