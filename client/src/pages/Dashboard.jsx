import { Button, Card, CardContent, Container, Typography } from '@mui/material';
import { useNavigate } from 'react-router-dom';

export default function Dashboard() {
  const navigate = useNavigate();

  return (
    <Container
      maxWidth={false}
      sx={{
        height: 'calc(100vh - 64px)',
        display: 'flex',
        marginLeft: '10vw',
        width: '80vw',
        alignItems: 'center',
        justifyContent: 'center'
      }}
    >
      <Card
        sx={{
          width: '100%',
          maxWidth: '80%'
        }}
      >
        <CardContent>
          <Typography variant="h5" gutterBottom align="center">
            Pastebin Dashboard
          </Typography>

          <Typography sx={{ mb: 3 }} align="center">
            Create and share text pastes with expiration and view limits.
          </Typography>

          <Button variant="contained" fullWidth onClick={() => navigate('/create')}>
            Create New Paste
          </Button>
        </CardContent>
      </Card>
    </Container>
  );
}
