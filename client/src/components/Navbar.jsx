import { AppBar, Button, Toolbar, Typography } from '@mui/material';
import { Link } from 'react-router-dom';

export default function Navbar() {
  return (
    <AppBar sx={{ width: '100%' }}>
      <Toolbar>
        <Typography
          component={Link}
          to="/"
          sx={{
            flexGrow: 1,
            textDecoration: 'none',
            color: 'inherit',
            fontWeight: 'bold'
          }}
        >
          Pastebin Lite
        </Typography>

        <Button color="inherit" component={Link} to="/create">
          Create Paste
        </Button>
      </Toolbar>
    </AppBar>
  );
}
