import { AppBar, Toolbar, Typography, Box, Button, Menu, MenuItem } from '@mui/material';
import { AccountCircle as AccountIcon, Logout as LogoutIcon } from '@mui/icons-material';
import { useAuth } from '../context/AuthContext';
import { useGetBalance } from '../../pages/transactions/hooks/useTransactions';
import { useNavigate, useLocation } from 'react-router-dom';
import { useState } from 'react';

export default function Header() {
  const { user, setUser } = useAuth();
  const navigate = useNavigate();
  const location = useLocation();
  const [anchorEl, setAnchorEl] = useState<null | HTMLElement>(null);

  // Determinar o tipo de usuário - normalizar para uppercase, traduzir para inglês, e usar fallback para cnpj
  let tipoNormalizado = (user?.tipo || '').toUpperCase();
  if (tipoNormalizado === 'EMPRESA') {
    tipoNormalizado = 'COMPANY';
  } else if (tipoNormalizado === 'ALUNO') {
    tipoNormalizado = 'STUDENT';
  }
  const isEmpresa = tipoNormalizado === 'COMPANY' || (user?.cnpj !== undefined && user?.cnpj !== null && user.cnpj !== '');
  
  const { balance } = useGetBalance(isEmpresa ? undefined : user?.id);

  const handleMenuOpen = (e: React.MouseEvent<HTMLElement>) => {
    setAnchorEl(e.currentTarget);
  };

  const handleMenuClose = () => {
    setAnchorEl(null);
  };

  const handleLogout = () => {
    setUser(null);
    navigate('/login');
    handleMenuClose();
  };

  const isVantagensActive = location.pathname.includes('/vantagens');
  const isExtratoActive = location.pathname.includes('/extrato');

  return (
    <AppBar position="sticky" sx={{ bgcolor: '#2d2d2d', boxShadow: '0 2px 8px rgba(0, 0, 0, 0.5)' }}>
      <Toolbar>
        {user && !isEmpresa && (
          <Box sx={{ display: 'flex', gap: 4, mr: 'auto' }}>
            <Button
              onClick={() => navigate(`/vantagens/${user.id}`)}
              sx={{
                color: '#f0f0f0',
                fontWeight: 600,
                fontSize: '1rem',
                textTransform: 'none',
                position: 'relative',
                pb: 0.5,
                p: 0,
                minWidth: 'auto',
                '&:hover': { bgcolor: 'transparent' },
                '&::after': {
                  content: '""',
                  position: 'absolute',
                  bottom: -8,
                  left: 0,
                  right: 0,
                  height: '3px',
                  bgcolor: isVantagensActive ? '#ff6b6b' : 'transparent',
                  transition: 'all 0.3s ease',
                },
              }}
            >
              Vantagens
            </Button>

            <Button
              onClick={() => navigate(`/extrato/${user.id}`)}
              sx={{
                color: '#f0f0f0',
                fontWeight: 600,
                fontSize: '1rem',
                textTransform: 'none',
                position: 'relative',
                pb: 0.5,
                p: 0,
                minWidth: 'auto',
                '&:hover': { bgcolor: 'transparent' },
                '&::after': {
                  content: '""',
                  position: 'absolute',
                  bottom: -8,
                  left: 0,
                  right: 0,
                  height: '3px',
                  bgcolor: isExtratoActive ? '#ff6b6b' : 'transparent',
                  transition: 'all 0.3s ease',
                },
              }}
            >
              Extrato
            </Button>
          </Box>
        )}

        {user && isEmpresa && (
          <Box sx={{ display: 'flex', gap: 4, mr: 'auto' }}>
            <Button
              sx={{
                color: '#f0f0f0',
                fontWeight: 600,
                fontSize: '1rem',
                textTransform: 'none',
                position: 'relative',
                pb: 0.5,
                p: 0,
                minWidth: 'auto',
                '&:hover': { bgcolor: 'transparent' },
                '&::after': {
                  content: '""',
                  position: 'absolute',
                  bottom: -8,
                  left: 0,
                  right: 0,
                  height: '3px',
                  bgcolor: isVantagensActive ? '#ff6b6b' : 'transparent',
                  transition: 'all 0.3s ease',
                },
              }}
            >
              Vantagens
            </Button>
          </Box>
        )}

        {user && (
          <Box sx={{ display: 'flex', alignItems: 'center', gap: 3, ml: 'auto' }}>
            {!isEmpresa && (
              <Box sx={{ display: 'flex', alignItems: 'center', gap: 1 }}>
                <Typography sx={{ color: '#e0e0e0', fontWeight: 600 }}>
                  Moedas:
                </Typography>
                <Typography sx={{ color: '#f0f0f0', fontWeight: 700, fontSize: '1.1rem' }}>
                  {balance}
                </Typography>
              </Box>
            )}

            {user.tipo === 'PROFESSOR' && (
              <Button
                color="inherit"
                onClick={() => navigate(`/professor/${user.id}/doar`)}
                sx={{ color: '#f0f0f0', fontWeight: 600, '&:hover': { bgcolor: 'rgba(240, 240, 240, 0.1)' } }}
              >
                Doar Moedas
              </Button>
            )}

            <Button
              onClick={handleMenuOpen}
              sx={{ color: '#b0b0b0', '&:hover': { bgcolor: 'rgba(176, 176, 176, 0.1)' } }}
            >
              <AccountIcon />
            </Button>

            <Menu anchorEl={anchorEl} open={!!anchorEl} onClose={handleMenuClose}>
              <MenuItem disabled>
                <Typography variant="body2">{user.nome}</Typography>
              </MenuItem>
              <MenuItem onClick={handleLogout}>
                <LogoutIcon sx={{ mr: 1 }} />
                Sair
              </MenuItem>
            </Menu>
          </Box>
        )}
      </Toolbar>
    </AppBar>
  );
}
