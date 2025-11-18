import { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';
import {
  Box,
  Container,
  TextField,
  Button,
  Typography,
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
import { useAuth } from '../../shared/context/AuthContext';


export default function Login() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [showPassword, setShowPassword] = useState(false);
  const [error, setError] = useState('');
  const [hasRedirected, setHasRedirected] = useState(false);
  const navigate = useNavigate();

  const { login, isLoading, isError, error: loginError, isSuccess, data } = useLogin();
  const { setUser } = useAuth();

  useEffect(() => {
    if (isError && loginError) {
      setError(loginError.message || 'Erro ao fazer login');
    }
  }, [isError, loginError]);

  useEffect(() => {
    if (isSuccess && data && !hasRedirected) {
      console.log('Login realizado com sucesso:', {
        id: data.id,
        nome: data.nome,
        email: data.email,
        tipo: data.tipo,
        cnpj: data.cnpj,
      });
      
      // O id pode ser string ou número - mantém como está (pode ser UUID)
      const userId = data.id;
      
      // Normalizar tipo para uppercase e traduzir para inglês
      let tipo = (data.tipo || '').toUpperCase().trim();
      if (tipo === 'EMPRESA') {
        tipo = 'COMPANY';
      } else if (tipo === 'ALUNO') {
        tipo = 'STUDENT';
      } else if (tipo === 'PROFESSOR') {
        tipo = 'PROFESSOR';
      }
      
      console.log('Tipo normalizado:', tipo);
      console.log('UserId:', userId);
      
      // Salvar dados do usuário no contexto (e localStorage via provider)
      setUser({ ...data, id: userId as any, tipo: tipo as any });
      
      // Redirecionar baseado no tipo de usuário
      setError('');
      setHasRedirected(true);
      
      setTimeout(() => {
        if (tipo === 'COMPANY') {
          console.log('Redirecionando para vantagens com ID:', userId);
          navigate(`/vantagens/${userId}`);
        } else if (tipo === 'PROFESSOR') {
          console.log('Redirecionando para professor com ID:', userId);
          navigate(`/professor/${userId}/doar`);
        } else if (tipo === 'STUDENT') {
          console.log('Redirecionando para extrato com ID:', userId);
          navigate(`/extrato/${userId}`);
        } else {
          console.error('Tipo de usuário desconhecido:', tipo);
          setError('Tipo de usuário não reconhecido');
        }
      }, 100);
    }
  }, [isSuccess, data, navigate, setUser, hasRedirected]);

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
        background: 'linear-gradient(135deg, #1a1a1a 0%, #2d2d2d 100%)',
      }}
    >
      <Container maxWidth="sm">
        <Box
          sx={{
            p: 4,
            borderRadius: 3,
            bgcolor: '#2d2d2d',
            color: '#e0e0e0',
            border: '1px solid #444',
          }}
        >
          <Box sx={{ mb: 3, textAlign: 'center' }}>
            <Typography
              variant="h4"
              component="h1"
              gutterBottom
              sx={{
                fontWeight: 700,
                color: '#f0f0f0',
              }}
            >
              Sistema de Moeda Estudantil
            </Typography>
            <Typography variant="body2" sx={{ color: '#b0b0b0' }}>
              Entre com suas credenciais para acessar o sistema
            </Typography>
          </Box>

          {error && (
            <Alert severity="error" sx={{ mb: 2, bgcolor: '#3d2020', color: '#ff6b6b', border: '1px solid #8b3a3a' }}>
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
                    <Email color="action" sx={{ color: '#b0b0b0' }} />
                  </InputAdornment>
                ),
              }}
              sx={{
                mb: 2,
                '& .MuiOutlinedInput-root': {
                  color: '#e0e0e0',
                  '& fieldset': { borderColor: '#444' },
                  '&:hover fieldset': { borderColor: '#ff6b6b' },
                  '&.Mui-focused fieldset': { borderColor: '#ff6b6b' },
                },
                '& .MuiInputLabel-root': { color: '#b0b0b0' },
                '& .MuiInputLabel-root.Mui-focused': { color: '#ff6b6b' },
              }}
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
                    <Lock color="action" sx={{ color: '#b0b0b0' }} />
                  </InputAdornment>
                ),
                endAdornment: (
                  <InputAdornment position="end">
                    <IconButton
                      onClick={handleClickShowPassword}
                      edge="end"
                      aria-label="toggle password visibility"
                      sx={{ color: '#b0b0b0' }}
                    >
                      {showPassword ? <VisibilityOff /> : <Visibility />}
                    </IconButton>
                  </InputAdornment>
                ),
              }}
              sx={{
                mb: 1,
                '& .MuiOutlinedInput-root': {
                  color: '#e0e0e0',
                  '& fieldset': { borderColor: '#444' },
                  '&:hover fieldset': { borderColor: '#ff6b6b' },
                  '&.Mui-focused fieldset': { borderColor: '#ff6b6b' },
                },
                '& .MuiInputLabel-root': { color: '#b0b0b0' },
                '& .MuiInputLabel-root.Mui-focused': { color: '#ff6b6b' },
              }}
            />

            <Box sx={{ textAlign: 'right', mb: 3 }}>
              <Link
                href="#"
                variant="body2"
                sx={{
                  color: '#ff6b6b',
                  textDecoration: 'none',
                  '&:hover': { 
                    textDecoration: 'underline',
                    color: '#ff5252',
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
                bgcolor: '#ff6b6b',
                color: '#fff',
                '&:hover': {
                  bgcolor: '#ff5252',
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

            <Divider sx={{ my: 2, bgcolor: '#444' }}>
              <Typography variant="body2" sx={{ color: '#b0b0b0' }}>
                ou
              </Typography>
            </Divider>

            <Box sx={{ textAlign: 'center' }}>
              <Typography variant="body2" sx={{ color: '#b0b0b0' }}>
                Não tem uma conta?{' '}
                <Link
                  href="#"
                  sx={{
                    color: '#ff6b6b',
                    fontWeight: 600,
                    textDecoration: 'none',
                    '&:hover': { 
                      textDecoration: 'underline',
                      color: '#ff5252',
                    },
                  }}
                >
                  Cadastre-se
                </Link>
              </Typography>
            </Box>
          </form>
        </Box>
      </Container>
    </Box>
  );
}
