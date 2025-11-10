import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import {
  Box,
  Container,
  TextField,
  Button,
  Typography,
  Paper,
  InputAdornment,
  IconButton,
  Link,
  Divider,
  Alert,
  CircularProgress,
} from '@mui/material';
import {
  Visibility,
  VisibilityOff,
  Email,
  Lock,
} from '@mui/icons-material';
import { useLogin } from './hooks/useLogin';


export default function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false);
  const [error, setError] = useState('');
  const navigate = useNavigate();

  const { login, isLoading, isError, error: loginError, isSuccess, data } = useLogin();

  useEffect(() => {
    if (isError && loginError) {
      setError(loginError.message || 'Erro ao fazer login');
    }
  }, [isError, loginError]);

  useEffect(() => {
    if (isSuccess && data) {
      console.log('Login realizado com sucesso:', {
        id: data.id,
        nome: data.nome,
        email: data.email,
        cpf: data.cpf,
        rg: data.rg,
        endereco: data.endereco,
        departamentoId: data.departamentoId,
        cnpj: data.cnpj,
      });
      setError('');
      navigate(`/vantagens/${data.id}`);
    }
  }, [isSuccess, data, navigate]);

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    setError('');

    if (!email || !password) {
      setError('Por favor, preencha todos os campos');
      return;
    }

    login(email);
  };

  const handleClickShowPassword = () => {
    setShowPassword(!showPassword);
  };

  return (
    <Box
      sx={{
        minHeight: '100vh',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'center',
        background: 'linear-gradient(135deg, #8EAA94 0%, #8EAA94 100%)',
      }}
    >
      <Container maxWidth="sm">
        <Paper
          elevation={10}
          sx={{
            p: 4,
            borderRadius: 3,
            backdropFilter: 'blur(10px)',
          }}
        >
          <Box sx={{ mb: 3, textAlign: 'center' }}>
            <Typography
              variant="h4"
              component="h1"
              gutterBottom
              sx={{
                fontWeight: 700,
                color: '#463a4dff',
              }}
            >
              Sistema de Moeda Estudantil
            </Typography>
            <Typography variant="body2" sx={{ color: '#463a4dff' }}>
              Entre com suas credenciais para acessar o sistema
            </Typography>
          </Box>

          {error && (
            <Alert severity="error" sx={{ mb: 2 }}>
              {error}
            </Alert>
          )}

          <form onSubmit={handleSubmit}>
            <TextField
              fullWidth
              label="E-mail"
              type="email"
              value={email}
              onChange={(e) => setEmail(e.target.value)}
              margin="normal"
              variant="outlined"
              placeholder="Digite seu e-mail"
              InputProps={{
                startAdornment: (
                  <InputAdornment position="start">
                    <Email color="action" />
                  </InputAdornment>
                ),
              }}
              sx={{ mb: 2 }}
            />

            <TextField
              fullWidth
              label="Senha"
              type={showPassword ? 'text' : 'password'}
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              margin="normal"
              variant="outlined"
              InputProps={{
                startAdornment: (
                  <InputAdornment position="start">
                    <Lock color="action" />
                  </InputAdornment>
                ),
                endAdornment: (
                  <InputAdornment position="end">
                    <IconButton
                      onClick={handleClickShowPassword}
                      edge="end"
                      aria-label="toggle password visibility"
                    >
                      {showPassword ? <VisibilityOff /> : <Visibility />}
                    </IconButton>
                  </InputAdornment>
                ),
              }}
              sx={{ mb: 1 }}
            />

            <Box sx={{ textAlign: 'right', mb: 3 }}>
              <Link
                href="#"
                variant="body2"
                sx={{
                  color: '#6C3751',
                  textDecoration: 'none',
                  '&:hover': { 
                    textDecoration: 'underline',
                    color: '#52223C',
                  },
                }}
              >
                Esqueceu sua senha?
              </Link>
            </Box>

            <Button
              type="submit"
              fullWidth
              variant="contained"
              size="large"
              disabled={isLoading}
              sx={{
                py: 1.5,
                mb: 2,
                fontWeight: 600,
                textTransform: 'none',
                fontSize: '1rem',
                bgcolor: '#6C3751',
                '&:hover': {
                  bgcolor: '#52223C',
                },
              }}
            >
              {isLoading ? (
                <>
                  <CircularProgress size={20} sx={{ color: 'white', mr: 1 }} />
                  Entrando...
                </>
              ) : (
                'Entrar'
              )}
            </Button>

            <Divider sx={{ my: 2 }}>
              <Typography variant="body2" color="text.secondary">
                ou
              </Typography>
            </Divider>

            <Box sx={{ textAlign: 'center' }}>
              <Typography variant="body2" color="text.secondary">
                Não tem uma conta?{' '}
                <Link
                  href="#"
                  sx={{
                    color: '#6C3751',
                    fontWeight: 600,
                    textDecoration: 'none',
                    '&:hover': { 
                      textDecoration: 'underline',
                      color: '#52223C',
                    },
                  }}
                >
                  Cadastre-se
                </Link>
              </Typography>
            </Box>
          </form>
        </Paper>
      </Container>
    </Box>
  );
}
