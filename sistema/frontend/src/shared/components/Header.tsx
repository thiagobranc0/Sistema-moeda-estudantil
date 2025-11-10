import { AppBar, Toolbar, Typography } from '@mui/material';

interface HeaderProps {
  title?: string;
}

export default function Header({ title = 'Sistema de Moeda Estudantil' }: HeaderProps) {
  return (
    <AppBar position="sticky" sx={{ bgcolor: '#8EAA94' }}>
      <Toolbar>
        <Typography variant="h6" component="h1" sx={{ flexGrow: 1, fontWeight: 600 }}>
          {title}
        </Typography>
      </Toolbar>
    </AppBar>
  );
}
